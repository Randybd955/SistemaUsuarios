package vista;


	import java.awt.BorderLayout;
	import java.awt.FlowLayout;
	import java.util.List;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JScrollPane;
	import javax.swing.JTable;
	import javax.swing.table.DefaultTableModel;
	import modelo.Usuario;
	import patrones.UsuarioFactory;
	import repositorio.GestorUsuarios;

	public class PrincipalFrame extends JFrame {
	    private final Usuario usuarioActual;
	    private final DefaultTableModel modeloTabla;
	    private final JTable tablaUsuarios;

	    public PrincipalFrame(Usuario usuarioActual) {
	        this.usuarioActual = usuarioActual;
	        setTitle("Panel Principal - Gestión de Usuarios");
	        setSize(760, 420);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setLayout(new BorderLayout(8, 8));

	        JLabel lblBienvenida = new JLabel("  Sesión activa: " + usuarioActual.getResumenPerfil());
	        add(lblBienvenida, BorderLayout.NORTH);

	        String[] columnas = {"Usuario", "Nombre", "Apellido", "Teléfono", "Correo"};
	        modeloTabla = new DefaultTableModel(columnas, 0) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };

	        tablaUsuarios = new JTable(modeloTabla);
	        add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);

	        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        JButton btnActualizar = new JButton("Actualizar Seleccionado");
	        JButton btnEliminar = new JButton("Eliminar Seleccionado");
	        JButton btnRefrescar = new JButton("Refrescar");
	        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

	        panelBotones.add(btnActualizar);
	        panelBotones.add(btnEliminar);
	        panelBotones.add(btnRefrescar);
	        panelBotones.add(btnCerrarSesion);
	        add(panelBotones, BorderLayout.SOUTH);

	        btnActualizar.addActionListener(e -> actualizarSeleccionado());
	        btnEliminar.addActionListener(e -> eliminarSeleccionado());
	        btnRefrescar.addActionListener(e -> cargarUsuarios());
	        btnCerrarSesion.addActionListener(e -> cerrarSesion());

	        cargarUsuarios();
	    }

	    private void cargarUsuarios() {
	        modeloTabla.setRowCount(0);
	        List<Usuario> lista = GestorUsuarios.getInstancia().obtenerTodos();
	        for (Usuario u : lista) {
	            modeloTabla.addRow(new Object[]{
	                u.getNombreUsuario(),
	                u.getNombre(),
	                u.getApellido(),
	                u.getTelofono(),
	                u.getCorreo()
	            });
	        }
	    }

	    private void actualizarSeleccionado() {
	        int fila = tablaUsuarios.getSelectedRow();
	        if (fila == -1) {
	            JOptionPane.showMessageDialog(this, "Seleccione un usuario en la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        String usuarioOriginal = modeloTabla.getValueAt(fila, 0).toString();
	        String nombreActual = modeloTabla.getValueAt(fila, 1).toString();
	        String apellidoActual = modeloTabla.getValueAt(fila, 2).toString();
	        String telefonoActual = modeloTabla.getValueAt(fila, 3).toString();
	        String correoActual = modeloTabla.getValueAt(fila, 4).toString();

	        String nuevoNombre = JOptionPane.showInputDialog(this, "Nombre:", nombreActual);
	        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) return;

	        String nuevoApellido = JOptionPane.showInputDialog(this, "Apellido:", apellidoActual);
	        if (nuevoApellido == null || nuevoApellido.trim().isEmpty()) return;

	        String nuevoTelefono = JOptionPane.showInputDialog(this, "Teléfono:", telefonoActual);
	        if (nuevoTelefono == null || nuevoTelefono.trim().isEmpty()) return;

	        String nuevoCorreo = JOptionPane.showInputDialog(this, "Correo:", correoActual);
	        if (nuevoCorreo == null || nuevoCorreo.trim().isEmpty()) return;

	        Usuario actualizado = UsuarioFactory.crearUsuario(
	            usuarioOriginal,
	            nuevoNombre,
	            nuevoApellido,
	            nuevoTelefono,
	            nuevoCorreo,
	            usuarioActual.getContrasena()
	        );

	        if (GestorUsuarios.getInstancia().actualizarUsuario(usuarioOriginal, actualizado)) {
	            JOptionPane.showMessageDialog(this, "Usuario actualizado.");
	            cargarUsuarios();
	        } else {
	            JOptionPane.showMessageDialog(this, "No se pudo actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    private void eliminarSeleccionado() {
	        int fila = tablaUsuarios.getSelectedRow();
	        if (fila == -1) {
	            JOptionPane.showMessageDialog(this, "Seleccione un usuario en la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        String usuarioObjetivo = modeloTabla.getValueAt(fila, 0).toString();
	        if (usuarioObjetivo.equalsIgnoreCase(usuarioActual.getNombreUsuario())) {
	            JOptionPane.showMessageDialog(this, "No puede eliminar el usuario con sesión activa.", "Aviso", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        int confirmacion = JOptionPane.showConfirmDialog(
	            this,
	            "¿Eliminar al usuario '" + usuarioObjetivo + "'?",
	            "Confirmar",
	            JOptionPane.YES_NO_OPTION
	        );

	        if (confirmacion == JOptionPane.YES_OPTION) {
	            if (GestorUsuarios.getInstancia().eliminarUsuario(usuarioObjetivo)) {
	                JOptionPane.showMessageDialog(this, "Usuario eliminado.");
	                cargarUsuarios();
	            }
	        }
	    }

	    private void cerrarSesion() {
	        new LoginFrame().setVisible(true);
	        dispose();
	    }
	}
	
	
	
	

