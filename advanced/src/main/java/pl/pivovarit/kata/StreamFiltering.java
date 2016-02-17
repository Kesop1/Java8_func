package pl.pivovarit.kata;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamFiltering {

    /**
     * Write a function that accepts a collection of predicates and returns a Stream filtered using this predicates
     */
    @Test
    public void StreamDynamicFiltering() throws Exception {
        // given
        final List<Predicate<Integer>> rules = Arrays.asList(
                i -> i % 2 == 1,
                i -> i > 10,
                i -> i < 20);

        final List<Integer> values = IntStream.range(0, 50).boxed().collect(toList());

        final BiFunction<Collection<Integer>, Collection<Predicate<Integer>>, Stream<Integer>> filteredStream =
                (data, predicates) -> filterWith(data.stream(), predicates);

        // when

        final List<Integer> result = filteredStream.apply(values, rules).collect(toList());

        // then
        assertThat(result).containsExactly(11, 13, 15, 17, 19);
    }

    /**
     * TODO: IMPLEMENT THIS
     */
    private <T> Stream<T> filterWith(final Stream<T> stream, final Collection<Predicate<T>> rules) {
        return rules.stream()
                .reduce(Predicate::and)
                .map(stream::filter)
                .orElse(stream);
    }

}
