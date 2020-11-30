import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Random r = new Random();
        int num = r.nextInt(20) + 1;

        int[][] array = new int[5][5];
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) array[i][j] = r.nextInt(10) + 1;
        }
        for (int i = 0; i < 5; i++) tasks.add(new Task(i, num, array));


        ThreadPoolExecutor fixedThreadPoolExecutor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try {
            Buscador buscador = fixedThreadPoolExecutor.invokeAny(tasks);
            System.out.printf("Se ha encentrado en la posicion %d - %d\n", buscador.getIndex1(), buscador.getIndex2());
        } catch (InterruptedException ignored) {
        } catch (ExecutionException e) {
            System.out.println("Nadie tiene el valor");
        } finally {
            System.out.println("Numero generado: " + num);
            Arrays.stream(array).forEach(Main::showAndSpace);
            fixedThreadPoolExecutor.shutdown();
        }
    }

    private static void mostrar(int index2) {
        if (index2 > 9) {
            System.out.printf("| %d", index2);
        } else {
            System.out.printf("| %d ", index2);
        }
    }

    private static void showAndSpace(int[] index1) {
        Arrays.stream(index1).forEach(Main::mostrar);
        System.out.println();
    }
}
