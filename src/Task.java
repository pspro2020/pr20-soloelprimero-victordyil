import java.util.Random;
import java.util.concurrent.Callable;

public class Task implements Callable<Buscador>, Buscador {
    private final int index1;
    private final int numeroBusqueda;
    private final int[][] arrayPrincipal;

    private int index2;
    public Task(int index1, int numeroBusqueda, int[][] arrayPrincipal) {
        this.index1 = index1;
        this.numeroBusqueda = numeroBusqueda;
        this.arrayPrincipal = arrayPrincipal;
    }

    @Override
    public int getIndex1() {
        return index1;
    }

    @Override
    public int getIndex2() {
        return index2;
    }

    private boolean buscador(int pos) throws InterruptedException {
        Random r = new Random();
        Thread.sleep(r.nextInt(500)+501);
        return arrayPrincipal[index1][pos] == numeroBusqueda;
    }

    @Override
    public Buscador call() throws Exception {
        boolean continuar = true;
        int indice = 0;
        do {
            if (buscador(indice)) {
                index2 = indice;
                continuar = false;

            } else indice++;
            if (indice > 4) throw new RuntimeException("No se encuentraen la posicion " + index1);
        } while (continuar);
        return this;
    }
}

interface Buscador {
    int getIndex1();
    int getIndex2();
}
