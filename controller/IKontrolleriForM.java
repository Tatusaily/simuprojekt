package controller;

import java.util.HashMap;

public interface IKontrolleriForM {

    // Rajapinta, joka tarjotaan moottorille:
    void increment_asiakkaat();

    void updateAll(HashMap<String, Integer> mappi);

    void increment_lentokone();
}
