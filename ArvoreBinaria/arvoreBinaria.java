/*
 * Alunos: João Gabriel Pitol e Henrique Zan Grande
 * Curso: Ciencia da Computação
 * 
 */

package ArvoreBinaria;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

class Node {
    long valor;
    Node esquerda;
    Node direita;

    Node(long l) {
        this.valor = l;
        direita = null;
        esquerda = null;
    }
}

public class arvoreBinaria {

    private static Node raiz;

    arvoreBinaria(int key) {
        raiz = new Node(key);
    }

    arvoreBinaria() {
        raiz = null;
    }

    private Node insercao(Node atual, long valor) {
        if (atual == null) {
            return new Node(valor);
        }
        if (valor <= atual.valor) {
            atual.esquerda = insercao(atual.esquerda, valor);
        } else {
            atual.direita = insercao(atual.direita, valor);
        }

        return atual;
    }
    public void inserir(long l) {
        raiz = insercao(raiz, l);
    }

    public void percorrerInordem(Node no) {
        if (no != null) {
            percorrerInordem(no.esquerda);
            System.out.print(" " + no.valor);
            percorrerInordem(no.direita);
        }
    }

    public void percorrerPreordem(Node no) {
        if (no != null) {
            System.out.print(" " + no.valor);
            percorrerPreordem(no.esquerda);
            percorrerPreordem(no.direita);
        }
    }

    public void percorrerPosordem(Node no) {
        if (no != null) {
            percorrerPosordem(no.esquerda);
            percorrerPosordem(no.direita);
            System.out.print(" " + no.valor);
        }
    }

    public boolean buscar(long valor) {
        return busca(raiz, valor);
    }

    private boolean busca(Node atual, long valor) {
        if (atual == null) {
            return false;
        }
        if (valor == atual.valor) {
            return true;
        }
        return valor < atual.valor ? busca(atual.esquerda, valor) : busca(atual.direita, valor);
    }

    private Node removerMinimo(Node atual) {
        if (atual == null) {
            return atual;
        }
        if (atual.esquerda == null) {
            return atual.direita;
        }
        atual.esquerda = removerMinimo(atual.esquerda);
        return atual;
    }

    private Node removerMaximo(Node atual) {
        if (atual == null) {
            return atual;
        }
        if (atual.direita == null) {
            return atual.esquerda;
        }
        atual.direita = removerMaximo(atual.direita);
        return atual;
    }

    private Node removerEscolha(Node atual, long valor) {
        if (atual == null) {
            return atual;
        }

        if (valor < atual.valor) {
            atual.esquerda = removerEscolha(atual.esquerda, valor);
        } else if (valor > atual.valor) {
            atual.direita = removerEscolha(atual.direita, valor);
        } else {
            if (atual.esquerda == null) {
                return atual.direita;
            } else if (atual.direita == null) {
                return atual.esquerda;
            }

            atual.valor = sucessor(atual.direita);
            atual.direita = removerEscolha(atual.direita, atual.valor);
        }

        return atual;
    }

    private long sucessor(Node raiz) {
        raiz = raiz.esquerda;
        while (raiz.direita != null) {
            raiz = raiz.direita;
        }
        return raiz.valor;
    }

    public static void visualizarArvoreBinaria(Node raiz) {
        Graph graph = new SingleGraph("Arvore Binaria");
        
        graph.setAttribute("ui.stylesheet",
                "node { size: 30px; fill-color: #6E0DFD, #2980b9; text-size: 14; text-color: #ffffff; text-style: bold; }" +
                "edge { fill-color: #7f8c8d; size: 2px; }");       
        
        addNodesELinhas(graph, raiz, null, "");

        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");

        graph.display();
    }

    private static void addNodesELinhas(Graph graph, Node node, org.graphstream.graph.Node parentNode, String parentEdgeId) {
        if (node == null) {
            return;
        }

        String nodeId = String.valueOf(node.valor) + System.identityHashCode(node);

        org.graphstream.graph.Node currentNode = graph.addNode(nodeId);
        currentNode.setAttribute("ui.label", String.valueOf(node.valor));

        if (parentNode == null) {
            currentNode.setAttribute("ui.style", "fill-color: #00ff00;");
        }

        if (parentNode != null) {
            String edgeId = parentEdgeId + nodeId;
            graph.addEdge(edgeId, parentNode.getId(), currentNode.getId());
        }

        addNodesELinhas(graph, node.esquerda, currentNode, "L");
        addNodesELinhas(graph, node.direita, currentNode, "R");
    }

    public static void main(String[] args) {
    	System.setProperty("org.graphstream.ui", "swing"); 
        arvoreBinaria arvore = new arvoreBinaria();
        int valor;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("(1) Inserir número\n(2) Remover menor número\n(3) Remover maior número\n(4) Remover número específico\n(5) Buscar número\n(6) Sair");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Quantos valores deseja inserir: ");
                    valor = scanner.nextInt();
                    for (int i = 0; i < valor; i++) {
                        int novoValor = ThreadLocalRandom.current().nextInt(valor + 1);
                        arvore.inserir(novoValor);
                        }

                    visualizarArvoreBinaria(raiz);
                    arvore.percorrerPreordem(raiz);
                    System.out.println("");
                    arvore.percorrerInordem(raiz);
                    System.out.println("");
                    arvore.percorrerPosordem(raiz);
                    break;
                case 2:
                    raiz = arvore.removerMinimo(raiz);
                    visualizarArvoreBinaria(raiz);
                    break;
                case 3:
                    raiz = arvore.removerMaximo(raiz);
                    visualizarArvoreBinaria(raiz);
                    break;
                case 4:
                    System.out.println("Insira o valor para remover: ");
                    valor = scanner.nextInt();
                    raiz = arvore.removerEscolha(raiz, valor);
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
                	visualizarArvoreBinaria(raiz);
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}