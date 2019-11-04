import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String text = enterTextFromFile();
        String convertedText = Huffman.convert(text);
        System.out.println(text);
        System.out.println(convertedText);
    }

    private static String enterTextFromFile() {
        String text = "";
        try (Scanner file = new Scanner(new FileReader("text.txt"))) {
            text = file.useDelimiter("\\A").next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
