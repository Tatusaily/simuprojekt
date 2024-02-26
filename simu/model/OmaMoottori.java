package simu.model;

import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

public class OmaMoottori extends Moottori{

	private Saapumisprosessi saapumisprosessi;

	private Palvelupiste[] palvelupisteet;

	private boolean BoardingOpen = false;

	public OmaMoottori(){

		palvelupisteet = new Palvelupiste[5]; // Lisätty kauppa ja aula

		palvelupisteet[0]=new Palvelupiste(new Normal(10,6), tapahtumalista, TapahtumanTyyppi.POISTU_CHECKIN, "Check-in");
		palvelupisteet[1]=new Palvelupiste(new Normal(10,10), tapahtumalista, TapahtumanTyyppi.POISTU_TARKASTUS, "Turvatarkastus");
		palvelupisteet[2]=new Palvelupiste(new Normal(13,6), tapahtumalista, TapahtumanTyyppi.POISTU_BOARDING, "Boarding");
		palvelupisteet[3]=new Palvelupiste(new Negexp(5,10), tapahtumalista, TapahtumanTyyppi.Aula, "Aula");
		palvelupisteet[4]=new Palvelupiste(new Negexp(5,10), tapahtumalista, TapahtumanTyyppi.Kauppa, "Kauppa");
		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARRIVE);

	}


	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t){// B-vaiheen tapahtumat

		if (Kello.getInstance().getAika() >= 3000 && !BoardingOpen) {   // Milloin Boarding on auennut
			BoardingOpen = true;
			System.out.println("Boarding on nyt avoinna.");
		}


		Asiakas a;
		switch ((TapahtumanTyyppi)t.getTyyppi()){		// Outo typecast
														// Tässä asiakkaat menee jonoon.
														// TODO laita asiakas myöhemmin jonoon kävelynopeuden perusteella
			case ARRIVE: palvelupisteet[0].lisaaJonoon(new Asiakas());
				       saapumisprosessi.generoiSeuraava();	// ARR1 luo aina uuden ARR1 tapahtuman.
				break;

			case POISTU_CHECKIN: a = (Asiakas)palvelupisteet[0].otaJonosta();
				   	   palvelupisteet[1].lisaaJonoon(a);
				break;

			case POISTU_TARKASTUS: a = (Asiakas)palvelupisteet[1].otaJonosta();
				   	   palvelupisteet[2].lisaaJonoon(a);

				break;

			case Aula:
				a = (Asiakas)palvelupisteet[3].otaJonosta();
				if (BoardingOpen && a.getKauppassakäyty()) {
					palvelupisteet[2].lisaaJonoon(a); // Aulasta boardingiin, jos asiakas on jo käynyt kaupassa
				} else {
					// Jos Boarding ei ole avoinna tai asiakas ei ole käynyt kaupassa, ohjaa kauppaan
					palvelupisteet[4].lisaaJonoon(a);
					a.setKauppassakäyty(true); // Merkitään, että asiakas on käynyt kaupassa
				}
				break;
			case Kauppa:

				a = (Asiakas)palvelupisteet[4].otaJonosta();
				palvelupisteet[3].lisaaJonoon(a); // Kaupasta aina aulaan
				break;


			case POISTU_BOARDING:
				       a = (Asiakas)palvelupisteet[2].otaJonosta();
					   a.setPoistumisaika(Kello.getInstance().getAika());
			           a.raportti();
		}
	}

	@Override
	protected void yritaCTapahtumat(){
		for (Palvelupiste p: palvelupisteet){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
	}

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
	}


}
