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
import javafx.scene.image.Image;

import java.io.IOException;
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
    private Stage tuloksetStage;
    private Parent tuloksetRoot;
    @FXML
    private Label asiakkaat; // Label, joka näyttää asiakkaiden lukumäärän tulosikkunassa

    /**
     * Käynnistää simulaattorin käyttöliittymän.
     * Luo kontrollerin ja lataa FXML-tiedoston.
     * @param stage edustaa pääikkunaa.
     * @throws Exception poikkeus, jos jotain menee pieleen.
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.xml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/src/main/resources/simuGUI.fxml")));
        this.tuloksetRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/src/main/resources/tuloksetGUI.fxml")));
        kontrolleri = new controller.Kontrolleri(this, tuloksetRoot);
      
        // aloitellaan näkymä.
        stage.setScene(new Scene(xml, 900, 600));
        stage.setTitle("Lentokenttäsimulaattori");
      
        // Ikoni ikkunan "title bariin"
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/src/main/resources/icon.png")));
        stage.getIcons().add(icon);
        stage.show();
      
        this.initializebuttons(); // napeille funktiot

        // Tulosikkunan luonti
        tuloksetStage = new Stage();
        tuloksetStage.setScene(new Scene(tuloksetRoot));
        tuloksetStage.setTitle("Simuloinnin tulokset");
        tuloksetStage.getIcons().add(icon);
    }

    /**
     * Metodi alustaa napit.
     * Aloita painike käynnistää simulaation ja lopeta painike lopettaa simulaation.
     */
    private void initializebuttons(){
        Button startbutton = (Button) xml.lookup("#aloita");
        Button lopetabutton = (Button) xml.lookup("#stopnappi");
        Button simutiedot = (Button) xml.lookup("#simutiedot");
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

        // Nappi, joka avaa uuden ikkunan, jossa näkyy simuloinnin tulokset
        simutiedot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/src/main/resources/tuloksetGUI.fxml"));
                fxmlLoader.setController(kontrolleri);
                Parent tuloksetRoot = fxmlLoader.load();
                Stage tuloksetStage = new Stage();
                tuloksetStage.setScene(new Scene(tuloksetRoot));
                tuloksetStage.setTitle("Simuloinnin tulokset");
                Image icon = new Image(getClass().getResourceAsStream("/src/main/resources/icon.png"));
                tuloksetStage.getIcons().add(icon);
                */
                tuloksetStage.show();
                increment_asiakkaat(tuloksetRoot);
                /*
                setKeskiaika(0.0, tuloksetRoot);
                setKokonaisaika(0.0, tuloksetRoot);
                */
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
    public void increment_asiakkaat(Parent tuloksetRoot) {
        Label asiakkaat = (Label) tuloksetRoot.lookup("#asiakas_lkm");
        int i = Integer.parseInt(asiakkaat.getText());
        i++;
        asiakkaat.setText(String.valueOf(i));
    }

    /**
     * Metodi asettaa keskimääräisen jonotusajan.
     * @param keskiaika keskimääräinen jonotusaika.
     */
    @Override
    public void setKeskiaika(double keskiaika, Parent tuloksetRoot) {
        Label keskiaika_label = (Label) tuloksetRoot.lookup("#asiakas_keskiarvo");
        keskiaika_label.setText(String.valueOf(keskiaika));
    }

    /**
     * Metodi asettaa kokonaisajan.
     * @param kokonaisaika kokonaisaika, joka asetetaan
     */
    @Override
    public void setKokonaisaika(double kokonaisaika, Parent tuloksetRoot) {
        Label kokonaisaika_label = (Label) tuloksetRoot.lookup("#kokonaisaika");
        kokonaisaika = Math.round(kokonaisaika * 10.0) / 10.0;
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
    public double getBoardingAika() {
        TextField boardingaika = (TextField) xml.lookup("#boardingaika");
        return Double.parseDouble(boardingaika.getText());
    }


    @Override
    public void updateAll(HashMap<String, Integer> mappi) {
        for (String key : mappi.keySet()) {
            switch (key.toUpperCase()){
                case "KAUPPA":
                    KauppaLukumaara(mappi.get(key));
                    break;
                case "AULA":
                    AulaLukumaara(mappi.get(key));
                    break;
                case "CHECK-IN":
                    UpdateCheckinLukumaara(mappi.get(key));
                    break;
                case "TURVATARKASTUS":
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

