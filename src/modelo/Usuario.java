package modelo;

public class Usuario extends Persona{
	private String nombreUsuario;
    private String contrasena;

    public Usuario(String nombreUsuario, String nombre, String apellido,
                   String telefono, String correo, String contrasena) {
        super(nombre, apellido, telefono, correo);
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String getResumenPerfil() {
        return getNombre() + " " + getApellido() + " (" + nombreUsuario + ")";
    }

}
