import java.util.regex.Pattern;

public class Checker {
    private static final String REGEXP = "(?=\\{)\\{[a-fA-F0-9]{8}(-[a-fA-F0-9]{4}){3}-[a-fA-F0-9]{12}}|[a-fA-F0-9]{8}(-[a-fA-F0-9]{4}){3}-[a-fA-F0-9]{12}";

    public static boolean checkGuid(String guid) {
        return Pattern.matches(REGEXP, guid);
    }
}
