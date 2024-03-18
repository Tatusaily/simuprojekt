package simu.model;

import distributions.ContinuousGenerator;
import distributions.Normal;
import simu.framework.*;

/**
 * Asiakas-luokka kuvaa kentällä olevaa asiakasta.
 * @version 1.0
 * @Author Tatu
 */


public class Asiakas implements Comparable<Asiakas> {
	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	private double jonoaika = 0;
	ContinuousGenerator kävelyaika = new Normal(15, 10);

	/**
	 * Palauttaa asiakkaan kävelyaikaan perustuvan poistumisajan.
	 * @return satunnaineen näyte asikkaan kävelyaika-jakaumasta
	 */
	public double getKävelyaika(){
		return kävelyaika.sample();
	}

	/**
	 * Luo uuden Asiakas-olion ja asettaa sen saapumisajan
	 * Kävelyaika-jakauman arvo asetetaan arvoon 100.
	 */
	public Asiakas(){
		kävelyaika.setSeed(100);
	    id = i++;
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo "+saapumisaika);
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}
	/**
	 * Asettaa asiakkaan poistumisajan.
	 * @param poistumisaika asiakkaan poistumisaika
	 */

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	/**
	 *Vertaa tätä Asiakas-oliota toiseen Asiakas-olioon perustuen jonoaikaan.
	 *  @param arg Asiakas-olio, johon tätä oliota verrataan
	 * @return -1, jos tämä olio on pienempi, 1 jos suurempi, 0 jos yhtä suuri
	 */
	public int compareTo(Asiakas arg) {
		if (this.jonoaika < arg.jonoaika) return -1;
		else if (this.jonoaika > arg.jonoaika) return 1;
		return 0;
	}

	/**
	 * Palauttaa asiakkaan id:n
	 * @return asiakkaan id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Asettaa asiakkaan jonoajan. Jonoaika lasketaan nykyisen kellonajan ja
	 * asiakkaan kävelyaika-jakaumasta saadun ajan summana
	 */
	public void setJonoaika() {
		double aika = Kello.getInstance().getAika() + getKävelyaika();
		this.jonoaika = aika;
	}

	/**
	 * Tulostaa raportin asiakkaasta. Raportti sisältää tiedot asiakkaan id:stä, saapumisajasta, poistumisajasta ja viipymisajasta kentällä
	 * Lisäksi lasketaan ja tulostetaan asiakkaiden läpimenoaikojen keskiarvo tähän asti.
	 */
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
