package models;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RangeTest {

    @Test
    void divisionTest() {
        BigInteger min = new BigInteger("10000");
        BigInteger max = new BigInteger("30000");

        Range range = new Range(min, max);
        List<Range> ranges = range.divideRanges(new BigInteger("4"));

        String expected = "[10000:15000, 15000:20000, 20000:25000, 25000:30000]";
        assertEquals(expected, ranges.toString());

    }
}