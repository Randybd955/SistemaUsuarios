package patrones;
import modelo.Usuario;
public class UsuarioFactory {
    public static Usuario  crearUsuario(String nombreUsuario, String nombre, String apellido,
                                        String telefono, String correo, String contraseña) {
    	return new Usuario(
    			nombreUsuario.trim(),
    			nombre.trim(),
    			apellido.trim(),
    			telefono.trim(),
    			correo.trim(),
    			contraseña
    	);
    }
}
