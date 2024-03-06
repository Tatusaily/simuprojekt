package simu.model;

import controller.IKontrolleriForM;
import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import view.simuGUI;

public class OmaMoottori extends Moottori{
	private Saapumisprosessi saapumisprosessi;

	private Palvelupiste[] palvelupisteet;

	public OmaMoottori(IKontrolleriForM kontrolleri){
		super(kontrolleri);
		palvelupisteet = new Palvelupiste[3];
		palvelupisteet[0]=new Palvelupiste(new Normal(10,6), tapahtumalista, TapahtumanTyyppi.POISTU_CHECKIN, "Check-in");
		palvelupisteet[1]=new Palvelupiste(new Normal(10,10), tapahtumalista, TapahtumanTyyppi.POISTU_TARKASTUS, "Turvatarkastus");
		palvelupisteet[2]=new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.POISTU_BOARDING, "Boarding");

		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARRIVE);

	}


	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		Asiakas a;
		switch ((TapahtumanTyyppi)t.getTyyppi()){	    // Asiakkaat menee jonoon
														// TODO laita asiakas myöhemmin jonoon kävelynopeuden perusteella (ONKO TEHTY?)

			case ARRIVE: palvelupisteet[0].lisaaJonoon(new Asiakas());
				saapumisprosessi.generoiSeuraava();	// ARR1 luo aina uuden ARR1 tapahtuman.
				// kontrolleri.visualisoiAsiakas(); // TÄÄ VOIDAAN OTTAA SITTEN KUN ON CANVAS
				break;

			case POISTU_CHECKIN: a = (Asiakas)palvelupisteet[0].otaJonosta();
				   	   palvelupisteet[1].lisaaJonoon(a);
				break;

			case POISTU_TARKASTUS: a = (Asiakas)palvelupisteet[1].otaJonosta();
				   	   palvelupisteet[2].lisaaJonoon(a);
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


	@Override
	public void lopeta() {
		this.endbutton = true;
	}
}
