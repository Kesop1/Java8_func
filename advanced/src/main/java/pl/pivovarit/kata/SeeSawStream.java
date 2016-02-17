package pl.pivovarit.kata;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.iterate;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.junit.Test;

public class SeeSawStream {
    /**
     * Write a function that accepts two integers(init value and delta value) and returns infinite stream of
     * numbers that "seesaw" around the init point.
     * For example: seesaw(5, 1) should return an infinite stream: 5, 6, 4, 7, 3, 8, 2 and so on
     *
     * @throws Exception
     */
    @Test
    public void SeeSaw() throws Exception {
        // given
        final BiFunction<Integer, Integer, Stream<Integer>> seesaw = SeeSawStream::seesawStream;

        // when

        final List<Integer> result = seesaw.apply(5, 1).limit(7).collect(toList());

        // then
        assertThat(result).containsExactly(5, 6, 4, 7, 3, 8, 2);

    }

    /**
     * TODO: IMPLEMENT THIS
     */
    public static Stream<Integer> seesawStream(final int init, final int d) {
        return iterate(init, c -> c > init ? 2 * init - c : 2 * init - c + d);
    }
}
