package view;

import javafx.scene.Parent;
import java.util.HashMap;

public interface ISimulaattorinUI {

	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	// Eli metodit, joita GUI lähettää Kontrollerille.
	double getAika();
	long getViive();

	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa
	// Eli metodit, joita Kontrolleri lähettää GUI:lle.
	void increment_asiakkaat(Parent tuloksetRoot); // Asiakkaat nousee yhdellä kun uusi asiakas saapuu
	void setKeskiaika(double keskiaika, Parent tuloksetRoot); // Keskimääräinen asiakkaan viettämä aika järjestelmässä
	void setKokonaisaika(double kokonaisaika, Parent tuloksetRoot); // Kokonaisaika, joka kuluu simuloinnissa
	void UpdateCheckinLukumaara(int checkin_lukum); // Check-in lukumäärä
	void UpdateTarkistusLukumaara(int tarkistus_lukum); // Tarkistus lukumäärä
	void AulaLukumaara(int aula_lukum); // Aula lukumäärä
	void KauppaLukumaara(int kauppa_lukum); // Kauppa lukumäärä
	void BoardingLukumaara(int boarding_lukum); // Boarding lukumäärä

	void updateAll(HashMap<String, Integer> mappi);

	void increment_lentokone();
}