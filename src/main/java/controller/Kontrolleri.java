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



/**
 * Kontrolleri-luokka vastaa simulaation käynnistämisestä ja lopettamisesta.
 * @author Abdullahi
 * @version 1.0
 */
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



	/**
	 * Moottori, joka suorittaa simulaation.
	 */
	private IMoottori moottori;
	/**
	 * Käyttöliittymä, jota kontrolleri ohjaa.
	 */
	private ISimulaattorinUI ui;
  private Parent tuloksetRoot;


	/**
	 * Kontrolleri-luokka vastaa simulaation käynnistämisestä ja lopettamisesta.
	 * @param ui käyttöliittymä, jota kontrolleri ohjaa.
	 * @
	 */
	public Kontrolleri(ISimulaattorinUI ui, Parent tuloksetRoot) {
		this.ui = ui;
		this.tuloksetRoot = tuloksetRoot;
	}
  
	private Boolean simulointiKaynnissa = false;

	/**
	 * Metotodi käynnistää simulaation. Luodaan uusi moottorisäie jokaista simulointia varten.
	 *
	 */
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

	/**
	 * Lopettaa simulaation.
	 */
	public void lopetaSimulointi() {
		moottori.toggleEndButton();
		simulointiKaynnissa = false;
	}

	/**
	 * Hidastaa moottorisäiettä
	 */
	@Override
	public void hidasta() {
		moottori.setViive((long) (moottori.getViive() * 1.10));
	}
  
	/**
	 * Nopeuttaa moottorisäiettä
	 * @return simulointiKaynnissa
	 */
	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long) (moottori.getViive() * 0.9));
	}


	// Simulointitulosten välittämistä käyttöliittymään.
	// Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:

	/**
	 * Päivittää käyttöliittymän asiakas-laskurin.
	 *
	 */
	@Override
	public void increment_asiakkaat(Parent tuloksetRoot) {
		Platform.runLater(() -> ui.increment_asiakkaat(tuloksetRoot));
	}

	/**
	 * Päivittää käyttöliittymän asiakas-laskurin.
	 *
	 */
	@Override
	public void updateAll(HashMap<String, Integer> mappi) {
		System.out.println(mappi.toString());
		Platform.runLater(() -> ui.updateAll(mappi));
	}

	/**
	 * Päivittää käyttöliittymän lentokone-laskurin.
	 */
	@Override
	public void increment_lentokone() {
		Platform.runLater(() -> ui.increment_lentokone());
	}

	public void updateaverageTime(double keskiaika, Parent tuloksetRoot) {
		Platform.runLater(() -> ui.setKeskiaika(keskiaika, tuloksetRoot));
	}

	public void totalTime(double kokonaisaika) {
		Platform.runLater(() -> ui.setKokonaisaika(kokonaisaika, tuloksetRoot));
	}
}
