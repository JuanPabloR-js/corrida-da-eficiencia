public class Buscas {

    public static int linear(int[] vetor, int alvo, Metricas m) {
        //Laço que percorre o vetor, verificando um elemento por vez.
        for (int i = 0; i < vetor.length; i++) {
            m.comparacoes++;

            if (vetor[i] == alvo) {
                return i; //Retorna a posição de elemento encontrado.
            }
        }
        return -1;
    }

    // O vetor precisa estar ordenado.
    public static int binaria(int[] vetor, int alvo, Metricas m) {
        int inicio = 0;
        int fim = vetor.length - 1;

        while (inicio <= fim) {
            int meio = inicio + (fim - inicio) / 2;

            m.comparacoes++;
            if (vetor[meio] == alvo) {
                return meio;
            }

            //Descarta a metade que não pode conter alvo.
            m.comparacoes++;
            if (vetor[meio] < alvo) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }
        return -1;
    }
}
