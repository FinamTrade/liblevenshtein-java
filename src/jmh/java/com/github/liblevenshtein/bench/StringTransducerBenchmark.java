package com.github.liblevenshtein.bench;

import com.github.liblevenshtein.collection.dictionary.SortedDawg;
import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.github.liblevenshtein.transducer.factory.TransducerBuilder;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

public class StringTransducerBenchmark {

    @State(Scope.Thread)
    public static class Seq {
        final ITransducer<Candidate> transducer;
        final SortedDawg dictionary;

        public Seq() {
            dictionary = new SortedDawg();
            char[] chars = new char[]{'a','b','c','d','e','f','g','h','j','k','l','m','n','o','p','r','s','t','u','v','w','x'};
            visitPermutations(chars, 0, dictionary::add);
            dictionary.finish();
            transducer = new TransducerBuilder()
                    .algorithm(Algorithm.TRANSPOSITION)
                    .defaultMaxDistance(2)
                    .dictionary(dictionary)
                    .build();
        }

        private static void visitPermutations(char[] chars, int offset, Consumer<String> consumer) {
            if (offset == chars.length) {
                consumer.accept(new String(chars));
            } else {
                for (int i = offset; i < chars.length; i++) {
                    swap(chars, offset, i);
                    visitPermutations(chars, i + 1, consumer);
                    swap(chars, offset, i);
                }
            }
        }

        private static void swap(char[] chars, int i, int j) {
            char c = chars[i];
            chars[i] = chars[j];
            chars[j] = c;
        }
    }

    @Benchmark
    public void measureStringTransducer_Distance0(Seq seq, Blackhole bh) {
        measureStringTransducer(seq, bh, 0);
    }

    @Benchmark
    public void measureStringTransducer_Distance1(Seq seq, Blackhole bh) {
        measureStringTransducer(seq, bh, 1);
    }

    @Benchmark
    public void measureStringTransducer_Distance2(Seq seq, Blackhole bh) {
        measureStringTransducer(seq, bh, 2);
    }

    private void measureStringTransducer(Seq seq, Blackhole bh, int maxDistance) {
        StreamSupport.stream(
                seq.transducer.transduce("afcdebhgjklnmorpstuvwx", maxDistance).spliterator(), false
        ).forEach(bh::consume);
    }

    public static void main(String[] args) {
        Iterator<String> iter = new Seq().dictionary.iterator();

        int k = 0;
        while (iter.hasNext() && k < 10_000) {
            System.out.println(iter.next());
            k++;
        }

        while (iter.hasNext() && k < 150_000) {
            iter.next();
            k++;
        }

        System.out.println("...");
        while (iter.hasNext() && k < 160_000) {
            System.out.println(iter.next());
            k++;
        }

        String median = null;
        while (iter.hasNext() && k < 1_990_000) {
            if (k == 1_000_000) {
                median = iter.next();
            } else {
                iter.next();
            }
            k++;
        }

        System.out.println("...");
        while (iter.hasNext() && k < 2_000_000) {
            System.out.println(iter.next());
            k++;
        }

        System.out.println();
        System.out.println("Total: " + k);
        System.out.println("Median: " + median);
    }
}
