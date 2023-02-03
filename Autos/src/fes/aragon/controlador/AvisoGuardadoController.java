package fes.aragon.controlador;

import fes.aragon.modelo.UsuarioInvitado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AvisoGuardadoController extends BaseController{

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    @FXML
    void aceptar(ActionEvent event) {
    	UsuarioInvitado.setInvitado(false);
    	this.cerrarVentana(BaseController.btn.get(BaseController.btn.size()-1));
    	this.cerrarVentana(this.btnAceptar);
    	BaseController.btn.clear();
    	BaseController.modificacionValida = false;
    	this.nuevaVentana("Inicio");
    	if (BaseController.usuarioInicioSesion != null) {
			this.ventanaEmergente("Salida", "Hasta Luego",
					"Vuelve pronto usuario: " + BaseController.usuarioInicioSesion.getUsuario());
			BaseController.usuarioInicioSesion = null;
		} else {
			this.ventanaEmergente("Salida", "Hasta Luego", "Vuelve pronto usuario: Invitado");
		}
    }

    @FXML
    void cancelar(ActionEvent event) {
    	this.cerrarVentana(this.btnAceptar);
    }

}
