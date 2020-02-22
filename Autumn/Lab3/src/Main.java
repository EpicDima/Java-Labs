import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Введите строку, для проверки на GUID");
            System.out.println("Введите 0, если хотите выйти");
            String s = in.nextLine();
            if (s.equals("0")) {
                exit = true;
            } else {
                if (Checker.checkGuid(s)) {
                    System.out.println("Введённая строка является GUID\n");
                } else {
                    System.out.println("Введённая строка НЕ является GUID\n");
                }
            }
        }
    }
}
