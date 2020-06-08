package unac.edu.co.poo2;

public class MoundAdd {
    static final int TAMINI = 20;
    private int numElem;
    private int[] v;

    public MoundAdd() {
        numElem = 0;
        v = new int[TAMINI];
    }

    public static int padre(int i) {
        return (i - 1) / 2;
    }

    public static int ChildL(int i) {
        return (2 * i + 1);
    }

    public static int ChildR(int i) {
        return (2 * i + 1) + 1;
    }

    public void insert(int key) {
        if (monticuloLleno()) {
            previous();
        }
        v[numElem] = key;
        floatUp(numElem);
        numElem++;
    }

    private boolean monticuloLleno() {
        return (numElem == v.length);
    }

    private void previous() {
        int[] previousV = v;
        v = new int[numElem + TAMINI];
        for (int j = 0; j < numElem; j++)
            v[j] = previousV[j];
    }

    private void floatUp(int i) {
        int newKey = v[i];
        while ((i > 0) && (v[padre(i)] > newKey)) {
            v[i] = v[padre(i)]; // baja el padre al hueco
            i = padre(i); // sube un nivel en el árbol
        }
        v[i] = newKey; // sitúa la clave en su posición
    }

    public int SearchMinimum() throws Exception {
        if (isEmpty())
            throw new Exception("Acceso a montículo vacío");
        return v[0];
    }

    public boolean isEmpty() {
        return numElem == 0;
    }

    public int removeMinimum() throws Exception {
        if (isEmpty())
            throw new Exception("Acceso a montículo vacío");
        int less;
        less = v[0];
        v[0] = v[numElem - 1];
        criba(0);
        numElem--;
        return less;
    }

    public void criba(int root) {
        boolean ismound;
        int child;
        ismound = false;
        while ((root < numElem / 2) && !ismound) {
            // determina el índice del child menor
            if (ChildL(root) == (numElem - 1)) // único descendiente
                child = ChildL(root);
            else {
                if (v[ChildL(root)] < v[ChildR(root)])

                    child = ChildL(root);
                else
                    child = ChildR(root);
            }
            // compara root con el menor de los hijos
            if (v[child] < v[root]) {
                int t = v[root];
                v[root] = v[child];
                v[child] = t;
                root = child; /* continua por la rama de claves mínimas */
            } else
                ismound = true;
        }
    }

    public static void criba2(int v[], int raiz, int last) {
        boolean ismound;
        int child;
        int numElem = last + 1;
        ismound = false;
        while ((raiz < numElem / 2) && !ismound) {
            // determina el índice del child mayor
            if (Mound.ChildL(raiz) == (numElem - 1))

                child = Mound.ChildL(raiz);
            else {
                if (v[Mound.ChildL(raiz)] > v[Mound.ChildR(raiz)])
                    child = Mound.ChildL(raiz);
                else
                    child = Mound.ChildR(raiz);
            }
            // compara raiz con el mayor de los hijos
            if (v[child] > v[raiz]) {
                int t = v[raiz];
                v[raiz] = v[child];
                v[child] = t;
                raiz = child; /* continua por la rama de claves máximas */
            } else
                ismound = true;
        }
    }

    public static void ordination(int v[], int n) {
        int j;
        for (j = n / 2; j >= 0; j--)
            criba2(v, j, n - 1);
        for (j = n - 1; j >= 1; j--) {
            int t;
            t = v[0];
            v[0] = v[j];
            v[j] = t;
            criba2(v, 0, j - 1);
        }
    }

    public void print() {
        System.out.print("\nMontículo: ");
        for (int i=0; i<numElem; i++) {
            System.out.print(v[i] + " ");
        }
    }

    public void order() {
        ordination( v, numElem);
    }

    public void paint(String prefix, boolean isCola, int n) {
        if (n < numElem) {
            System.out.println(prefix + (isCola ? "└---- " : "├----- ") + v[n]);
            paint(prefix + (isCola ? "    " : "|   "), false, ChildL(n));
            paint(prefix + (isCola ? "    " : "│   "), true, ChildR(n));
        }
    }

    public void paint() {
        System.out.println();
        paint("", true, 0);
    }
}
