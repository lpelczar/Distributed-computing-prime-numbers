import java.math.BigInteger;

public class App {
    public static void main(String[] args) {
        BigInteger maxValue = new BigInteger("1111111");
        for(BigInteger value = new BigInteger("21323"); value.compareTo(maxValue) <= 0; value = value.add(new BigInteger("1"))){
            if(isPrime(value)){
                System.out.println("IT's PRIME!" + value);
            };
        }
    }

    private static boolean isPrime(BigInteger bigPrime){
        if(bigPrime.compareTo(new BigInteger("1")) == 0 || bigPrime.compareTo(new BigInteger("2")) == 0 || bigPrime.compareTo(new BigInteger("3")) == 0){
            return true;
        }
        if(BigInteger.ZERO.equals(bigPrime.mod(new BigInteger("2")))){
            return false;
        }

        for(BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(bigPrime) < 1; i = i.add(new BigInteger("2")) ){
            if(BigInteger.ZERO.equals(bigPrime.mod(i))){

                return  false;
            }
        }

        return true;
    }
}
