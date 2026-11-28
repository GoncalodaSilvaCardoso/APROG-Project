# TRABALHO_PRÁTICO_M2

> **Algoritmia e Programação (APROG)** · Licenciatura em Engenharia Informática  
> Instituto Superior de Engenharia do Porto (ISEP) · Ano Letivo 2026/2027

---

## Sumário

* [1. Visão Geral](#1-visão-geral)
* [2. Funcionalidades](#2-funcionalidades)
* [3. Estrutura do Repositório](#3-estrutura-do-repositório)
* [4. Formato de Entrada](#4-formato-de-entrada)
* [5. Compilação e Execução](#5-compilação-e-execução)
* [6. Autoria](#6-autoria)

---

## 1. Visão Geral

Aplicação desenvolvida em **Java** para análise do tempo diário de utilização de redes sociais (em minutos) por um conjunto de pessoas. A partir de dados estruturados — fornecidos por *standard input* ou ficheiro de texto — o programa constrói uma matriz de utilização e executa sequencialmente um conjunto de operações estatísticas, classificações e visualizações em consola.

---

## 2. Funcionalidades

| Alínea | Funcionalidade | Descrição |
| :---: | :--- | :--- |
| **a)** | Leitura e armazenamento | Carrega pessoas, redes sociais e matriz de tempos em memória através de *arrays* e variáveis locais. |
| **b)** | Tabela de utilização | Apresenta os tempos diários (minutos/dia) por utilizador e por rede social. |
| **c)** | Totais e médias individuais | Calcula o tempo total diário e a média por rede social de cada pessoa. |
| **d)** | Utilizador(es) mais ativo(s) | Identifica, para cada rede social, quem regista o maior tempo de utilização. |
| **e)** | Acima da média em todas as redes | Lista as pessoas com tempo superior à média em todas as redes sociais, ou `none` caso não existam. |
| **f)** | Rede(s) social(is) mais utilizada(s) | Determina a rede social (ou redes) com maior tempo de uso para cada pessoa. |
| **g)** | Percentagem de utilização | Calcula, para cada pessoa, a percentagem de tempo em cada rede utilizada. |
| **h)** | Perfis de utilização | Representa graficamente os perfis conforme o tempo total diário: *Moderate* ($< 90$ min), *Regular* ($90\text{–}180$ min), *Intensive* ($]180, 300]$ min) e *Very Intensive* ($> 300$ min). |
| **i)** | Histograma de redes sociais | Ordena as redes de forma decrescente (tempo total, número de utilizadores e ordem alfabética) e apresenta um histograma percentual (escala $1/5$, intervalos de $25\%$). |
| **j)** | Utilizador(es) mais equilibrado(s) | Apura a(s) pessoa(s) com menor diferença entre o tempo máximo e mínimo entre redes sociais. |

---

## 3. Estrutura do Repositório

```text
.
├── src/
│   └── DK_1260800_1261485.java   # Implementação principal em Java
├── teste.txt                     # Conjunto de dados de exemplo
└── README.md                     # Documentação do projeto
