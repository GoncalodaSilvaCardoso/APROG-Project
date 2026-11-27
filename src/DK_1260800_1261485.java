import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DK_1260800_1261485 {

    static final char SEPARADOR_NOMES = ',';
    static final int SEM_ARGUMENTOS = 0;
    static final int INDICE_NOME_FICHEIRO = 0;

    static final int LARGURA_NOME = 13;
    static final int LARGURA_COLUNA = 11;
    static final int LARGURA_PERCENTAGEM = 12;
    static final int LARGURA_PERFIL = 13;

    static final int MAXIMO_MODERADO = 90;
    static final int MAXIMO_REGULAR = 180;
    static final int MAXIMO_INTENSIVO = 300;

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
        Scanner scanner = abrirFonteDados(args);
        String[] nomes = lerNomes(scanner);
        String[] redesSociais = lerNomes(scanner);
        int[][] minutos = lerMinutos(scanner, nomes.length, redesSociais.length);
        scanner.close();

        // b)
        mostrarTabelaFormatada(nomes, redesSociais, minutos);
        System.out.println();

        // c)
        mostrarUtilizacaoDiaria(nomes, minutos);
        System.out.println();

        // d)
        mostrarPessoasMaisAtivas(nomes, redesSociais, minutos);
        System.out.println();

        // e)
        mostrarPessoasAcimaDaMedia(nomes, minutos);
        System.out.println();

        // f)
        mostrarRedeSocialMaisUsada(nomes, redesSociais, minutos);
        System.out.println();

        // g)
        mostrarPercentagemDeUso(nomes, redesSociais, minutos);
        System.out.println();

        // h)
        mostrarPerfis(nomes, minutos);
        System.out.println();

        // i)
        mostrarGraficoRedesSociais(redesSociais, minutos);
        System.out.println();

        // j)
        mostrarUtilizadoresMaisEquilibrados(nomes, minutos);
    }

    // a)
    public static Scanner abrirFonteDados(String[] args) throws FileNotFoundException {
        if (args.length > SEM_ARGUMENTOS) {
            return new Scanner(new File(args[INDICE_NOME_FICHEIRO]));
        }
        return new Scanner(System.in);
    }

    public static String[] lerNomes(Scanner scanner) {
        String linha = scanner.nextLine();
        String[] nomes = new String[contarNomes(linha)];
        String nomeAtual = "";
        int indiceNome = 0;

        for (int posicao = 0; posicao < linha.length(); posicao++) {
            char caracter = linha.charAt(posicao);

            if (caracter == SEPARADOR_NOMES) {
                nomes[indiceNome] = nomeAtual;
                indiceNome++;
                nomeAtual = "";
            } else {
                nomeAtual = nomeAtual + caracter;
            }
        }
        nomes[indiceNome] = nomeAtual;

        return nomes;
    }

    public static int contarNomes(String linha) {
        int separadores = 0;

        for (int posicao = 0; posicao < linha.length(); posicao++) {
            if (linha.charAt(posicao) == SEPARADOR_NOMES) {
                separadores++;
            }
        }
        return separadores + 1;
    }

    public static int[][] lerMinutos(Scanner scanner, int quantidadePessoas, int quantidadeRedesSociais) {
        int[][] minutos = new int[quantidadePessoas][quantidadeRedesSociais];

        for (int n = 0; n < quantidadePessoas; n++) {
            for (int s = 0; s < quantidadeRedesSociais; s++) {
                minutos[n][s] = scanner.nextInt();
            }
        }
        return minutos;
    }

    // b)
    public static void mostrarTabelaFormatada(String[] nomes, String[] redesSociais, int[][] minutos) {

        System.out.println("b) Social Media Usage (minutes/day)");

        System.out.printf("%-" + LARGURA_NOME + "s", "");
        for (int s = 0; s < redesSociais.length; s++) {
            System.out.printf("%" + LARGURA_COLUNA + "s", redesSociais[s]);
        }
        System.out.println();

        for (int n = 0; n < nomes.length; n++) {
            System.out.printf("%-" + LARGURA_NOME + "s", nomes[n]);
            for (int s = 0; s < redesSociais.length; s++) {
                System.out.printf("%" + LARGURA_COLUNA + "d", minutos[n][s]);
            }
            System.out.println();

        }
    }

    // c)
    public static void mostrarUtilizacaoDiaria(String[] nomes, int[][] minutos) {
        System.out.println("c) Daily usage");

        for (int n = 0; n < nomes.length; n++) {
            int total = obterTotal(minutos[n]);
            double media = obterMedia(total, minutos[n].length);

            System.out.printf("%-" + LARGURA_NOME + "s: %5d min (%5.1f min average/network)%n", nomes[n], total, media);
        }
    }

    // d)
    public static void mostrarPessoasMaisAtivas(String[] nomes, String[] redesSociais, int[][] minutos) {

        System.out.println("d) Most active Users");

        for (int s = 0; s < redesSociais.length; s++) {

            int maximo = obtermaximoColuna(minutos, s);
            System.out.printf("%-" + LARGURA_NOME + "s: ", redesSociais[s]);

            for (int n = 0; n < nomes.length; n++) {
                if (minutos[n][s] == maximo){
                    System.out.printf("\"%s\" ", nomes[n]);
                }
            }
            System.out.println();
        }
    }

    // e)
    public static void mostrarPessoasAcimaDaMedia(String[] nomes, int[][] minutos) {
        System.out.println("e) Users consistently above average");

        double[] medias = obterMediasPorRede(minutos);
        boolean encontrado = false;

        for (int n = 0; n < nomes.length; n++) {
            if (estaAcimaDaMediaEmTodas(minutos[n], medias)) {
                System.out.println(nomes[n]);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("none");
        }
    }

    // f)
    public static void mostrarRedeSocialMaisUsada(String[] nomes, String[] redesSociais, int[][] minutos) {

        System.out.println("f) Most used social network");

        for (int n = 0; n < nomes.length; n++) {
            int maximo = obterMaximoLinha(minutos, n);

            System.out.printf("%-" + LARGURA_NOME + "s: ", nomes[n]);

            for (int s = 0; s < redesSociais.length; s++) {
                if (minutos[n][s] == maximo){
                    System.out.printf("[%s] ", redesSociais[s]);
                }
            }
            System.out.println();
        }
    }

    // g)
    public static void mostrarPercentagemDeUso(String[] nomes, String[] redesSociais, int[][] minutos) {
        System.out.println("g) Percentage of use");

        System.out.printf("%-" + LARGURA_NOME + "s", "");
        for (int s = 0; s < redesSociais.length; s++) {
            System.out.printf("%" + LARGURA_PERCENTAGEM + "s", redesSociais[s]);
        }
        System.out.println();

        for (int n = 0; n < nomes.length; n++) {
            int total = obterTotal(minutos[n]);
            System.out.printf("%-" + LARGURA_NOME + "s", nomes[n]);

            for (int s = 0; s < redesSociais.length; s++) {
                if (estaUsada(minutos[n][s])) {
                    System.out.printf("%" + (LARGURA_PERCENTAGEM - 1) + ".2f%%", obterPercentagem(minutos[n][s], total));
                } else {
                    System.out.printf("%" + LARGURA_PERCENTAGEM + "s", "");
                }
            }
            System.out.println();
        }
    }

    // h)
    public static void mostrarPerfis(String[] nomes, int[][] minutos) {

        String[] perfisUtilizador = {"Moderate", "Regular", "Intensive", "Very Intensive"};
        System.out.println("h) Users profile");

        for (int p = 0; p < perfisUtilizador.length; p++) {
            System.out.printf("%-" + LARGURA_PERFIL + "s", perfisUtilizador[p]);
        }
        System.out.println();

        for (int p = 0; p < perfisUtilizador.length; p++) {
            obterTabulacao();
            if (p < perfisUtilizador.length - 1){
                System.out.print("|");
            }
        }
        System.out.println();

        String[][] matriz = obterMatrizPerfis(nomes, minutos, perfisUtilizador.length);

        for (int n = 0; n < matriz.length; n++) {
            for (int p = 0; p < matriz[n].length; p++) {
                System.out.printf("%-" + LARGURA_PERFIL + "s", matriz[n][p]);
            }
            System.out.println();
        }
    }

    // i)
    public static void mostrarGraficoRedesSociais(String[] redesSociais, int[][] minutos) {
        System.out.println("i) Use of social networks");

        int[] totais = obterTotaisPorRede(minutos);
        int[] utilizadores = obterUtilizadoresPorRede(minutos);
        int[] ordem = ordenarRedesSociais(redesSociais, totais, utilizadores);
        int totalGeral = obterTotal(totais);

        for (int p = 0; p < ordem.length; p++) {
            int s = ordem[p];
            double percentagem = obterPercentagem(totais[s], totalGeral);
            int asteriscos = (int) (percentagem / PERCENTAGEM_POR_ASTERISCO + ARREDONDAMENTO);

            System.out.printf("%-" + LARGURA_NOME + "s|", redesSociais[s]);
            for (int a = 0; a < asteriscos; a++) {
                System.out.print(ASTERISCO);
            }
            System.out.printf(" %.2f%%%n", percentagem);
        }

        mostrarLegenda();
    }

    // j)
    public static void mostrarUtilizadoresMaisEquilibrados(String[] nomes, int[][] minutos) {

        System.out.println("j) Most balanced users");

        int menor = obterMenorDiferenca(minutos);
        boolean primeiro = true;

        System.out.print("{");
        for (int n = 0; n < nomes.length; n++) {
            if (obterDiferenca(minutos, n) == menor){
                if (!primeiro){
                    System.out.print(", ");
                }
                System.out.printf("\"%s\"", nomes[n]);
                primeiro = false;
            }
        }
        System.out.println("}");
    }

    // Other functions

    // Used in: c), g), h), i)
    public static int obterTotal(int[] valores) {
        int total = 0;

        for (int i = 0; i < valores.length; i++) {
            total += valores[i];
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
    public static int[] obterTotaisPorRede(int[][] minutos) {
        int[] totais = new int[minutos[0].length];

        for (int s = 0; s < totais.length; s++) {
            for (int n = 0; n < minutos.length; n++) {
                totais[s] += minutos[n][s];
            }
        }
        return totais;
    }

    // Used in: f), j)
    public static int obterMaximoLinha(int[][] minutos, int linha) {
        int maximo = minutos[linha][0];

        for (int s = 1; s < minutos[linha].length; s++) {
            if (minutos[linha][s] > maximo) {
                maximo = minutos[linha][s];
            }
        }
        return maximo;
    }

    // Functions for d)
    public static int obtermaximoColuna(int[][] minutos, int coluna) {
        int maximo = minutos[0][coluna];

        for (int n = 1; n < minutos.length; n++) {
            if (minutos[n][coluna] > maximo) {
                maximo = minutos[n][coluna];
            }
        }
        return maximo;
    }

    // Functions for e)
    public static double[] obterMediasPorRede(int[][] minutos) {
        int[] totais = obterTotaisPorRede(minutos);
        double[] medias = new double[totais.length];

        for (int s = 0; s < totais.length; s++) {
            medias[s] = obterMedia(totais[s], minutos.length);
        }
        return medias;
    }

    public static boolean estaAcimaDaMediaEmTodas(int[] minutosPessoa, double[] medias) {
        boolean acima = true;
        int s = 0;

        while (acima && s < minutosPessoa.length) {
            if (minutosPessoa[s] <= medias[s]) {
                acima = false;
            }
            s++;
        }
        return acima;
    }

    // Functions for h)
    public static void obterTabulacao() {
        for (int i = 0; i < LARGURA_NOME - 1; i++) {
            System.out.print("-");
        }
    }

    public static int obterColunaPerfil(int total) {
        if (total < MAXIMO_MODERADO) {
            return 0;
        } else if (total <= MAXIMO_REGULAR) {
            return 1;
        } else if (total <= MAXIMO_INTENSIVO) {
            return 2;
        }
        return 3;
    }

    public static String[][] obterMatrizPerfis(String[] nomes, int[][] minutos, int quantidadePerfis) {
        String[][] matriz = new String[nomes.length][quantidadePerfis];

        for (int n = 0; n < nomes.length; n++) {
            for (int p = 0; p < quantidadePerfis; p++) {
                matriz[n][p] = "";
            }
            int coluna = obterColunaPerfil(obterTotal(minutos[n]));
            matriz[n][coluna] = nomes[n];
        }
        return matriz;
    }

    // Functions for i)
    public static void mostrarLegenda() {
        System.out.printf("%" + LARGURA_NOME + "s+", "");
        for (int percentagem = INTERVALO_LEGENDA; percentagem <= MAXIMO_LEGENDA; percentagem += INTERVALO_LEGENDA) {
            System.out.print(SEGMENTO_LEGENDA);
        }
        System.out.println();

        System.out.printf("%" + (LARGURA_NOME + 1) + "s", "%");
        for (int percentagem = INTERVALO_LEGENDA; percentagem <= MAXIMO_LEGENDA; percentagem += INTERVALO_LEGENDA) {
            System.out.printf("%" + LARGURA_COLUNA_LEGENDA + "d", percentagem);
        }
        System.out.println();
    }

    public static int[] obterUtilizadoresPorRede(int[][] minutos) {
        int[] utilizadores = new int[minutos[0].length];

        for (int s = 0; s < utilizadores.length; s++) {
            for (int n = 0; n < minutos.length; n++) {
                if (estaUsada(minutos[n][s])) {
                    utilizadores[s]++;
                }
            }
        }
        return utilizadores;
    }

    public static int[] ordenarRedesSociais(String[] redesSociais, int[] totais, int[] utilizadores) {
        int[] ordem = new int[redesSociais.length];

        for (int s = 0; s < ordem.length; s++) {
            ordem[s] = s;
        }

        for (int p = 0; p < ordem.length; p++) {
            int primeiro = p;

            for (int c = p; c < ordem.length; c++) {
                if (vemAntes(ordem[c], ordem[primeiro], redesSociais, totais, utilizadores)) {
                    primeiro = c;
                }
            }

            int auxiliar = ordem[p];
            ordem[p] = ordem[primeiro];
            ordem[primeiro] = auxiliar;
        }
        return ordem;
    }

    public static boolean vemAntes(int a, int b, String[] redesSociais, int[] totais, int[] utilizadores) {
        if (totais[a] != totais[b]) {
            return totais[a] > totais[b];
        }
        if (utilizadores[a] != utilizadores[b]) {
            return utilizadores[a] > utilizadores[b];
        }
        return redesSociais[a].toLowerCase().compareTo(redesSociais[b].toLowerCase()) < 0;
    }

    // Functions for j)
    public static int obterminimoLinha(int[][] minutos, int linha) {
        int minimo = minutos[linha][0];

        for (int s = 1; s < minutos[linha].length; s++) {
            if (minutos[linha][s] < minimo) {
                minimo = minutos[linha][s];
            }
        }
        return minimo;
    }

    public static int obterDiferenca(int[][] minutos, int linha) {
        return obterMaximoLinha(minutos, linha) - obterminimoLinha(minutos, linha);
    }

    public static int obterMenorDiferenca(int[][] minutos) {
        int menor = obterDiferenca(minutos, 0);

        for (int n = 1; n < minutos.length; n++) {
            int diferenca = obterDiferenca(minutos, n);

            if (diferenca < menor) {
                menor = diferenca;
            }
        }
        return menor;
    }
}
