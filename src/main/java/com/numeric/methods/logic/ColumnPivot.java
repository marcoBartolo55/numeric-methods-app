package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;

public class ColumnPivot {

    // Clase auxiliar para guardar el estado de las variables y pasarlas a la TableView
    public static class VariableResult {
        private final String variable;
        private final String value;

        public VariableResult(String variable, String value) {
            this.variable = variable;
            this.value = value;
        }

        public String getVariable() { return variable; }
        public String getValue() { return value; }
    }

    /**
     * Resuelve un sistema Ax = b usando Eliminación Gaussiana con Pivoteo Parcial Escalado
     * @param A Matriz de coeficientes
     * @param b Vector de términos independientes
     * @param decimals Número de decimales para el formato del log
     * @param sb StringBuilder para acumular la bitácora de operaciones
     * @return Lista con los resultados de cada variable X_i
     */
    public static List<VariableResult> solveWithScaledPartialPivot(double[][] A, double[] b, int decimals, StringBuilder sb) {
        int n = A.length;
        int[] index = new int[n]; // Vector de punteros a filas
        double[] s = new double[n]; // Vector de escala

        String format = "%." + decimals + "f";

        sb.append("=== INICIANDO PIVOTEO PARCIAL ESCALADO ===\n\n");

        // 1. Inicializar el vector de punteros e identificar los máximos por fila (Escalas)
        sb.append("Paso 1: Determinando el Vector de Escala (s):\n");
        for (int i = 0; i < n; i++) {
            index[i] = i;
            double maxVal = 0.0;
            for (int j = 0; j < n; j++) {
                maxVal = Math.max(maxVal, Math.abs(A[i][j]));
            }
            if (maxVal == 0.0) {
                sb.append("Error: El sistema no tiene solución única (Fila ").append(i + 1).append(" llena de ceros).\n");
                return null;
            }
            s[i] = maxVal;
            sb.append(String.format("  Fila %d -> Máximo absoluto (s_%d) = " + format + "\n", (i + 1), (i + 1), s[i]));
        }

        // 2. Proceso de Eliminación hacia adelante
        for (int k = 0; k < n - 1; k++) {
            sb.append(String.format("\n--- Procesando Columna de Pivote k = %d ---\n", k + 1));
            
            // Buscar la fila de pivote óptima basándose en los cocientes |a_ik| / s_i
            int maxRowIndex = k;
            double maxRatio = Math.abs(A[index[k]][k]) / s[index[k]];
            sb.append(String.format("  Cocientes (|a_ik| / s_i) para columna %d:\n", k + 1));
            sb.append(String.format("    Fila %d: |" + format + "| / " + format + " = " + format + "\n", 
                    index[k] + 1, A[index[k]][k], s[index[k]], maxRatio));

            for (int i = k + 1; i < n; i++) {
                double ratio = Math.abs(A[index[i]][k]) / s[index[i]];
                sb.append(String.format("    Fila %d: |" + format + "| / " + format + " = " + format + "\n", 
                        index[i] + 1, A[index[i]][k], s[index[i]], ratio));
                if (ratio > maxRatio) {
                    maxRatio = ratio;
                    maxRowIndex = i;
                }
            }

            // Intercambiar punteros de fila si es necesario
            if (maxRowIndex != k) {
                int temp = index[k];
                index[k] = index[maxRowIndex];
                index[maxRowIndex] = temp;
                sb.append(String.format("  [INTERCAMBIO]: La Fila %d se vuelve la fila pivote actual (Fila %d original).\n", 
                        (k + 1), index[k] + 1));
            } else {
                sb.append(String.format("  [MANTENER]: La Fila %d se mantiene como pivote.\n", index[k] + 1));
            }

            int pivotRow = index[k];
            if (Math.abs(A[pivotRow][k]) < 1e-12) {
                sb.append("Error: Elemento de pivote cercano a cero. El sistema podría no tener solución única.\n");
                return null;
            }

            // Eliminar los elementos debajo del pivote
            for (int i = k + 1; i < n; i++) {
                int currentRow = index[i];
                double factor = A[currentRow][k] / A[pivotRow][k];
                sb.append(String.format("    Eliminando Fila %d: Multiplicador m = " + format + "\n", currentRow + 1, factor));
                
                A[currentRow][k] = 0.0; // Forzar el cero debajo de la diagonal
                for (int j = k + 1; j < n; j++) {
                    A[currentRow][j] -= factor * A[pivotRow][j];
                }
                b[currentRow] -= factor * b[pivotRow];
            }
        }

        // 3. Sustitución hacia atrás (Back-substitution)
        sb.append("\n=== SUSTITUCIÓN HACIA ATRÁS ===\n");
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            int pivotRow = index[i];
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[pivotRow][j] * x[j];
            }
            x[i] = (b[pivotRow] - sum) / A[pivotRow][i];
            sb.append(String.format("  x_%d = (" + format + " - (" + format + ")) / " + format + " = " + format + "\n", 
                    (i + 1), b[pivotRow], sum, A[pivotRow][i], x[i]));
        }

        // Empaquetar resultados para la UI
        List<VariableResult> results = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            results.add(new VariableResult("x_" + (i + 1), String.format(format, x[i])));
        }
        return results;
    }
}