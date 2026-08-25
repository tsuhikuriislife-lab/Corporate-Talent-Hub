public class Empleados {
    private byte nivelAcceso;
    private short anioIngreso;
    private int idEmpleado;
    private long numeroDocumento;
    private float puntajeTest;
    private double salarioBase;
    private char tipoContrato;
    private boolean esActivo;
    private String nombre;
    private int edad;
    private int idSede;
    private double bonoMensual;
    private double[][] calificacionesTrimestres;

    public Empleados(byte nivelAcceso, short anioIngreso, int idEmpleado, long numeroDocumento, float puntajeTest,
            double salarioBase, char tipoContrato, boolean esActivo, String nombre, int edad, int idSede,
            double bonoMensual, double[][] calificacionesTrimestres) {
        this.nivelAcceso = nivelAcceso;
        this.anioIngreso = anioIngreso;
        this.idEmpleado = idEmpleado;
        this.numeroDocumento = numeroDocumento;
        this.puntajeTest = puntajeTest;
        this.salarioBase = salarioBase;
        this.tipoContrato = tipoContrato;
        this.esActivo = esActivo;
        this.nombre = nombre;
        this.edad = edad;
        this.idSede = idSede;
        this.bonoMensual = bonoMensual;
        this.calificacionesTrimestres = calificacionesTrimestres;
    }

    public double calcularSalarioFinal() {
        /*
         * Primero se ejecutan los parentesis
         * Luego las multiplicaciones y divisiones
         * Por último, sumas y restas
         */
        return (salarioBase + (bonoMensual * 1.10)) - (salarioBase * 0.05);
    }

    public boolean obtieneBono() {
        return idEmpleado % 2 == 0;
    }

    public boolean validarElegibilidad() {
        return (puntajeTest > 85 && edad < 30) || (idSede == 1 && !esActivo);
    }

    public void sumarBono(double nuevoBono) {
        this.bonoMensual += nuevoBono;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public double getBonoMensual() {
        return bonoMensual;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double[][] getCalificacionesTrimestres() {
        return calificacionesTrimestres;
    }

    public String obtenerCategoriaSalarial() {
        double salarioFinal = calcularSalarioFinal();

        int categoria;
        if (salarioFinal < 2_000_000) {
            categoria = 1;
        } else if (salarioFinal < 4_000_000) {
            categoria = 2;
        } else {
            categoria = 3;
        }

        /*
         * Switch moderno (arrow syntax ->):
         * A diferencia del switch tradicional, la sintaxis con flechas (->) incluye
         * breaks IMPLÍCITOS, lo que elimina por completo el riesgo de "fall-through".
         * Cada case ejecuta únicamente su expresión y retorna el valor sin continuar
         * al siguiente case. Esto hace que el código sea más seguro y legible,
         * ya que el compilador garantiza que no se ejecutará código no deseado
         * por la ausencia de un break olvidado.
         */
        return switch (categoria) {
            case 1 -> "Salario Mínimo";
            case 2 -> "Salario Medio";
            case 3 -> "Salario Alto";
            default -> "Categoría desconocida";
        };
    }

    @Override
    public String toString() {
        return "semana_uno.Empleado{" +
                "nivelAcceso=" + nivelAcceso +
                ", anioIngreso=" + anioIngreso +
                ", idEmpleado=" + idEmpleado +
                ", numeroDocumento=" + numeroDocumento +
                ", puntajeTest=" + puntajeTest +
                ", salarioBase=" + salarioBase +
                ", tipoContrato=" + tipoContrato +
                ", esActivo=" + esActivo +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", idSede=" + idSede +
                ", bonoMensual=" + bonoMensual +
                ", calificacionesTrimestres=" + java.util.Arrays.deepToString(calificacionesTrimestres) +
                '}';
    }
}
