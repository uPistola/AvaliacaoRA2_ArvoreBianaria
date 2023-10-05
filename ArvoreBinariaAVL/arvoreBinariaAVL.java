/*
 * Alunos: João Gabriel Pitol e Henrique Zan Grande
 * Curso: Ciencia da Computação
 * 
 */

package ArvoreBinariaAVL;

import java.util.Random;
import java.util.Scanner;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

class No {
    int valor;
    int altura;
    No esquerda;
    No direita;

    No(int valor) {
        this.valor = valor;
        this.altura = 1; 
        direita = null;
        esquerda = null;
    }
}

public class arvoreBinariaAVL {
    private static No raiz;

    arvoreBinariaAVL() {
        raiz = null;
    }

    private int altura(No no) {
        if (no == null) {
            return 0;
        }
        return no.altura;
    }

    private int fatorBalanceamento(No no) {
        if (no == null) {
            return 0;
        }
        return altura(no.esquerda) - altura(no.direita);
    }
    private int max(int a, int b) {
        return a > b ? a : b;
    }
    private No rotacaoDireita(No y) {
        No x = y.esquerda;
        No T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        No T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    public void inserir(int valor) {
        raiz = inserir(raiz, valor);
    }

    private No inserir(No no, int valor) {
        if (no == null) {
            return new No(valor);
        }

        if (valor <= no.valor) { 
            no.esquerda = inserir(no.esquerda, valor);
        } else {
            no.direita = inserir(no.direita, valor);
        }

        no.altura = 1 + max(altura(no.esquerda), altura(no.direita));

        int balanceamento = fatorBalanceamento(no);

        if (balanceamento > 1 && valor < no.esquerda.valor) {
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && valor > no.direita.valor) {
            return rotacaoEsquerda(no);
        }

        if (balanceamento > 1 && valor > no.esquerda.valor) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && valor < no.direita.valor) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public void percorrerEmOrdem(No no) {
        if (no != null) {
            percorrerEmOrdem(no.esquerda);
            System.out.print(" " + no.valor);
            percorrerEmOrdem(no.direita);
        }
    }

    public void percorrerPreOrdem(No no) {
        if (no != null) {
            System.out.print(" " + no.valor);
            percorrerPreOrdem(no.esquerda);
            percorrerPreOrdem(no.direita);
        }
    }

    public void percorrerPosOrdem(No no) {
        if (no != null) {
            percorrerPosOrdem(no.esquerda);
            percorrerPosOrdem(no.direita);
            System.out.print(" " + no.valor);
        }
    }

    public boolean buscar(int valor) {
        return busca(raiz, valor);
    }

    private boolean busca(No atual, int valor) {
        if (atual == null) {
            return false;
        }
        if (valor == atual.valor) {
            return true;
        }
        return valor < atual.valor ? busca(atual.esquerda, valor) : busca(atual.direita, valor);
    }

    private No removerMinimo(No atual) {
        if (atual == null) {
            return atual;
        }
        if (atual.esquerda == null) {
            return atual.direita;
        }
        atual.esquerda = removerMinimo(atual.esquerda);
        
        atual.altura = 1 + max(altura(atual.esquerda), altura(atual.direita));

        int balanceamento = fatorBalanceamento(atual);

        if (balanceamento > 1 && fatorBalanceamento(atual.esquerda) >= 0) {
            return rotacaoDireita(atual);
        }

        if (balanceamento < -1 && fatorBalanceamento(atual.direita) <= 0) {
            return rotacaoEsquerda(atual);
        }

        if (balanceamento > 1 && fatorBalanceamento(atual.esquerda) < 0) {
            atual.esquerda = rotacaoEsquerda(atual.esquerda);
            return rotacaoDireita(atual);
        }

        if (balanceamento < -1 && fatorBalanceamento(atual.direita) > 0) {
            atual.direita = rotacaoDireita(atual.direita);
            return rotacaoEsquerda(atual);
        }
        
        return atual;
    }

    private No removerMaximo(No atual) {
        if (atual == null) {
            return atual;
        }
        if (atual.direita == null) {
            return atual.esquerda;
        }
        atual.direita = removerMaximo(atual.direita);
        atual.altura = 1 + max(altura(atual.esquerda), altura(atual.direita));

        int balanceamento = fatorBalanceamento(atual);

        if (balanceamento > 1 && fatorBalanceamento(atual.esquerda) >= 0) {
            return rotacaoDireita(atual);
        }

        if (balanceamento < -1 && fatorBalanceamento(atual.direita) <= 0) {
            return rotacaoEsquerda(atual);
        }

        if (balanceamento > 1 && fatorBalanceamento(atual.esquerda) < 0) {
            atual.esquerda = rotacaoEsquerda(atual.esquerda);
            return rotacaoDireita(atual);
        }

        if (balanceamento < -1 && fatorBalanceamento(atual.direita) > 0) {
            atual.direita = rotacaoDireita(atual.direita);
            return rotacaoEsquerda(atual);
        }
        return atual;
    }

    private No removerValor(No atual, int valor) {
        if (atual == null) {
            return atual;
        }
        if (valor < atual.valor) {
            atual.esquerda = removerValor(atual.esquerda, valor);
        } else if (valor > atual.valor) {
            atual.direita = removerValor(atual.direita, valor);
        } else {
            if (atual.esquerda == null) {
                return atual.direita;
            } else if (atual.direita == null) {
                return atual.esquerda;
            }
            atual.valor = sucessor(atual.direita);
            atual.direita = removerValor(atual.direita, atual.valor);
        }
        
        atual.altura = 1 + max(altura(atual.esquerda), altura(atual.direita));

        int balanceamento = fatorBalanceamento(atual);

        if (balanceamento > 1 && fatorBalanceamento(atual.esquerda) >= 0) {
            return rotacaoDireita(atual);
        }

        if (balanceamento < -1 && fatorBalanceamento(atual.direita) <= 0) {
            return rotacaoEsquerda(atual);
        }

        if (balanceamento > 1 && fatorBalanceamento(atual.esquerda) < 0) {
            atual.esquerda = rotacaoEsquerda(atual.esquerda);
            return rotacaoDireita(atual);
        }

        if (balanceamento < -1 && fatorBalanceamento(atual.direita) > 0) {
            atual.direita = rotacaoDireita(atual.direita);
            return rotacaoEsquerda(atual);
        }

        return atual;
    }

    private int sucessor(No raiz) {
        raiz = raiz.esquerda;
        while (raiz.direita != null) {
            raiz = raiz.direita;
        }
        return raiz.valor;
    }

    public void removerMinimo() {
        raiz = removerMinimo(raiz);
    }

    public void removerMaximo() {
        raiz = removerMaximo(raiz);
    }

    public void remover(int valor) {
        raiz = removerValor(raiz, valor);
    }
    
    public static void visualizarArvoreBinaria(No raiz) {
        Graph graph = new SingleGraph("Árvore Binária AVL");
        
        graph.setAttribute("ui.stylesheet",
                "node { size: 30px; fill-color: #6E0DFD, #2980b9; text-size: 14; text-color: #ffffff; text-style: bold; }" +
                "edge { fill-color: #7f8c8d; size: 2px; }");       
        
        adicionarNosEConexoes(graph, raiz, null, "");

        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");

        graph.display();
    }

    private static void adicionarNosEConexoes(Graph graph, No no, org.graphstream.graph.Node paiNode, String idConexaoPai) {
        if (no == null) {
            return;
        }

        String idNo = String.valueOf(no.valor) + System.identityHashCode(no);

        org.graphstream.graph.Node noAtual = graph.addNode(idNo);
        noAtual.setAttribute("ui.label", String.valueOf(no.valor));

        if (paiNode == null) {
            noAtual.setAttribute("ui.style", "fill-color: #00ff00;");
        }

        if (paiNode != null) {
            String idConexao = idConexaoPai + idNo;
            graph.addEdge(idConexao, paiNode.getId(), noAtual.getId());
        }

        adicionarNosEConexoes(graph, no.esquerda, noAtual, "L");
        adicionarNosEConexoes(graph, no.direita, noAtual, "R");
    }

    public static void main(String[] args) {
    	System.setProperty("org.graphstream.ui", "swing"); 
        arvoreBinariaAVL arvore = new arvoreBinariaAVL();
        int valor;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("(1) Inserir número \n(2) Remover menor número\n(3) Remover maior número\n(4) Remover número específico\n(5) Buscar número \n(6) Sair");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Quantos valores deseja inserir: ");
                    valor = scanner.nextInt();
                    Random rand = new Random();
                    for (int i = 0; i < valor; i++) {
                    	if(valor >= 10000) {
                    		int novoValor = rand.nextInt(1000000);
                    		System.out.println(novoValor);
                    		arvore.inserir(novoValor);
                    	} else {
                    		int novoValor = rand.nextInt(valor + (2*valor));
                    		System.out.println(novoValor);
                    		arvore.inserir(novoValor);
                    	}
                       }
                    visualizarArvoreBinaria(raiz);
                    break;
                case 2:
                    arvore.removerMinimo();
                    visualizarArvoreBinaria(raiz);
                    break;
                case 3:
                    arvore.removerMaximo();
                    visualizarArvoreBinaria(raiz);
                    break;
                case 4:
                    System.out.println("Insira o valor para remover: ");
                    valor = scanner.nextInt();
                    arvore.remover(valor);
                    visualizarArvoreBinaria(raiz);
                    break;
                case 5:
                    System.out.println("Insira o valor para buscar: ");
                    valor = scanner.nextInt();
                    if (arvore.buscar(valor)) {
                        System.out.println("Elemento encontrado!");
                    } else {
                        System.out.println("Elemento não encontrado!");
                    }
                    break;
                case 6:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}