package com.u3.lab2.nqueens;

/**
 * N-Queens: variante naive vs bitmask para comparación de eficiencia.
 *
 * <p>
 * Resuelve el problema de colocar n reinas en un tablero n×n
 * sin que ninguna se ataque, usando dos estrategias de poda distintas.
 */
public class NQueens {

    /**
     * Cuenta soluciones al problema N-Queens usando verificación iterativa.
     *
     * <p>
     * Complejidad: O(n!) en peor caso — verifica cada columna
     * iterando por todas las reinas previas para detectar conflictos.
     *
     * @param n tamaño del tablero (n >= 1)
     * @return número total de soluciones válidas
     */
    public static long solveNaive(int n) {
        return naiveHelper(n, 0, new int[n]);
    }

    private static long naiveHelper(int n, int row, int[] cols) {
        if (row == n)
            return 1;
        long count = 0;
        for (int col = 0; col < n; col++) {
            if (isValid(cols, row, col)) {
                cols[row] = col;
                count += naiveHelper(n, row + 1, cols);
            }
        }
        return count;
    }

    /**
     * Verifica si colocar una reina en (row, col) es válido.
     *
     * @param cols arreglo de columnas ocupadas por fila
     * @param row  fila actual
     * @param col  columna candidata
     * @return true si no hay conflicto de columna ni diagonal
     */
    private static boolean isValid(int[] cols, int row, int col) {
        for (int r = 0; r < row; r++) {
            if (cols[r] == col)
                return false; // misma columna
            if (Math.abs(cols[r] - col) == row - r)
                return false; // diagonal
        }
        return true;
    }

    /**
     * Cuenta soluciones al problema N-Queens usando manipulación de bits.
     *
     * <p>
     * Codifica columnas y diagonales ocupadas en enteros de 32 bits.
     * La verificación de conflictos es O(1) por operaciones de bits,
     * eliminando la iteración de la variante naive.
     *
     * <p>
     * Complejidad: O(n!) en peor caso pero con constante mucho menor
     * que naive por la eliminación inmediata de posiciones inválidas.
     *
     * @param n tamaño del tablero (n >= 1, n <= 30 por límite de int)
     * @return número total de soluciones válidas
     */
    public static long solveBitmask(int n) {
        return bitmaskHelper(n, 0, 0, 0, 0);
    }

    /**
     * Auxiliar recursivo de solveBitmask con estado codificado en bits.
     *
     * @param n     tamaño del tablero
     * @param row   fila actual
     * @param cols  bitmask de columnas ocupadas
     * @param diag1 bitmask de diagonales descendentes ocupadas (shift izquierda)
     * @param diag2 bitmask de diagonales ascendentes ocupadas (shift derecha)
     * @return número de soluciones a partir del estado actual
     */
    private static long bitmaskHelper(int n, int row,
            int cols, int diag1, int diag2) {
        if (row == n)
            return 1;
        long count = 0;
        // Columnas disponibles: las que no están bloqueadas por cols, diag1 ni diag2
        int available = ((1 << n) - 1) & ~(cols | diag1 | diag2);
        while (available != 0) {
            int bit = available & (-available); // bit menos significativo libre
            available -= bit;
            count += bitmaskHelper(n, row + 1,
                    cols | bit,
                    (diag1 | bit) << 1, // diagonal desciende → shift izquierda
                    (diag2 | bit) >> 1); // diagonal asciende → shift derecha
        }
        return count;
    }
}