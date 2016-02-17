package pl.pivovarit.ninetynine;

import static java.util.function.Predicate.isEqual;

import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Exercise_3_Strings {

    /**
     * Write a method that removes all spaces and capitalizes every "a" letter
     * @throws Exception
     */
    @Test
    public void StringOperations() throws Exception {
        // given
        final String input = "I never saw a purple cow";

        // when
        final String result = input.chars()
                .mapToObj(i -> (char) i)
                .map(Object::toString)
                .filter(isEqual(" ").negate())
                .map(c -> c.equals("a") ? "A" : c)
                .collect(Collectors.joining());


        // then
        Assertions.assertThat(result).isEqualTo("IneversAwApurplecow");
    }
}
