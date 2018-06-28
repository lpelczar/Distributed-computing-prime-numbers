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

    @Test
    void test_BigRangeWithPrimes(){
        Range range = new Range(new BigInteger("46219"), new BigInteger("47419"));
        this.primeCalculator = new PrimeCalculator(range);
        Result testResult = this.primeCalculator.calculate();
        assertEquals("[46219, 46229, 46237, 46261, 46271, 46273, 46279, 46301, 46307, 46309, " +
                        "46327, 46337, 46349, 46351, 46381, 46399, 46411, 46439, 46441, 46447, " +
                        "46451, 46457, 46471, 46477, 46489, 46499, 46507, 46511, 46523, 46549, " +
                        "46559, 46567, 46573, 46589, 46591, 46601, 46619, 46633, 46639, 46643, 46649, " +
                        "46663, 46679, 46681, 46687, 46691, 46703, 46723, 46727, 46747, " +
                        "46751, 46757, 46769, 46771, 46807, 46811, 46817, 46819, 46829, 46831, "+
                        "46853, 46861, 46867, 46877, 46889, 46901, 46919, 46933, 46957, 46993, " +
                        "46997, 47017, 47041, 47051, 47057, 47059, 47087, 47093, 47111, 47119, " +
                        "47123, 47129, 47137, 47143, 47147, 47149, 47161, 47189, 47207, 47221, " +
                        "47237, 47251, 47269, 47279, 47287, 47293, 47297, 47303, 47309, 47317, "+
                        "47339, 47351, 47353, 47363, 47381, 47387, 47389, 47407, 47417, 47419]",
                testResult.toString());
    }


}