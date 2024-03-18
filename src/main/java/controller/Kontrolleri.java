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

public class Kontrolleri implements IKontrolleriForM, IKontrolleriForV {

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
		if (moottori == null) {
			moottori = new OmaMoottori(this, tuloksetRoot);    // luodaan uusi moottorisäie jokaista simulointia varten
			moottori.setBoardingAika(ui.getBoardingAika());
		}
		if (simulointiKaynnissa) {
			moottori.toggleEndButton();
		} else {
			moottori.setViive(ui.getViive());
			moottori.setSimulointiaika(ui.getAika());
			((Thread) moottori).start();
			simulointiKaynnissa = true;
		}
	}

	public void lopetaSimulointi() {
		moottori.toggleEndButton();
		simulointiKaynnissa = false;
	}

	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long) (moottori.getViive() * 1.10));
	}

	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long) (moottori.getViive() * 0.9));
	}


	// Simulointitulosten välittämistä käyttöliittymään.
	// Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:
	@Override
	public void increment_asiakkaat(Parent tuloksetRoot) {
		Platform.runLater(() -> ui.increment_asiakkaat(tuloksetRoot));
	}

	@Override
	public void updateAll(HashMap<String, Integer> mappi) {
		System.out.println(mappi.toString());
		Platform.runLater(() -> ui.updateAll(mappi));
	}

	@Override
	public void increment_lentokone() {
		Platform.runLater(() -> ui.increment_lentokone());
	}

	public void updateaverageTime(double keskiaika, Parent tuloksetRoot) {
		Platform.runLater(() -> ui.setKeskiaika(keskiaika, tuloksetRoot));
	}

	public void totaltime(double kokonaisaika, Parent tuloksetRoot) {
		Platform.runLater(() -> ui.setKokonaisaika(kokonaisaika, tuloksetRoot));
	}
}
