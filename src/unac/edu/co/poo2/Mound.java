package unac.edu.co.poo2;

public class Mound {
    static final int TAMINI = 20;
    private int numElem;
    private Comparator[] v;

    public Mound() {
        numElem = 0;
        v = new Comparator[TAMINI];
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

    private boolean moundfull() {
        return (numElem == v.length);
    }

    private void enlarge() {
        Comparator[] anteriorV = v;
        v = new Comparator[numElem + TAMINI];
        for (int j = 0; j < numElem; j++)
            v[j] = anteriorV[j];
    }
    private void floatUp(int i) {
        Comparator newKey = v[i];
        while ((i > 0) && (v[padre(i)].mayorQue(newKey))) {
            v[i] = v[padre(i)]; // baja el padre al hueco
            i = padre(i); // sube un nivel en el árbol
        }
        v[i] = newKey; // sitúa la clave en su posición
    }



    public void insert(Comparator key) {
        if (moundfull()) {
            enlarge();
        }
        v[numElem] = key;
        floatUp(numElem);
        numElem++;
    }

    public Comparator buscarMinimo() throws Exception {
        if (isEmpty())
            throw new Exception("Acceso a montículo vacío");
        return v[0];
    }

    public boolean isEmpty() {
        return numElem == 0;
    }


    public void criba(int raiz) {
        boolean esMonticulo;
        int hijo;
        esMonticulo = false;
        while ((raiz < numElem / 2) && !esMonticulo) {
            // determina el índice del hijo menor
            if (ChildL(raiz) == (numElem - 1)) // único descendiente
                hijo = ChildL(raiz);
            else {
                if (v[ChildL(raiz)].menorQue(v[ChildR(raiz)]))
                    hijo = ChildL(raiz);
                else
                    hijo = ChildR(raiz);
            }
            // compara raiz con el menor de los hijos
            if (v[hijo].menorQue(v[raiz])) {
                Comparator t = v[raiz];
                v[raiz] = v[hijo];
                v[hijo] = t;
                raiz = hijo; /* continua por la rama de claves mínimas */
            } else
                esMonticulo = true;
        }
    }

    public Comparator eliminarMinimo() throws Exception {
        if (isEmpty())
            throw new Exception("Acceso a montículo vacío");
        Comparator menor;
        menor = v[0];
        v[0] = v[numElem - 1];
        criba(0);
        numElem--;
        return menor;
    }


    public static void criba2(Comparator v[], int raiz, int ultimo) {
        boolean esMonticulo;
        int hijo;
        int numElem = ultimo + 1;
        esMonticulo = false;
        while ((raiz < numElem / 2) && !esMonticulo) {
            // determina el índice del hijo mayor
            if (Mound.ChildL(raiz) == (numElem - 1))
                hijo = Mound.ChildL(raiz);
            else {
                if (v[Mound.ChildL(raiz)].mayorQue(v[Mound.ChildR(raiz)]))
                    hijo = Mound.ChildL(raiz);
                else
                    hijo = Mound.ChildR(raiz);
            }
            // compara raiz con el mayor de los hijos
            if (v[hijo].mayorQue(v[raiz])) {
                Comparator t = v[raiz];
                v[raiz] = v[hijo];
                v[hijo] = t;
                raiz = hijo; /* continua por la rama de claves máximas */
            } else
                esMonticulo = true;
        }
    }

    public static void ordenacionMonticulo(Comparator v[], int n) {
        int j;
        for (j = n / 2; j >= 0; j--)
            criba2(v, j, n - 1);
        for (j = n - 1; j >= 1; j--) {
            Comparator t;
            t = v[0];
            v[0] = v[j];
            v[j] = t;
            criba2(v, 0, j - 1);
        }
    }
}
