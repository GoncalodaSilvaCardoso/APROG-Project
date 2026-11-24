import java.util.Scanner; // permite ler dados escritos no teclado

// NOTA: para a entrega, o nome da classe e do ficheiro têm de ser iguais ao pedido no enunciado:
// <turma>_<nºaluno1>_<nºaluno2> (por exemplo DA_11223344_55667788)
public class Alinea_i {

    // Único atributo da classe: o Scanner é declarado uma só vez, é "static" para poder ser usado
    // pelos métodos "static" e partilhado por todos os métodos de leitura (sem o passar por parâmetro)
    static Scanner scanner = new Scanner(System.in);

    // ============================================================
    // MAIN
    // O main só lê os dados e chama os métodos de cada alínea,
    // para ficar o mais limpo possível (modularização)
    // ============================================================
    public static void main(String[] args) {
        // a) Ler a informação e guardá-la em memória
        // 1.ª linha do input: nomes das pessoas (cada posição do array é uma pessoa)
        String[] nomesPessoas = lerNomes();
        // 2.ª linha do input: nomes das redes sociais (cada posição do array é uma rede)
        String[] nomesRedesSociais = lerNomes();
        // linhas seguintes: matriz com os minutos (linhas = pessoas, colunas = redes sociais)
        int[][] minutosDeUtilizacao = lerMinutosDeUtilizacao(nomesPessoas.length, nomesRedesSociais.length);

        // c) Visualizar, para cada pessoa, o tempo diário de utilização e a média por rede
        visualizarUtilizacaoDiaria(nomesPessoas, minutosDeUtilizacao);

        // e) Visualizar as pessoas acima da média em todas as redes sociais (ou "none")
        visualizarPessoasAcimaDaMedia(nomesPessoas, minutosDeUtilizacao, nomesRedesSociais.length);

        // g) Visualizar, para cada pessoa, a percentagem de utilização de cada rede social
        visualizarPercentagemDeUtilizacao(nomesPessoas, nomesRedesSociais, minutosDeUtilizacao);

        // i) Visualizar o gráfico das redes sociais ordenadas por utilização
        visualizarUtilizacaoDasRedes(nomesRedesSociais, minutosDeUtilizacao);
    }

    // ============================================================
    // ALÍNEA A) - Leitura dos dados
    // ============================================================

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

    // ============================================================
    // ALÍNEA C) - Tempo diário de utilização e média por rede
    // ============================================================

    // Visualizar, para cada pessoa, o tempo diário de utilização e a média por rede
    static void visualizarUtilizacaoDiaria(String[] nomesPessoas, int[][] minutosDeUtilizacao) {
        // título da alínea, igual ao do exemplo do enunciado
        System.out.println("c) Daily usage");

        // percorre as pessoas (cada linha da matriz é uma pessoa)
        for (int indicePessoa = 0; indicePessoa < nomesPessoas.length; indicePessoa++) {
            // total de minutos desta pessoa: soma da linha da matriz
            int totalMinutos = calcularTotalMinutos(minutosDeUtilizacao[indicePessoa]);
            // média = total a dividir pelo nº de redes (.length da linha = nº de colunas)
            double mediaMinutosPorRede = calcularMedia(totalMinutos, minutosDeUtilizacao[indicePessoa].length);

            // %-13s : nome alinhado à esquerda em 13 caracteres, para os ":" ficarem alinhados
            // %5d   : total de minutos (inteiro) alinhado à direita em 5 caracteres
            // %5.1f : média com 1 casa decimal em 5 caracteres (o printf arredonda: 41.25 -> 41.3)
            // %n    : muda de linha
            System.out.printf("%-13s: %5d min (%5.1f min average/network)%n", nomesPessoas[indicePessoa], totalMinutos, mediaMinutosPorRede);
        }

        // linha em branco para separar da alínea seguinte, como no exemplo do enunciado
        System.out.println();
    }

    // Soma os valores de um array de minutos (usado nas alíneas c, g e i)
    static int calcularTotalMinutos(int[] minutos) {
        // acumulador: começa a 0 e vai somando cada valor do array
        int totalMinutos = 0;

        // percorre todas as posições do array
        for (int posicao = 0; posicao < minutos.length; posicao++) {
            totalMinutos += minutos[posicao]; // totalMinutos = totalMinutos + minutos[posicao]
        }

        return totalMinutos;
    }

    // Calcula a média de um total repartido por uma quantidade de elementos (usado nas alíneas c e e)
    static double calcularMedia(int total, int quantidadeElementos) {
        // o cast (double) obriga a divisão a ser decimal; sem ele, 165 / 4 daria 41 (divisão inteira)
        return (double) total / quantidadeElementos;
    }

    // ============================================================
    // ALÍNEA E) - Pessoas acima da média em todas as redes sociais
    // ============================================================

    // Visualizar as pessoas cujo tempo é superior à média em todas as redes sociais, ou "none"
    static void visualizarPessoasAcimaDaMedia(String[] nomesPessoas, int[][] minutosDeUtilizacao, int quantidadeRedesSociais) {
        // média de cada rede social (calculada uma só vez, antes do ciclo das pessoas)
        double[] mediasPorRede = calcularMediasPorRede(minutosDeUtilizacao, quantidadeRedesSociais);
        // "encontrei alguma pessoa acima da média?" - começa a false (ainda não encontrei ninguém)
        boolean existePessoaAcimaDaMedia = false;

        // título da alínea, igual ao do exemplo do enunciado
        System.out.println("e) Users consistently above average");

        // percorre as pessoas
        for (int indicePessoa = 0; indicePessoa < nomesPessoas.length; indicePessoa++) {
            // se a pessoa estiver acima da média em todas as redes, mostra o nome dela
            if (estaAcimaDaMediaEmTodasAsRedes(minutosDeUtilizacao[indicePessoa], mediasPorRede)) {
                System.out.println(nomesPessoas[indicePessoa]);
                // regista que já foi encontrada pelo menos uma pessoa
                existePessoaAcimaDaMedia = true;
            }
        }

        // se a variável continuar a false, ninguém cumpriu a condição: mostra "none"
        if (!existePessoaAcimaDaMedia) {
            System.out.println("none");
        }

        // linha em branco para separar da alínea seguinte
        System.out.println();
    }

    // Calcula a média de minutos de cada rede social (média de cada coluna da matriz)
    static double[] calcularMediasPorRede(int[][] minutosDeUtilizacao, int quantidadeRedesSociais) {
        // total de minutos de cada rede (soma de cada coluna); partilhado com a alínea i
        int[] totaisPorRede = calcularTotaisPorRede(minutosDeUtilizacao, quantidadeRedesSociais);
        // array onde ficam as médias: posição j = média da rede j
        double[] mediasPorRede = new double[quantidadeRedesSociais];

        // percorre as redes sociais
        for (int indiceRedeSocial = 0; indiceRedeSocial < quantidadeRedesSociais; indiceRedeSocial++) {
            // média da rede = total da rede a dividir pelo nº de pessoas (nº de linhas da matriz)
            mediasPorRede[indiceRedeSocial] = calcularMedia(totaisPorRede[indiceRedeSocial], minutosDeUtilizacao.length);
        }

        return mediasPorRede;
    }

    // Verifica se uma pessoa tem tempo superior à média em todas as redes sociais
    static boolean estaAcimaDaMediaEmTodasAsRedes(int[] minutosDaPessoa, double[] mediasPorRede) {
        // parte do princípio de que a pessoa está acima da média em todas as redes
        boolean acimaDaMedia = true;

        // posição da rede social que está a ser verificada (começa na primeira)
        int indiceRedeSocial = 0;

        // continua enquanto a pessoa ainda cumpre a condição E ainda há redes por verificar;
        // assim o ciclo pára logo que a pessoa falha numa rede (sem precisar de "break")
        while (acimaDaMedia && indiceRedeSocial < minutosDaPessoa.length) {
            // "superior" é estritamente maior; se for menor ou igual à média, falha esta rede
            if (minutosDaPessoa[indiceRedeSocial] <= mediasPorRede[indiceRedeSocial]) {
                // basta falhar numa rede para a pessoa deixar de cumprir a condição
                acimaDaMedia = false;
            }

            // passa à rede seguinte
            indiceRedeSocial++;
        }

        return acimaDaMedia;
    }

    // ============================================================
    // ALÍNEA G) - Percentagem de utilização de cada rede social por pessoa
    // ============================================================

    // Visualizar, para cada pessoa, a percentagem de tempo de cada rede social utilizada
    static void visualizarPercentagemDeUtilizacao(String[] nomesPessoas, String[] nomesRedesSociais, int[][] minutosDeUtilizacao) {
        // título da alínea, igual ao do exemplo do enunciado
        System.out.println("g) Percentage of use");

        // cabeçalho: primeiro um espaço vazio com a largura da coluna dos nomes das pessoas
        System.out.printf("%-13s", "");
        // depois o nome de cada rede social, alinhado à direita em colunas de 12 caracteres
        for (int indiceRedeSocial = 0; indiceRedeSocial < nomesRedesSociais.length; indiceRedeSocial++) {
            System.out.printf("%12s", nomesRedesSociais[indiceRedeSocial]);
        }
        // muda de linha no fim do cabeçalho
        System.out.println();

        // uma linha por pessoa
        for (int indicePessoa = 0; indicePessoa < nomesPessoas.length; indicePessoa++) {
            // total de minutos da pessoa (calculado uma só vez por pessoa, antes do ciclo das redes)
            int totalMinutos = calcularTotalMinutos(minutosDeUtilizacao[indicePessoa]);

            // nome da pessoa à esquerda, alinhado em 13 caracteres
            System.out.printf("%-13s", nomesPessoas[indicePessoa]);

            // percorre as redes sociais da pessoa
            for (int indiceRedeSocial = 0; indiceRedeSocial < nomesRedesSociais.length; indiceRedeSocial++) {
                // minutos que a pessoa passou nesta rede
                int minutosRedeSocial = minutosDeUtilizacao[indicePessoa][indiceRedeSocial];

                if (foiUtilizada(minutosRedeSocial)) {
                    // %11.2f : percentagem com 2 casas decimais em 11 caracteres
                    // %%     : escreve o símbolo % (um % sozinho seria interpretado como formatação)
                    // 11 + 1 do símbolo % = 12 caracteres, a mesma largura das colunas do cabeçalho
                    System.out.printf("%11.2f%%", calcularPercentagem(minutosRedeSocial, totalMinutos));
                } else {
                    // rede não utilizada: imprime 12 espaços para a coluna ficar vazia mas alinhada
                    System.out.printf("%12s", "");
                }
            }

            // muda de linha no fim de cada pessoa
            System.out.println();
        }

        // linha em branco para separar da alínea seguinte
        System.out.println();
    }

    // Calcula a percentagem que uma parte representa de um total (usado nas alíneas g e i)
    static double calcularPercentagem(int parte, int total) {
        // 100 como constante; é "double" para que a conta seja decimal desde o início
        final double PERCENTAGEM_TOTAL = 100.0;

        return PERCENTAGEM_TOTAL * parte / total;
    }

    // Verifica se uma rede social foi utilizada, ou seja, se tem minutos acima de zero (usado nas alíneas g e i)
    static boolean foiUtilizada(int minutos) {
        // 0 minutos significa que a pessoa não utilizou a rede
        final int MINUTOS_SEM_UTILIZACAO = 0;

        return minutos > MINUTOS_SEM_UTILIZACAO;
    }

    // ============================================================
    // ALÍNEA I) - Gráfico das redes sociais ordenadas por utilização
    // ============================================================

    // Visualizar as redes sociais ordenadas, com um histograma da percentagem de utilização e a legenda
    static void visualizarUtilizacaoDasRedes(String[] nomesRedesSociais, int[][] minutosDeUtilizacao) {
        // carácter com que se desenha a barra do histograma
        final char ASTERISCO = '*';
        // escala de 1/5 pedida no enunciado: cada asterisco representa 5% (100% = 20 asteriscos)
        final double PERCENTAGEM_POR_ASTERISCO = 5.0;
        // somar 0.5 antes do cast (int) faz o arredondamento ao inteiro mais próximo
        final double ARREDONDAMENTO = 0.5;

        // minutos totais de cada rede (soma de cada coluna da matriz) -> critério 1 da ordenação
        int[] totaisPorRede = calcularTotaisPorRede(minutosDeUtilizacao, nomesRedesSociais.length);
        // número de pessoas que usaram cada rede -> critério 2 da ordenação
        int[] utilizadoresPorRede = contarUtilizadoresPorRede(minutosDeUtilizacao, nomesRedesSociais.length);
        // índices das redes pela ordem em que devem ser mostradas (maior utilização primeiro)
        int[] ordemRedes = ordenarRedesSociais(nomesRedesSociais, totaisPorRede, utilizadoresPorRede);
        // total de minutos de todas as redes: serve de base ao cálculo das percentagens (100%)
        int totalGeralMinutos = calcularTotalMinutos(totaisPorRede);

        // título da alínea, igual ao do exemplo do enunciado
        System.out.println("i) Use of social networks");

        // percorre as posições da ordem (a 1.ª linha desenhada é a rede mais utilizada)
        for (int posicao = 0; posicao < ordemRedes.length; posicao++) {
            // converte "posição na ordem" em "coluna da matriz", ligando a ordem aos arrays originais
            int indiceRedeSocial = ordemRedes[posicao];
            // percentagem desta rede em relação ao total de todas as redes
            double percentagem = calcularPercentagem(totaisPorRede[indiceRedeSocial], totalGeralMinutos);
            // nº de asteriscos = percentagem a dividir por 5, arredondado ao inteiro mais próximo
            int quantidadeAsteriscos = (int) (percentagem / PERCENTAGEM_POR_ASTERISCO + ARREDONDAMENTO);

            // nome da rede alinhado à esquerda em 13 caracteres, seguido da barra vertical "|"
            // (sem %n, porque a linha continua com os asteriscos)
            System.out.printf("%-13s|", nomesRedesSociais[indiceRedeSocial]);

            // desenha a barra: imprime o asterisco tantas vezes quantas calculadas, na mesma linha
            for (int asterisco = 0; asterisco < quantidadeAsteriscos; asterisco++) {
                System.out.print(ASTERISCO);
            }

            // espaço + percentagem com 2 casas decimais + símbolo % (%%) + mudança de linha (%n)
            System.out.printf(" %.2f%%%n", percentagem);
        }

        // depois das barras, desenha a legenda horizontal
        visualizarLegendaHorizontal();
    }

    // Visualizar a legenda horizontal com intervalos de 25% (escala de 1/5: cada asterisco vale 5%)
    static void visualizarLegendaHorizontal() {
        // intervalo entre marcas da legenda (25%) e valor final da escala (100%), pedidos no enunciado
        final int INTERVALO_PERCENTAGEM = 25;
        final int PERCENTAGEM_MAXIMA = 100;

        // 1.ª linha da legenda: 13 espaços (texto vazio em %13s) e o "+" inicial,
        // que fica na mesma coluna da barra vertical "|" dos gráficos
        System.out.printf("%13s+", "");
        // repete 4 vezes (percentagem = 25, 50, 75 e 100), e cada vez imprime um segmento "----+"
        // cada segmento tem 5 caracteres porque 25% equivale a 5 asteriscos (25 / 5)
        for (int percentagem = INTERVALO_PERCENTAGEM; percentagem <= PERCENTAGEM_MAXIMA; percentagem += INTERVALO_PERCENTAGEM) {
            System.out.print("----+");
        }
        // muda de linha no fim da primeira linha da legenda
        System.out.println();

        // 2.ª linha da legenda: o símbolo "%" alinhado à direita em 14 caracteres,
        // ficando por baixo do primeiro "+" (aqui o "%" é só texto, por isso não precisa de %%)
        System.out.printf("%14s", "%");
        // escreve 25, 50, 75 e 100, cada um alinhado à direita em 5 caracteres,
        // para o último dígito ficar por baixo do respetivo "+"
        for (int percentagem = INTERVALO_PERCENTAGEM; percentagem <= PERCENTAGEM_MAXIMA; percentagem += INTERVALO_PERCENTAGEM) {
            System.out.printf("%5d", percentagem);
        }
        // muda de linha no fim da legenda
        System.out.println();
    }

    // Calcula o total de minutos de cada rede social (soma de cada coluna da matriz; usado nas alíneas e e i)
    static int[] calcularTotaisPorRede(int[][] minutosDeUtilizacao, int quantidadeRedesSociais) {
        // posição j = total da rede j (todas as posições começam a 0, valor inicial certo para somar)
        int[] totaisPorRede = new int[quantidadeRedesSociais];

        // ciclo exterior: fixa uma coluna (uma rede social)
        for (int indiceRedeSocial = 0; indiceRedeSocial < quantidadeRedesSociais; indiceRedeSocial++) {
            // ciclo interior: percorre as linhas (as pessoas) dessa coluna
            for (int indicePessoa = 0; indicePessoa < minutosDeUtilizacao.length; indicePessoa++) {
                // soma os minutos de cada pessoa ao total da rede
                totaisPorRede[indiceRedeSocial] += minutosDeUtilizacao[indicePessoa][indiceRedeSocial];
            }
        }

        return totaisPorRede;
    }

    // Conta quantas pessoas utilizaram cada rede social
    static int[] contarUtilizadoresPorRede(int[][] minutosDeUtilizacao, int quantidadeRedesSociais) {
        // posição j = nº de pessoas que usaram a rede j (começa a 0)
        int[] utilizadoresPorRede = new int[quantidadeRedesSociais];

        // ciclo exterior: fixa uma coluna (uma rede social)
        for (int indiceRedeSocial = 0; indiceRedeSocial < quantidadeRedesSociais; indiceRedeSocial++) {
            // ciclo interior: percorre as pessoas dessa rede
            for (int indicePessoa = 0; indicePessoa < minutosDeUtilizacao.length; indicePessoa++) {
                // só conta a pessoa se ela tiver usado a rede (minutos > 0)
                if (foiUtilizada(minutosDeUtilizacao[indicePessoa][indiceRedeSocial])) {
                    utilizadoresPorRede[indiceRedeSocial]++;
                }
            }
        }

        return utilizadoresPorRede;
    }

    // Ordena os índices das redes sociais (ordenação por seleção), sem alterar os arrays originais
    // Ordenam-se os índices e não os arrays porque os arrays são paralelos (a posição j de cada um
    // refere-se à mesma rede j) e, assim, continuam alinhados entre si
    static int[] ordenarRedesSociais(String[] nomesRedesSociais, int[] totaisPorRede, int[] utilizadoresPorRede) {
        // array dos índices das redes: no fim terá a ordem em que as redes devem ser mostradas
        int[] ordemRedes = new int[nomesRedesSociais.length];

        // ordem inicial = ordem original (0, 1, 2, 3, ...)
        for (int indiceRedeSocial = 0; indiceRedeSocial < ordemRedes.length; indiceRedeSocial++) {
            ordemRedes[indiceRedeSocial] = indiceRedeSocial;
        }

        // para cada posição, procura a rede que deve ficar lá entre as que ainda não estão ordenadas
        for (int posicao = 0; posicao < ordemRedes.length; posicao++) {
            // guarda a posição da rede que deve vir primeiro (começa por assumir que é a da posição atual)
            int posicaoPrimeira = posicao;

            // compara com todas as redes que ainda não estão ordenadas
            for (int candidata = posicao; candidata < ordemRedes.length; candidata++) {
                // se a candidata deve vir antes da escolhida até agora, passa a ser a escolhida
                if (deveVirAntes(ordemRedes[candidata], ordemRedes[posicaoPrimeira], nomesRedesSociais, totaisPorRede, utilizadoresPorRede)) {
                    posicaoPrimeira = candidata;
                }
            }

            // troca as duas posições; a variável auxiliar é necessária para não perder o valor
            // que está na posição atual antes de ele ser substituído
            int redeAuxiliar = ordemRedes[posicao];
            ordemRedes[posicao] = ordemRedes[posicaoPrimeira];
            ordemRedes[posicaoPrimeira] = redeAuxiliar;
        }

        return ordemRedes;
    }

    // Verifica se a rede A deve aparecer antes da rede B: (1) mais minutos, (2) mais utilizadores, (3) ordem alfabética
    // redeA e redeB são índices de redes (colunas da matriz), e não posições na ordem
    static boolean deveVirAntes(int redeA, int redeB, String[] nomesRedesSociais, int[] totaisPorRede, int[] utilizadoresPorRede) {
        // resultado: true se a rede A deve vir antes da rede B
        boolean vemAntes;

        if (totaisPorRede[redeA] != totaisPorRede[redeB]) {
            // critério 1: os minutos totais são diferentes, por isso quem tem mais minutos vem primeiro
            vemAntes = totaisPorRede[redeA] > totaisPorRede[redeB];
        } else if (utilizadoresPorRede[redeA] != utilizadoresPorRede[redeB]) {
            // critério 2 (só se houver empate nos minutos): quem tem mais utilizadores vem primeiro
            vemAntes = utilizadoresPorRede[redeA] > utilizadoresPorRede[redeB];
        } else {
            // critério 3 (só se também houver empate nos utilizadores): ordem alfabética (A -> Z)

            // nomes das duas redes em minúsculas, para a comparação não distinguir maiúsculas de minúsculas
            // (o compareTo distingue-as: as letras maiúsculas vêm antes das minúsculas)
            String nomeRedeA = nomesRedesSociais[redeA].toLowerCase();
            String nomeRedeB = nomesRedesSociais[redeB].toLowerCase();

            // compareTo devolve um número negativo se o nome A vem antes do B no alfabeto,
            // 0 se forem iguais e um número positivo se o nome A vem depois do B
            int resultadoComparacao = nomeRedeA.compareTo(nomeRedeB);

            // resultado negativo = o nome A vem antes do B
            vemAntes = resultadoComparacao < 0;
        }

        return vemAntes;
    }
}

