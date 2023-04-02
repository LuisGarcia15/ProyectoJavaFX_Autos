package fes.aragon.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import fes.aragon.modelo.Tienda;
import fes.aragon.modelo.Tiendas;
import fes.aragon.modelo.TipoError;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ModificarTiendaController extends BaseController implements Initializable {
	private Tienda tienda = Tiendas.getObjeto().getGrupoTiendas().get(
			Tiendas.getObjeto().getIndice());
	private String mensaje = "";
	
	@FXML
	private Button btnAceptar;

	@FXML
	private Button btnAutomoviles;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnGerente;

	@FXML
	private TextField txtCorreo;

	@FXML
	private TextField txtDireccion;

	@FXML
    private ChoiceBox<String> chcEstado;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtTelefono;

	@FXML
    void cancelarTienda(ActionEvent event) {
    	this.cerrarVentana(btnCancelar);
    }

	@FXML
	void modificarAuto(ActionEvent event) {
		BaseController.btn.add(this.btnAutomoviles);
		this.nuevaVentana("ListaAutos");
	}

	@FXML
	void modificarGerente(ActionEvent event) {
		this.nuevaVentana("ModificarGerenteVentas");
	}

	@FXML
	void modificarTienda(ActionEvent event) {
		if(this.verificar() && this.verificarDuplicado()) {
			tienda.setCorreo(this.txtCorreo.getText());
			tienda.setDireccion(this.txtDireccion.getText());
			tienda.setEstado(this.chcEstado.getValue());
			tienda.setNombre(this.txtNombre.getText());
			tienda.setTelefono(this.txtTelefono.getText());
			Tiendas.getObjeto().getGrupoTiendas().set(
					Tiendas.getObjeto().getIndice(), tienda);
			Tiendas.getObjeto().setIndice(-1);
	    	Tiendas.getObjeto().setModificarTienda(false);
	    	Tiendas.getObjeto().setIndiceAuto(-1);
	    	BaseController.modificacionValida = true;
	    	this.cerrarVentana(btnAceptar);
		}else {
			ventanaEmergente("Error", "Seleccione una fila",this.mensaje);
			this.mensaje = "";
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		this.verificarEntrada(txtCorreo, TipoError.CORREO);
		this.verificarEntrada(txtTelefono, TipoError.TELEFONO);
		this.chcEstado.getItems().addAll("Selecciona una fila","Aguascalientes","Baja California", "Baja California Sur", "Campeche",
				"Chiapas", "Chihuahua","Ciudad de Mexico", "Coahuila", "Colima", "Durango", "Estado de Mexico", "Guanajuato", "Guerrero",
				"Hidalgo", "Jalisco", "Michoacan", "Morelos", "Nayarit", "Nuevo Leon", "Oaxaca", "Puebla", "Queretaro", "Quintana Roo", 
				"San Luis Potosi", "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatan"	, "Zacatecas");
		
		this.txtCorreo.setText(tienda.getCorreo());
		this.txtDireccion.setText(tienda.getDireccion());
		this.chcEstado.setValue(tienda.getEstado());
		this.txtNombre.setText(tienda.getNombre());
		this.txtTelefono.setText(tienda.getTelefono());

	}
	
	private boolean verificar() {
		boolean valido = true;
		if((this.txtNombre.getText() == null) || (this.txtNombre.getText() != null && this.txtNombre.getText().isEmpty())) {
			this.mensaje += "El nombre de la tienda no es valida, es vacio\n";
			valido = false;
		}
		
		if((this.txtDireccion.getText() == null) || (this.txtDireccion.getText() != null && this.txtDireccion.getText().isEmpty())) {
			this.mensaje += "La direccion de la tienda no es valida, es vacio\n";
			valido = false;
		}
		
		if((this.chcEstado.getSelectionModel().getSelectedIndex() == 0) || (this.chcEstado.getSelectionModel().getSelectedIndex() == 0 ||
				this.chcEstado.getSelectionModel().getSelectedIndex() == -1)) {
			this.mensaje += "Seleccione un estado\n";
			valido = false;
		}
		
		if((this.txtCorreo.getText() == null) || (this.txtCorreo.getText() != null && this.txtCorreo.getText().isEmpty())) {
			this.mensaje += "El correo de la tienda no es valida, es vacio\n";
			valido = false;
		}
		
		if(!this.correoValido) {
			this.mensaje += "El correo de la tienda no es valido, esta mas estructurado\n";
			valido = false;
		}
		
		if((this.txtTelefono.getText() == null) || (this.txtTelefono.getText() != null && this.txtTelefono.getText().isEmpty())) {
			this.mensaje += "El telefono de la tienda no es valido, es vacio\n";
			valido = false;
		}

		if(!this.telefonoValido) {
			this.mensaje += "El telefono del hotel no es valido, minimo 10, maximo 10 numeros\n";
			valido = false;
		}
		
		return valido;
	}
	
	private boolean verificarDuplicado() {
		boolean valido = true;
		boolean validoDireccion = true;
		boolean validoNombre = true;
		boolean validoTelefono = true;
		boolean validoCorreo = true;
		ObservableList<Tienda> tiendas= Tiendas.getObjeto().getGrupoTiendas();
		for(int i = 0; i < tiendas.size(); i++) {
			if(this.tienda == tiendas.get(i)) {
				continue;
			}
			if(tiendas.get(i).getEstado().equals(this.chcEstado.getValue()) && 
					tiendas.get(i).getDireccion().equals(this.txtDireccion.getText())) {
				mensaje += "No es posible crear una tienda con la misma direccion y estado.\n";
				valido = false;
				validoDireccion = false;
				}
			
			if(tiendas.get(i).getNombre().equals(this.txtNombre.getText()) && validoNombre) {
				mensaje += "No es posible crear una tienda con el mismo nombre.\n";
				valido = false;
				validoNombre = false;
				}

				if(tiendas.get(i).getTelefono().equals(this.txtTelefono.getText()) && validoTelefono) {
				mensaje += "No es posible crear una tienda con el mismo telefono.\n";
				valido = false;
				validoTelefono = false;
				}

				if(tiendas.get(i).getCorreo().equals(this.txtCorreo.getText()) && validoCorreo) {
				mensaje += "No es posible crear una tienda con el mismo correo.\n";
				valido = false;
				validoCorreo = false;
				}
				
				if(validoCorreo == false && validoNombre == false && validoTelefono == false && validoDireccion == false) {
				return valido;
				}
		}
		return valido;
	}
}

