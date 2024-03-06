package simu.framework;

import simu.model.TapahtumanTyyppi;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Tapahtumalista {
	private PriorityQueue<Tapahtuma> lista = new PriorityQueue<Tapahtuma>();
	
	public Tapahtumalista(){
	 
	}
	
	public Tapahtuma poista(){
		Trace.out(Trace.Level.INFO,"Tapahtumalistasta poisto " + lista.peek().getTyyppi() + " " + lista.peek().getAika() );
		return lista.remove();
	}
	
	public void lisaa(Tapahtuma t){
		Trace.out(Trace.Level.INFO,"Tapahtumalistaan lisätään uusi " + t.getTyyppi() + " " + t.getAika());
		lista.add(t);
	}
	
	public double getSeuraavanAika(){
		return lista.peek().getAika();
	}
	public Boolean isEmpty(){
		return lista.isEmpty();
	}
	public HashMap<String, Integer> getTapahtumat(){
		HashMap<String, Integer> tapahtumat = new HashMap<>();
		for (Tapahtuma t : lista){
			int count = 0;
			String tyyppi = t.getTyyppi().toString();
			if (tapahtumat.containsKey(tyyppi)){
				count = tapahtumat.get(tyyppi);
			}
			tapahtumat.put(tyyppi, 1 + count);
		}
		return tapahtumat;
	}

}
