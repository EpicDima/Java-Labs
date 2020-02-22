import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CheckerTest {

    @Test
    void checkGuid() {
        Assertions.assertTrue(Checker.checkGuid("e02fd0e4-00fd-090A-ca30-0d00a0038ba0"));
        Assertions.assertTrue(Checker.checkGuid("{6F9619FF-8B86-D011-B42D-00CF4FC964FF}"));
        Assertions.assertTrue(Checker.checkGuid("6F9619FF-8B86-D011-B42D-00CF4FC964FF"));
        Assertions.assertTrue(Checker.checkGuid("{e02fd0e4-00fd-090A-ca30-0d00a0038ba0}"));

        Assertions.assertFalse(Checker.checkGuid(""));
        Assertions.assertFalse(Checker.checkGuid("e02fd0e400fd090Aca300d00a0038ba0"));
        Assertions.assertFalse(Checker.checkGuid("{6F9619FF-8B86-D011-B42D-00CF4FC964FF"));
        Assertions.assertFalse(Checker.checkGuid("6F9619FF-8B86-D011-B42D-00CF4FC964FF}"));
        Assertions.assertFalse(Checker.checkGuid("e02fd0e4-00fd-090A-ca30-0d00a0038ba0}"));
        Assertions.assertFalse(Checker.checkGuid("{e02fd0e4-00fd-090A-ca30-0d00a0038ba0"));
        Assertions.assertFalse(Checker.checkGuid("6F9619FF8B86D011B42D00CF4FC964FF"));
        Assertions.assertFalse(Checker.checkGuid("{6F9619FF8B86D011B42D00CF4FC964FF}"));
        Assertions.assertFalse(Checker.checkGuid("e02fd0e400fd090Aca300d00a0038ba0"));
        Assertions.assertFalse(Checker.checkGuid("e02fd0e400fd-090A-ca30-0d00a0038ba0"));
        Assertions.assertFalse(Checker.checkGuid("{6F961FF-8B86-D011-B42D-00CF4FC964FF}"));
        Assertions.assertFalse(Checker.checkGuid("6F9619FF-8B86-D01142D-00CF4FC964FF"));
        Assertions.assertFalse(Checker.checkGuid("{e02fd0e4-00fd-090Aca30-0d00a0038ba0}"));
        Assertions.assertFalse(Checker.checkGuid("{e02fd0e4-00fd-090A-Ka30-0d00a0038ba0}"));
        Assertions.assertFalse(Checker.checkGuid("{е02fd0e4-00fd-090A-Ka30-0d00a0038ba0}")); // русская буква е
    }
}