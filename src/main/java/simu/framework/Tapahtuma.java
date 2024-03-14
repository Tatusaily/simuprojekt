package simu.framework;

public class Tapahtuma implements Comparable<Tapahtuma> {

	private ITapahtumanTyyppi tyyppi;
	private double aika;
	
	public Tapahtuma(ITapahtumanTyyppi tyyppi, double aika){
		this.tyyppi = tyyppi;
		this.aika = aika;
	}

	public ITapahtumanTyyppi getTyyppi() {
		return tyyppi;
	}
	public double getAika() {
		return aika;
	}

	@Override
	public int compareTo(Tapahtuma arg) {
		if (this.aika < arg.aika) return -1;
		else if (this.aika > arg.aika) return 1;
		return 0;
	}

	@Override
	public String toString() {
		return "Tapahtuma{" +
				"tyyppi=" + tyyppi +
				", aika=" + aika +
				'}';
	}
}
