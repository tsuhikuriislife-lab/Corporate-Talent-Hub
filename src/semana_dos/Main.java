package semana_dos;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/**
 * ============================================================================
 *  CORPORATE TALENT HUB
 * ----------------------------------------------------------------------------
 *  Módulo de evaluación de talento corporativo.
 * <p>
 *  OBJETIVO PEDAGÓGICO:
 *  Este archivo es un ÚNICO programa ejecutable que contrasta, punto por
 *  punto, la sintaxis "LEGACY" de Java 8 con las características modernas
 *  consolidadas en las versiones LTS Java 17 / 21. Cada sección incluye
 *  comentarios "LEGACY vs MODERNO" explicando el porqué del cambio.
 * <p>
 *  No usa librerías externas: solo java.util.Scanner, java.util.Locale y
 *  java.util.InputMismatchException (todas parte del JDK estándar).
 * ============================================================================
 */
public class Main {

    // Capacidad fija del "Talent Hub" para esta demo (arrays simples en vez
    // de colecciones dinámicas, para mantener el foco pedagógico en el
    // manejo de matrices pedido en el requisito 3).
    private static final int MAX_EMPLEADOS = 10;
    private static final int TRIMESTRES = 3;

    // Req. 3 -> Matriz double[][]: filas = empleados, columnas = trimestres.
    private static final double[][] calificaciones = new double[MAX_EMPLEADOS][TRIMESTRES];
    private static final String[] nombres = new String[MAX_EMPLEADOS];
    private static int totalEmpleados = 0;

    /*
     * ============================================================================
     * NOTA TÉCNICA (Req. 4) - Evolución del diagnóstico de errores en Java
     * ============================================================================
     * LEGACY (Java 8):
     *   Ante una cadena de llamadas como  empleado.getContrato().getSalario(),
     *   si algún objeto intermedio es null, Java 8 lanza únicamente:
     *       Exception in thread "main" java.lang.NullPointerException
     *   ...sin indicar CUÁL referencia era null. Depurar esto exigía revisar
     *   manualmente cada eslabón de la cadena con breakpoints o prints.
     *
     * MODERNO (Java 17/21 - "Helpful NullPointerExceptions", activas por
     * defecto desde Java 15 mediante -XX:+ShowCodeDetailsInExceptionMessages):
     *   El mismo error ahora reporta, por ejemplo:
     *       Cannot invoke "Contrato.getSalario()" because the return value
     *       of "Empleado.getContrato()" is null
     *   Esto señala EXACTAMENTE la variable/metodo que devolvió null,
     *   reduciendo drásticamente el tiempo de depuración. Java 17/21 también
     *   mejora en general la legibilidad de los stack traces en la JVM.
     * ============================================================================
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US); // Asegura que "." sea el separador decimal
        boolean continuar = true;

        // ------------------------------------------------------------------
        // Req. 2: Menú activo continuo -> bucle do-while + Scanner.
        // Se usa do-while (y no while) porque el menú debe mostrarse SIEMPRE
        // al menos una vez, sin importar la condición de salida.
        // ------------------------------------------------------------------
        do {
            mostrarMenu();

            int opcion;
            try {
                opcion = leerEntero(scanner, "Seleccione una opción: ");
            } catch (InputMismatchException e) {
                // Req. 4: captura de InputMismatchException para blindar el
                // programa ante entradas no numéricas (ej. el usuario escribe "abc").
                System.out.println("⚠️  Entrada inválida. Ingrese un número entero.");
                scanner.nextLine(); // limpiar el token inválido del buffer
                continue;
            }

            // ----------------------------------------------------------------
            // Req. 1 (LEGACY): switch clásico con "case : break;"
            // ----------------------------------------------------------------
            // En Java 8, cada "case" requiere un "break;" explícito. Si se
            // olvida, el flujo de ejecución "cae" (fall-through) silenciosamente
            // al siguiente case sin avisar en tiempo de compilación: es una de
            // las fuentes de bugs más clásicas y difíciles de detectar en
            // revisiones de código. Compárese con la versión MODERNA de
            // obtenerCategoriaSalarial() más abajo, donde esto es imposible.
            // ----------------------------------------------------------------
            switch (opcion) {
                case 1:
                    registrarEmpleado(scanner);
                    break;
                case 2:
                    evaluarEmpleado(scanner);
                    break;
                case 3:
                    listarReporteCompleto();
                    break;
                case 4:
                    System.out.println("Saliendo del Corporate Talent Hub...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no reconocida. Intente nuevamente.");
                    break;
            }

        } while (continuar);

        scanner.close();
    }

    // ========================================================================
    // MENÚ
    // ========================================================================
    private static void mostrarMenu() {
        System.out.println("\n──────────────── CORPORATE TALENT HUB ────────────────");
        System.out.println("1. Registrar nuevo empleado");
        System.out.println("2. Evaluar empleado (cargar calificaciones trimestrales)");
        System.out.println("3. Ver reporte completo de talento");
        System.out.println("4. Salir");
        System.out.println("────────────────────────────────────────────────────");
    }

    // ========================================================================
    // REGISTRO DE EMPLEADOS
    // ========================================================================
    private static void registrarEmpleado(Scanner scanner) {
        if (totalEmpleados >= MAX_EMPLEADOS) {
            System.out.println("⚠️  Capacidad máxima del Talent Hub alcanzada.");
            return;
        }

        System.out.print("Nombre del nuevo empleado: ");
        String nombre = scanner.nextLine();

        nombres[totalEmpleados] = nombre;
        totalEmpleados++;

        System.out.println("✅ Empleado \"" + nombre + "\" registrado con ID #" + (totalEmpleados - 1));
    }

    private static void listarNombresRegistrados() {
        System.out.println("Empleados registrados:");
        for (int i = 0; i < totalEmpleados; i++) {
            System.out.println("  [" + i + "] " + nombres[i]);
        }
    }

    // ========================================================================
    // CAPTURA DE CALIFICACIONES (Req. 2: var + validaciones if/else)
    // ========================================================================
    private static void evaluarEmpleado(Scanner scanner) {
        if (totalEmpleados == 0) {
            System.out.println("⚠️  No hay empleados registrados aún.");
            return;
        }

        listarNombresRegistrados();

        int id;
        try {
            id = leerEntero(scanner, "Ingrese el ID del empleado a evaluar: ");
        } catch (InputMismatchException e) {
            System.out.println("⚠️  ID inválido. Debe ser un número entero.");
            scanner.nextLine();
            return;
        }

        // Req. 2: validación de rango con if/else ANTES de procesar los datos.
        if (id < 0 || id >= totalEmpleados) {
            System.out.println("⚠️  ID fuera de rango. Empleados válidos: 0 a " + (totalEmpleados - 1));
            return;
        } else {
            System.out.println("Ingrese las calificaciones (escala 0.0 a 10.0) para \"" + nombres[id] + "\":");
        }

        for (int trimestre = 0; trimestre < TRIMESTRES; trimestre++) {
            boolean valorValido = false;

            while (!valorValido) {
                try {
                    // ------------------------------------------------------------
                    // Req. 2: uso de "var" (Java 11+) para variables locales.
                    // ------------------------------------------------------------
                    // LEGACY (Java 8):   double nota = scanner.nextDouble();
                    // MODERNO (Java 11+): var nota = scanner.nextDouble();
                    //   El compilador infiere el tipo (double) a partir del valor
                    //   de retorno de nextDouble() en tiempo de COMPILACIÓN.
                    //   IMPORTANTE: "var" no es tipado dinámico ni un Object;
                    //   sigue siendo fuertemente tipado, solo se evita la
                    //   redundancia de escribir "double" dos veces en la misma línea.
                    // ------------------------------------------------------------
                    var nota = leerDouble(scanner, "  Trimestre " + (trimestre + 1) + ": ");

                    // Req. 2: validación de rango con if/else antes de procesar.
                    if (nota < 0.0 || nota > 10.0) {
                        System.out.println("  ⚠️  La calificación debe estar entre 0.0 y 10.0. Intente de nuevo.");
                    } else {
                        calificaciones[id][trimestre] = nota;
                        valorValido = true;
                    }
                } catch (InputMismatchException e) {
                    // Req. 4: blindaje ante entradas no numéricas al capturar notas.
                    System.out.println("  ⚠️  Debe ingresar un valor numérico (ej. 8.5).");
                    scanner.nextLine(); // limpiar el token inválido del buffer
                }
            }
        }

        System.out.println("✅ Calificaciones registradas para \"" + nombres[id] + "\".\n");
    }

    // ========================================================================
    // REPORTE (Req. 3: matriz + bucles anidados + casting)
    // ========================================================================
    private static void listarReporteCompleto() {
        if (totalEmpleados == 0) {
            System.out.println("⚠️  No hay empleados registrados aún.");
            return;
        }

        System.out.println("\n===================== REPORTE DE TALENTO =====================");

        // Req. 3: bucles for ANIDADOS para recorrer la matriz double[][]
        // y calcular el promedio de cada empleado.
        for (int i = 0; i < totalEmpleados; i++) {
            double suma = 0.0;

            for (int t = 0; t < TRIMESTRES; t++) {
                suma += calificaciones[i][t];
            }

            double promedio = suma / TRIMESTRES;

            // ------------------------------------------------------------------
            // Req. 3: casting explícito double -> int ("Puntaje Simplificado").
            // ------------------------------------------------------------------
            // Java NUNCA convierte automáticamente un double a int (sería una
            // conversión "estrechante" con posible pérdida de información),
            // por eso se exige el casting explícito "(int)". Esto además
            // TRUNCA la parte decimal (no redondea): 8.9 -> 8, no 9.
            // Se documenta aquí la pérdida de precisión: el "Puntaje
            // Simplificado" es solo un indicador rápido de lectura, NUNCA
            // debe usarse para cálculos financieros o de bonos reales.
            // ------------------------------------------------------------------
            int puntajeSimplificado = (int) promedio;

            // Req. 4: operador ternario para el estado de promoción.
            String estadoPromocion = (promedio >= 8.0) ? "APTO PARA PROMOCIÓN" : "NO APTO AÚN";

            String categoria = obtenerCategoriaSalarial(promedio);

            System.out.printf(
                    "ID %d | %-15s | Promedio: %.2f | Puntaje Simplificado: %d | %-20s | %s%n",
                    i, nombres[i], promedio, puntajeSimplificado, estadoPromocion, categoria);
        }

        System.out.println("================================================================\n");
    }

    // ========================================================================
    // Req. 1 (MODERNO): Switch Expression con sintaxis de flecha "->"
    // ========================================================================
    private static String obtenerCategoriaSalarial(double promedioFinal) {
        // Los switch de Java (clásico y expression) solo evalúan etiquetas
        // discretas, no rangos continuos de tipo double. Por eso convertimos
        // el promedio a un "bracket" entero (mismo concepto de casting del
        // Req. 3, aplicado aquí para habilitar el switch).
        int bracket = (int) Math.floor(promedioFinal);

        // ------------------------------------------------------------------
        // MODERNO (Java 14+, estable desde Java 17/21): Switch Expression "->"
        // ------------------------------------------------------------------
        // Ventajas frente al switch LEGACY del menú principal (main):
        //   1. No requiere "break;" -> elimina por completo el riesgo de
        //      fall-through: cada rama "->" ejecuta SOLO su propio caso.
        //   2. Es una EXPRESIÓN: retorna un valor directamente con el propio
        //      switch, sin variables temporales mutables ni "return" repetidos
        //      dentro de cada case.
        //   3. Es más BREVE: permite agrupar varias etiquetas en una sola
        //      línea, por ejemplo "case 9, 10 ->", algo imposible de escribir
        //      de forma compacta en el switch clásico de Java 8.
        //   4. El compilador puede exigir exhaustividad (todas las rutas
        //      cubiertas), reduciendo bugs de casos no contemplados.
        // ------------------------------------------------------------------
        return switch (bracket) {
            case 9, 10 -> "Categoría A - Alto Desempeño (Bono Completo)";
            case 7, 8 -> "Categoría B - Desempeño Sólido (Bono Parcial)";
            case 5, 6 -> "Categoría C - Desempeño Estándar (Sin Bono)";
            default -> "Categoría D - Bajo Desempeño (Plan de Mejora)";
        };
    }

    // ========================================================================
    // UTILIDADES DE LECTURA (Req. 4: manejo de InputMismatchException)
    // ========================================================================
    // Nota: estos métodos NO capturan la excepción internamente a propósito;
    // la propagan hacia el llamador para que cada punto del menú decida cómo
    // reaccionar (mensaje distinto según si se esperaba un ID, una opción de
    // menú o una calificación). El try-catch real vive en cada llamador.
    private static int leerEntero(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        int valor = scanner.nextInt();
        scanner.nextLine(); // consumir el salto de línea pendiente en el buffer
        return valor;
    }

    private static double leerDouble(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        double valor = scanner.nextDouble();
        scanner.nextLine(); // consumir el salto de línea pendiente en el buffer
        return valor;
    }
}