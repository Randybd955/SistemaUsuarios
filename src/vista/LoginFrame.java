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
import repositorio.GestorUsuarios;

public class LoginFrame extends JFrame {
    private final JTextField txtUsuario;
    private final JPasswordField txtContrasena;

    public LoginFrame() {
        setTitle("Inicio de Sesion - Sistema de Usuarios");
        setSize(380, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Usuario:"), gbc);

        txtUsuario = new JTextField(16);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);

        txtContrasena = new JPasswordField(16);
        gbc.gridx = 1;
        panel.add(txtContrasena, gbc);

        JButton btnIniciar = new JButton("Iniciar Sesión");
        JButton btnRegistrar = new JButton("Registrarse");

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(btnIniciar, gbc);

        gbc.gridx = 1;
        panel.add(btnRegistrar, gbc);

        add(panel);

        btnIniciar.addActionListener(e -> iniciarSesion());
        btnRegistrar.addActionListener(e -> abrirRegistro());
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete usuario y contraseña.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario autenticado = GestorUsuarios.getInstancia().IniciarSesion(usuario, contrasena);
        if (autenticado != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido, " + autenticado.getResumenPerfil());
            new PrincipalFrame(autenticado).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistro() {
        new RegistroFrame(this).setVisible(true);
        setVisible(false);
    }
}
