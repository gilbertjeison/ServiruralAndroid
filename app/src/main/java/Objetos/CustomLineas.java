package Objetos;

/**
 * Created by Desarrollo on 07/09/2015.
 */
public class CustomLineas {
    public long id;
    public String precio_compra;
    public String linea;
    public String valor_pagar;


    public CustomLineas() {
    }

    public CustomLineas(long id, String precio_compra, String linea, String valor_pagar) {
        this.id = id;
        this.precio_compra = precio_compra;
        this.linea = linea;
        this.valor_pagar = valor_pagar;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(String precio_compra) {
        this.precio_compra = precio_compra;
    }

    public String getValor_pagar() {
        return valor_pagar;
    }

    public void setValor_pagar(String valor_pagar) {
        this.valor_pagar = valor_pagar;
    }
}
