package Framework;

import java.util.PriorityQueue;

public class Tapahtumalista {
    private static final PriorityQueue<Tapahtuma> lista
            = new PriorityQueue<Tapahtuma>(Tapahtuma::compareTo);

    public void add(Tapahtuma a){
        lista.add(a);
    }
    public PriorityQueue<Tapahtuma> getList(){  // Ei ole missään järjestyksessä.
        return lista;
    }

    public void remove(){
        lista.remove();
    }
}