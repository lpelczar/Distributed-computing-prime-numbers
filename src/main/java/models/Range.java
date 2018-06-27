package models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Range implements Serializable {

    private BigInteger min;
    private BigInteger max;

    public Range(BigInteger min, BigInteger max) {
        if (min.compareTo(max) >= 1) {
            throw new IllegalArgumentException("Min cannot be greater than max!");
        }

        this.min = min;
        this.max = max;
    }

    public List<Range> divideRanges(Integer number) {

        BigInteger fullRange = max.subtract(min);
        BigInteger dividedRange = fullRange.divide(BigInteger.valueOf(number));

        List<Range> ranges = new ArrayList<>();

        BigInteger minValue = min;

        for (int i = 1; i <= number; i++) {
            if (i != 1) {
                minValue = minValue.add(new BigInteger("1"));
            }

            Range range = new Range(minValue, minValue = minValue.add(dividedRange).subtract(new BigInteger("1")));
            ranges.add(range);
        }

        if (minValue.compareTo(max) < 0) {
            ranges.get(ranges.size() - 1).setMax(max);
        }

        return ranges;
    }

    public void setMax(BigInteger max) {
        this.max = max;
    }

    public BigInteger getMin() {
        return min;
    }

    public BigInteger getMax() {
        return max;
    }

    @Override
    public String toString() {
        return min + ":" + max;
    }
}
