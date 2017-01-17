package Objetos;

/**
 * Created by Desarrollo on 21/12/2015.
 */
public class RecaudoSoatListItem {

    private long codigo;
    private long codigo_cliente;
    private String nombre;
    private String direccion;
    private String barrio;
    private long saldo_soat;
    private long recibo_venta;
    private String fecha_venta;
    private long codigo_vendedor;
    private long codigo_cobrador;
    private long precio_venta;
    private String observacion_recaudo;
    private long valor_pagado;
    private long recibo_generado;
    private String fecha_cobro;
    private String fecha_export;
    private String nombre_vendedor;
    private String nombre_cobrador;
    private int num_soats;
    private int producto;
    private String dias_cobro;
    private long codigo_venta;
    private boolean cobrado;
    private String poliza;
    private String tipo_vehiculo;
    private String placa;

    public RecaudoSoatListItem(String barrio, boolean cobrado, long codigo, long codigo_cliente, long codigo_cobrador, long codigo_vendedor, long codigo_venta, String dias_cobro, String direccion, String fecha_cobro, String fecha_export, String fecha_venta, String nombre, String nombre_cobrador, String nombre_vendedor, int num_soats, String observacion_recaudo, String placa, String poliza, long precio_venta, int producto, long recibo_generado, long recibo_venta, long saldo_soat, String tipo_vehiculo, long valor_pagado) {
        this.barrio = barrio;
        this.cobrado = cobrado;
        this.codigo = codigo;
        this.codigo_cliente = codigo_cliente;
        this.codigo_cobrador = codigo_cobrador;
        this.codigo_vendedor = codigo_vendedor;
        this.codigo_venta = codigo_venta;
        this.dias_cobro = dias_cobro;
        this.direccion = direccion;
        this.fecha_cobro = fecha_cobro;
        this.fecha_export = fecha_export;
        this.fecha_venta = fecha_venta;
        this.nombre = nombre;
        this.nombre_cobrador = nombre_cobrador;
        this.nombre_vendedor = nombre_vendedor;
        this.num_soats = num_soats;
        this.observacion_recaudo = observacion_recaudo;
        this.placa = placa;
        this.poliza = poliza;
        this.precio_venta = precio_venta;
        this.producto = producto;
        this.recibo_generado = recibo_generado;
        this.recibo_venta = recibo_venta;
        this.saldo_soat = saldo_soat;
        this.tipo_vehiculo = tipo_vehiculo;
        this.valor_pagado = valor_pagado;
    }

    public RecaudoSoatListItem() {
    }

    public long getRecibo_venta() {
        return recibo_venta;
    }

    public void setRecibo_venta(long recibo_venta) {
        this.recibo_venta = recibo_venta;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public boolean isCobrado() {
        return cobrado;
    }

    public void setCobrado(boolean cobrado) {
        this.cobrado = cobrado;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public long getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(long codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public long getCodigo_cobrador() {
        return codigo_cobrador;
    }

    public void setCodigo_cobrador(long codigo_cobrador) {
        this.codigo_cobrador = codigo_cobrador;
    }

    public long getCodigo_vendedor() {
        return codigo_vendedor;
    }

    public void setCodigo_vendedor(long codigo_vendedor) {
        this.codigo_vendedor = codigo_vendedor;
    }

    public long getCodigo_venta() {
        return codigo_venta;
    }

    public void setCodigo_venta(long codigo_venta) {
        this.codigo_venta = codigo_venta;
    }

    public String getDias_cobro() {
        return dias_cobro;
    }

    public void setDias_cobro(String dias_cobro) {
        this.dias_cobro = dias_cobro;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha_cobro() {
        return fecha_cobro;
    }

    public void setFecha_cobro(String fecha_cobro) {
        this.fecha_cobro = fecha_cobro;
    }

    public String getFecha_export() {
        return fecha_export;
    }

    public void setFecha_export(String fecha_export) {
        this.fecha_export = fecha_export;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre_cobrador() {
        return nombre_cobrador;
    }

    public void setNombre_cobrador(String nombre_cobrador) {
        this.nombre_cobrador = nombre_cobrador;
    }

    public String getNombre_vendedor() {
        return nombre_vendedor;
    }

    public void setNombre_vendedor(String nombre_vendedor) {
        this.nombre_vendedor = nombre_vendedor;
    }

    public int getNum_soats() {
        return num_soats;
    }

    public void setNum_soats(int num_soats) {
        this.num_soats = num_soats;
    }

    public String getObservacion_recaudo() {
        return observacion_recaudo;
    }

    public void setObservacion_recaudo(String observacion_recaudo) {
        this.observacion_recaudo = observacion_recaudo;
    }

    public long getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(long precio_venta) {
        this.precio_venta = precio_venta;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public long getRecibo_generado() {
        return recibo_generado;
    }

    public void setRecibo_generado(long recibo_generado) {
        this.recibo_generado = recibo_generado;
    }

    public long getSaldo_soat() {
        return saldo_soat;
    }

    public void setSaldo_soat(long saldo_soat) {
        this.saldo_soat = saldo_soat;
    }

    public long getValor_pagado() {
        return valor_pagado;
    }

    public void setValor_pagado(long valor_pagado) {
        this.valor_pagado = valor_pagado;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getPoliza() {
        return poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    public String getTipo_vehiculo() {
        return tipo_vehiculo;
    }

    public void setTipo_vehiculo(String tipo_vehiculo) {
        this.tipo_vehiculo = tipo_vehiculo;
    }
}
