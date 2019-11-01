import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        int n = 10;
        List<Integer> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(random.nextInt());
        }

        for (Integer integer : list) {
            System.out.format("%d ", integer);
        }

        System.out.println("\n");
        for (int i = list.size(); i > 0; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    list.set(j,     list.get(j) ^ list.get(j + 1));
                    list.set(j + 1, list.get(j) ^ list.get(j + 1));
                    list.set(j,     list.get(j) ^ list.get(j + 1));
                }
            }
        }

        for (Integer integer : list) {
            System.out.format("%d ", integer);
        }
    }
}
