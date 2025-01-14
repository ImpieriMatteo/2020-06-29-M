/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Integer anno = this.boxAnno.getValue();
    	if(anno==null) {
    		this.txtResult.appendText("Devi prima selezionare un ANNO");
    		return;
    	}
    	
    	String result = this.model.creaGrafo(anno);
    	
    	this.txtResult.appendText(result);
    	this.btnAdiacenti.setDisable(false);
    	
    	this.boxRegista.getItems().clear();
    	this.boxRegista.getItems().addAll(this.model.getAllVertex());
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Integer anno = this.boxAnno.getValue();
    	if(anno==null || anno!=this.model.getAnno()) {
    		this.txtResult.appendText("Devi prima creare il GRAFO corrispondente all'anno scelto");
    		return;
    	}
    	
    	Director scelto = this.boxRegista.getValue();
    	if(scelto==null) {
    		this.txtResult.appendText("Devi prima scegliere un DIRETTORE");
    		return;
    	}
    	
    	Map<Director, Integer> vicini = this.model.getAllAdiacenti(scelto);
    	this.txtResult.appendText("REGISTI ADIACENTI A "+scelto.toString()+": \n\n");
    	for(Director d : vicini.keySet()) {
    		this.txtResult.appendText(d.toString()+" - # attori condivisi "+vicini.get(d)+"\n");
    	}
    	
    	this.btnCercaAffini.setDisable(false);
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Integer anno = this.boxAnno.getValue();
    	if(anno==null || anno!=this.model.getAnno()) {
    		this.txtResult.appendText("Devi prima creare il GRAFO corrispondente all'anno scelto");
    		return;
    	}
    	
    	Integer c;
    	try {
    		c = Integer.parseInt(this.txtAttoriCondivisi.getText());
    	}
    	catch(NumberFormatException e) {
    		this.txtResult.appendText("Devi inserire un numero INTERO");
    		return;
    	}
    	
    	Director scelto = this.boxRegista.getValue();
    	if(scelto==null) {
    		this.txtResult.appendText("Devi prima scegliere un DIRETTORE");
    		return;
    	}
    	
    	this.model.trovaPercorso(c, scelto);
    	
    	this.txtResult.appendText("PERCORSO TROVATO CON "+this.model.getRegistiCoinvoltiBest()+" REGISTI e "+this.model.getAttoriCondivisiBest()+" ATTORI CONDIVISI: \n\n");
    	for(Director d : this.model.getPercorsoBest()) {
    		this.txtResult.appendText("° "+d.toString()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	
    	for(int i=4; i<=6; i++) {
    		this.boxAnno.getItems().add(2000+i);
    	}
    }
    
}
