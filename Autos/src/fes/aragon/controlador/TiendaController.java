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

public class TiendaController extends BaseController implements Initializable{
	private Tienda tienda;
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
    	Tiendas.getObjeto().getGrupoTiendas().remove(Tiendas.getObjeto().getGrupoTiendas().size()-1);
    	cerrarVentana(btnCancelar);
    	modificarGerente = true;
    	modificarAuto = true;
    }

    @FXML
    void nuevaTienda(ActionEvent event) {
    	if(this.verificar() && this.verificarDuplicado() && this.confirmacionGerente()) {
    		this.tienda.setNombre(this.txtNombre.getText());
    		this.tienda.setDireccion(this.txtDireccion.getText());
    		this.tienda.setEstado(this.chcEstado.getValue());
    		this.tienda.setTelefono(this.txtTelefono.getText());
    		this.tienda.setCorreo(this.txtCorreo.getText());
    		BaseController.modificacionValida = true;
    		Tiendas.getObjeto().getGrupoTiendas().set(
    				Tiendas.getObjeto().getGrupoTiendas().size()-1, tienda);
    		this.cerrarVentana(btnAceptar);
    		modificarGerente = true;
    		modificarAuto = false;
    	}else {
    		ventanaEmergente("Error", "Datos Erroneos", this.mensaje);
    		this.mensaje = "";
    	}
    }

    @FXML
    void nuevoAuto(ActionEvent event) {
    	if(BaseController.modificarAuto == false) {
    		ventanaEmergente("Error", "Limite de autos superado", "Modifique o agregue nuevos autos desde la ventana principal");
    	}else {
    	this.nuevaVentana("NuevoAuto");
    	BaseController.modificarAuto = false;
    	}
    }

    @FXML
    void nuevoGerente(ActionEvent event) {
    	if(BaseController.modificarGerente == false) {
    		ventanaEmergente("Error", "No es posible modificar un Gerente ya creado", "Modifique desde la ventana principal");
    	}else {
    	this.nuevaVentana("GerenteVentas");
    	BaseController.modificarGerente = false;
    	}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.verificarEntrada(txtCorreo, TipoError.CORREO);
		this.verificarEntrada(txtTelefono, TipoError.TELEFONO);
		this.chcEstado.getItems().addAll("Selecciona una fila:","Aguascalientes","Baja California", "Baja California Sur", "Campeche",
				"Chiapas", "Chihuahua","Ciudad de México", "Coahuila", "Colima", "Durango", "Estado de México", "Guanajuato", "Guerrero",
				"Hidalgo", "Jalisco", "Michoacán", "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro", "Quintana Roo", 
				"San Luis Potosí", "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán"	, "Zacatecas");
		this.chcEstado.getSelectionModel().select(0);
		
		tienda = Tiendas.getObjeto().getGrupoTiendas().get(
				Tiendas.getObjeto().getGrupoTiendas().size()-1);
	}
	
	private boolean verificar() {
		boolean valido = true;
		if((this.txtNombre.getText() == null) || (this.txtNombre.getText() != null && this.txtNombre.getText().isEmpty())) {
			this.mensaje += "El nombre de la tienda no es valida, es vacio\n";
			valido = false;
		}
		
		if((this.txtDireccion.getText() == null) || (this.txtDireccion.getText() != null && this.txtDireccion.getText().isEmpty())) {
			this.mensaje += "La dirección de la tienda no es valida, es vacio\n";
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
			this.mensaje += "El telefono del hotel no es válido, mínimo 10, máximo 10 números\n";
			valido = false;
		}
		
		return valido;
	}
	
	private boolean confirmacionGerente() {
		boolean valido = true;
		if(this.tienda.getGerenteVentas().getApellidoMaterno() == null ||
				this.tienda.getGerenteVentas().getApellidoPaterno() == null ||
				this.tienda.getGerenteVentas().getCorreo() == null ||
				this.tienda.getGerenteVentas().getNombre() == null ||
				this.tienda.getGerenteVentas().getRfc() == null  ||
				this.tienda.getGerenteVentas().getTelefono() == null ||
				String.valueOf(this.tienda.getGerenteVentas().getEdad()) == null) {
				this.mensaje += "No se ha asignado correctamente algún gerente para esta tienda. \n";
				valido = false;
				return valido;
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
				mensaje += "No es posible crear una tienda con la misma dirección y estado.\n";
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