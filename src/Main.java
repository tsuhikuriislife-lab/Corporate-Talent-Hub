import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static ArrayList<Empleados> listaEmpleados = new ArrayList<>();

    public static void main(String[] args) {
        String header = """
                =============================
                    Corporate Talent Hub
                =============================
                """;
        JOptionPane.showMessageDialog(null, header);

        boolean continuar = true;

        while (continuar) {
            String menu = """
                    Menú de Inicio
                    1- Mostrar información del usuario
                    2- Agregar información
                    3- Promedio de calificaciones
                    4- Salir
                    """;

            String opcionStr = JOptionPane.showInputDialog(null, menu, "Seleccione una opción", JOptionPane.QUESTION_MESSAGE);

            if (opcionStr == null) {
                continuar = false;
                break;
            }

            int opcion;
            try {
                opcion = Integer.parseInt(opcionStr.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
                continue;
            }

            /*
             * Switch tradicional (case: / break):
             * En este tipo de switch, los "break" son OBLIGATORIOS para evitar el "fall-through".
             * Si se omite un break, la ejecución continuará hacia el siguiente case sin detenerse,
             * ejecutando código no deseado. El compilador NO genera un error por la ausencia de break,
             * lo que puede provocar bugs silenciosos difíciles de detectar en tiempo de ejecución.
             */
            switch (opcion) {
                case 1:
                    mostrarInformacion();
                    break;
                case 2:
                    agregarInformacion();
                    break;
                case 3:
                    calcularPromedioTrimestre();
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "¡Hasta luego!");
                    continuar = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }

    private static void mostrarInformacion() {
        if (listaEmpleados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay empleados registrados.", "Información", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (int i = 0; i < listaEmpleados.size(); i++) {
            Empleados empleado = listaEmpleados.get(i);

            String info = "Empleado #" + (i + 1) + "\n" +
                    "Nombre: " + empleado.getNombre() + "\n" +
                    "ID: " + empleado.getIdEmpleado() + "\n" +
                    "Bono Mensual: " + empleado.getBonoMensual() + "\n" +
                    "Salario Final: " + empleado.calcularSalarioFinal() + "\n" +
                    "Categoría Salarial: " + empleado.obtenerCategoriaSalarial() + "\n" +
                    "¿Obtiene bono?: " + empleado.obtieneBono() + "\n" +
                    "¿Elegible?: " + empleado.validarElegibilidad() + "\n" +
                    "Calificaciones: " + java.util.Arrays.deepToString(empleado.getCalificacionesTrimestres());

            JOptionPane.showMessageDialog(null, info, "Información del Empleado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void calcularPromedioTrimestre() {
        if (listaEmpleados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay empleados registrados.",
                    "Promedio de Calificaciones", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < listaEmpleados.size(); i++) {
            Empleados empleado = listaEmpleados.get(i);
            double[][] calificaciones = empleado.getCalificacionesTrimestres();

            resultado.append("Empleado #").append(i + 1)
                    .append(": ").append(empleado.getNombre()).append("\n");

            if (calificaciones == null || calificaciones.length == 0) {
                resultado.append("  Sin calificaciones registradas.\n\n");
                continue;
            }

            double sumaTotal = 0.0;
            int contadorTotal = 0;

            for (int trimestre = 0; trimestre < calificaciones.length; trimestre++) {
                if (calificaciones[trimestre] == null || calificaciones[trimestre].length == 0) {
                    resultado.append("  Trimestre ").append(trimestre + 1)
                            .append(": Sin calificaciones\n");
                    continue;
                }

                double sumaTrimestre = 0.0;
                for (double cal : calificaciones[trimestre]) {
                    sumaTrimestre += cal;
                    sumaTotal += cal;
                    contadorTotal++;
                }

                double promedioTrimestre = sumaTrimestre / calificaciones[trimestre].length;
                resultado.append("  Trimestre ").append(trimestre + 1)
                        .append(": Promedio = ").append(String.format("%.2f", promedioTrimestre)).append("\n");
            }

            if (contadorTotal > 0) {
                double promedioGeneral = sumaTotal / contadorTotal;

                /*
                 * Casting explícito de double a int:
                 * Al convertir el promedio general (double) a int, se realiza un TRUNCAMIENTO,
                 * es decir, se elimina por completo la parte decimal del valor.
                 *
                 * Esto conlleva a una PÉRDIDA DE PRECISIÓN en la información, ya que:
                 * - El tipo double almacena valores con punto flotante (ej: 85.67)
                 * - El tipo int solo almacena valores enteros (ej: 85)
                 * - La parte fraccionaria (0.67) se descarta sin redondeo
                 *
                 * Ejemplo: si promedioGeneral = 89.97, después del casting promedioEntero = 89
                 * Se pierden 0.97 puntos de información, lo cual puede afectar la evaluación
                 * real del desempeño del empleado.
                 *
                 * Este tipo de casting es una conversión "narrowing" (estrechamiento),
                 * donde se pasa de un tipo de mayor capacidad (double, 64 bits)
                 * a uno de menor capacidad (int, 32 bits), perdiendo tanto la precisión
                 * decimal como el rango de valores representables.
                 */
                int promedioEntero = (int) promedioGeneral;

                resultado.append("  Promedio General (double): ")
                        .append(String.format("%.2f", promedioGeneral)).append("\n");
                // Se muestra el valor truncado para evidenciar la pérdida de precisión
                resultado.append("  Promedio General (int - con pérdida de precisión): ")
                        .append(promedioEntero).append("\n");
            }

            resultado.append("\n");
        }

        JOptionPane.showMessageDialog(null, resultado.toString(),
                "Promedio de Calificaciones", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void agregarInformacion() {
        try (var scanner = new Scanner(System.in)) {
            System.out.println("\n===== Agregar información del empleado =====\n");

            // --- byte: nivelAcceso (rango: -128 a 127) ---
            System.out.print("Nivel de acceso (byte: -128 a 127): ");
            var nivelAccesoInput = scanner.nextInt();
            if (nivelAccesoInput < Byte.MIN_VALUE || nivelAccesoInput > Byte.MAX_VALUE) {
                System.out.println("Error: El valor " + nivelAccesoInput + " excede el rango de byte (" + Byte.MIN_VALUE + " a " + Byte.MAX_VALUE + ").");
                return;
            }
            var nivelAcceso = (byte) nivelAccesoInput;

            // --- short: anioIngreso (rango: -32768 a 32767) ---
            System.out.print("Año de ingreso (short: -32768 a 32767): ");
            var anioIngresoInput = scanner.nextInt();
            if (anioIngresoInput < Short.MIN_VALUE || anioIngresoInput > Short.MAX_VALUE) {
                System.out.println("Error: El valor " + anioIngresoInput + " excede el rango de short (" + Short.MIN_VALUE + " a " + Short.MAX_VALUE + ").");
                return;
            }
            var anioIngreso = (short) anioIngresoInput;

            // --- int: idEmpleado (rango: -2147483648 a 2147483647) ---
            System.out.print("ID del empleado (int): ");
            var idEmpleado = scanner.nextInt();

            // --- long: numeroDocumento (rango amplio, sufijo L) ---
            System.out.print("Número de documento (long): ");
            var numeroDocumento = scanner.nextLong();

            // --- float: puntajeTest (rango: ~1.4E-45 a ~3.4E+38) ---
            System.out.print("Puntaje del test (float: hasta ~3.4E+38): ");
            var puntajeTestInput = scanner.nextDouble();
            if (puntajeTestInput < -Float.MAX_VALUE || puntajeTestInput > Float.MAX_VALUE) {
                System.out.println("Error: El valor " + puntajeTestInput + " excede el rango de float.");
                return;
            }
            var puntajeTest = (float) puntajeTestInput;

            // --- double: salarioBase (rango amplio) ---
            System.out.print("Salario base (double): ");
            var salarioBase = scanner.nextDouble();

            // --- char: tipoContrato (un solo carácter) ---
            System.out.print("Tipo de contrato (char, un solo carácter): ");
            var tipoContratoInput = scanner.next();
            if (tipoContratoInput.length() != 1) {
                System.out.println("Error: Debe ingresar exactamente un carácter para el tipo de contrato.");
                return;
            }
            var tipoContrato = tipoContratoInput.charAt(0);

            // --- boolean: esActivo (true o false) ---
            System.out.print("¿Está activo? (boolean: true/false): ");
            var esActivo = scanner.nextBoolean();

            // --- String: nombre ---
            scanner.nextLine(); // Limpiar buffer
            System.out.print("Nombre del empleado (String): ");
            var nombre = scanner.nextLine();

            // --- int: edad (validación lógica: 18 a 100) ---
            System.out.print("Edad (int: 18 a 100): ");
            var edad = scanner.nextInt();
            if (edad < 18 || edad > 100) {
                System.out.println("Error: La edad debe estar entre 18 y 100.");
                return;
            }

            // --- int: idSede ---
            System.out.print("ID de sede (int): ");
            var idSede = scanner.nextInt();

            // --- double: bonoMensual ---
            System.out.print("Bono mensual (double): ");
            var bonoMensual = scanner.nextDouble();

            // --- double[][]: calificacionesTrimestres (3 trimestres) ---
            System.out.println("\n--- Calificaciones por trimestre ---");
            System.out.print("¿Cuántas calificaciones por trimestre? ");
            var calificacionesPorTrimestre = scanner.nextInt();
            var calificaciones = new double[3][calificacionesPorTrimestre];

            for (var trimestre = 0; trimestre < 3; trimestre++) {
                System.out.println("Trimestre " + (trimestre + 1) + ":");
                for (var cal = 0; cal < calificacionesPorTrimestre; cal++) {
                    System.out.print("  Calificación " + (cal + 1) + ": ");
                    calificaciones[trimestre][cal] = scanner.nextDouble();
                }
            }

            // Crear el empleado con los datos validados
            var nuevoEmpleado = new Empleados(
                    nivelAcceso, anioIngreso, idEmpleado, numeroDocumento,
                    puntajeTest, salarioBase, tipoContrato, esActivo,
                    nombre, edad, idSede, bonoMensual, calificaciones);

            // Guardar en el ArrayList
            listaEmpleados.add(nuevoEmpleado);

            System.out.println("\n===== Empleado creado exitosamente =====");
            System.out.println(nuevoEmpleado);
            System.out.println("Salario final: " + nuevoEmpleado.calcularSalarioFinal());
            System.out.println("Categoría salarial: " + nuevoEmpleado.obtenerCategoriaSalarial());
        }

        System.out.println("Total de empleados registrados: " + listaEmpleados.size());
    }

}