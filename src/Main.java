import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String header = """
                =============================
                    Corporate Talent Hub
                =============================
                """;
        JOptionPane.showMessageDialog(null, header);

        Empleado empleado = new Empleado(
                (byte) 1,
                (short) 2024,
                123321321,
                123123123L,
                98.2F,
                1200000.0,
                'b',
                true,
                "Enrique",
                28,
                1,
                200000
        );

        EmpresaRecord empresa = new EmpresaRecord("empresa1", "123456789-0", "2001");

        System.out.println(empleado);
        System.out.println("Empresa: " + empresa.nombre());
        System.out.println("Salario final: " + empleado.calcularSalarioFinal());
        System.out.println("¿ID par con bono extra?: " + empleado.obtieneBono());
        System.out.println("¿Empleado elegible?: " + empleado.validarElegibilidad());

        if (empleado.obtieneBono()) {
            empleado.sumarBono(100_000.0);
            System.out.println("Bono actualizado con +=: " + empleado.getBonoMensual());
        }

        compararReferencias();
        ejecutarLaboratorioDeNulos(empleado);

    }
    private static Empleado crearEmpleadoDePrueba() {
        return new Empleado(
                (byte) 3,             // byte
                (short) 2024,         // short
                102,                  // int: ID par
                1_023_456_789L,       // long: sufijo L
                92.5f,                // float: sufijo f
                3_000_000.0,          // double
                'I',                  // char: contrato indefinido
                true,                 // boolean
                "Laura Gómez",        // String
                27,
                2,
                500_000.0);
    }

    private static void compararReferencias() {
        Empleado primero = crearEmpleadoDePrueba();
        Empleado segundo = crearEmpleadoDePrueba();
        Empleado aliasDelPrimero = primero;

        System.out.println("primero == segundo: " + (primero == segundo));
        System.out.println("primero == aliasDelPrimero: "
                + (primero == aliasDelPrimero));
    }

    private static void ejecutarLaboratorioDeNulos(Empleado empleado) {
        empleado.setNombre(null);

        try {
            System.out.println(empleado.getNombre().toUpperCase());
        } catch (NullPointerException excepcion) {
            System.out.println("NPE controlada: " + excepcion.getMessage());
        }
    }
}