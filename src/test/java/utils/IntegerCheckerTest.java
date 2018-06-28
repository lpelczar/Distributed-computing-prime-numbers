package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerCheckerTest {

    @Test
    void test_PassEmptyString(){

        String empty = "";
        assertFalse(IntegerChecker.isInteger(empty));
    }

    @Test
    void test_PassNonNumricValues(){

        String word = "blabla123";
        assertFalse(IntegerChecker.isInteger(word));
    }

}