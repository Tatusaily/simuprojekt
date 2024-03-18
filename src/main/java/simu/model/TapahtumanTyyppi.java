package simu.model;

import simu.framework.ITapahtumanTyyppi;

/**
 * TapahtumanTyyppi-luokka, joka sisältää tapahtuman tyypin.
 * @author Tatusaily
 * @version 1.0
  */

public enum TapahtumanTyyppi implements ITapahtumanTyyppi{
	ARRIVE, KAUPPA , AULA , CHECKIN, TARKISTUS, BOARDING;

}
