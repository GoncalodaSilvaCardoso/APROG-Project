#APROG-Project
# 📊 Análise de Hábitos de Utilização de Redes Sociais

> **Trabalho Prático de Algoritmia e Programação (APROG) — 2026/2027**  
> Licenciatura em Engenharia Informática (LEI) — Instituto Superior de Engenharia do Porto (ISEP)

---

## 📋 Sobre o Projeto

Este projeto consiste numa aplicação desenvolvida em **Java** que analisa o tempo de utilização diário (em minutos) que um conjunto de pessoas dedica a diferentes redes sociais. 

A partir de um conjunto de dados estruturado (via teclado ou ficheiro de texto), o programa processa uma matriz de utilização e gera métricas estatísticas, classificações de perfil, rankings e representações gráficas em consola, permitindo caracterizar padrões individuais e comportamentos globais do grupo.

---

## 🚀 Funcionalidades Implementadas

A aplicação executa sequencialmente as seguintes operações de análise:

- **a) Leitura e Armazenamento:** Carregamento dos nomes das pessoas, redes sociais e matriz de tempos diários em memória (utilizando arrays e matrizes, sem variáveis globais).
- **b) Tabela de Utilização:** Visualização formatada dos tempos diários (minutos/dia) por pessoa e por rede social.
- **c) Totais e Médias Individuais:** Cálculo do tempo total diário e da média de minutos por rede social para cada utilizador.
- **d) Utilizador Mais Ativo por Rede:** Identificação da(s) pessoa(s) com maior tempo de utilização em cada plataforma.
- **e) Utilizadores Acima da Média:** Listagem das pessoas cujo tempo de utilização supera a média em **todas** as redes sociais simultaneamente (ou `none`, caso não se verifique).
- **f) Rede Social Favorita:** Identificação da(s) rede(s) social(is) mais utilizada(s) por cada pessoa.
- **g) Distribuição Percentual:** Cálculo da percentagem de tempo que cada pessoa dedica às redes sociais que efetivamente utiliza.
- **h) Perfis de Utilizador:** Classificação visual dos utilizadores em quatro categorias com base no tempo total diário:
  - `Moderate`: $< 90$ minutos
  - `Regular`: $[90, 180]$ minutos
  - `Intensive`: $]180, 300]$ minutos
  - `Very Intensive`: $> 300$ minutos
- **i) Histograma de Redes Sociais:** Ordenação decrescente das plataformas (por tempo total, número de utilizadores e ordem alfabética) acompanhada de um histograma horizontal percentual (escala $1/5$, intervalos de $25\%$).
- **j) Utilizadores Mais Equilibrados:** Identificação da(s) pessoa(s) com menor diferença entre o tempo máximo e mínimo gasto nas suas redes sociais.

---

## 🗂️ Estrutura do Repositório

```text
├── src/
│   └── DK_1260800_1261485.java   # Código-fonte principal da aplicação
├── teste.txt                     # Ficheiro de exemplo com dados de entrada
└── README.md                     # Documentação do projeto
