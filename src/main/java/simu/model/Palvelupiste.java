package simu.model;

import distributions.ContinuousGenerator;
import simu.framework.*;

import java.util.PriorityQueue;


/**
 * Palvelupiste-luokka, joka sisältää palvelupisteen toiminnallisuudet.
 * @author Tatusaily
 * @version 1.0
 */
public class Palvelupiste {

	private final PriorityQueue<Asiakas> jono = new PriorityQueue<>(); // Tietorakennetoteutus
	private final ContinuousGenerator generator;
	private final Tapahtumalista tapahtumalista;
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	private final String nimi;

	
	private boolean varattu = false;

	/**
	 * Palvelupisteen konstruktori.
	 * @param generator Palvelupisteen palveluaika
	 * @param tapahtumalista Tapahtumalista, johon palvelupisteen tapahtumat lisätään
	 * @param tyyppi Palvelupisteen tapahtuman tyyppi
	 * @param nimi Palvelupisteen nimi
	 */

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, String nimi){
		this.tapahtumalista = tapahtumalista;
		this.nimi = nimi;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
	}

	/**
	 * Metodi lisää jonoon asiakkaan.
	 * @param a Asiakas lisätään jonoon.
	 */
	public void lisaaJonoon(Asiakas a){
		a.setJonoaika();
		jono.add(a);
		
	}

	/**
	 * Metodi poistaa jonosta asiakkaan.
	 * @return Palauttaa jonosta poistetun asiakkaan.
	 */

	public Asiakas otaJonosta(){
		varattu = false;
		return jono.poll();
	}

	/**
	 * Metodi aloittaa uuden palvelun asiakkaalle, joka on jonossa.
	 * Palveluaika määritetään satunnaislukugeneraattorilla.
	 */

	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Asiakas seuraava = jono.peek();
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + seuraava.getId());
		
		varattu = true;
		double palveluaika = generator.sample();
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,
				Kello.getInstance().getAika()+palveluaika));
	}

	/**
	 * Tarkistaa, onko palvelupiste varattu
	 * @return true, jos palvelupiste on varattu
	 */

	public boolean onVarattu(){
		return varattu;
	}

	/**
	 * Tarkistaa onko jonossa asiakkaita
	 * @return true, jos jonossa on asiakkaita
	 */

	public boolean onJonossa(){
		return jono.size() != 0;
	}

	public Integer getJononKoko() {
		return jono.size();
	}

	public String getNimi() {
		return nimi;
	}
}
