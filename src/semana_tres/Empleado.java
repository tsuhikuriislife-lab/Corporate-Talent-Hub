package semana_tres;

public class Empleado {
    private String id;
    private String nombre;
    private double salario;
    private double puntaje;

    public Empleado(String id, String nombre, double salario, double puntaje) {
        this.id = id;
        this.nombre = nombre;
        this.salario = salario;
        this.puntaje = puntaje;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getSalario() { return salario; }
    public double getPuntaje() { return puntaje; }

    @Override
    public String toString() {
        return String.format("Empleado[ID=%s, Nombre=%s, Salario=%.2f, Puntaje=%.1f]",
                id, nombre, salario, puntaje);
    }
}