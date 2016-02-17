package pl.pivovarit.kata;


import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class CustomCollectors {

    /**
     * Implement a custom toImmutableSet() collector that collects element of any given type to ImmutableSet(Guava)
     * instance.
     */
    @Test
    public void CustomImmutableSetCollector() {
        // given
        final List<String> input = Arrays.asList("a", "b", "c", "a");

        // when
        final ImmutableSet<String> result = input.stream()
                .collect(toImmutableSet());

        // then
        Assertions.assertThat(result)
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    public static <T> ImmutableSetCollector<T> toImmutableSet() {
        return new ImmutableSetCollector<>();
    }

    /**
     * TODO: IMPLEMENT THIS
     */
    public static class ImmutableSetCollector<T> implements Collector<T, ImmutableSet.Builder<T>, ImmutableSet<T>> {

        @Override
        public Supplier<ImmutableSet.Builder<T>> supplier() {
            return ImmutableSet::builder;
        }

        @Override
        public BiConsumer<ImmutableSet.Builder<T>, T> accumulator() {
            return ImmutableSet.Builder::add;
        }

        @Override
        public BinaryOperator<ImmutableSet.Builder<T>> combiner() {
            return (left, right) -> left.addAll(right.build());
        }

        @Override
        public Function<ImmutableSet.Builder<T>, ImmutableSet<T>> finisher() {
            return ImmutableSet.Builder::build;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Sets.immutableEnumSet(Characteristics.UNORDERED, Characteristics.CONCURRENT);
        }
    }


}


