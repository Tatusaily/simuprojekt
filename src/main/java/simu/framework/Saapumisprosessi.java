package simu.framework;
import distributions.ContinuousGenerator;
import simu.model.OmaMoottori;

public class Saapumisprosessi {
	
	private ContinuousGenerator generaattori;
	private Tapahtumalista tapahtumalista;
	private ITapahtumanTyyppi tyyppi;

	public Saapumisprosessi(ContinuousGenerator g, Tapahtumalista tl, ITapahtumanTyyppi tyyppi){
		this.generaattori = g;
		this.tapahtumalista = tl;
		this.tyyppi = tyyppi;
	}

	public void generoiSeuraava(OmaMoottori omaMoottori){
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika()+generaattori.sample());
		tapahtumalista.lisaa(t);
		omaMoottori.increment_asiakkaat();
	}

}
