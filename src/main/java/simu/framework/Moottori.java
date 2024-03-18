package simu.framework;


import controller.IKontrolleriForM; // UUSI

import java.util.HashMap;

public abstract class Moottori extends Thread implements IMoottori{  // UUDET MÄÄRITYKSET

	private double simulointiaika = 0;
	private long viive = 0;

	private Kello kello;
	public Boolean endbutton = false;
	protected Boolean boardingOpen = false;
	private double boardingAika;
	protected Tapahtumalista tapahtumalista;

	protected IKontrolleriForM kontrolleri;


	public Moottori(IKontrolleriForM kontrolleri){  // UUSITTU

		this.kontrolleri = kontrolleri;  //UUSI

		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia

		tapahtumalista = new Tapahtumalista();

		// Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa


	}

	@Override
	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}
	public void setBoardingAika(double aika){boardingAika = aika;}

	@Override // UUSI
	public void setViive(long viive) {
		this.viive = viive;
	}

	@Override // UUSI
	public long getViive() {
		return viive;
	}

	@Override
	public void run(){ // Entinen aja()
		alustukset(); // luodaan mm. ensimmäinen tapahtuma
		while (simuloidaan() && !endbutton){
			checkBoarding();
			viive(); // UUSI
			Trace.out(Trace.Level.INFO, "\nA-vaihe: kello on " + nykyaika());
			kello.setAika(nykyaika());
			updateTime();
			Trace.out(Trace.Level.INFO, "\nB-vaihe:" );
			suoritaBTapahtumat();
			Trace.out(Trace.Level.INFO, "\nC-vaihe:" );
			yritaCTapahtumat();
		}
		tulokset();

	}

	protected abstract void updateTime();


	protected boolean checkBoarding() {
		return kello.getAika() >= boardingAika;
	}

	private void suoritaBTapahtumat(){
		while (tapahtumalista.getSeuraavanAika() == kello.getAika()){
			suoritaTapahtuma(tapahtumalista.poista());
		}
	}

	protected abstract void yritaCTapahtumat();


	private double nykyaika(){
		return tapahtumalista.getSeuraavanAika();
	}

	private boolean simuloidaan(){
		Trace.out(Trace.Level.INFO, "Kello on: " + kello.getAika());
		return kello.getAika() < simulointiaika;
	}


	private void viive() { // UUSI
		Trace.out(Trace.Level.INFO, "Viive " + viive);
		try {
			sleep(viive);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected abstract void alustukset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void suoritaTapahtuma(Tapahtuma t);  // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void tulokset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa


}