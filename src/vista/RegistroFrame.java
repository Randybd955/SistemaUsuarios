package vista;


	import java.awt.GridBagConstraints;
	import java.awt.GridBagLayout;
	import java.awt.Insets;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JPasswordField;
	import javax.swing.JTextField;
	import modelo.Usuario;
	import patrones.UsuarioFactory;
	import repositorio.GestorUsuarios;

	public class RegistroFrame extends JFrame {
	    private final JFrame loginFrame;
	    private final JTextField txtUsuario;
	    private final JTextField txtNombre;
	    private final JTextField txtApellido;
	    private final JTextField txtTelefono;
	    private final JTextField txtCorreo;
	    private final JPasswordField txtContrasena;
	    private final JPasswordField txtConfirmarContrasena;

	    public RegistroFrame(JFrame loginFrame) {
	        this.loginFrame = loginFrame;
	        setTitle("Registro de Usuario");
	        setSize(430, 340);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setLocationRelativeTo(null);

	        JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(5, 8, 5, 8);
	        gbc.fill = GridBagConstraints.HORIZONTAL;

	        txtUsuario = new JTextField(18);
	        txtNombre = new JTextField(18);
	        txtApellido = new JTextField(18);
	        txtTelefono = new JTextField(18);
	        txtCorreo = new JTextField(18);
	        txtContrasena = new JPasswordField(18);
	        txtConfirmarContrasena = new JPasswordField(18);

	        agregarFila(panel, gbc, 0, "Usuario:", txtUsuario);
	        agregarFila(panel, gbc, 1, "Nombre:", txtNombre);
	        agregarFila(panel, gbc, 2, "Apellido:", txtApellido);
	        agregarFila(panel, gbc, 3, "Teléfono:", txtTelefono);
	        agregarFila(panel, gbc, 4, "Correo:", txtCorreo);
	        agregarFila(panel, gbc, 5, "Contraseña:", txtContrasena);
	        agregarFila(panel, gbc, 6, "Confirmar contraseña:", txtConfirmarContrasena);

	        JButton btnGuardar = new JButton("Registrar");
	        JButton btnVolver = new JButton("Volver");

	        gbc.gridx = 0;
	        gbc.gridy = 7;
	        panel.add(btnGuardar, gbc);

	        gbc.gridx = 1;
	        panel.add(btnVolver, gbc);

	        add(panel);

	        btnGuardar.addActionListener(e -> registrarUsuario());
	        btnVolver.addActionListener(e -> volverALogin());
	    }

	    private void agregarFila(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JTextField campo) {
	        gbc.gridx = 0;
	        gbc.gridy = fila;
	        panel.add(new JLabel(etiqueta), gbc);

	        gbc.gridx = 1;
	        panel.add(campo, gbc);
	    }

	    private void registrarUsuario() {
	        String usuario = txtUsuario.getText().trim();
	        String nombre = txtNombre.getText().trim();
	        String apellido = txtApellido.getText().trim();
	        String telefono = txtTelefono.getText().trim();
	        String correo = txtCorreo.getText().trim();
	        String contrasena = new String(txtContrasena.getPassword());
	        String confirmar = new String(txtConfirmarContrasena.getPassword());

	        if (usuario.isEmpty() || nombre.isEmpty() || apellido.isEmpty()
	                || telefono.isEmpty() || correo.isEmpty()
	                || contrasena.isEmpty() || confirmar.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        if (!contrasena.equals(confirmar)) {
	            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        if (GestorUsuarios.getInstancia().existeNombreUsuario(usuario)) {
	            JOptionPane.showMessageDialog(this, "Ese nombre de usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        Usuario nuevo = UsuarioFactory.crearUsuario(usuario, nombre, apellido, telefono, correo, contrasena);
	        if (GestorUsuarios.getInstancia().registrarUsuario(nuevo)) {
	            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.");
	            volverALogin();
	        } else {
	            JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    private void volverALogin() {
	        dispose();
	        if (loginFrame != null) {
	            loginFrame.setVisible(true);
	        }
	    }
	}


