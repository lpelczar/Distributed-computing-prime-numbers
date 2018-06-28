package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumericCheckerTest {

    @Test
    void test_PassEmptyString(){

        String empty = "";
        assertFalse(NumericChecker.isNumeric(empty));
    }

    @Test
    void test_PassNonNumricValues(){

        String word = "blabla123";
        assertFalse(NumericChecker.isNumeric(word));
    }

}