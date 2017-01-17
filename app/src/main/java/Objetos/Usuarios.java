package Objetos;

import java.util.Date;

/**
 * Created by Desarrollo on 03/09/2015.
 */
public class Usuarios {
    public int codigo;
    public String nombre;
    public int cedula;
    public int codigo_zona;
    public String usuario ;
    public String contrasena;
    public int codigo_estado_usuario;
    public Date fecha_ultimo_login ;
    public int codigo_rol;

    //CAMPOS AJENOS NECESARIOS
    public String desc_zona ;
    public String desc_estado_asesor;
    public String desc_rol;


    public Usuarios() {
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Date getFecha_ultimo_login() {
        return fecha_ultimo_login;
    }

    public void setFecha_ultimo_login(Date fecha_ultimo_login) {
        this.fecha_ultimo_login = fecha_ultimo_login;
    }

    public String getDesc_zona() {
        return desc_zona;
    }

    public void setDesc_zona(String desc_zona) {
        this.desc_zona = desc_zona;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public int getCodigo_zona() {
        return codigo_zona;
    }

    public void setCodigo_zona(int codigo_zona) {
        this.codigo_zona = codigo_zona;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getCodigo_estado_usuario() {
        return codigo_estado_usuario;
    }

    public void setCodigo_estado_usuario(int codigo_estado_usuario) {
        this.codigo_estado_usuario = codigo_estado_usuario;
    }

    public int getCodigo_rol() {
        return codigo_rol;
    }

    public void setCodigo_rol(int codigo_rol) {
        this.codigo_rol = codigo_rol;
    }

    public String getDesc_estado_asesor() {
        return desc_estado_asesor;
    }

    public void setDesc_estado_asesor(String desc_estado_asesor) {
        this.desc_estado_asesor = desc_estado_asesor;
    }

    public String getDesc_rol() {
        return desc_rol;
    }

    public void setDesc_rol(String desc_rol) {
        this.desc_rol = desc_rol;
    }
}
