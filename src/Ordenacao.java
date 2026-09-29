public class Ordenacao {

    public static void selectionSort(int[] vetor, Metricas m) {
        for (int i = 0; i < vetor.length - 1; i++) {
            int menor = i;

            //Procura o menor elemento na parte ainda não ordenada.
            for (int j = i + 1; j < vetor.length; j++) {
                m.comparacoes++;

                if (vetor[j] < vetor[menor]) {
                    menor = j;
                }
            }

            //Coloca o menor elemento na posição atual.
            if (menor != i) {
                int auxiliar = vetor[i];
                vetor[i] = vetor[menor];
                vetor[menor] = auxiliar;

                m.trocas++;
                //Cada troca faz duas escritas no vetor.
                m.movimentos += 2;
            }
        }
    }

    public static void mergeSort(int[] vetor, Metricas m) {
        //Vetor auxiliar usado para juntar as partes em ordem.
        int[] auxiliar = new int[vetor.length];
        dividir(vetor, auxiliar, 0, vetor.length - 1, m);
    }

    private static void dividir(int[] vetor, int[] auxiliar, int inicio, int fim, Metricas m) {
        //Uma parte com zero ou um elemento ja está ordenada.
        if (inicio >= fim) {
            return;
        }

        int meio = inicio + (fim - inicio) / 2;

        //Ordena as duas metades e depois junta os resultados.
        dividir(vetor, auxiliar, inicio, meio, m);
        dividir(vetor, auxiliar, meio + 1, fim, m);
        juntar(vetor, auxiliar, inicio, meio, fim, m);
    }

    private static void juntar(int[] vetor, int[] auxiliar, int inicio, int meio, int fim, Metricas m) {
        //Guarda os valores antes de sobrescrever o vetor original.
        for (int i = inicio; i <= fim; i++) {
            auxiliar[i] = vetor[i];
            m.movimentos++;
        }

        int esquerda = inicio;
        int direita = meio + 1;

        for (int i = inicio; i <= fim; i++) {
            if (esquerda > meio) {
                //A metade esquerda acabou: usa os valores da direita.
                vetor[i] = auxiliar[direita++];
            } else if (direita > fim) {
                //A metade da direita acabou: usa os valores da esquerda.
                vetor[i] = auxiliar[esquerda++];
            } else {
                //Escolhe o menor valor entre as duas metades.
                m.comparacoes++;

                if (auxiliar[esquerda] <= auxiliar[direita]) {
                    vetor[i] = auxiliar[esquerda++];
                } else {
                    vetor[i] = auxiliar[direita++];
                }
            }
            //Conta cada escrita de um elemento no vetor.
            m.movimentos++;
        }
    }
}
