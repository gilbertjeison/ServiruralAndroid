package Objetos;

/**
 * Created by Desarrollo on 04/09/2015.
 */
public class RecuadoListItem {
    private int imeagen;
    private long codigo;
    private long codigo_cliente;
    private String nombre;
    private String direccion;
    private String barrio;
    private long linea;
    private long saldo_linea;
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
    private int dia_corte;
    private int num_lineas;
//    private int producto;
    private String dias_cobro;
    private long codigo_venta;
    private boolean cobrado;
    private long minutos;

    public RecuadoListItem(int imeagen,
                           long codigo,
                           long codigo_cliente,
                           String nombre,
                           String direccion,
                           String barrio,
                           long linea,
                           long saldo_linea,
                           long recibo_venta,
                           String fecha_venta,
                           long codigo_vendedor,
                           long codigo_cobrador,
                           long precio_venta,
                           String observacion_recaudo,
                           long valor_pagado,
                           long recibo_generado,
                           String fecha_cobro,
                           String fecha_export,
                           String nombre_vendedor,
                           String nombre_cobrador,
                           int dia_corte,
                           int num_lineas,
                           long codigo_venta_,
                           String dias_cobro_) {

        this.imeagen = imeagen;
        this.codigo = codigo;
        this.codigo_cliente = codigo_cliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.barrio = barrio;
        this.linea = linea;
        this.saldo_linea = saldo_linea;
        this.recibo_venta = recibo_venta;
        this.fecha_venta = fecha_venta;
        this.codigo_vendedor = codigo_vendedor;
        this.codigo_cobrador = codigo_cobrador;
        this.precio_venta = precio_venta;
        this.observacion_recaudo = observacion_recaudo;
        this.valor_pagado = valor_pagado;
        this.recibo_generado = recibo_generado;
        this.fecha_cobro = fecha_cobro;
        this.fecha_export = fecha_export;
        this.nombre_vendedor = nombre_vendedor;
        this.nombre_cobrador = nombre_cobrador;
        this.dia_corte = dia_corte;
        this.num_lineas = num_lineas;
        this.codigo_venta = codigo_venta_;
        this.dias_cobro = dias_cobro_;
    }

    public RecuadoListItem(int imeagen, long codigo, long codigo_cliente, String nombre,
                           String direccion, String barrio, long linea, long saldo_linea,
                                long recibo_venta, String fecha_venta, long codigo_vendedor,
                                    long codigo_cobrador, long precio_venta,
                                        String fecha_export, String nombre_vendedor,
                                            String nombre_cobrador, int dia_corte,
                                                int num_lineas,long cod_venta, long valor_pagado_,long minutos_) {
        this.imeagen = imeagen;
        this.codigo = codigo;
        this.codigo_cliente = codigo_cliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.barrio = barrio;
        this.linea = linea;
        this.saldo_linea = saldo_linea;
        this.recibo_venta = recibo_venta;
        this.fecha_venta = fecha_venta;
        this.codigo_vendedor = codigo_vendedor;
        this.codigo_cobrador = codigo_cobrador;
        this.precio_venta = precio_venta;
        this.fecha_export = fecha_export;
        this.nombre_vendedor = nombre_vendedor;
        this.nombre_cobrador = nombre_cobrador;
        this.dia_corte = dia_corte;
        this.num_lineas = num_lineas;
        this.codigo_venta = cod_venta;
        this.valor_pagado = valor_pagado_;
        this.minutos = minutos_;
    }

    public RecuadoListItem(long codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public RecuadoListItem() {
    }

    public long getMinutos() {
        return minutos;
    }

//    public void setMinutos(long minutos) {
//        this.minutos = minutos;
//    }

    public boolean isCobrado() {
        return cobrado;
    }

    public void setCobrado(boolean cobrado) {
        this.cobrado = cobrado;
    }

    public int getNum_lineas() {
        return num_lineas;
    }

//    public void setNum_lineas(int num_lineas) {
//        this.num_lineas = num_lineas;
//    }

//    public int getImeagen() {
//        return imeagen;
//    }
//
//    public void setImeagen(int imeagen) {
//        this.imeagen = imeagen;
//    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public long getCodigo_cliente() {
        return codigo_cliente;
    }

//    public void setCodigo_cliente(long codigo_cliente) {
//        this.codigo_cliente = codigo_cliente;
//    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public long getLinea() {
        return linea;
    }

    public void setLinea(long linea) {
        this.linea = linea;
    }

    public long getSaldo_linea() {
        return saldo_linea;
    }

//    public void setSaldo_linea(long saldo_linea) {
//        this.saldo_linea = saldo_linea;
//    }

    public long getRecibo_venta() {
        return recibo_venta;
    }

//    public void setRecibo_venta(long recibo_venta) {
//        this.recibo_venta = recibo_venta;
//    }

    public String getFecha_venta() {
        return fecha_venta;
    }

//    public void setFecha_venta(String fecha_venta) {
//        this.fecha_venta = fecha_venta;
//    }

    public long getCodigo_vendedor() {
        return codigo_vendedor;
    }

//    public void setCodigo_vendedor(long codigo_vendedor) {
//        this.codigo_vendedor = codigo_vendedor;
//    }

    public long getCodigo_cobrador() {
        return codigo_cobrador;
    }

//    public void setCodigo_cobrador(long codigo_cobrador) {
//        this.codigo_cobrador = codigo_cobrador;
//    }

    public long getPrecio_venta() {
        return precio_venta;
    }

//    public void setPrecio_venta(long precio_venta) {
//        this.precio_venta = precio_venta;
//    }

    public String getObservacion_recaudo() {
        return observacion_recaudo;
    }

    public void setObservacion_recaudo(String observacion_recaudo) {
        this.observacion_recaudo = observacion_recaudo;
    }

    public long getValor_pagado() {
        return valor_pagado;
    }

    public void setValor_pagado(long valor_pagado) {
        this.valor_pagado = valor_pagado;
    }

    public long getRecibo_generado() {
        return recibo_generado;
    }

//    public void setRecibo_generado(long recibo_generado) {
//        this.recibo_generado = recibo_generado;
//    }

    public String getFecha_cobro() {
        return fecha_cobro;
    }

//    public void setFecha_cobro(String fecha_cobro) {
//        this.fecha_cobro = fecha_cobro;
//    }

    public String getFecha_export() {
        return fecha_export;
    }

//    public void setFecha_export(String fecha_export) {
//        this.fecha_export = fecha_export;
//    } tony

    public String getNombre_vendedor() {
        return nombre_vendedor;
    }

    public void setNombre_vendedor(String nombre_vendedor) {
        this.nombre_vendedor = nombre_vendedor;
    }

    public String getNombre_cobrador() {
        return nombre_cobrador;
    }

    public void setNombre_cobrador(String nombre_cobrador) {
        this.nombre_cobrador = nombre_cobrador;
    }

    public int getDia_corte() {
        return dia_corte;
    }

//    public void setDia_corte(int dia_corte) {
//        this.dia_corte = dia_corte;
//    }


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
}
