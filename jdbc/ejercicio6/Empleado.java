public class Empleado {
    private int id;
    private String nombre;
    private String departamento;

    public Empleado(int id, String nombre, String departamento) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
    }

    public Empleado(String nombre, String departamento) {
        this(0, nombre, departamento);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDepartamento() { return departamento; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    @Override
    public String toString() {
        return "Empleado{id=" + id + ", nombre='" + nombre + "', departamento='" + departamento + "'}";
    }
}
