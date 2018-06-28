package solver;

import models.Range;
import models.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class PrimeCalculatorTest {

    private PrimeCalculator primeCalculator;

    @Test
    void test_OneIsNotPrime(){
        Range range = new Range(BigInteger.ZERO, BigInteger.ONE);
        this.primeCalculator = new PrimeCalculator(range);

        Result testResult = this.primeCalculator.calculate();
        assertEquals(testResult.getNumbers().size(), 0);

    }

    @Test
    void test_TenFirstPrimes(){
        Range range = new Range(BigInteger.ZERO, BigInteger.TEN);
        this.primeCalculator = new PrimeCalculator(range);
        Result testResult = this.primeCalculator.calculate();
        assertEquals(testResult.toString(), "[2, 3, 5, 7]");
    }

    @Test
    void test_NegativeDigits(){
        Range range = new Range(new BigInteger("-100"), BigInteger.ONE);
        this.primeCalculator = new PrimeCalculator(range);
        Result testResult = this.primeCalculator.calculate();
        assertEquals(testResult.getNumbers().size(), 0);
    }

}