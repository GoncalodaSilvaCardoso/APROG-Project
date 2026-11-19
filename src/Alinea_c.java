import java.util.Scanner;

public class Alinea_c {

    static Scanner scanner = new Scanner(System.in);

    // ============================================================
    // MAIN
    // ============================================================
    public static void main(String[] args) {
        // a) Ler a informação e guardá-la em memória
        String[] nomesPessoas = lerNomes();
        String[] nomesRedesSociais = lerNomes();
        int[][] minutosDeUtilizacao = lerMinutosDeUtilizacao(nomesPessoas.length, nomesRedesSociais.length);

        // c) Visualizar tempo diário de utilização e média por rede
        visualizarUtilizacaoDiaria(nomesPessoas, minutosDeUtilizacao);
    }

    // ============================================================
    // ALÍNEA A) - Leitura dos dados
    // ============================================================

    // Lê uma linha com nomes separados por vírgula e guarda-os num array
    static String[] lerNomes() {
        final char SEPARADOR_DE_NOMES = ',';

        String linha = scanner.nextLine();
        String[] nomes = new String[contarNomes(linha)];
        String nomeAtual = "";
        int indiceNome = 0;

        for (int posicao = 0; posicao < linha.length(); posicao++) {
            char caracter = linha.charAt(posicao);

            if (caracter == SEPARADOR_DE_NOMES) {
                nomes[indiceNome] = nomeAtual;
                indiceNome++;
                nomeAtual = "";
            } else {
                nomeAtual = nomeAtual + caracter;
            }
        }

        // o último nome não tem vírgula a seguir
        nomes[indiceNome] = nomeAtual;

        return nomes;
    }

    // Conta quantos nomes existem numa linha
    static int contarNomes(String linha) {
        final char SEPARADOR_DE_NOMES = ',';
        final int ULTIMO_NOME = 1;

        int quantidadeVirgulas = 0;

        for (int posicao = 0; posicao < linha.length(); posicao++) {
            if (linha.charAt(posicao) == SEPARADOR_DE_NOMES) {
                quantidadeVirgulas++;
            }
        }

        return quantidadeVirgulas + ULTIMO_NOME;
    }

    // Lê os minutos de utilização e guarda-os numa matriz (linhas = pessoas, colunas = redes sociais)
    static int[][] lerMinutosDeUtilizacao(int quantidadePessoas, int quantidadeRedesSociais) {
        int[][] minutosDeUtilizacao = new int[quantidadePessoas][quantidadeRedesSociais];

        for (int indicePessoa = 0; indicePessoa < quantidadePessoas; indicePessoa++) {
            for (int indiceRedeSocial = 0; indiceRedeSocial < quantidadeRedesSociais; indiceRedeSocial++) {
                minutosDeUtilizacao[indicePessoa][indiceRedeSocial] = scanner.nextInt();
            }
        }

        return minutosDeUtilizacao;
    }

    // ============================================================
    // ALÍNEA C) - Tempo diário de utilização e média por rede
    // ============================================================

    // Visualizar, para cada pessoa, o tempo diário de utilização e a média por rede
    static void visualizarUtilizacaoDiaria(String[] nomesPessoas, int[][] minutosDeUtilizacao) {
        System.out.println("c) Daily usage");

        for (int indicePessoa = 0; indicePessoa < nomesPessoas.length; indicePessoa++) {
            int totalMinutos = calcularTotalMinutos(minutosDeUtilizacao[indicePessoa]);
            double mediaMinutosPorRede = calcularMediaPorRede(totalMinutos, minutosDeUtilizacao[indicePessoa].length);

            System.out.printf("%-13s: %5d min (%5.1f min average/network)%n", nomesPessoas[indicePessoa], totalMinutos, mediaMinutosPorRede);
        }

        System.out.println();
    }

    // Soma os minutos de todas as redes sociais de uma pessoa
    static int calcularTotalMinutos(int[] minutosDaPessoa) {
        int totalMinutos = 0;

        for (int indiceRedeSocial = 0; indiceRedeSocial < minutosDaPessoa.length; indiceRedeSocial++) {
            totalMinutos += minutosDaPessoa[indiceRedeSocial];
        }

        return totalMinutos;
    }

    // Calcula a média de minutos por rede social
    static double calcularMediaPorRede(int totalMinutos, int quantidadeRedesSociais) {
        return (double) totalMinutos / quantidadeRedesSociais;
    }
}