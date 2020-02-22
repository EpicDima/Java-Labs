import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Runner {
    public static void main(String[] args) {

        System.out.println("Вариант №7. Реализованы функции сложения, вычитания и умножения комплексных чисел.\n");

        int numberOfElements = 10;
        int queueLength = 3;

        List<Complex> inputList1 = new ArrayList<>(numberOfElements);
        List<Complex> inputList2 = new ArrayList<>(numberOfElements);
        Random random = new Random();
        for (int i = 0; i < numberOfElements; i++) {
            inputList1.add(new Complex(random.nextDouble(), random.nextDouble()));
            inputList2.add(new Complex(random.nextDouble(), random.nextDouble()));
        }

        SynchronizedQueue<Complex> queue = new SynchronizedQueue<>(queueLength);

        Thread input1 = new Thread(() -> {
            for (int i = 0; i < numberOfElements; i++) {
                if (!queue.put(inputList1.get(i).add(inputList2.get(i)))) {
                    i--;
                }
            }
        });

        Thread input2 = new Thread(() -> {
            for (int i = 0; i < numberOfElements; i++) {
                if (!queue.put(inputList1.get(i).sub(inputList2.get(i)))) {
                    i--;
                }
            }
        });

        Thread output = new Thread(() -> {
            for (int i = 0; i < 2 * numberOfElements; i++) {
                if (!queue.isEmpty()) {
                    System.out.println(queue.get());
                } else {
                    i--;
                }
            }
        });

        input1.start();
        input2.start();
        output.start();

        try {
            input1.join();
            input2.join();
            output.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            input1.interrupt();
            input2.interrupt();
            output.interrupt();
        }
    }
}
