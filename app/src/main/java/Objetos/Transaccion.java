package Objetos;

import java.util.Date;

/**
 * Created by Desarrollo on 16/12/2015.
 */
public class Transaccion {
    int codigo;
    long codigo_venta;
    int tipo_transaccion;
    long precio;
    long recibo;
    Date fecha;
    String observacion;


    public Transaccion() {
    }

    public Transaccion(int codigo, long codigo_venta, Date fecha, String observacion, long precio, long recibo, int tipo_transaccion) {
        this.codigo = codigo;
        this.codigo_venta = codigo_venta;
        this.fecha = fecha;
        this.observacion = observacion;
        this.precio = precio;
        this.recibo = recibo;
        this.tipo_transaccion = tipo_transaccion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public long getCodigo_venta() {
        return codigo_venta;
    }

    public void setCodigo_venta(long codigo_venta) {
        this.codigo_venta = codigo_venta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public long getPrecio() {
        return precio;
    }

    public void setPrecio(long precio) {
        this.precio = precio;
    }

    public long getRecibo() {
        return recibo;
    }

    public void setRecibo(long recibo) {
        this.recibo = recibo;
    }

    public int getTipo_transaccion() {
        return tipo_transaccion;
    }

    public void setTipo_transaccion(int tipo_transaccion) {
        this.tipo_transaccion = tipo_transaccion;
    }
}
