package Objetos;

/**
 * Created by Desarrollo on 23/12/2015.
 */
public class Transa {

    int codigo;
    String tipo_transaccion;
    String linea;
    String precio;
    String fecha;
    String observacion;


    public Transa(int codigo, String tipo_transaccion, String linea, String precio, String fecha, String observacion) {
        this.codigo = codigo;
        this.tipo_transaccion = tipo_transaccion;
        this.linea = linea;
        this.precio = precio;
        this.fecha = fecha;
        this.observacion = observacion;
    }

    public Transa() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipo_transaccion() {
        return tipo_transaccion;
    }

    public void setTipo_transaccion(String tipo_transaccion) {
        this.tipo_transaccion = tipo_transaccion;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
