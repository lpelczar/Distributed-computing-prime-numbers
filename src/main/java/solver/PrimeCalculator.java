package solver;

import models.Range;
import models.Result;

import java.math.BigInteger;
import java.util.Set;
import java.util.TreeSet;

public class PrimeCalculator {

    private Range range;

    public PrimeCalculator(Range range) {
        this.range = range;
    }

    public Result calculate() {

        Set<BigInteger> resultSet = new TreeSet<>();

        for (BigInteger value = range.getMin(); value.compareTo(range.getMax()) <= 0; value = value.add(new BigInteger("1"))) {
            if (isPrime(value)) {
                resultSet.add(value);
            }
        }

        return new Result(resultSet);
    }

    private boolean isPrime(BigInteger bigPrime){
        if (bigPrime.compareTo(BigInteger.ONE) == 0){
            return false;
        }
        if (bigPrime.compareTo(new BigInteger("2")) == 0 ||
            bigPrime.compareTo(new BigInteger("3")) == 0) {
            return true;
        }
        if (BigInteger.ZERO.equals(bigPrime.mod(new BigInteger("2")))) {
            return false;
        }

        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(bigPrime) < 1; i = i.add(new BigInteger("2")) ) {
            if (BigInteger.ZERO.equals(bigPrime.mod(i))) {
                return  false;
            }
        }
        return true;
    }
}
