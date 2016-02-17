package pl.pivovarit.ninetynine;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

public class Exercise_1_General {

    /**
     * https://github.com/spring-projects/spring-data-examples/tree/master/jpa/java8
     */


    /**
     * P01 (**) Uppercase all strings in a list
     */
    @Test
    public void P01() throws Exception {

        // given
        final List<String> input = asList("Marek", "Grzegorz", "Vladimir");

        // when
        final List<String> result = input.stream()
                .map(String::toUpperCase)
                .collect(toList());

        // then
        assertThat(result).containsExactly("MAREK", "GRZEGORZ", "VLADIMIR");
    }

    /**
     * P02 (**) Uppercase all list elements and discard names containing less than 6 characters
     */
    @Test
    public void P02() throws Exception {

        // given
        final List<String> input = asList("Marek", "Grzegorz", "Vladimir");

        // when
        final List<String> result = input.stream()
                .map(String::toUpperCase)
                .filter(s -> s.length() >= 6)
                .collect(toList());

        // then
        assertThat(result).containsExactly("GRZEGORZ", "VLADIMIR");
    }

    /**
     * P03 (**) Find the longest name
     */
    @Test
    public void P03() throws Exception {

        // given
        final List<String> input = asList("Marek", "Grzegorz", "Vlad");

        // when
        final String result = input.stream()
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(NoSuchElementException::new);

        // then
        assertThat(result).isEqualTo("Grzegorz");
    }

    /**
     * P04 (**) Flatten a nested list structure.
     */
    @Test
    public void P04() throws Exception {

        // given
        final List<List<Integer>> input = asList(asList(1, 2), asList(3, 4), asList(5, 6));

        // when
        final List<Integer> result = input.stream()
                .flatMap(Collection::stream)
                .collect(toList());

        // then
        assertThat(result).containsExactly(1, 2, 3, 4, 5, 6);
    }

    /**
     * P05 (**) Eliminate duplicates of list elements.
     */
    @Test
    public void P05() throws Exception {

        // given
        final List<Integer> input = asList(1, 2, 2, 3, 3, 3, 5, 5, 5, 6, 4);

        // when
        final List<Integer> result = input.stream()
                .distinct()
                .collect(toList());

        // then
        assertThat(result).doesNotHaveDuplicates();
    }

    /**
     * P06 (*) Duplicate the elements of a list.
     */
    @Test
    public void P06() throws Exception {

        // given
        final List<Integer> input = asList(1, 2, 3, 4);

        // when
        final List<Integer> result = input.stream()
                .flatMap(i -> Stream.of(i, i))
                .collect(toList());

        // then
        assertThat(result).containsExactly(1, 1, 2, 2, 3, 3, 4, 4);
    }

    /**
     * P07 (**) Duplicate the elements of a list a given number of times.
     */
    @Test
    public void P07() throws Exception {

        // given
        final List<Integer> input = asList(1, 2, 3, 4);
        final int givenNumberOfTimes = 3;

        // when
        final List<Integer> result = input.stream()
                .flatMap(i -> Stream.iterate(i, UnaryOperator.identity()).limit(givenNumberOfTimes))
                .collect(toList());

        // then
        assertThat(result).containsExactly(1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4);
    }

    /**
     * Create a stream only with multiples of 3, starting from 0, size of 10, by using {@link Stream#iterate}
     */
    @Test
    public void P08() {
        // when
        final List<Integer> result = Stream.iterate(0, ele -> ele + 3).limit(10).collect(toList());

        // then
        assertThat(result).containsOnly(0, 3, 6, 9, 12, 15, 18, 21, 24, 27);
    }

    /**
     * P08 (***) Rotate a list N places to the left.
     * @throws Exception
     */
    @Test
    public void P09() throws Exception {

        // given
        final List<Integer> input = asList(1, 2, 3, 4);
        final int N = 3;

        // when
        final List<Integer> result = Stream.concat(input.stream(), input.stream())
                .skip(N % input.size())
                .limit(input.size())
                .collect(toList());

        // then
        assertThat(result).containsExactly(4, 1, 2, 3);
    }

    /**
     * P09 (*) Create a list containing all integers within a given range.
     */
    @Test
    public void P10() throws Exception {

        // given
        final int rangeStart = 4;
        final int rangeEndExclusive = 7;

        // when
        final List<Integer> result = IntStream.range(rangeStart, rangeEndExclusive)
                .boxed()
                .collect(toList());

        // then
        assertThat(result).containsExactly(4, 5, 6);
    }

    /**
     * P10 (*) Lotto: Draw N different random numbers from the set 1..M.
     */
    @Test
    public void P11() throws Exception {

        // given
        final int N = 6;
        final int M = 49;
        final Random random = new Random();

        // when
        final List<Integer> result = random.ints(9, M)
                .boxed()
                .limit(N)
                .collect(toList());

        // then
        assertThat(result)
                .isSubsetOf(IntStream.rangeClosed(0, M).boxed().collect(toList()))
                .hasSize(N);
    }

    /**
     * P11 (**) Sorting a list of lists according to length of sublists.
     */
    @Test
    public void P12() throws Exception {
        // given
        final List<List<Integer>> input = asList(asList(1, 2, 3), asList(2), asList(3, 2));

        // when
        final List<List<Integer>> result = input.stream()
                .sorted(Comparator.comparing(List::size))
                .collect(toList());

        // then
        assertThat(result.get(0).size()).isEqualTo(1);
        assertThat(result.get(1).size()).isEqualTo(2);
        assertThat(result.get(2).size()).isEqualTo(3);
    }

    /**
     * P12 (**) Check if all doubles sum up to 100, if no throw an exception
     */
    @Test(expected = NoSuchElementException.class)
    public void P13() throws Exception {
        // given
        final List<Double> input = Arrays.asList(5d, 6d, 7d);

        // when
        input.stream()
                .reduce(Double::sum)
                .filter(sum -> sum == 100)
                .orElseThrow(NoSuchElementException::new);
    }

    /**
     * P14 Create a stream of sums of pair of consecutive elements.
     * [1, 2, 3, 4] -> [3, 5, 7]
     * **hint** org.apache.commons.lang3.tuple.Pair
     */
    @Test
    public void P14() throws Exception {
        // given
        final List<Integer> input = asList(1, 2, 3, 4);

        // when
        final List<Integer> result = IntStream.range(0, input.size() - 1)
                .mapToObj(i -> Pair.of(input.get(i), input.get(i + 1)))
                .map(pair -> pair.getLeft() + pair.getRight())
                .collect(Collectors.toList());

        // then
        assertThat(result)
                .hasSize(3)
                .containsExactly(3, 5, 7);
    }

}


