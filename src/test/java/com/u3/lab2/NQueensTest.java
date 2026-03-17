package com.u3.lab2;

import com.u3.lab2.nqueens.NQueens;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para NQueens con JUnit 5.
 * Verifica que ambas variantes producen resultados idénticos
 * para n = 1..12 usando valores de referencia conocidos.
 */
class NQueensTest {

    /** Valores de referencia para n = 1..12 */
    private static final long[] EXPECTED = {
            0, // índice 0 no usado
            1, // n=1
            0, // n=2
            0, // n=3
            2, // n=4
            10, // n=5
            4, // n=6
            40, // n=7
            92, // n=8
            352, // n=9
            724, // n=10
            2680, // n=11
            14200 // n=12
    };

    @Test
    void testNaiveN1a8() {
        for (int n = 1; n <= 8; n++)
            assertEquals(EXPECTED[n], NQueens.solveNaive(n),
                    "solveNaive falló para n=" + n);
    }

    @Test
    void testBitmaskN1a8() {
        for (int n = 1; n <= 8; n++)
            assertEquals(EXPECTED[n], NQueens.solveBitmask(n),
                    "solveBitmask falló para n=" + n);
    }

    @Test
    void testNaiveN9a12() {
        for (int n = 9; n <= 12; n++)
            assertEquals(EXPECTED[n], NQueens.solveNaive(n),
                    "solveNaive falló para n=" + n);
    }

    @Test
    void testBitmaskN9a12() {
        for (int n = 9; n <= 12; n++)
            assertEquals(EXPECTED[n], NQueens.solveBitmask(n),
                    "solveBitmask falló para n=" + n);
    }

    @Test
    void testAmbosProducenMismoResultado() {
        for (int n = 1; n <= 12; n++)
            assertEquals(NQueens.solveNaive(n), NQueens.solveBitmask(n),
                    "Resultados distintos para n=" + n);
    }
}