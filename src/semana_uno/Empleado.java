package semana_uno;

public class Empleado {
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

    public Empleado(byte nivelAcceso, short anioIngreso, int idEmpleado, long numeroDocumento, float puntajeTest, double salarioBase, char tipoContrato, boolean esActivo, String nombre, int edad, int idSede, double bonoMensual) {
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
    }

    public double calcularSalarioFinal(){
        /*
        * Primero se ejecutan los parentesis
        * Luego las multiplicaciones y divisiones
        * Por último, sumas y restas
         */
        return (salarioBase + (bonoMensual * 1.10)) - (salarioBase * 0.05);
    }

    public boolean obtieneBono(){
        return idEmpleado % 2 == 0;
    }

    public boolean validarElegibilidad(){
        return (puntajeTest > 85 && edad < 30) || (idSede == 1 && !esActivo);
    }

    public void sumarBono(double nuevoBono){
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
                '}';
    }
}
