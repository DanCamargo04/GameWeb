package pack;

public class Pilha {

    private static int TAM_DEFAULT = 100;
    private int topoPilha;
    private int e[];

    public Pilha(int tamanho) {
        this.e = new int[tamanho];
        this.topoPilha = -1;
    }

    public Pilha() {
        this(TAM_DEFAULT);
    }

    public boolean isEmpty() {
        if (this.topoPilha == -1)
            return true;
        else
            return false;
    }

    public boolean isFull() {
        if (this.topoPilha == this.e.length - 1)
            return true;
        else
            return false;
    }

    public void push(int e) {
        if (!this.isFull())
            this.e[++this.topoPilha] = e;
        else
            System.out.println("overflow - Estouro de Pilha");
    }

    public int pop() {
        if (!this.isEmpty())
            return this.e[this.topoPilha--];
        else {
            System.out.println("underflow - Esvaziamento de Pilha");
            return -1;
        }
    }

    public int topo() {
        if (!this.isEmpty())
            return this.e[this.topoPilha];
        else {
            System.out.println("Underlow - Esvaziamento de Pilha");
            return -1;
        }
    }

    public int totalElementos() {
        return topoPilha + 1;
    }

}