package com.u3.lab2.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Construcción del árbol de Huffman y generación de códigos binarios.
 *
 * <p>
 * Implementa el algoritmo Greedy de Huffman:
 * en cada paso extrae los dos nodos de menor frecuencia del min-heap
 * y los combina en un nodo interno, garantizando el árbol de prefijo
 * óptimo. Complejidad: O(n log n) donde n es el número de símbolos únicos.
 */
public class HuffmanEncoder {

    /**
     * Construye el árbol de Huffman a partir de un mapa de frecuencias.
     *
     * <p>
     * Complejidad: O(n log n) — n inserciones y extracciones del heap.
     *
     * @param freqs mapa de carácter a frecuencia (no nulo, no vacío)
     * @return raíz del árbol de Huffman construido
     * @pre freqs no es nulo y contiene al menos un símbolo
     * @post retorna un árbol de prefijo óptimo para las frecuencias dadas
     */
    public static HuffmanNode buildTree(Map<Character, Integer> freqs) {
        PriorityQueue<HuffmanNode> heap = new PriorityQueue<>();
        freqs.forEach((c, f) -> heap.add(new HuffmanNode(c, f, null, null)));
        while (heap.size() > 1) {
            HuffmanNode a = heap.poll();
            HuffmanNode b = heap.poll();
            // Nodo interno: símbolo nulo, frecuencia = suma de hijos
            heap.add(new HuffmanNode('\0', a.freq() + b.freq(), a, b));
        }
        return heap.poll();
    }

    /**
     * Genera el mapa de carácter a código binario (String de 0s y 1s).
     *
     * <p>
     * Complejidad: O(n) — recorrido DFS del árbol con n hojas.
     * El código resultante cumple la propiedad de prefijo: ningún
     * código es prefijo de otro.
     *
     * @param root raíz del árbol de Huffman (no nula)
     * @return mapa con el código de prefijo de cada símbolo
     * @pre root no es nulo
     * @post ningún código en el resultado es prefijo de otro
     */
    public static Map<Character, String> generateCodes(HuffmanNode root) {
        Map<Character, String> codes = new HashMap<>();
        if (root.isLeaf()) {
            codes.put(root.symbol(), "0");
            return codes;
        }
        traverse(root, "", codes);
        return codes;
    }

    /**
     * Recorre el árbol en DFS asignando prefijos binarios a las hojas.
     *
     * @param node   nodo actual del recorrido
     * @param prefix prefijo acumulado hasta el nodo actual
     * @param codes  mapa donde se almacenan los códigos generados
     */
    private static void traverse(HuffmanNode node, String prefix,
            Map<Character, String> codes) {
        if (node.isLeaf()) {
            codes.put(node.symbol(), prefix);
            return;
        }
        traverse(node.left(), prefix + "0", codes);
        traverse(node.right(), prefix + "1", codes);
    }
}