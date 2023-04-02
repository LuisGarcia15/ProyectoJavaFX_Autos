package fes.aragon.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fes.aragon.modelo.TipoError;
import fes.aragon.modelo.Usuario;
import fes.aragon.modelo.archivo.UsuariosArchivo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegistroController extends BaseController implements Initializable {
	Usuario usuario = new Usuario();
	private String mensaje = "";

	@FXML
	private Button btnAceptar;

	@FXML
	private Button btnCerrar;

	@FXML
	private TextField txtContrasenia;

	@FXML
	private TextField txtCorreoElectronico;

	@FXML
	private TextField txtPin;

	@FXML
	private TextField txtUsuario;

	@FXML
	void entrada(ActionEvent event) {
		if (verificar() && verificarDuplicado()) {
			this.usuario.setUsuario(this.txtUsuario.getText());
			this.usuario.setCorreoElectronico(this.txtCorreoElectronico.getText());
			this.usuario.setContrasenia(this.txtContrasenia.getText());
			this.usuario.setPin(this.txtPin.getText());
			UsuariosArchivo.getUsuarios().getListaUsuarios().add(usuario);
			this.escrituraArchivo();
			this.cerrarVentana(btnAceptar);
			this.pinValido = true;
			this.ventanaEmergente("PIN", "Recuerde su PIN",
					"Con el PIN podra modificar aspectos importantes de la aplicacion, " + " NO SE PUEDE MODIFICAR");
		} else {
			this.ventanaEmergente("Error", "Datos Erroneos", this.mensaje);
			mensaje = "";
		}
	}

	@FXML
	void salida(ActionEvent event) {
		this.escrituraArchivo();
		this.cerrarVentana(btnCerrar);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.verificarEntrada(txtUsuario, TipoError.USUARIO);
		this.verificarEntrada(txtCorreoElectronico, TipoError.CORREO);
		this.verificarEntrada(txtContrasenia, TipoError.CONTRASENIA);
		this.verificarEntrada(txtPin, TipoError.PIN);

		File f = new File(System.getProperty("user.dir") + "/Usuarios/usuarios.fes");
		try {
			FileInputStream fi = new FileInputStream(f);
			ObjectInputStream lectura = new ObjectInputStream(fi);
			@SuppressWarnings("unchecked")
			ArrayList<Usuario> usuario = (ArrayList<Usuario>) lectura.readObject();
			UsuariosArchivo.getUsuarios().setListaUsuarios(usuario);
			lectura.close();
			fi.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} catch (ClassNotFoundException e) {

		}
	}

	private boolean verificar() {
		boolean valido = true;

		if (!this.usuarioValido) {
			this.mensaje += "El usuario no es valido, esta mal estructurado, debe contener"
					+ " el primer elemento como letra seguido de entre 5 a 12 caracteres libres.\n\n";
			valido = false;
		}

		if (!this.correoValido) {
			this.mensaje += "El correo no es valido, esta mas estructurado.\n\n";
			valido = false;
		}

		if (!this.contraseniaValido) {
			this.mensaje += "La contrasenia no es valida, esta mal estructurado, debe contener"
					+ " la primera letra en mayuscula seguido de entre 5  a 7 caracteres libres.\n\n";
			valido = false;
		}

		if (!this.pinValido) {
			this.mensaje += "El pin no es valido, debe de ser de 4 digitos, el primer y ultimo caracter"
					+ " no deben ser numeros ceros.\n\n";
			valido = false;
		}

		if ((this.txtUsuario.getText() == null)
				|| (this.txtUsuario.getText() != null && this.txtUsuario.getText().isEmpty())) {
			this.mensaje += "El usuario no es valido, es vacio\n";
			valido = false;
		}

		if ((this.txtCorreoElectronico.getText() == null)
				|| (this.txtCorreoElectronico.getText() != null && this.txtCorreoElectronico.getText().isEmpty())) {
			this.mensaje += "El correo electronico no es valido, es vacio\n";
			valido = false;
		}

		if ((this.txtContrasenia.getText() == null)
				|| (this.txtContrasenia.getText() != null && this.txtContrasenia.getText().isEmpty())) {
			this.mensaje += "La contrasenia no es valida, es vacia\n";
			valido = false;
		}

		if ((this.txtPin.getText() == null) || (this.txtPin.getText() != null && this.txtPin.getText().isEmpty())) {
			this.mensaje += "El pin no es valido, es vacio\n";
			valido = false;
		}
		return valido;
	}

	public void escrituraArchivo() {
		File f = new File(System.getProperty("user.dir") + "/Usuarios/usuarios.fes");
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(f);
			ObjectOutputStream escritua = new ObjectOutputStream(fo);
			escritua.writeObject(UsuariosArchivo.getUsuarios().getListaUsuarios());
			escritua.close();
			fo.close();
		} catch (FileNotFoundException e) {
			this.ventanaEmergente("ERROR", "Error en sistema", "Archivo no encontrado");
		} catch (IOException ex) {
			this.ventanaEmergente("ERROR", "Error en sistema", "Archivo no encontrado");
		}
	}

	private boolean verificarDuplicado() {
		boolean valido = true;
		boolean validoUsuario = true;
		boolean validoCorreo = true;
		ArrayList<Usuario> usuarios = UsuariosArchivo.getUsuarios().getListaUsuarios();
		for (int i = 0; i < usuarios.size(); i++) {
			
			if (usuarios.get(i).getUsuario().equals(this.txtUsuario.getText())) {
				mensaje += "No es posible crear un usuario repetido.\n";
				valido = false;
				validoUsuario = false;
			}

			if (usuarios.get(i).getCorreoElectronico().equals(this.txtCorreoElectronico.getText())) {
				mensaje += "Ya existe una cuenta con este correo.\n";
				valido = false;
				validoCorreo = false;
			}
			if (validoCorreo == false && validoUsuario == false) {
				return valido;
			}
		}
		return valido;
	}

}
