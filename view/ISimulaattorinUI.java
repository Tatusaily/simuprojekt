package view;

public interface ISimulaattorinUI {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getAika();
	public long getViive();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	 void setLoppuaika(double aika);

	 void setAsiakasLkm(int asiakasLkm);

	 void setKeskiaika(double keskiaika);

	 void setKokonaisaika(double kokonaisaika);
	 void UpdateCheckinLukumaara(int checkin_lukum);
	 void UpdateTarkistusLukumaara(int tarkistus_lukum);
	 void AulaLukumaara(int aula_lukum);
	 void KauppaLukumaara(int kauppa_lukum);
	 void BoardingLukumaara(int boarding_lukum);
	 void LentokoneLukumaara(int lentokone_lukum);
	 void AsiakasKeskiarvo(int asiakas_keskiarvo);


}
