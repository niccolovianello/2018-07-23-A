/**
 * Sample Skeleton for 'NewUfoSightings.fxml' Controller Class
 */

package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Adiacenza;
import it.polito.tdp.newufosightings.model.Model;
import it.polito.tdp.newufosightings.model.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewUfoSightingsController {

	private Model model;
	
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="btnSelezionaAnno"
	private Button btnSelezionaAnno; // Value injected by FXMLLoader

	@FXML // fx:id="cmbBoxForma"
	private ComboBox<String> cmbBoxForma; // Value injected by FXMLLoader

	@FXML // fx:id="btnCreaGrafo"
	private Button btnCreaGrafo; // Value injected by FXMLLoader

	@FXML // fx:id="txtT1"
	private TextField txtT1; // Value injected by FXMLLoader

	@FXML // fx:id="txtAlfa"
	private TextField txtAlfa; // Value injected by FXMLLoader

	@FXML // fx:id="btnSimula"
	private Button btnSimula; // Value injected by FXMLLoader

	@FXML
	void doSelezionaAnno(ActionEvent event) {
		
		txtResult.clear();
		String input = txtAnno.getText().trim();
		
		try {
			int anno = Integer.parseInt(input);
			
			if(anno < 1910 || anno > 2014) {
				txtResult.appendText("Inserire un numero tra 1910 e 2014.");
			}
			
			btnCreaGrafo.setDisable(false);
			cmbBoxForma.setDisable(false);
			
			cmbBoxForma.getItems().clear();
			
			cmbBoxForma.getItems().addAll(this.model.getShapes(anno));
			
		}
		
		catch(NumberFormatException nfe) {
			txtResult.appendText("Inserire un numero tra 1910 e 2014.");
		}

	}
	
	@FXML
	void doCreaGrafo(ActionEvent event) {
		
		txtResult.clear();
		
		String input = txtAnno.getText().trim();
		int anno = Integer.parseInt(input);
		
		model.creaGrafo(anno, cmbBoxForma.getValue());
		
		for(State s : model.getStatiPesi().keySet()) {
			txtResult.appendText(s.getName() + " - " + model.getStatiPesi().get(s) + "\n");
		}
		
		txtT1.setDisable(false);
		txtAlfa.setDisable(false);
		btnSimula.setDisable(false);
		
	}

	@FXML
	void doSimula(ActionEvent event) {
		
		txtResult.clear();
		String inputT1 = txtT1.getText().trim();
		String inputAlfa = txtT1.getText().trim();
		
		try {
			int T1 = Integer.parseInt(inputT1);
			int alfa = Integer.parseInt(inputAlfa);
			
			int anno = Integer.parseInt(txtAnno.getText().trim());
			
			if(T1 < 0 || T1 > 365) {
				txtResult.appendText("T1 dev'essere un intero compreso tra 0 e 365.");
			}
			if(alfa < 0 || alfa > 365) {
				txtResult.appendText("Alfa dev'essere un intero compreso tra 0 e 100.");
			}
			
			model.simula(T1, alfa, anno);
		}
		
		catch(NumberFormatException nfe) {
			txtResult.appendText("Inserire un numero tra 0 e 365.");
		}

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnSelezionaAnno != null : "fx:id=\"btnSelezionaAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert cmbBoxForma != null : "fx:id=\"cmbBoxForma\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtAlfa != null : "fx:id=\"txtAlfa\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		btnCreaGrafo.setDisable(true);
		cmbBoxForma.setDisable(true);
		txtT1.setDisable(true);
		txtAlfa.setDisable(true);
		btnSimula.setDisable(true);
	}
}
