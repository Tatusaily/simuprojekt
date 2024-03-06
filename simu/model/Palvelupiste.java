package simu.model;

import simu.framework.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import eduni.distributions.ContinuousGenerator;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private final PriorityQueue<Asiakas> jono = new PriorityQueue<>(); // Tietorakennetoteutus
	private final ContinuousGenerator generator;
	private final Tapahtumalista tapahtumalista;
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	private final String nimi;
	
	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	
	private boolean varattu = false;


	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, String nimi){
		this.tapahtumalista = tapahtumalista;
		this.nimi = nimi;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
				
	}


	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		a.setJonoaika();
		jono.add(a);
		
	}


	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}


	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Asiakas seuraava = jono.peek();
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + seuraava.getId());
		
		varattu = true;
		double palveluaika = generator.sample();
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,
				Kello.getInstance().getAika()+palveluaika));
	}



	public boolean onVarattu(){
		return varattu;
	}



	public boolean onJonossa(){
		return jono.size() != 0;
	}

}
