# Estrategias de Diseño de Algoritmos — Lab 2

**Curso:** Diseño de Algoritmos y Sistemas — Unidad 3  
**Actividad:** Post-Contenido 2  
**Programa:** Ingeniería de Sistemas — Universidad de Santander (UDES)  
**Año:** 2026

---

## Descripción

Se implementa el algoritmo de Huffman como caso de estudio de Greedy
con demostración de corrección, y N-Queens con Backtracking y poda
por bitmask como caso de estudio de búsqueda exhaustiva eficiente.
Se analiza empíricamente la reducción del espacio de búsqueda
producida por la poda y se compara el comportamiento de ambas
variantes.

---

## Requisitos

- Java 17+
- Maven 3.8+

```bash
java --version
mvn --version
```

---

## Instrucciones de build y ejecución

```bash
# 1. Clonar el repositorio
git clone https://github.com/<usuario>/algoritmos-castellanos-post2-u3.git
cd algoritmos-castellanos-post2-u3

# 2. Compilar y ejecutar tests
mvn clean test

# 3. Empaquetar
mvn clean package -q

# 4. Ejecutar demostración
java -cp target/lab2-estrategias-1.0.0.jar com.u3.lab2.huffman.Main
```

---

## Experimento 1: Algoritmo de Huffman (Greedy)

### Ejemplo con "aabbbcccc"

**Frecuencias:**

| Símbolo | Frecuencia |
| ------- | ---------- |
| a       | 2          |
| b       | 3          |
| c       | 4          |

**Árbol de Huffman (formato texto):**

```
        [9]
       /   \
     [5]    c(4)
    /   \
  a(2)  b(3)
```

**Códigos generados:**

| Símbolo | Frecuencia | Código | Bits |
| ------- | ---------- | ------ | ---- |
| c       | 4          | 0      | 1    |
| a       | 2          | 10     | 2    |
| b       | 3          | 11     | 2    |

**Comparación de longitud:**

| Método    | Bits totales |
| --------- | ------------ |
| Huffman   | 14 bits      |
| ASCII     | 72 bits      |
| Reducción | 80.6%        |

### Corrección del algoritmo Greedy

El algoritmo de Huffman es óptimo porque en cada paso selecciona
los dos nodos de menor frecuencia del min-heap, garantizando que
los símbolos más frecuentes reciben los códigos más cortos. La
propiedad de prefijo se cumple por construcción del árbol binario:
ningún código es prefijo de otro porque los símbolos solo aparecen
en las hojas. Los 5 tests JUnit 5 verifican estas propiedades
incluyendo la verificación exhaustiva de prefijos para todos los
pares de símbolos.

---

## Experimento 2: N-Queens con Backtracking

### Tabla de análisis (n = 6..14)

| n   | Soluciones | Nodos Naive | Nodos Bitmask | Razón  |
| --- | ---------- | ----------- | ------------- | ------ |
| 6   | 4          | 153         | 153           | 1.0000 |
| 7   | 40         | 552         | 552           | 1.0000 |
| 8   | 92         | 2.057       | 2.057         | 1.0000 |
| 9   | 352        | 8.394       | 8.394         | 1.0000 |
| 10  | 724        | 35.539      | 35.539        | 1.0000 |
| 11  | 2.680      | 166.926     | 166.926       | 1.0000 |
| 12  | 14.200     | 856.189     | 856.189       | 1.0000 |
| 13  | 73.712     | 4.674.890   | 4.674.890     | 1.0000 |
| 14  | 365.596    | 27.358.553  | 27.358.553    | 1.0000 |

### Análisis de la poda

Los resultados muestran que ambas variantes exploran exactamente
el mismo número de nodos para todo n evaluado, con una razón de
reducción de 1.0 en todos los casos. Esto se debe a que tanto
la variante naive como la variante bitmask implementan backtracking
con poda completa: ambas descartan posiciones inválidas antes de
realizar la llamada recursiva, recorriendo el mismo árbol de
búsqueda podado. La diferencia entre las dos implementaciones no
radica en el número de ramas exploradas sino en el costo por nodo:
la variante naive verifica conflictos iterando por todas las reinas
previas en O(n) por verificación, mientras que la variante bitmask
realiza la misma verificación en O(1) mediante operaciones de bits
que codifican columnas y diagonales ocupadas en enteros de 32 bits.

El número de nodos explorados crece significativamente con n: de
153 nodos para n=6 a más de 27 millones para n=14, lo que ilustra
el crecimiento superexponencial del espacio de búsqueda sin poda.
Con poda ambas variantes reducen el árbol completo de n^n nodos
(más de 11 billones para n=14 sin poda) a apenas 27 millones de
nodos explorados, una reducción de más de 400.000x respecto al
espacio de búsqueda sin restricciones. La densidad de soluciones
válidas decrece con n (365.596 soluciones de 27 millones de nodos
para n=14, aproximadamente 1.3%), lo que explica por qué la poda
es cada vez más efectiva para tableros grandes: la mayoría de las
ramas son descartadas tempranamente por conflictos de columna o
diagonal.

---

## Conclusión

El algoritmo de Huffman demuestra que la estrategia Greedy produce
soluciones óptimas cuando la elección local (símbolo de menor
frecuencia) garantiza el óptimo global por la propiedad de árbol
de prefijo. N-Queens demuestra que el backtracking con poda reduce
el espacio de búsqueda de forma drástica: de más de 11 billones de
nodos posibles a 27 millones para n=14. La variante bitmask no
reduce el número de nodos explorados respecto a naive porque ambas
implementan la misma poda lógica, pero sí reduce el tiempo por
nodo al reemplazar la verificación iterativa O(n) por operaciones
de bits O(1).
