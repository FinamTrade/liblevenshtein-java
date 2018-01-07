package eee;

import com.github.liblevenshtein.collection.dictionary.SortedDawg;
import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.github.liblevenshtein.transducer.factory.TransducerBuilder;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:ruslan.sennov@gmail.com">Ruslan Sennov</a>
 */
public class MyBenchmark {

    @State(Scope.Thread)
    public static class Seq {

        final SortedDawg dictionary;
        final ITransducer<Candidate> transducer;

        public Seq() {
            dictionary = new SortedDawg();
            for (int i = 0; i < 1000000; i++) {
                dictionary.add(intTerm(i, 6));
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
    public void it(Seq seq, Blackhole bh) {
        seq.transducer.transduce("111111").forEach(bh::consume);
    }

    public static void main(String[] args) {
        Seq seq = new Seq();
        List<String> list = new ArrayList<>();
        seq.transducer.transduce("111111").forEach(c -> list.add(c.term()));
        System.out.println(list.size());
        for (int i = 0; i < 10 && i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
