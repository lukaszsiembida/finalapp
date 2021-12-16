package pl.sda.lambda_and_generic_type;

import org.junit.jupiter.api.Test;
import pl.sda.BigDecimalTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class LambdaExample {

    List<String> pets = Arrays.asList("hamster", "pig", "dog", " dog", null);
    Predicate<String> notNullPredicate = p -> p != null;
   Function<String, String> trimFunction = p -> p.trim();

    @Test
    void basicLambdas(){
        pets.stream()
                .filter(notNullPredicate)
                .map(trimFunction)
                .distinct()
                .sorted()
                .forEach(p -> System.out.println(p));
    }

    @FunctionalInterface // pełni rolę ciecia XD nie można dać 2 funkcji
    public interface MathOperation {
        int calculate(int x, int y);
    }

    @FunctionalInterface
    public interface SuperMathOperation<T> {
        T calculate(T x, T y);
    }

    @FunctionalInterface
    public interface TurboMathOperation<X, Y, RESULT> {
        RESULT calculate(X x, Y y);
    }

    @Test
    void calculatorTest(){
        MathOperation adder = (a, b) -> a+b;
        System.out.println(adder.calculate(2,3));;
    }

    @Test
    void superCalculatorTest(){
        SuperMathOperation<Float> adderFloat = (a, b) -> a+b;
        System.out.println(adderFloat.calculate(2.3f,3.4f));;
    }

    @Test
    void turboCalculatorTest(){
        TurboMathOperation<String, Integer, BigDecimal> adder = (a, b) ->
                BigDecimal.valueOf(Double.parseDouble(a)).add(BigDecimal.valueOf(b));
        System.out.println(adder.calculate("1.4",1));
    }
}
