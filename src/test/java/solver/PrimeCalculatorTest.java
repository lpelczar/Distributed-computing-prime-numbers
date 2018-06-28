package solver;

import models.Range;
import models.Result;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class PrimeCalculatorTest {

    private PrimeCalculator primeCalculator;

    @Test
    void test_OneIsNotPrime(){
        Range range = new Range(BigInteger.ZERO, BigInteger.ONE);
        this.primeCalculator = new PrimeCalculator(range);

        Result testResult = this.primeCalculator.calculate();
        assertEquals(0, testResult.getNumbers().size());

    }

    @Test
    void test_TenFirstPrimes(){
        Range range = new Range(BigInteger.ZERO, BigInteger.TEN);
        this.primeCalculator = new PrimeCalculator(range);
        Result testResult = this.primeCalculator.calculate();
        assertEquals("[2, 3, 5, 7]", testResult.toString());
    }

    @Test
    void test_NegativeDigits(){
        Range range = new Range(new BigInteger("-100"), BigInteger.ONE);
        this.primeCalculator = new PrimeCalculator(range);
        Result testResult = this.primeCalculator.calculate();
        assertEquals(0, testResult.getNumbers().size());
    }

    @Test
    void test_RangeWithoutPrimes(){
        Range range = new Range(new BigInteger("104724"), new BigInteger("104728"));
        this.primeCalculator = new PrimeCalculator(range);
        Result testresult = this.primeCalculator.calculate();
        assertEquals(0, testresult.getNumbers().size());
    }
    

}