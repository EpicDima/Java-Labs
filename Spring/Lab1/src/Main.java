import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        new Menu().openMenu();
//        input1();
//        input2();
    }

    public static void input1() {
        InputStreamReader inputReader = new InputStreamReader(System.in);
        while (true) {
            char ch = 0;
            try {
                ch = (char) inputReader.read();
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
            System.out.println(ch);
        }
    }

    public static void input2() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println(bufferedReader.readLine());
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
        }
    }
}
