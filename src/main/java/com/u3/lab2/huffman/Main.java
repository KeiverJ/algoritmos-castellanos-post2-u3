package com.u3.lab2.huffman;

import com.u3.lab2.nqueens.NQueensAnalysis;

import java.util.Map;

/**
 * Clase principal que demuestra Huffman y el análisis de N-Queens.
 */
public class Main {

    /**
     * Punto de entrada de la aplicación.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        demoHuffman();
        demoNQueens();
    }

    /**
     * Demuestra el algoritmo de Huffman con la cadena "aabbbcccc".
     */
    private static void demoHuffman() {
        System.out.println("=== Algoritmo de Huffman ===");
        System.out.println("Texto: \"aabbbcccc\"");
        System.out.println("Frecuencias: a=2, b=3, c=4");

        Map<Character, Integer> freqs = Map.of('a', 2, 'b', 3, 'c', 4);
        HuffmanNode root = HuffmanEncoder.buildTree(freqs);
        Map<Character, String> codes = HuffmanEncoder.generateCodes(root);

        System.out.println("\nCódigos generados:");
        codes.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.printf("  '%c' (freq=%d) -> %s (%d bits)%n",
                        e.getKey(), freqs.get(e.getKey()),
                        e.getValue(), e.getValue().length()));

        // Calcular longitud total
        String texto = "aabbbcccc";
        int huffmanBits = 0;
        for (char c : texto.toCharArray())
            huffmanBits += codes.get(c).length();
        int asciiBits = texto.length() * 8;

        System.out.printf("%nLongitud Huffman:  %d bits%n", huffmanBits);
        System.out.printf("Longitud ASCII:    %d bits%n", asciiBits);
        System.out.printf("Reducción:         %.1f%%%n",
                (1.0 - (double) huffmanBits / asciiBits) * 100);
    }

    /**
     * Ejecuta el análisis de N-Queens para n = 6..14 e imprime la tabla.
     */
    private static void demoNQueens() {
        System.out.println("\n=== Análisis N-Queens: Naive vs Bitmask ===");
        System.out.printf("%-4s %-10s %-15s %-15s %-10s%n",
                "n", "Soluciones", "Nodos Naive", "Nodos Bitmask", "Razón");
        System.out.println("-".repeat(58));

        for (int n = 6; n <= 14; n++) {
            NQueensAnalysis.Result r = NQueensAnalysis.analyze(n);
            System.out.printf("%-4d %-10d %-15d %-15d %.4f%n",
                    r.n(), r.solutions(),
                    r.nodesNaive(), r.nodesBitmask(),
                    r.reductionRatio());
        }
    }
}