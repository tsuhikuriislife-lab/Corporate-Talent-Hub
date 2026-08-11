package semana_tres;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionEmpleados {

    // =========================================================================
    // TASK 1: Almacenamiento Dinámico (Java 8/11)
    // =========================================================================
    private final ArrayList<Empleado> listaEmpleados = new ArrayList<>();
    private final HashMap<String, Empleado> mapaEmpleados = new HashMap<>();

    public void agregarEmpleado(Empleado emp) {
        listaEmpleados.add(emp);
        mapaEmpleados.put(emp.getId(), emp);
    }

    public void listarEmpleados() {
        if (listaEmpleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        for (var emp : listaEmpleados) {
            System.out.println(emp);
        }
    }

    public Empleado buscarPorId(String id) {
        // Búsqueda instantánea O(1) con HashMap
        return mapaEmpleados.get(id);
    }

    public boolean eliminarEmpleado(String id) {
        Empleado emp = mapaEmpleados.remove(id);
        if (emp != null) {
            listaEmpleados.remove(emp);
            return true;
        }
        return false;
    }

    // =========================================================================
    // TASK 2: Factory Methods e Inmutabilidad (Java 9/11)
    // =========================================================================
    public void cargarConfiguracion() {
        /*
         * EXPLICACIÓN:
         * List.of() y Map.of() (introducidos en Java 9) crean colecciones INMUTABLES.
         * Son más seguras que un ArrayList tradicional porque garantizan thread-safety
         * de lectura y evitan modificaciones accidentales de la configuración en tiempo de ejecución.
         *
         * NOTA: Intentar llamar a .add() o .put() sobre estas colecciones lanzará una
         * excepción 'UnsupportedOperationException'. Tampoco admiten elementos 'null'.
         */
        List<String> tecnologias = List.of("Java 21", "Spring Boot", "Docker", "PostgreSQL");
        Map<String, String> sedes = Map.of("Sede1", "Medellín", "Sede2", "Bogotá", "Sede3", "Cali");

        System.out.println("Tecnologías soportadas (Inmutable): " + tecnologias);
        System.out.println("Sedes operativas (Inmutable): " + sedes);
    }

    // =========================================================================
    // TASK 3: Sequenced Collections (Java 21 LTS)
    // =========================================================================
    public void demostrarAccesoSecuencial() {
        if (listaEmpleados.isEmpty()) return;

        System.out.println("\n--- TASK 3: Comparativa de Acceso Secuencial ---");

        // --- Sintaxis Legacy (Java 8/11) ---
        // Se requiere calcular índices manualmente: riesgo de IndexOutOfBoundsException o código menos expresivo.
        Empleado primerLegacy = listaEmpleados.get(0);
        Empleado ultimoLegacy = listaEmpleados.get(listaEmpleados.size() - 1);
        System.out.println("[Legacy 8/11] Primero: " + primerLegacy.getNombre() + " | Último: " + ultimoLegacy.getNombre());

        // --- Sintaxis Moderna (Java 21 LTS) ---
        /*
         * MEJORA EN JAVA 21:
         * Las interfaces de la jerarquía SequencedCollection (implementadas por ArrayList)
         * introducen acceso semántico de primer nivel: getFirst(), getLast() y reversed().
         * Evita cálculos manuales de tipo size() - 1 y mejora drásticamente la legibilidad.
         */
        Empleado primerJava21 = listaEmpleados.getFirst();
        Empleado ultimoJava21 = listaEmpleados.getLast();
        System.out.println("[Java 21 LTS] Primero: " + primerJava21.getNombre() + " | Último: " + ultimoJava21.getNombre());

        System.out.println("\n[Java 21 LTS] Recorrido en orden inverso con reversed():");
        for (var emp : listaEmpleados.reversed()) {
            System.out.println(" -> " + emp.getNombre());
        }
    }

    // =========================================================================
    // TASK 4: Filtrado Avanzado, var y Reportes (Java 11+)
    // =========================================================================
    public void filtrarPorPuntajeMinimo(double puntajeMinimo) {
        // removeIf elimina elementos in-place usando una expresión Lambda
        listaEmpleados.removeIf(emp -> {
            boolean eliminar = emp.getPuntaje() < puntajeMinimo;
            if (eliminar) {
                mapaEmpleados.remove(emp.getId()); // Mantenemos sincronizado el HashMap
            }
            return eliminar;
        });
    }

    public void generarReporte() {
        System.out.println("\n--- REPORTE FINAL ---");

        // Inferencia de tipos con 'var' (Java 10/11)
        var totalEmpleados = listaEmpleados.size();

        if (totalEmpleados == 0) {
            System.out.println("Sin datos para generar reporte.");
            return;
        }

        var sumaSalarios = 0.0;
        for (var emp : listaEmpleados) { // 'var' reemplaza la declaración explícita 'Empleado emp'
            sumaSalarios += emp.getSalario();
        }

        var promedioSalarios = sumaSalarios / totalEmpleados;

        System.out.println("Total de empleados activos: " + totalEmpleados);
        System.out.printf("Promedio de salarios: $%.2f%n", promedioSalarios);
    }

    // =========================================================================
    // Método Principal de Prueba
    // =========================================================================
    public static void main(String[] args) {
        var gestion = new GestionEmpleados();

        // 1. Cargar Configuración Inmutable (Task 2)
        gestion.cargarConfiguracion();

        // 2. Registrar Empleados (Task 1)
        gestion.agregarEmpleado(new Empleado("E01", "Ana Gómez", 3500.0, 85.0));
        gestion.agregarEmpleado(new Empleado("E02", "Carlos Ruiz", 2800.0, 60.0));
        gestion.agregarEmpleado(new Empleado("E03", "Beatriz López", 4200.0, 92.0));
        gestion.agregarEmpleado(new Empleado("E04", "David Vera", 2100.0, 55.0));

        System.out.println("\n--- Lista Inicial de Empleados ---");
        gestion.listarEmpleados();

        // 3. Probar Sequenced Collections (Task 3 - Java 21)
        gestion.demostrarAccesoSecuencial();

        // 4. Filtrado mediante removeIf (Task 4)
        System.out.println("\n--- Filtrando empleados con puntaje < 70.0 ---");
        gestion.filtrarPorPuntajeMinimo(70.0);
        gestion.listarEmpleados();

        // 5. Reporte Final (Task 4)
        gestion.generarReporte();
    }
}