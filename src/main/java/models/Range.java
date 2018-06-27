package models;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Range {

    private BigInteger min;
    private BigInteger max;

    public Range(BigInteger min, BigInteger max) {
        this.min = min;
        this.max = max;
    }

    public List<Range> divideRanges(BigInteger number) {

        BigInteger fullRange = max.subtract(min);
        BigInteger dividedRange = fullRange.divide(number);

        List<Range> ranges = new ArrayList<>();

        BigInteger minValue = min;
        while (minValue.compareTo(max) <= -1) {
            Range range = new Range(minValue, minValue = minValue.add(dividedRange));
            ranges.add(range);
        }

        if (minValue.compareTo(max) < 0) {
            ranges.get(ranges.size()).setMax(max);
        }

        return ranges;
    }

    public void setMax(BigInteger max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return min + ":" + max;
    }
}
