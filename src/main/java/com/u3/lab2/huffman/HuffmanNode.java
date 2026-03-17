package com.u3.lab2.huffman;

/**
 * Nodo del árbol de Huffman implementado como record Java 17.
 *
 * <p>
 * Las hojas representan símbolos con su frecuencia.
 * Los nodos internos agrupan frecuencias con símbolo nulo.
 *
 * <p>
 * Implementa Comparable para ser usado en PriorityQueue
 * como min-heap ordenado por frecuencia ascendente.
 */
public record HuffmanNode(char symbol, int freq,
        HuffmanNode left, HuffmanNode right)
        implements Comparable<HuffmanNode> {

    /**
     * Compara nodos por frecuencia ascendente para el min-heap.
     *
     * @param other nodo con el que se compara
     * @return valor negativo, cero o positivo según la frecuencia
     */
    @Override
    public int compareTo(HuffmanNode other) {
        return Integer.compare(this.freq, other.freq);
    }

    /**
     * Indica si este nodo es una hoja del árbol.
     * Las hojas no tienen hijos y representan un símbolo.
     *
     * @return true si el nodo es hoja, false si es nodo interno
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }
}