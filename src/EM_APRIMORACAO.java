import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EM_APRIMORACAO {

    static Scanner scanner = new Scanner(System.in);

    static final int OPCAO_FICHEIRO = 1;
    static final int OPCAO_TECLADO = 2;

    static final char SEPARADOR_NOMES = ',';
    static final int PRIMEIRO_INDICE = 0;
    static final int SEGUNDO_INDICE = 1;
    static final int DESLOCAMENTO_ULTIMO_INDICE = 1;

    static final int LARGURA_NOME = 13;
    static final int LARGURA_COLUNA = 11;
    static final int LARGURA_PERCENTAGEM = 12;
    static final int LARGURA_NUMERO_PERCENTAGEM = LARGURA_PERCENTAGEM - 1;
    static final int LARGURA_PERFIL = 13;
    static final int LARGURA_TRACEJADO = LARGURA_NOME - 1;
    static final int LARGURA_PREFIXO_LEGENDA = LARGURA_NOME + 1;

    static final int MAXIMO_MODERADO = 90;
    static final int MAXIMO_REGULAR = 180;
    static final int MAXIMO_INTENSIVO = 300;

    static final int COLUNA_MODERADO = 0;
    static final int COLUNA_REGULAR = 1;
    static final int COLUNA_INTENSIVO = 2;
    static final int COLUNA_MUITO_INTENSIVO = 3;

    static final int SEM_UTILIZACAO = 0;
    static final double TOTAL_PERCENTAGEM = 100.0;
    static final double PERCENTAGEM_POR_ASTERISCO = 5.0;
    static final double ARREDONDAMENTO = 0.5;
    static final char ASTERISCO = '*';
    static final int INTERVALO_LEGENDA = 25;
    static final int MAXIMO_LEGENDA = 100;
    static final int LARGURA_COLUNA_LEGENDA = 5;
    static final String SEGMENTO_LEGENDA = "----+";

    public static void main(String[] args) throws FileNotFoundException {

        // a)
        escolherFonteDeDados();
        String[] nomesPessoas = lerNomes();
        String[] nomesRedesSociais = lerNomes();
        int[][] minutosPorPessoaERede = lerMinutos(nomesPessoas.length, nomesRedesSociais.length);
        scanner.close();

        // b)
        mostrarTabelaFormatada(nomesPessoas, nomesRedesSociais, minutosPorPessoaERede);
        System.out.println();

        // c)
        mostrarUtilizacaoDiaria(nomesPessoas, minutosPorPessoaERede);
        System.out.println();

        // d)
        mostrarPessoasMaisAtivas(nomesPessoas, nomesRedesSociais, minutosPorPessoaERede);
        System.out.println();

        // e)
        mostrarPessoasAcimaDaMedia(nomesPessoas, minutosPorPessoaERede);
        System.out.println();

        // f)
        mostrarRedeSocialMaisUsada(nomesPessoas, nomesRedesSociais, minutosPorPessoaERede);
        System.out.println();

        // g)
        mostrarPercentagemDeUso(nomesPessoas, nomesRedesSociais, minutosPorPessoaERede);
        System.out.println();

        // h)
        mostrarPerfis(nomesPessoas, minutosPorPessoaERede);
        System.out.println();

        // i)
        mostrarGraficoRedesSociais(nomesRedesSociais, minutosPorPessoaERede);
        System.out.println();

        // j)
        mostrarUtilizadoresMaisEquilibrados(nomesPessoas, minutosPorPessoaERede);
    }

    // a)
    public static void escolherFonteDeDados() throws FileNotFoundException {
        int opcaoEscolhida;

        do {
            System.out.println("Para inserir ficheiro escreva " + OPCAO_FICHEIRO + ", para teclado escreva " + OPCAO_TECLADO);
            opcaoEscolhida = Integer.parseInt(scanner.nextLine());
        } while (opcaoEscolhida != OPCAO_FICHEIRO && opcaoEscolhida != OPCAO_TECLADO);

        if (opcaoEscolhida == OPCAO_FICHEIRO) {
            System.out.println("Nome do ficheiro:");
            String nomeDoFicheiro = scanner.nextLine();
            scanner = new Scanner(new File(nomeDoFicheiro));
        }
    }

    public static String[] lerNomes() {
        String linhaLida = scanner.nextLine();
        String[] nomesLidos = new String[contarNomes(linhaLida)];
        String nomeEmConstrucao = "";
        int indiceNomeAtual = PRIMEIRO_INDICE;

        for (int posicaoNaLinha = 0; posicaoNaLinha < linhaLida.length(); posicaoNaLinha++) {
            char caracterAtual = linhaLida.charAt(posicaoNaLinha);

            if (caracterAtual == SEPARADOR_NOMES) {
                nomesLidos[indiceNomeAtual] = nomeEmConstrucao;
                indiceNomeAtual++;
                nomeEmConstrucao = "";
            } else {
                nomeEmConstrucao = nomeEmConstrucao + caracterAtual;
            }
        }
        nomesLidos[indiceNomeAtual] = nomeEmConstrucao;

        return nomesLidos;
    }

    public static int contarNomes(String linhaLida) {
        int quantidadeSeparadores = 0;

        for (int posicaoNaLinha = 0; posicaoNaLinha < linhaLida.length(); posicaoNaLinha++) {
            if (linhaLida.charAt(posicaoNaLinha) == SEPARADOR_NOMES) {
                quantidadeSeparadores++;
            }
        }
        return quantidadeSeparadores + 1;
    }

    public static int[][] lerMinutos(int quantidadePessoas, int quantidadeRedesSociais) {
        int[][] minutosPorPessoaERede = new int[quantidadePessoas][quantidadeRedesSociais];

        for (int indicePessoa = 0; indicePessoa < quantidadePessoas; indicePessoa++) {
            for (int indiceRedeSocial = 0; indiceRedeSocial < quantidadeRedesSociais; indiceRedeSocial++) {
                minutosPorPessoaERede[indicePessoa][indiceRedeSocial] = scanner.nextInt();
            }
        }
        return minutosPorPessoaERede;
    }

    // b)
    public static void mostrarTabelaFormatada(String[] nomesPessoas, String[] nomesRedesSociais, int[][] minutosPorPessoaERede) {

        System.out.println("b) Social Media Usage (minutes/day)");

        System.out.printf("%-" + LARGURA_NOME + "s", "");
        for (int indiceRedeSocial = 0; indiceRedeSocial < nomesRedesSociais.length; indiceRedeSocial++) {
            System.out.printf("%" + LARGURA_COLUNA + "s", nomesRedesSociais[indiceRedeSocial]);
        }
        System.out.println();

        for (int indicePessoa = 0; indicePessoa < nomesPessoas.length; indicePessoa++) {
            System.out.printf("%-" + LARGURA_NOME + "s", nomesPessoas[indicePessoa]);
            for (int indiceRedeSocial = 0; indiceRedeSocial < nomesRedesSociais.length; indiceRedeSocial++) {
                System.out.printf("%" + LARGURA_COLUNA + "d", minutosPorPessoaERede[indicePessoa][indiceRedeSocial]);
            }
            System.out.println();
        }
    }

    // c)
    public static void mostrarUtilizacaoDiaria(String[] nomesPessoas, int[][] minutosPorPessoaERede) {
        System.out.println("c) Daily usage");

        for (int indicePessoa = 0; indicePessoa < nomesPessoas.length; indicePessoa++) {
            int totalMinutosPessoa = obterTotal(minutosPorPessoaERede[indicePessoa]);
            double mediaMinutosPorRede = obterMedia(totalMinutosPessoa, minutosPorPessoaERede[indicePessoa].length);

            System.out.printf("%-" + LARGURA_NOME + "s: %5d min (%5.1f min average/network)%n",
                    nomesPessoas[indicePessoa], totalMinutosPessoa, mediaMinutosPorRede);
        }
    }

    // d)
    public static void mostrarPessoasMaisAtivas(String[] nomesPessoas, String[] nomesRedesSociais, int[][] minutosPorPessoaERede) {

        System.out.println("d) Most active Users");

        for (int indiceRedeSocial = 0; indiceRedeSocial < nomesRedesSociais.length; indiceRedeSocial++) {

            int maximoMinutosNaRede = maximoColuna(minutosPorPessoaERede, indiceRedeSocial);
            System.out.printf("%-" + LARGURA_NOME + "s: ", nomesRedesSociais[indiceRedeSocial]);

            for (int indicePessoa = 0; indicePessoa < nomesPessoas.length; indicePessoa++) {
                if (minutosPorPessoaERede[indicePessoa][indiceRedeSocial] == maximoMinutosNaRede) {
                    System.out.printf("\"%s\" ", nomesPessoas[indicePessoa]);
                }
            }
            System.out.println();
        }
    }

    // e)
    public static void mostrarPessoasAcimaDaMedia(String[] nomesPessoas, int[][] minutosPorPessoaERede) {
        System.out.println("e) Users consistently above average");

        double[] mediasPorRede = obterMediasPorRede(minutosPorPessoaERede);
        boolean encontrouPessoa = false;

        for (int indicePessoa = 0; indicePessoa < nomesPessoas.length; indicePessoa++) {
            if (estaAcimaDaMediaEmTodas(minutosPorPessoaERede[indicePessoa], mediasPorRede)) {
                System.out.println(nomesPessoas[indicePessoa]);
                encontrouPessoa = true;
            }
        }

        if (!encontrouPessoa) {
            System.out.println("none");
        }
    }

    // f)
    public static void mostrarRedeSocialMaisUsada(String[] nomesPessoas, String[] nomesRedesSociais, int[][] minutosPorPessoaERede) {

        System.out.println("f) Most used social network");

        for (int indicePessoa = 0; indicePessoa < nomesPessoas.length; indicePessoa++) {
            int maximoMinutosDaPessoa = maximoLinha(minutosPorPessoaERede, indicePessoa);

            System.out.printf("%-" + LARGURA_NOME + "s: ", nomesPessoas[indicePessoa]);

            for (int indiceRedeSocial = 0; indiceRedeSocial < nomesRedesSociais.length; indiceRedeSocial++) {
                if (minutosPorPessoaERede[indicePessoa][indiceRedeSocial] == maximoMinutosDaPessoa) {
                    System.out.printf("[%s] ", nomesRedesSociais[indiceRedeSocial]);
                }
            }
            System.out.println();
        }
    }

    // g)
    public static void mostrarPercentagemDeUso(String[] nomesPessoas, String[] nomesRedesSociais, int[][] minutosPorPessoaERede) {
        System.out.println("g) Percentage of use");

        System.out.printf("%-" + LARGURA_NOME + "s", "");
        for (int indiceRedeSocial = 0; indiceRedeSocial < nomesRedesSociais.length; indiceRedeSocial++) {
            System.out.printf("%" + LARGURA_PERCENTAGEM + "s", nomesRedesSociais[indiceRedeSocial]);
        }
        System.out.println();

        for (int indicePessoa = 0; indicePessoa < nomesPessoas.length; indicePessoa++) {
            int totalMinutosPessoa = obterTotal(minutosPorPessoaERede[indicePessoa]);
            System.out.printf("%-" + LARGURA_NOME + "s", nomesPessoas[indicePessoa]);

            for (int indiceRedeSocial = 0; indiceRedeSocial < nomesRedesSociais.length; indiceRedeSocial++) {
                int minutosNaRede = minutosPorPessoaERede[indicePessoa][indiceRedeSocial];

                if (estaUsada(minutosNaRede)) {
                    System.out.printf("%" + LARGURA_NUMERO_PERCENTAGEM + ".2f%%",
                            obterPercentagem(minutosNaRede, totalMinutosPessoa));
                } else {
                    System.out.printf("%" + LARGURA_PERCENTAGEM + "s", "");
                }
            }
            System.out.println();
        }
    }

    // h)
    public static void mostrarPerfis(String[] nomesPessoas, int[][] minutosPorPessoaERede) {

        String[] nomesPerfis = {"Moderate", "Regular", "Intensive", "Very Intensive"};
        System.out.println("h) Users profile");

        for (int indicePerfil = 0; indicePerfil < nomesPerfis.length; indicePerfil++) {
            System.out.printf("%-" + LARGURA_PERFIL + "s", nomesPerfis[indicePerfil]);
        }
        System.out.println();

        for (int indicePerfil = 0; indicePerfil < nomesPerfis.length; indicePerfil++) {
            mostrarTracejado();
            if (indicePerfil < nomesPerfis.length - DESLOCAMENTO_ULTIMO_INDICE) {
                System.out.print("|");
            }
        }
        System.out.println();

        String[][] matrizPerfis = obterMatrizPerfis(nomesPessoas, minutosPorPessoaERede, nomesPerfis.length);

        for (int indicePessoa = 0; indicePessoa < matrizPerfis.length; indicePessoa++) {
            for (int indicePerfil = 0; indicePerfil < matrizPerfis[indicePessoa].length; indicePerfil++) {
                System.out.printf("%-" + LARGURA_PERFIL + "s", matrizPerfis[indicePessoa][indicePerfil]);
            }
            System.out.println();
        }
    }

    // i)
    public static void mostrarGraficoRedesSociais(String[] nomesRedesSociais, int[][] minutosPorPessoaERede) {
        System.out.println("i) Use of social networks");

        int[] totaisPorRede = obterTotaisPorRede(minutosPorPessoaERede);
        int[] utilizadoresPorRede = obterUtilizadoresPorRede(minutosPorPessoaERede);
        int[] ordemDasRedes = ordenarRedesSociais(nomesRedesSociais, totaisPorRede, utilizadoresPorRede);
        int totalGeralMinutos = obterTotal(totaisPorRede);

        for (int posicaoNaOrdem = 0; posicaoNaOrdem < ordemDasRedes.length; posicaoNaOrdem++) {
            int indiceRedeSocial = ordemDasRedes[posicaoNaOrdem];
            double percentagemDaRede = obterPercentagem(totaisPorRede[indiceRedeSocial], totalGeralMinutos);
            int quantidadeAsteriscos = (int) (percentagemDaRede / PERCENTAGEM_POR_ASTERISCO + ARREDONDAMENTO);

            System.out.printf("%-" + LARGURA_NOME + "s|", nomesRedesSociais[indiceRedeSocial]);
            for (int asteriscoAtual = 0; asteriscoAtual < quantidadeAsteriscos; asteriscoAtual++) {
                System.out.print(ASTERISCO);
            }
            System.out.printf(" %.2f%%%n", percentagemDaRede);
        }

        mostrarLegenda();
    }

    // j)
    public static void mostrarUtilizadoresMaisEquilibrados(String[] nomesPessoas, int[][] minutosPorPessoaERede) {

        System.out.println("j) Most balanced users");

        int menorDiferenca = obterMenorDiferenca(minutosPorPessoaERede);
        boolean ePrimeiraPessoa = true;

        System.out.print("{");
        for (int indicePessoa = 0; indicePessoa < nomesPessoas.length; indicePessoa++) {
            if (obterDiferenca(minutosPorPessoaERede, indicePessoa) == menorDiferenca) {
                if (!ePrimeiraPessoa) {
                    System.out.print(", ");
                }
                System.out.printf("\"%s\"", nomesPessoas[indicePessoa]);
                ePrimeiraPessoa = false;
            }
        }
        System.out.println("}");
    }

    // Other functions

    // Used in: c), g), h), i)
    public static int obterTotal(int[] valores) {
        int total = 0;

        for (int indiceValor = 0; indiceValor < valores.length; indiceValor++) {
            total += valores[indiceValor];
        }
        return total;
    }

    // Used in: c), e)
    public static double obterMedia(int total, int quantidade) {
        return (double) total / quantidade;
    }

    // Used in: g), i)
    public static double obterPercentagem(int parte, int total) {
        return TOTAL_PERCENTAGEM * parte / total;
    }

    // Used in: g), i)
    public static boolean estaUsada(int minutos) {
        return minutos > SEM_UTILIZACAO;
    }

    // Used in: e), i)
    public static int[] obterTotaisPorRede(int[][] minutosPorPessoaERede) {
        int[] totaisPorRede = new int[minutosPorPessoaERede[PRIMEIRO_INDICE].length];

        for (int indiceRedeSocial = 0; indiceRedeSocial < totaisPorRede.length; indiceRedeSocial++) {
            for (int indicePessoa = 0; indicePessoa < minutosPorPessoaERede.length; indicePessoa++) {
                totaisPorRede[indiceRedeSocial] += minutosPorPessoaERede[indicePessoa][indiceRedeSocial];
            }
        }
        return totaisPorRede;
    }

    // Used in: f), j)
    public static int maximoLinha(int[][] minutosPorPessoaERede, int indicePessoa) {
        int maximo = minutosPorPessoaERede[indicePessoa][PRIMEIRO_INDICE];

        for (int indiceRedeSocial = SEGUNDO_INDICE; indiceRedeSocial < minutosPorPessoaERede[indicePessoa].length; indiceRedeSocial++) {
            if (minutosPorPessoaERede[indicePessoa][indiceRedeSocial] > maximo) {
                maximo = minutosPorPessoaERede[indicePessoa][indiceRedeSocial];
            }
        }
        return maximo;
    }

    // Functions for d)
    public static int maximoColuna(int[][] minutosPorPessoaERede, int indiceRedeSocial) {
        int maximo = minutosPorPessoaERede[PRIMEIRO_INDICE][indiceRedeSocial];

        for (int indicePessoa = SEGUNDO_INDICE; indicePessoa < minutosPorPessoaERede.length; indicePessoa++) {
            if (minutosPorPessoaERede[indicePessoa][indiceRedeSocial] > maximo) {
                maximo = minutosPorPessoaERede[indicePessoa][indiceRedeSocial];
            }
        }
        return maximo;
    }

    // Functions for e)
    public static double[] obterMediasPorRede(int[][] minutosPorPessoaERede) {
        int[] totaisPorRede = obterTotaisPorRede(minutosPorPessoaERede);
        double[] mediasPorRede = new double[totaisPorRede.length];

        for (int indiceRedeSocial = 0; indiceRedeSocial < totaisPorRede.length; indiceRedeSocial++) {
            mediasPorRede[indiceRedeSocial] = obterMedia(totaisPorRede[indiceRedeSocial], minutosPorPessoaERede.length);
        }
        return mediasPorRede;
    }

    public static boolean estaAcimaDaMediaEmTodas(int[] minutosDaPessoa, double[] mediasPorRede) {
        boolean estaAcima = true;
        int indiceRedeSocial = 0;

        while (estaAcima && indiceRedeSocial < minutosDaPessoa.length) {
            if (minutosDaPessoa[indiceRedeSocial] <= mediasPorRede[indiceRedeSocial]) {
                estaAcima = false;
            }
            indiceRedeSocial++;
        }
        return estaAcima;
    }

    // Functions for h)
    public static void mostrarTracejado() {
        for (int tracoAtual = 0; tracoAtual < LARGURA_TRACEJADO; tracoAtual++) {
            System.out.print("-");
        }
    }

    public static int obterColunaPerfil(int totalMinutos) {
        if (totalMinutos < MAXIMO_MODERADO) {
            return COLUNA_MODERADO;
        } else if (totalMinutos <= MAXIMO_REGULAR) {
            return COLUNA_REGULAR;
        } else if (totalMinutos <= MAXIMO_INTENSIVO) {
            return COLUNA_INTENSIVO;
        }
        return COLUNA_MUITO_INTENSIVO;
    }

    public static String[][] obterMatrizPerfis(String[] nomesPessoas, int[][] minutosPorPessoaERede, int quantidadePerfis) {
        String[][] matrizPerfis = new String[nomesPessoas.length][quantidadePerfis];

        for (int indicePessoa = 0; indicePessoa < nomesPessoas.length; indicePessoa++) {
            for (int indicePerfil = 0; indicePerfil < quantidadePerfis; indicePerfil++) {
                matrizPerfis[indicePessoa][indicePerfil] = "";
            }
            int colunaDoPerfil = obterColunaPerfil(obterTotal(minutosPorPessoaERede[indicePessoa]));
            matrizPerfis[indicePessoa][colunaDoPerfil] = nomesPessoas[indicePessoa];
        }
        return matrizPerfis;
    }

    // Functions for i)
    public static void mostrarLegenda() {
        System.out.printf("%" + LARGURA_NOME + "s+", "");
        for (int percentagem = INTERVALO_LEGENDA; percentagem <= MAXIMO_LEGENDA; percentagem += INTERVALO_LEGENDA) {
            System.out.print(SEGMENTO_LEGENDA);
        }
        System.out.println();

        System.out.printf("%" + LARGURA_PREFIXO_LEGENDA + "s", "%");
        for (int percentagem = INTERVALO_LEGENDA; percentagem <= MAXIMO_LEGENDA; percentagem += INTERVALO_LEGENDA) {
            System.out.printf("%" + LARGURA_COLUNA_LEGENDA + "d", percentagem);
        }
        System.out.println();
    }

    public static int[] obterUtilizadoresPorRede(int[][] minutosPorPessoaERede) {
        int[] utilizadoresPorRede = new int[minutosPorPessoaERede[PRIMEIRO_INDICE].length];

        for (int indiceRedeSocial = 0; indiceRedeSocial < utilizadoresPorRede.length; indiceRedeSocial++) {
            for (int indicePessoa = 0; indicePessoa < minutosPorPessoaERede.length; indicePessoa++) {
                if (estaUsada(minutosPorPessoaERede[indicePessoa][indiceRedeSocial])) {
                    utilizadoresPorRede[indiceRedeSocial]++;
                }
            }
        }
        return utilizadoresPorRede;
    }

    public static int[] ordenarRedesSociais(String[] nomesRedesSociais, int[] totaisPorRede, int[] utilizadoresPorRede) {
        int[] ordemDasRedes = new int[nomesRedesSociais.length];

        for (int indiceRedeSocial = 0; indiceRedeSocial < ordemDasRedes.length; indiceRedeSocial++) {
            ordemDasRedes[indiceRedeSocial] = indiceRedeSocial;
        }

        for (int posicaoAtual = 0; posicaoAtual < ordemDasRedes.length; posicaoAtual++) {
            int posicaoDaPrimeira = posicaoAtual;

            for (int posicaoCandidata = posicaoAtual; posicaoCandidata < ordemDasRedes.length; posicaoCandidata++) {
                if (vemAntes(ordemDasRedes[posicaoCandidata], ordemDasRedes[posicaoDaPrimeira],
                        nomesRedesSociais, totaisPorRede, utilizadoresPorRede)) {
                    posicaoDaPrimeira = posicaoCandidata;
                }
            }

            int redeGuardada = ordemDasRedes[posicaoAtual];
            ordemDasRedes[posicaoAtual] = ordemDasRedes[posicaoDaPrimeira];
            ordemDasRedes[posicaoDaPrimeira] = redeGuardada;
        }
        return ordemDasRedes;
    }

    public static boolean vemAntes(int redeA, int redeB, String[] nomesRedesSociais, int[] totaisPorRede, int[] utilizadoresPorRede) {
        if (totaisPorRede[redeA] != totaisPorRede[redeB]) {
            return totaisPorRede[redeA] > totaisPorRede[redeB];
        }
        if (utilizadoresPorRede[redeA] != utilizadoresPorRede[redeB]) {
            return utilizadoresPorRede[redeA] > utilizadoresPorRede[redeB];
        }
        return nomesRedesSociais[redeA].toLowerCase().compareTo(nomesRedesSociais[redeB].toLowerCase()) < 0;
    }

    // Functions for j)
    public static int minimoLinha(int[][] minutosPorPessoaERede, int indicePessoa) {
        int minimo = minutosPorPessoaERede[indicePessoa][PRIMEIRO_INDICE];

        for (int indiceRedeSocial = SEGUNDO_INDICE; indiceRedeSocial < minutosPorPessoaERede[indicePessoa].length; indiceRedeSocial++) {
            if (minutosPorPessoaERede[indicePessoa][indiceRedeSocial] < minimo) {
                minimo = minutosPorPessoaERede[indicePessoa][indiceRedeSocial];
            }
        }
        return minimo;
    }

    public static int obterDiferenca(int[][] minutosPorPessoaERede, int indicePessoa) {
        return maximoLinha(minutosPorPessoaERede, indicePessoa) - minimoLinha(minutosPorPessoaERede, indicePessoa);
    }

    public static int obterMenorDiferenca(int[][] minutosPorPessoaERede) {
        int menorDiferenca = obterDiferenca(minutosPorPessoaERede, PRIMEIRO_INDICE);

        for (int indicePessoa = SEGUNDO_INDICE; indicePessoa < minutosPorPessoaERede.length; indicePessoa++) {
            int diferencaDaPessoa = obterDiferenca(minutosPorPessoaERede, indicePessoa);

            if (diferencaDaPessoa < menorDiferenca) {
                menorDiferenca = diferencaDaPessoa;
            }
        }
        return menorDiferenca;
    }
}
