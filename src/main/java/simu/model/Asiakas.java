package simu.model;

import distributions.ContinuousGenerator;
import distributions.Normal;
import simu.framework.*;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas implements Comparable<Asiakas> {
	private double saapumisaika;	// simulaatioon saapumisen aika
	private double poistumisaika;	// simulaatiosta lähtemisen aika
	private int id;
	private static int i = 1;
	private static long sum = 0;
	private double jonoaika = 0;
	ContinuousGenerator kävelyaika = new Normal(15, 10);

	public double getKävelyaika(){
		return kävelyaika.sample();
	}
	public Asiakas(){
		kävelyaika.setSeed(100);
	    id = i++;
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo "+saapumisaika);
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	public int compareTo(Asiakas arg) {
		if (this.jonoaika < arg.jonoaika) return -1;
		else if (this.jonoaika > arg.jonoaika) return 1;
		return 0;
	}

	public int getId() {
		return id;
	}

	public void setJonoaika() {
		double aika = Kello.getInstance().getAika() + getKävelyaika();
		this.jonoaika = aika;
	}

	public void raportti(){
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui kentälle: " +saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui kentältä: " +poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi kentällä: " +(poistumisaika-saapumisaika));
		sum += (poistumisaika-saapumisaika);
		double keskiarvo = sum/id;
		System.out.println(("Asiakkaiden läpimenoaikojen keskiarvo tähän asti "+ keskiarvo));
	}

}