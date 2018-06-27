package models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;

public class Result implements Serializable {

    private Set<BigInteger> numbers;

    public Result(Set<BigInteger> numbers) {
        this.numbers = numbers;
    }

    public Set<BigInteger> getNumbers() {
        return numbers;
    }
}
