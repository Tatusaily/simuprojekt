package controller;

import javafx.scene.Parent;

import java.util.HashMap;

public interface IKontrolleriForM {

    // Rajapinta, joka tarjotaan moottorille:
    void increment_asiakkaat(Parent tuloksetRoot);

    void updateAll(HashMap<String, Integer> mappi);

    void increment_lentokone();

    void updateaverageTime(double keskiarvo, Parent tuloksetRoot);

    void totalTime(double aika);
}
