package simu.model;

import controller.IKontrolleriForM;
import simu.framework.*;
import distributions.Negexp;
import distributions.Normal;

public class OmaMoottori extends Moottori{
	private final Saapumisprosessi saapumisprosessi;
	private final Palvelupiste[] palvelupisteet;
	private boolean BoardingOpen = false;

	/**
	 * Konstruktori luo uuden moottorin ja asettaa sille kontrollerin.
	 * @param kontrolleri käytettävä kontrolleri
	 * @param kontrolleri
	 */

	public OmaMoottori(IKontrolleriForM kontrolleri){
		super(kontrolleri);
		palvelupisteet = new Palvelupiste[5];
		palvelupisteet[0]=new Palvelupiste(new Normal(10,6), tapahtumalista, TapahtumanTyyppi.CHECKIN, "Check-in");
		palvelupisteet[1]=new Palvelupiste(new Normal(10,10), tapahtumalista, TapahtumanTyyppi.TARKISTUS, "Turvatarkastus");
		palvelupisteet[2]=new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.BOARDING, "Boarding");
		palvelupisteet[3]=new Palvelupiste(new Negexp(5,10), tapahtumalista, TapahtumanTyyppi.AULA, "Aula");
		palvelupisteet[4]=new Palvelupiste(new Negexp(5,10), tapahtumalista, TapahtumanTyyppi.KAUPPA, "Kauppa");

		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARRIVE);

	}

	/**
	 * Ensimmäinen tapahtuma luodaan ja simulointi käynnistyy.
	 */

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava();
	}

	/**
	 * Ensimmäinen tapahtuma luodaan ja simulointi käynnistyy.
	 * Jokainen tapahtumatyyppi vastaa erilaista toimintoa simulaatiossa.
	 * @param t suoritettava tapahtuma
	 */

	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat
		// TODO Boarding aukeaminen
		Asiakas a;
		switch ((TapahtumanTyyppi)t.getTyyppi()){


			case ARRIVE: palvelupisteet[0].lisaaJonoon(new Asiakas());
				saapumisprosessi.generoiSeuraava();
				kontrolleri.increment_asiakkaat();
				break;

			case CHECKIN: a = (Asiakas)palvelupisteet[0].otaJonosta();
				   	   palvelupisteet[1].lisaaJonoon(a);
				break;

			case TARKISTUS: a = (Asiakas)palvelupisteet[1].otaJonosta();
				   	   palvelupisteet[3].lisaaJonoon(a);
				break;
			case AULA:
				a = (Asiakas)palvelupisteet[3].otaJonosta();
				if (BoardingOpen) {
					palvelupisteet[2].lisaaJonoon(a);
				} else {

					palvelupisteet[4].lisaaJonoon(a);
				}
				break;
			case KAUPPA:
				a = (Asiakas)palvelupisteet[4].otaJonosta();
				palvelupisteet[3].lisaaJonoon(a);
				break;
			case BOARDING:
				       a = (Asiakas)palvelupisteet[2].otaJonosta();
					   a.setPoistumisaika(Kello.getInstance().getAika());
			           a.raportti();
					   kontrolleri.increment_lentokone();
		}
	}

	/**
	 * Yrittää suorittaa kaikki palvelupisteet, joissa ei ole varattua asiakasta.
	 * Se käy läpi kaikki palvelupisteet ja aloittaa palvelun, jos palvelupisteessä on jonoa.
	 */

	@Override
	protected void yritaCTapahtumat(){
		for (Palvelupiste p: palvelupisteet){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
	}

	/**
	 * Tämä metodi kutsutaan simulaation lopussa
	 * Tulostaa simulaation tulokset.
	 */
	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
	}

	/**
	 * Lopettaa simulaation.
	 */
	@Override
	public void lopeta() {
		this.endbutton = true;
	}
	public void avaaBoarding() {
		System.out.println("Boarding avattu");
		BoardingOpen = true;
	}
}
