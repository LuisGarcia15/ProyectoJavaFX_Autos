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

public class CambiarContraseniaController extends BaseController implements Initializable{
	private String mensaje = "";
    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtContrasenaAnterior;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNuevaContrasena;

    @FXML
    void aceptar(ActionEvent event) {
    	if(this.verificar() && this.validarUsuario()) {
    		this.cerrarVentana(btnAceptar);
    	}else {
    		this.ventanaEmergente("ERROR", "Datos Incorrectos" , this.mensaje);
    		this.mensaje = "";
    	}
    }

    @FXML
    void cancelar(ActionEvent event) {
    	this.cerrarVentana(btnCancelar);
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	this.verificarEntrada(txtCorreo, TipoError.CORREO);
    	this.verificarEntrada(txtNuevaContrasena, TipoError.CONTRASENIA);
		this.verificarEntrada(txtContrasenaAnterior, TipoError.CONTRASENIA);
		
		File f = new File(System.getProperty("user.dir") + "/Usuarios/usuarios.fes");
		try {
			FileInputStream fi = new FileInputStream(f);
			ObjectInputStream lectura = new ObjectInputStream(fi);
			@SuppressWarnings("unchecked")
			ArrayList<Usuario> usuario = (ArrayList<Usuario>) lectura.readObject();
			UsuariosArchivo.getUsuarios().getListaUsuarios().clear();
			UsuariosArchivo.getUsuarios().setListaUsuarios(usuario);
			lectura.close();
			fi.close();
		} catch (FileNotFoundException e) {
			this.ventanaEmergente("ERROR", "Error en sistema", "Archivo no encontrado");
		} catch (IOException e) {
			this.ventanaEmergente("ERROR", "Error en sistema", "Archivo no encontrado");
		} catch (ClassNotFoundException e) {
			this.ventanaEmergente("ERROR", "Error en sistema", "Clase no encontrado");
		}
	}
    
    private boolean verificar() {
		boolean valido = true;

		if (!this.correoValido) {
			this.mensaje += "El correo no es valido, esta mas estructurado.\n\n";
			valido = false;
		}

		if (!this.contraseniaValido) {
			this.mensaje += "La contrasenia no es valida, esta mal estructurado, debe contener"
					+ " la primera letra en mayuscula seguido de entre 5  a 7 caracteres libres.\n\n";
			valido = false;
		}


		if ((this.txtNuevaContrasena.getText() == null)
				|| (this.txtNuevaContrasena.getText() != null && this.txtNuevaContrasena.getText().isEmpty())) {
			this.mensaje += "La nueva contrasenia no es valida, es vacia\n";
			valido = false;
		}

		if ((this.txtCorreo.getText() == null)
				|| (this.txtCorreo.getText() != null && this.txtCorreo.getText().isEmpty())) {
			this.mensaje += "El correo electronico no es valido, es vacio\n";
			valido = false;
		}

		if ((this.txtContrasenaAnterior.getText() == null)
				|| (this.txtContrasenaAnterior.getText() != null && this.txtContrasenaAnterior.getText().isEmpty())) {
			this.mensaje += "La contrasenia anterior no es valida, es vacia\n";
			valido = false;
		}

		return valido;
	}
    
    private boolean validarUsuario() {
		boolean valido = false;
		ArrayList<Usuario> usuarios = UsuariosArchivo.getUsuarios().getListaUsuarios();
		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getContrasenia().equals(this.txtContrasenaAnterior.getText())
					&& usuarios.get(i).getCorreoElectronico().equals(this.txtCorreo.getText())) {
				
				if(usuarios.get(i).getContrasenia().equals(this.txtNuevaContrasena.getText())) {
					this.mensaje += "No es posible cambiar la contrasenia por otra que este en uso. \n";
					return valido;
				}else {
				UsuariosArchivo.getUsuarios().getListaUsuarios().get(i).setContrasenia(this.txtNuevaContrasena.getText());
				this.escrituraArchivo();
				valido = true;
				return valido;
				}
			}
		}
		this.mensaje = "El correo electronico o la contrasenia es incorrecta.\n";
		return valido;
	}
    
    private void escrituraArchivo() {
		File f = new File(System.getProperty("user.dir") + "/Usuarios/usuarios.fes");
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(f,false);
			ObjectOutputStream escritua = new ObjectOutputStream(fo);	
			escritua.writeObject(UsuariosArchivo.getUsuarios().getListaUsuarios());
			escritua.close();
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


}
