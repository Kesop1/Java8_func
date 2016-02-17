package pl.pivovarit.ninetynine;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Exercise_2_Primes {

    /**
     * TODO: IMPLEMENT THIS
     */
    final static IntPredicate isPrime = i -> i > 1 && IntStream.rangeClosed(2, (int) (Math.sqrt(i))).noneMatch(n -> i % n == 0);

    /**
     * P31 (**) Determine whether a given integer number is prime.
     */
    @Test
    public void Primes01() throws Exception {
        assertThat(IntStream.of(0).allMatch(isPrime)).isFalse();
        assertThat(IntStream.of(1).allMatch(isPrime)).isFalse();
        assertThat(IntStream.of(2, 3, 5, 7).allMatch(isPrime)).isTrue();
        assertThat(IntStream.of(8, 16, 39).allMatch(isPrime)).isFalse();
    }

    /**
     * Generate 10000 first prime numbers
     */
    @Test
    public void Primes02() throws Exception {
        // when
        final List<Integer> result = IntStream.iterate(0, i -> i + 1)
                .filter(isPrime)
                .limit(10000)
                .boxed()
                .collect(toList());

        // then
        Assertions.assertThat(result.stream().allMatch(Exercise_2_Primes::isPrime));
    }

    /**
     * Oldschool referential isPrime() implementation
     */
    private static boolean isPrime(long x) {
        if (x < 2) return false;

        for (long n = 2; n <= Math.sqrt(x); n++) {
            if (x % n == 0) {
                return false;
            }
        }
        return true;
    }
}
