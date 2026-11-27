package repositorio;

import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;




public class GestorUsuarios {
	    private static GestorUsuarios instancia;
		private final List<Usuario> usuarios;
			
		private GestorUsuarios() {
	    	this.usuarios  = new ArrayList<>();
	    	// Usuario inicial de prueba para poder iniciar sesion rapido
	    	usuarios.add(new Usuario(
	    	     "admin",
	    		 "Admin",
	    		 "Sistema",
	    		 "809-000-0000",
	    		 "admin@correo.com",
	    		 "1234"
	       ));
	    }
 public static GestorUsuarios getInstancia() {
	 if (instancia == null) {
		 instancia = new GestorUsuarios();
		 
	 }
	 return instancia;
 }
 public boolean existeNombreUsuario(String nombreUsuario) {
	 for (Usuario u : usuarios) {
		 if (u.getNombreUsuario().equalsIgnoreCase(nombreUsuario.trim())) {
			 return true;
		 }
	 }
	 return false;
 }
 public boolean registrarUsuario(Usuario nuevoUsuario) {
	 if(nuevoUsuario == null || existeNombreUsuario(nuevoUsuario.getNombreUsuario())) {
		 return false;
	 }
	 usuarios.add(nuevoUsuario);
	 return true;
 }
 public Usuario IniciarSesion(String nombreUsuario, String contrasena) {
	 for (Usuario u : usuarios) {
		 if (u.getNombreUsuario().equalsIgnoreCase(nombreUsuario.trim())
				 && u.getContrasena().equals(contrasena)) {
			 return u;
		 }
	 }
	 return null;
 }
 public List  <Usuario> obtenerTodos() {
	 return new ArrayList<> (usuarios) ; 
}
 public boolean actualizarUsuario(String nombreUsuarioOriginal, Usuario datosActualizados) {
	 for (int i = 0; i <usuarios.size(); i++)  {
		 Usuario actual = usuarios.get(i);
		 if (actual.getNombreUsuario().equalsIgnoreCase(nombreUsuarioOriginal)) {
			 usuarios.set(i,  datosActualizados);
			 return true;
		 }
	 }
	 return false;
 }
 public boolean eliminarUsuario(String nombreUsuario) {
	 return usuarios.removeIf(u-> u.getNombreUsuario().equalsIgnoreCase(nombreUsuario));
 }
}
