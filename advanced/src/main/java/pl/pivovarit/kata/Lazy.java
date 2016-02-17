package pl.pivovarit.kata;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.touk.throwing.ThrowingRunnable.unchecked;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.junit.Test;

import com.jayway.awaitility.Awaitility;

public class Lazy {
    private static final int MAGIC_NUMBER = 666;

    /**
     * Make this compute before we die.
     * You can change the "when" section
     */
    @Test
    public void lazyInit() {
        // when

        // then
        Awaitility.await()
                .atMost(1, TimeUnit.SECONDS)
                .until(() -> assertThat(theBlackBox(15, () -> reallyLongComputation())).isEqualTo(MAGIC_NUMBER));
    }


    /**
     * Do not even touch this.
     */
    private int reallyLongComputation() {
        unchecked(() -> Thread.sleep(Long.MAX_VALUE)).run();
        return 42;
    }

    /**
     * You can change this as long the original functionality is preserved.
     */
    private static int theBlackBox(final int someParam, final Supplier<Integer> input) {
        return someParam < 3 ? input.get() : MAGIC_NUMBER;
    }
}
