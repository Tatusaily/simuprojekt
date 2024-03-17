package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import view.ISimulaattorinUI;

import java.util.HashMap;

public class Kontrolleri implements IKontrolleriForM, IKontrolleriForV{

	@FXML
	private TextField simulointiaika;
	@FXML
	private Slider nopeusarvo;
	@FXML
	private Button aloita;
	@FXML
	private Button stopnappi;
	@FXML
	private Label kokonaisaika;
	@FXML
	private Label asiakas_keskiarvo;
	@FXML
	private Label asiakas_lkm;
	@FXML
	private Label checkin_lukum;
	@FXML
	private Label tarkastus_lukum;
	@FXML
	private Label aula_lukum;
	@FXML
	private Label kauppa_lukum;
	@FXML
	private Label boarding_lukum;
	@FXML
	private Label lentokone_lukum;




	private IMoottori moottori;
	private ISimulaattorinUI ui;

	private Parent tuloksetRoot;

	public Kontrolleri(ISimulaattorinUI ui, Parent tuloksetRoot) {
		this.ui = ui;
		this.tuloksetRoot = tuloksetRoot;
	}


	// Moottorin ohjausta:
	private Boolean simulointiKaynnissa = false;
	@Override
	public void kaynnistaSimulointi() {
		moottori = new OmaMoottori(this, tuloksetRoot);
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(ui.getViive());
		((Thread)moottori).start();
		simulointiKaynnissa = true;
	}
	public void lopetaSimulointi() {
		moottori.lopeta();
		simulointiKaynnissa = false;
	}

	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*1.10));
	}

	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*0.9));
	}



	// Simulointitulosten välittämistä käyttöliittymään.
	// Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:
	@Override
	public void increment_asiakkaat(Parent tuloksetRoot) {
		Platform.runLater(()->ui.increment_asiakkaat(tuloksetRoot));
	}

	@Override
	public void updateAll(HashMap<String, Integer> mappi) {
		Platform.runLater(()->ui.updateAll(mappi));
	}

	@Override
	public void increment_lentokone() {
		Platform.runLater(()->ui.increment_lentokone());
	}

	public void updateaverageTime(double keskiaika, Parent tuloksetRoot) {
		Platform.runLater(()->ui.setKeskiaika(keskiaika, tuloksetRoot));
	}

	public void totaltime(double kokonaisaika, Parent tuloksetRoot) {
		Platform.runLater(()->ui.setKokonaisaika(kokonaisaika, tuloksetRoot));
	}

	public void UpdateCheckinLukumaara(int checkin_lukum) {
		Platform.runLater(()->ui.UpdateCheckinLukumaara(checkin_lukum));
	}

	public void TarkistusLukumaara(int tarkistus_lukum) {
		Platform.runLater(()->ui.UpdateTarkistusLukumaara(tarkistus_lukum));
	}


	public void AulaLukumaara(int aula_lukum) {
		Platform.runLater(()->ui.AulaLukumaara(aula_lukum));
	}

	public void KauppaLukumaara(int kauppa_lukum) {
		Platform.runLater(()->ui.KauppaLukumaara(kauppa_lukum));
	}

	public void BoardingLukumaara(int boarding_lukum) {
		Platform.runLater(()->ui.BoardingLukumaara(boarding_lukum));
	}
}
