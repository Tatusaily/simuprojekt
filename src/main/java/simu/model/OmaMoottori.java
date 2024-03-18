package simu.model;

import controller.IKontrolleriForM;
import simu.framework.*;
import distributions.Negexp;
import distributions.Normal;
import javafx.scene.Parent;

import java.util.HashMap;

public class OmaMoottori extends Moottori{
	private final Saapumisprosessi saapumisprosessi;
	private final Palvelupiste[] palvelupisteet;
	private HashMap<String, Integer> jonot = new HashMap<>();
	private boolean BoardingOpen = false;
	private Parent tuloksetRoot; // new class variable

	public OmaMoottori(IKontrolleriForM kontrolleri, Parent tuloksetRoot){
		super(kontrolleri);
		this.tuloksetRoot = tuloksetRoot; // store the Parent object
		palvelupisteet = new Palvelupiste[5];
		palvelupisteet[0]=new Palvelupiste(new Normal(10,8), tapahtumalista, TapahtumanTyyppi.CHECKIN, "Check-in");
		palvelupisteet[1]=new Palvelupiste(new Normal(10,8), tapahtumalista, TapahtumanTyyppi.TARKISTUS, "Turvatarkastus");
		palvelupisteet[2]=new Palvelupiste(new Normal(5,4), tapahtumalista, TapahtumanTyyppi.BOARDING, "Boarding");
		palvelupisteet[3]=new Palvelupiste(new Normal(5,4), tapahtumalista, TapahtumanTyyppi.AULA, "Aula");
		palvelupisteet[4]=new Palvelupiste(new Normal(5,4), tapahtumalista, TapahtumanTyyppi.KAUPPA, "Kauppa");

		saapumisprosessi = new Saapumisprosessi(new Negexp(1,5), tapahtumalista, TapahtumanTyyppi.ARRIVE);

	}


	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat
		// TODO Boarding aukeaminen
		Asiakas a;
		switch ((TapahtumanTyyppi)t.getTyyppi()){
			case ARRIVE: palvelupisteet[0].lisaaJonoon(new Asiakas());	// Arrive -> Check-in
				saapumisprosessi.generoiSeuraava();
				kontrolleri.increment_asiakkaat(tuloksetRoot);
				break;

			case CHECKIN: a = (Asiakas)palvelupisteet[0].otaJonosta();	// Check-in -> Tarkistus
				   	   palvelupisteet[1].lisaaJonoon(a);
				break;

			case TARKISTUS: a = (Asiakas)palvelupisteet[1].otaJonosta();
				   	   palvelupisteet[3].lisaaJonoon(a);
				break;
			case AULA:
				a = (Asiakas)palvelupisteet[3].otaJonosta();
				if (checkBoarding()){
					palvelupisteet[2].lisaaJonoon(a); // Aulasta boardingiin
				} else {
					// Jos Boarding ei ole avoinna -> ohjaa kauppaan
					palvelupisteet[4].lisaaJonoon(a);
				}
				break;
			case KAUPPA:
				a = (Asiakas)palvelupisteet[4].otaJonosta();
				palvelupisteet[3].lisaaJonoon(a); // Kaupasta aina aulaan
				break;
			case BOARDING:
				       a = (Asiakas)palvelupisteet[2].otaJonosta();
					   a.setPoistumisaika(Kello.getInstance().getAika());
			           a.raportti();
					   kontrolleri.increment_lentokone();
		}
		updatequeues();
	}

	private void updatequeues() {
		for (Palvelupiste p: palvelupisteet){
			jonot.put(p.getNimi(), p.getJononKoko());
		}
		kontrolleri.updateAll(jonot);
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
	public void toggleEndButton() {
		this.endbutton = !this.endbutton;
	}
}
