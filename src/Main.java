import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // Tamanhos obrigatórios definidos.
        int[] tamanhos = {100, 1000, 5000, 10000, 50000, 100000};

        String[] algoritmos = {"Busca Linear", "Busca Binaria", "Selection Sort", "Merge Sort"};

        for (int tamanho : tamanhos) {
            int[] aleatorio = new int[tamanho];

            //Permite gerar os mesmos dados em outra execução.
            Random random = new Random(42 + tamanho);

            for (int i = 0; i < tamanho; i++) {
                aleatorio[i] = random.nextInt(tamanho * 10);
            }

            // Prepara uma versão ordenada dos mesmos números.
            int[] ordenado = aleatorio.clone();
            Arrays.sort(ordenado);

            // Testa as duas entradas: aleatória e ordenada.
            for (int tipo = 0; tipo < 2; tipo++) {
                String nomeTipo = tipo == 0 ? "Aleatorio" : "Ordenado";
                int[] original = tipo == 0 ? aleatorio : ordenado;

                for (int algoritmo = 0; algoritmo < 4; algoritmo++) {
                    long somaTempo = 0;
                    long somaComparacoes = 0;
                    long somaTrocas = 0;
                    long somaMovimentos = 0;

                    System.out.println("\nAlgoritmo: " + algoritmos[algoritmo]);
                    System.out.println("Tamanho: " + tamanho);
                    System.out.println("Tipo: " + nomeTipo);

                    // Quatro execuções: uma de aquecimento e três válidas.
                    for (int repeticao = 1; repeticao <= 4; repeticao++) {

                        // Cada execução recebe uma cópia nova.
                        // A busca binária sempre recebe dados ordenados,
                        // inclusive quando a entrada original é aleatória.
                        int[] vetor = algoritmo == 1 ? ordenado.clone() : original.clone();

                        // Novos contadores para não acumular execuções anteriores.
                        Metricas metricas = new Metricas();
                        int posicao = -1;

                        // Mede somente a execução e suas contagens.
                        long inicio = System.nanoTime();

                        // -1 não existe nos vetores gerados.
                        if (algoritmo == 0) {
                            posicao = Buscas.linear(vetor, -1, metricas);
                        } else if (algoritmo == 1) {
                            posicao = Buscas.binaria(vetor, -1, metricas);
                        } else if (algoritmo == 2) {
                            Ordenacao.selectionSort(vetor, metricas);
                        } else {
                            Ordenacao.mergeSort(vetor, metricas);
                        }

                        long tempo = System.nanoTime() - inicio;

                        // Valida fora do tempo medido:
                        // buscas devem retornar -1;
                        // ordenações devem produzir o vetor esperado.
                        boolean correto = algoritmo < 2 ? posicao == -1 : Arrays.equals(vetor, ordenado);

                        if (!correto) {
                            throw new IllegalStateException("Erro em " + algoritmos[algoritmo]);
                        }

                        System.out.println("Execucao " + repeticao + (repeticao == 1 ? " (aquecimento)" : "") + " | tempo: " + tempo + " ns" + " | comparacoes: " + metricas.comparacoes + " | trocas: " + metricas.trocas + " | movimentos: " + metricas.movimentos);

                        // A primeira execução não entra na média.
                        if (repeticao > 1) {
                            somaTempo += tempo;
                            somaComparacoes += metricas.comparacoes;
                            somaTrocas += metricas.trocas;
                            somaMovimentos += metricas.movimentos;
                        }
                    }

                    // Divide por 3.0 para manter as casas decimais.
                    System.out.println("MEDIA DAS 3 EXECUCOES:" + " tempo: " + somaTempo / 3.0 + " ns" + " | comparacoes: " + somaComparacoes / 3.0 + " | trocas: " + somaTrocas / 3.0 + " | movimentos: " + somaMovimentos / 3.0);
                }
            }
        }
    }
}