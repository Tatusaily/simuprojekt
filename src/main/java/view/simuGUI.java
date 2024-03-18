package view;

import controller.IKontrolleriForV;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Objects;

/**
 * simuGUI-luokka, joka toteuttaa ISimulaattorinUI-rajapinnan.
 * @author Tatusaily
 * @version 1.0
 */
public class simuGUI extends Application implements ISimulaattorinUI {
    private Parent xml;
    private IKontrolleriForV kontrolleri;

    /**
     * Käynnistää simulaattorin käyttöliittymän.
     * Luo kontrollerin ja lataa FXML-tiedoston.
     * @param stage edustaa pääikkunaa.
     * @throws Exception poikkeus, jos jotain menee pieleen.
     */
    @Override
    public void start(Stage stage) throws Exception {

        kontrolleri = new controller.Kontrolleri(this);
        this.xml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/src/main/resources/simuGUI.fxml")));
        stage.setScene(new Scene(xml, 900, 600));
        stage.show();
        this.initializebuttons();
    }

    /**
     * Metodi alustaa napit.
     * Aloita painike käynnistää simulaation ja lopeta painike lopettaa simulaation.
     */

    private void initializebuttons(){
        Button startbutton = (Button) xml.lookup("#aloita");
        Button lopetabutton = (Button) xml.lookup("#stopnappi");
        startbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                aloitaSimulointi();
            }
        });
        lopetabutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lopetaSimulointi();
            }
        });

    }

    /**
     * Metodi käynnistää simulaation.
     */
    private void aloitaSimulointi() {
        kontrolleri.kaynnistaSimulointi();
        xml.lookup("#aloita").setDisable(true);
    }

    /**
     * Metodi lopettaa simulaation.
     */
    private void lopetaSimulointi() {
        kontrolleri.lopetaSimulointi();
        xml.lookup("#aloita").setDisable(false);
    }

    /**
     * Metodi kasvattaa asiakkaiden lukumäärää yhdellä.
     */
    @Override
    public void increment_asiakkaat() {
        Label asiakkaat = (Label) xml.lookup("#asiakas_lkm");
        int i = Integer.parseInt(asiakkaat.getText());
        i++;
        asiakkaat.setText(String.valueOf(i));
    }

    /**
     * Metodi asettaa keskimääräisen jonotusajan.
     * @param keskiaika keskimääräinen jonotusaika.
     */
    @Override
    public void setKeskiaika(double keskiaika) {
        Label keskiaika_label = (Label) xml.lookup("#asiakas_keskiarvo");
        keskiaika_label.setText(String.valueOf(keskiaika));
    }

    /**
     * Metodi asettaa kokonaisajan.
     * @param kokonaisaika kokonaisaika, joka asetetaan
     */
    @Override
    public void setKokonaisaika(double kokonaisaika) {
        Label kokonaisaika_label = (Label) xml.lookup("#kokonaisaika");
        kokonaisaika_label.setText(String.valueOf(kokonaisaika));
    }

    /**
     * Tämä metodi päivittää check-in jonon pituuden.
     * @param checkin_lukum check-in jonon pituus.
     */
    @Override
    public void UpdateCheckinLukumaara(int checkin_lukum) {
        Label checkin_lukum_label = (Label) xml.lookup("#checkin_lukum");
        checkin_lukum_label.setText(String.valueOf(checkin_lukum));
    }


    /**
     * Tämä metodi päivittää tarkistus jonon pituuden.
     * @param tarkistus_lukum tarkistus-lukumäärä, joka asetetaan.
     */
    @Override
    public void UpdateTarkistusLukumaara(int tarkistus_lukum) {
        Label tarkistus_lukum_label = (Label) xml.lookup("#tarkastus_lukum");
        tarkistus_lukum_label.setText(String.valueOf(tarkistus_lukum));
    }

    /**
     * Tämä metodi päivittää aula-lukumäärän
     * @param aula_lukum aula-lukumäärä, joka asetetaan.
     */
    @Override
    public void AulaLukumaara(int aula_lukum) {
        Label aula_lukum_label = (Label) xml.lookup("#aula_lukum");
        aula_lukum_label.setText(String.valueOf(aula_lukum));
    }

    /**
     * Tämä metodi päivittää kauppa-lukumäärän
     * @param kauppa_lukum kauppa-lukumäärä, joka asetetaan.
     */

    @Override
    public void KauppaLukumaara(int kauppa_lukum) {
        Label kauppa_lukum_label = (Label) xml.lookup("#kauppa_lukum");
        kauppa_lukum_label.setText(String.valueOf(kauppa_lukum));
    }

    /**
     * Tämä metodi päivittää boarding-lukumäärän
     * @param boarding_lukum boarding-lukumäärä, joka asetetaan.
     */

    @Override
    public void BoardingLukumaara(int boarding_lukum) {
        Label boarding_lukum_label = (Label) xml.lookup("#boarding_lukum");
        boarding_lukum_label.setText(String.valueOf(boarding_lukum));
    }

    /**
     * Tämä metodi päivittää lentokone-lukumäärän
     */
    @Override
    public void increment_lentokone() {
        Label lentokone_lukum_label = (Label) xml.lookup("#lentokone_lukum");
        int i = Integer.parseInt(lentokone_lukum_label.getText());
        i++;
        lentokone_lukum_label.setText(String.valueOf(i));
    }

    /**
     * Tämä metodi päivittää päivittää kaikki lukumäärät HashMapin avulla.
     * @param mappi HashMap, joka sisältää kaikki lukumäärät.
     */

    @Override
    public void updateAll(HashMap<String, Integer> mappi) {
        for (String key : mappi.keySet()) {
            switch (key){
                case "KAUPPA":
                    KauppaLukumaara(mappi.get(key));
                    break;
                case "AULA":
                    AulaLukumaara(mappi.get(key));
                    break;
                case "CHECK-IN":
                    UpdateCheckinLukumaara(mappi.get(key));
                    break;
                case "TARKISTUS":
                    UpdateTarkistusLukumaara(mappi.get(key));
                    break;
                case "BOARDING":
                    BoardingLukumaara(mappi.get(key));
                    break;
            }
        }
    }

    /**
     * Tämä metodi hakee simulaation ajan.
     * @return Simulaation aika
     */

    @Override
    public double getAika() {
        TextField aika = (TextField) xml.lookup("#simulointiaika");
        return Double.parseDouble(aika.getText());
    }

    /**
     * Tämä metodi hakee simulaation viiveen.
     * @return Simulaation viive kokonaislukuna
     */
    @Override
    public long getViive() {
        Slider viive = (Slider) xml.lookup("#nopeusarvo");
        return (long) viive.getValue();
    }

}
