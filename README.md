# Análise de Hábitos de Utilização de Redes Sociais

![Java](https://img.shields.io/badge/Java-JDK%208-007396?style=flat-square&logo=openjdk&logoColor=white)
![Instituição](https://img.shields.io/badge/ISEP-LEI%20%7C%20APROG-8A1538?style=flat-square)
![Ano Letivo](https://img.shields.io/badge/Ano%20Letivo-2026%2F2027-333333?style=flat-square)

**Unidade Curricular:** Algoritmia e Programação (APROG)  
**Curso:** Licenciatura em Engenharia Informática (LEI)  
**Instituição:** Instituto Superior de Engenharia do Porto (ISEP)  

---

## Sumário

1. [Enquadramento](#1-enquadramento)
2. [Especificação Funcional](#2-especificação-funcional)
3. [Arquitetura e Estrutura do Repositório](#3-arquitetura-e-estrutura-do-repositório)
4. [Formato de Entrada de Dados](#4-formato-de-entrada-de-dados)
5. [Compilação e Execução](#5-compilação-e-execução)
6. [Equipa e Distribuição de Trabalho](#6-equipa-e-distribuição-de-trabalho)

---

## 1. Enquadramento

O presente projeto consiste no desenvolvimento de uma aplicação de consola em **Java** destinada à análise estatística e comportamental do tempo diário dedicado a diferentes plataformas de redes sociais por um conjunto de utilizadores.

A partir da leitura de registos estruturados (em minutos por dia), o sistema armazena a informação numa matriz bidimensional e processa um conjunto sequencial de métricas individuais e globais, permitindo identificar padrões de consumo, perfis de intensidade e desequilíbrios de utilização.

---

## 2. Especificação Funcional

O programa executa de forma sequencial as dez alíneas definidas no caderno de encargos:

| Alínea | Módulo Funcional | Descrição Técnica |
| :---: | :--- | :--- |
| **a)** | **Leitura e Armazenamento** | Carregamento dos identificadores de pessoas ($P$), redes sociais ($R$) e matriz de tempos ($P \times R$) em memória, recorrendo exclusivamente a *arrays* e variáveis locais. |
| **b)** | **Matriz de Utilização** | Apresentação tabular formatada dos minutos diários registados por utilizador e por plataforma. |
| **c)** | **Métricas Individuais** | Cálculo do tempo total diário e da média aritmética de utilização por rede social para cada pessoa. |
| **d)** | **Líderes por Plataforma** | Determinação do(s) utilizador(es) com maior tempo de permanência em cada rede social. |
| **e)** | **Utilização Acima da Média** | Identificação dos utilizadores que superam a média global em **todas** as redes sociais simultaneamente (apresentando `none` na ausência de ocorrências). |
| **f)** | **Preferência Individual** | Extração da(s) rede(s) social(is) com maior volume de utilização para cada pessoa. |
| **g)** | **Distribuição Relativa** | Cálculo percentual do tempo alocado por cada utilizador às redes sociais efetivamente utilizadas ($> 0$ min). |
| **h)** | **Classificação de Perfis** | Mapeamento gráfico dos utilizadores em quatro escalões de intensidade: `Moderate` ($< 90$), `Regular` ($[90, 180]$), `Intensive` ($]180, 300]$) e `Very Intensive` ($> 300$). |
| **i)** | **Histograma de Plataformas** | Ordenação decrescente das redes sociais (critérios: tempo total, número de utilizadores e ordem alfabética) com representação gráfica percentual (escala $1/5$, intervalos de $25\%$). |
| **j)** | **Equilíbrio de Utilização** | Identificação do(s) utilizador(es) com menor amplitude entre a rede social mais utilizada e a menos utilizada. |

---

## 3. Arquitetura e Estrutura do Repositório

```text
APROG-Project/
├── src/
│   └── DK_1260800_1261485.java    # Código-fonte monolítico modularizado (submissão oficial)
├── teste.txt                      # Conjunto de dados padrão para validação funcional
└── README.md                      # Documentação técnica do projeto
