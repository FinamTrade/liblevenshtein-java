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
import java.util.stream.StreamSupport;

public class IntegerTransducerBenchmark {
    private static final int DICTIONARY_SIZE = 2_000_000;

    @State(Scope.Thread)
    public static class Seq {
        final ITransducer<Candidate> transducer;
        final SortedDawg dictionary;

        public Seq() {
            dictionary = new SortedDawg();
            for (int i = 0; i < DICTIONARY_SIZE; i++) {
                dictionary.add(intTerm(i, String.valueOf(DICTIONARY_SIZE).length()));
            }
            dictionary.finish();
            transducer = new TransducerBuilder()
                    .algorithm(Algorithm.TRANSPOSITION)
                    .defaultMaxDistance(2)
                    .dictionary(dictionary)
                    .build();
        }

        private static String intTerm(int t, int pad) {
            StringBuilder s = new StringBuilder(Integer.toString(t));
            while (s.length() < pad) {
                s.insert(0, '0');
            }
            return s.toString();
        }
    }

    @Benchmark
    public void measureIntegerTransducer_Distance0(Seq seq, Blackhole bh) {
        measureIntegerTransducer(seq, bh, 0);
    }

    @Benchmark
    public void measureIntegerTransducer_Distance1(Seq seq, Blackhole bh) {
        measureIntegerTransducer(seq, bh, 1);
    }

    @Benchmark
    public void measureIntegerTransducer_Distance2(Seq seq, Blackhole bh) {
        measureIntegerTransducer(seq, bh, 2);
    }

    private void measureIntegerTransducer(Seq seq, Blackhole bh, int maxDistance) {
        StreamSupport.stream(
                seq.transducer.transduce("987654", maxDistance).spliterator(), false
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

        while (iter.hasNext() && k < 1_990_000) {
            iter.next();
            k++;
        }

        System.out.println("...");
        while (iter.hasNext() && k < 2_000_000) {
            System.out.println(iter.next());
            k++;
        }

        System.out.println();
        System.out.println("Total: " + k);
    }
}
