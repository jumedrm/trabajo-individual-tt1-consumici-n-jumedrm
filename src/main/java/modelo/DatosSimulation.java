package modelo;

public class DatosSimulation {
    private String resultado;
    private double rendimiento;

    public DatosSimulation(String resultado, double rendimiento) {
        this.resultado = resultado;
        this.rendimiento = rendimiento;
    }

    public String getResultado() { return resultado; }
    public double getRendimiento() { return rendimiento; }
}