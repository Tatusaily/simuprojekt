package simu.model;

import simu.framework.*;
import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private final LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus
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
		jono.add(a);
		
	}


	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}


	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Asiakas seuraava = jono.peek();
		// TODO Asiakkaan kävelyajan erottelu palveluajasta fiksusti
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + seuraava.getId());
		double kävelyaika = seuraava.getKävelyaika();
		Trace.out(Trace.Level.INFO, "Asiakkaan kävelyaika: " + kävelyaika);
		
		varattu = true;
		double palveluaika = generator.sample();
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,
				Kello.getInstance().getAika()+palveluaika+kävelyaika));
	}



	public boolean onVarattu(){
		return varattu;
	}



	public boolean onJonossa(){
		return jono.size() != 0;
	}

}
