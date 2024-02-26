package simu.model;

import simu.framework.ITapahtumanTyyppi;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi implements ITapahtumanTyyppi{
	ARRIVE, Kauppa , Aula , POISTU_CHECKIN, POISTU_TARKASTUS, POISTU_BOARDING;

}
