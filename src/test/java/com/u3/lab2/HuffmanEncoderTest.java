package com.u3.lab2;

import com.u3.lab2.huffman.HuffmanEncoder;
import com.u3.lab2.huffman.HuffmanNode;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para HuffmanEncoder con JUnit 5.
 * Verifica corrección del árbol, propiedad de prefijo y compresión.
 */
class HuffmanEncoderTest {

    /** Frecuencias para "aabbbcccc": a=2, b=3, c=4 */
    private Map<Character, Integer> freqsAabbbcccc() {
        return Map.of('a', 2, 'b', 3, 'c', 4);
    }

    @Test
    void testArbolSeConstruye() {
        HuffmanNode root = HuffmanEncoder.buildTree(freqsAabbbcccc());
        assertNotNull(root);
        assertEquals(9, root.freq());
    }

    @Test
    void testCodigosGenerados() {
        HuffmanNode root = HuffmanEncoder.buildTree(freqsAabbbcccc());
        Map<Character, String> codes = HuffmanEncoder.generateCodes(root);
        assertTrue(codes.containsKey('a'));
        assertTrue(codes.containsKey('b'));
        assertTrue(codes.containsKey('c'));
    }

    @Test
    void testPropiedadDePrefijo() {
        HuffmanNode root = HuffmanEncoder.buildTree(freqsAabbbcccc());
        Map<Character, String> codes = HuffmanEncoder.generateCodes(root);
        // Ningún código debe ser prefijo de otro
        for (Map.Entry<Character, String> e1 : codes.entrySet()) {
            for (Map.Entry<Character, String> e2 : codes.entrySet()) {
                if (!e1.getKey().equals(e2.getKey())) {
                    assertFalse(
                            e1.getValue().startsWith(e2.getValue()),
                            "El código de '" + e1.getKey() +
                                    "' es prefijo de '" + e2.getKey() + "'");
                }
            }
        }
    }

    @Test
    void testCompresionMenorQueASCII() {
        String texto = "aabbbcccc";
        Map<Character, Integer> freqs = Map.of('a', 2, 'b', 3, 'c', 4);
        HuffmanNode root = HuffmanEncoder.buildTree(freqs);
        Map<Character, String> codes = HuffmanEncoder.generateCodes(root);

        // Longitud codificada con Huffman
        int huffmanBits = 0;
        for (char c : texto.toCharArray())
            huffmanBits += codes.get(c).length();

        // Longitud con ASCII estándar (8 bits por carácter)
        int asciiBits = texto.length() * 8;

        assertTrue(huffmanBits < asciiBits,
                "Huffman (" + huffmanBits + " bits) debe ser menor que ASCII (" + asciiBits + " bits)");
    }

    @Test
    void testSimboloUnico() {
        HuffmanNode root = HuffmanEncoder.buildTree(Map.of('x', 5));
        Map<Character, String> codes = HuffmanEncoder.generateCodes(root);
        assertEquals("0", codes.get('x'));
    }
}