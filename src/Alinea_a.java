import java.io.File; // representa um ficheiro de texto (usado para ler os dados de um ficheiro)
import java.io.FileNotFoundException; // erro que pode acontecer ao abrir um ficheiro que não existe
import java.util.Scanner; // permite ler dados escritos no teclado ou guardados num ficheiro

// NOTA: para a entrega, o nome da classe e do ficheiro têm de ser iguais ao pedido no enunciado:
// <turma>_<nºaluno1>_<nºaluno2> (por exemplo DA_11223344_55667788)
public class Alinea_a {

    // Único atributo da classe: o Scanner é declarado uma só vez, é "static" para poder ser usado
    // pelos métodos "static" e partilhado por todos os métodos de leitura (sem o passar por parâmetro)
    // Por omissão lê do teclado (System.in); se for indicado um ficheiro, passa a ler do ficheiro
    // (ver abrirOrigemDosDados)
    static Scanner scanner = new Scanner(System.in);

    // ============================================================
    // MAIN
    // O main só lê os dados e chama os métodos de cada alínea,
    // para ficar o mais limpo possível (modularização)
    // ============================================================
    // "throws FileNotFoundException" é obrigatório porque abrir um ficheiro pode falhar
    public static void main(String[] args) throws FileNotFoundException {
        // a) Escolher a origem dos dados: teclado (por omissão) ou ficheiro de texto (se for indicado em args)
        abrirOrigemDosDados(args);

        // a) Ler a informação e guardá-la em memória
        // 1.ª linha do input: nomes das pessoas (cada posição do array é uma pessoa)
        String[] nomesPessoas = lerNomes();
        // 2.ª linha do input: nomes das redes sociais (cada posição do array é uma rede)
        String[] nomesRedesSociais = lerNomes();
        // linhas seguintes: matriz com os minutos (linhas = pessoas, colunas = redes sociais)
        int[][] minutosDeUtilizacao = lerMinutosDeUtilizacao(nomesPessoas.length, nomesRedesSociais.length);

        // a) Já não são precisos mais dados: fecha o scanner (os ficheiros devem ser sempre fechados)
        scanner.close();

        // c) Visualizar, para cada pessoa, o tempo diário de utilização e a média por rede


        // e) Visualizar as pessoas acima da média em todas as redes sociais (ou "none")

        // g) Visualizar, para cada pessoa, a percentagem de utilização de cada rede social


        // i) Visualizar o gráfico das redes sociais ordenadas por utilização

    }

    static void abrirOrigemDosDados(String[] argumentos) throws FileNotFoundException {
        // sem argumentos = nenhum ficheiro indicado = ler do teclado
        final int QUANTIDADE_ARGUMENTOS_SEM_FICHEIRO = 0;
        // o nome do ficheiro é o primeiro argumento
        final int POSICAO_NOME_FICHEIRO = 0;

        if (argumentos.length > QUANTIDADE_ARGUMENTOS_SEM_FICHEIRO) {
            // o Scanner tem de receber um objeto File (e não só o nome), senão leria o próprio texto do nome
            scanner = new Scanner(new File(argumentos[POSICAO_NOME_FICHEIRO]));
        }
    }

    // Lê uma linha com nomes separados por vírgula e guarda-os num array
// (é usado duas vezes: para as pessoas e para as redes sociais)
    static String[] lerNomes() {
        // vírgula que separa os nomes na linha do input (constante para não ter o carácter solto no código)
        final char SEPARADOR_DE_NOMES = ',';

        // lê a linha inteira, por exemplo "Ana,Barros,Carla,Daniel,Elsa"
        String linha = scanner.nextLine();
        // o array tem de ter o tamanho certo desde o início, por isso contamos primeiro os nomes
        String[] nomes = new String[contarNomes(linha, SEPARADOR_DE_NOMES)];
        // guarda os caracteres do nome que está a ser lido neste momento
        String nomeAtual = "";
        // posição do array onde vai ser guardado o próximo nome
        int indiceNome = 0;

        // percorre a linha carácter a carácter, da primeira à última posição
        for (int posicao = 0; posicao < linha.length(); posicao++) {
            // carácter que está na posição atual da linha
            char caracter = linha.charAt(posicao);

            if (caracter == SEPARADOR_DE_NOMES) {
                // chegou ao fim de um nome: guarda-o no array
                nomes[indiceNome] = nomeAtual;
                // avança para a próxima posição do array
                indiceNome++;
                // limpa o nome atual para começar a ler o nome seguinte
                nomeAtual = "";
            } else {
                // ainda faz parte do nome: junta o carácter ao nome atual
                nomeAtual = nomeAtual + caracter;
            }
        }

        // o último nome não tem vírgula a seguir, por isso é guardado depois do ciclo
        nomes[indiceNome] = nomeAtual;

        return nomes;
    }

    // Conta quantos nomes existem numa linha
// (nº de nomes = nº de separadores + 1, porque o último nome não tem separador)
    static int contarNomes(String linha, char separadorDeNomes) {
        // o "+ 1" da fórmula: o último nome que não tem separador a seguir
        final int ULTIMO_NOME = 1;

        // conta quantas vírgulas existem na linha
        int quantidadeSeparadores = 0;

        // percorre a linha carácter a carácter
        for (int posicao = 0; posicao < linha.length(); posicao++) {
            // se o carácter for uma vírgula, soma 1 ao contador
            if (linha.charAt(posicao) == separadorDeNomes) {
                quantidadeSeparadores++;
            }
        }

        return quantidadeSeparadores + ULTIMO_NOME;
    }

    // Lê os minutos de utilização e guarda-os numa matriz (linhas = pessoas, colunas = redes sociais)
// Assim, minutosDeUtilizacao[i][j] são os minutos da pessoa i na rede j
    static int[][] lerMinutosDeUtilizacao(int quantidadePessoas, int quantidadeRedesSociais) {
        // cria a matriz com o tamanho certo (todas as posições começam a 0)
        int[][] minutosDeUtilizacao = new int[quantidadePessoas][quantidadeRedesSociais];

        // ciclo exterior: percorre as pessoas (linhas da matriz)
        for (int indicePessoa = 0; indicePessoa < quantidadePessoas; indicePessoa++) {
            // ciclo interior: percorre as redes sociais (colunas da matriz)
            for (int indiceRedeSocial = 0; indiceRedeSocial < quantidadeRedesSociais; indiceRedeSocial++) {
                // nextInt() lê o número seguinte, ignorando espaços e mudanças de linha
                minutosDeUtilizacao[indicePessoa][indiceRedeSocial] = scanner.nextInt();
            }
        }

        return minutosDeUtilizacao;
    }
}
