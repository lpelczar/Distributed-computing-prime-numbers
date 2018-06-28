package models;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RangeTest {

    @Test
    void test_ThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Range(BigInteger.TEN, BigInteger.ONE);
        });
    }

    @Test
    void divisionTest() {
        BigInteger min = new BigInteger("10000");
        BigInteger max = new BigInteger("30000");

        Range range = new Range(min, max);
        List<Range> ranges = range.divideRanges(4);

        String expected = "[10000:14999, 15000:19999, 20000:24999, 25000:30000]";
        assertEquals(expected, ranges.toString());

    }

    @Test
    void edgeDivision2Test() {
        BigInteger min = new BigInteger("10000");
        BigInteger max = new BigInteger("33333");
        Range range = new Range(min, max);
        List<Range> ranges = range.divideRanges(5);

        String expected = "[10000:14665, 14666:19331, 19332:23997, 23998:28663, 28664:33333]";
        assertEquals(expected, ranges.toString());
    }
}