package fes.aragon.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fes.aragon.modelo.TipoError;
import fes.aragon.modelo.Usuario;
import fes.aragon.modelo.UsuarioInvitado;
import fes.aragon.modelo.archivo.UsuariosArchivo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InicioController extends BaseController implements Initializable {
	private UsuariosArchivo usuario = new UsuariosArchivo();
	private String mensaje = "";

	@FXML
	private Button btnAceptar;

	@FXML
	private Button btnCerrar;

	@FXML
	private Button btnInvitado;

	@FXML
	private Button btnRegistrarse;

	@FXML
	private Button btnRestaurarContrasenia;

	@FXML
	private TextField txtContrasenia;

	@FXML
	private TextField txtCorreoElectronico;

	@FXML
	void entrada(ActionEvent event) {
		this.leerArchivo();
		if (this.verificar() && this.verificarDatos()) {
			this.nuevaVentana("Menu");
			this.cerrarVentana(btnAceptar);
			this.ventanaEmergente("Bienvenido", "Bienvenido", "Hola usuario: " + BaseController.usuarioInicioSesion.getUsuario());
		} else {
			this.ventanaEmergente("Error", "Datos Erroneos", this.mensaje);
			mensaje = "";
		}
	}

	@FXML
	void entradaInvitado(ActionEvent event) {
		UsuarioInvitado.setInvitado(true);
		this.cerrarVentana(btnCerrar);
		this.nuevaVentana("Menu");
		this.ventanaEmergente("Bienvenido", "Bienvenido", "Hola Invitado");
	}

	@FXML
	void registro(ActionEvent event) {
		this.nuevaVentana("Registro");
	}

	@FXML
	void salida(ActionEvent event) {
		this.cerrarVentana(btnCerrar);
	}

	@FXML
	void restablecerContrasenia(ActionEvent event) {
		this.nuevaVentana("CambiarContrasena");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.verificarEntrada(txtCorreoElectronico, TipoError.CORREO);
		this.verificarEntrada(txtContrasenia, TipoError.CONTRASENIA);
	}

	private boolean verificar() {
		boolean valido = true;

		if (!this.correoValido) {
			this.mensaje += "El correo no es valido, esta mas estructurado.\n\n";
			valido = false;
		}

		if (!this.contraseniaValido) {
			this.mensaje += "La contraseña no es valida, esta mal estructurado, debe contener"
					+ " la primera letra en mayúscula seguido de entre 5  a 7 caracteres libres.\n\n";
			valido = false;
		}

		if ((this.txtCorreoElectronico.getText() == null)
				|| (this.txtCorreoElectronico.getText() != null && this.txtCorreoElectronico.getText().isEmpty())) {
			this.mensaje += "El correo electrónico no es valido, es vacio\n";
			valido = false;
		}

		if ((this.txtContrasenia.getText() == null)
				|| (this.txtContrasenia.getText() != null && this.txtContrasenia.getText().isEmpty())) {
			this.mensaje += "La contraseña no es valida, es vacia\n";
			valido = false;
		}

		return valido;
	}

	public void leerArchivo() {
		File f = new File(System.getProperty("user.dir") + "/Usuarios/usuarios.fes");
		try {
			FileInputStream fi = new FileInputStream(f);
			ObjectInputStream lectura = new ObjectInputStream(fi);
			@SuppressWarnings("unchecked")
			ArrayList<Usuario> usuario = (ArrayList<Usuario>) lectura.readObject();
			for (Usuario usuario2 : usuario) {
				System.out.println(usuario2 + "\n");
			}
			System.out.println("--------------------------------------");
			this.usuario.setListaUsuarios(usuario);
			lectura.close();
			fi.close();
		} catch (ClassNotFoundException e) {
			this.ventanaEmergente("ERROR", "Error en sistema", "Clase no encontrado");
		} catch (IOException ex) {
			this.ventanaEmergente("ERROR", "Error en sistema", "Archivo no encontrado");
		}
	}

	private boolean verificarDatos() {
		boolean valido = false;
		ArrayList<Usuario> usuarios = usuario.getListaUsuarios();
		for (int i = 0; i < usuarios.size(); i++) {
			int y = i;

			if (usuarios.get(i).getContrasenia().equals(this.txtContrasenia.getText())
					&& usuarios.get(i).getCorreoElectronico().equals(this.txtCorreoElectronico.getText())) {
				BaseController.usuarioInicioSesion = usuarios.get(i);
				valido = true;
				return valido;
			}

			if (!(usuarios.get(i).getContrasenia().equals(this.txtContrasenia.getText()))
					&& usuarios.get(i).getCorreoElectronico().equals(this.txtCorreoElectronico.getText())) {
				mensaje += "La contrasenia es incorrecta.\n";
				valido = false;
				return valido;
			}

			if (usuarios.get(i).getContrasenia().equals(this.txtContrasenia.getText())
					&& !(usuarios.get(i).getCorreoElectronico().equals(this.txtCorreoElectronico.getText()))) {
				mensaje += "La contrasenia o el correo electrónico son incorrectos.\n";
				valido = false;
				return valido;
			}

			if (!(usuarios.get(i).getContrasenia().equals(this.txtContrasenia.getText()))
					&& !(usuarios.get(i).getCorreoElectronico().equals(this.txtCorreoElectronico.getText()))) {
				if (y != usuarios.size() - 1) {
					continue;
				} else {
					mensaje += "Es posible que no exista usuarios con los datos suministrados.\n";
					valido = false;
					return valido;
				}
			}

		}
		return valido;
	}

}
