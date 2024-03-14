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

public class simuGUI extends Application implements ISimulaattorinUI {
    private Parent xml;
    private IKontrolleriForV kontrolleri;

    @Override
    public void start(Stage stage) throws Exception {
        // Kontrollerin luonti
        kontrolleri = new controller.Kontrolleri(this);
        // aloitellaan näkymä.
        this.xml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/src/main/resources/simuGUI.fxml")));
        stage.setScene(new Scene(xml, 900, 600));
        stage.setTitle("Lentokenttäsimulaattori");
        // Ikoni ikkunan "title bariin"
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/src/main/resources/icon.png")));
        stage.getIcons().add(icon);
        stage.show();
        this.initializebuttons(); // napeille funktiot
    }
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
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/src/main/resources/tuloksetGUI.fxml"));
                    // Kontrollerin asettaminen uudelle ikkunalle
                    fxmlLoader.setController(kontrolleri);
                    // Ladataan fxml-tiedosto
                    Parent tuloksetRoot = fxmlLoader.load();
                    // Luodaan uusi stage
                    Stage tuloksetStage = new Stage();
                    // Uusi näkymä ja otsikko stageen
                    tuloksetStage.setScene(new Scene(tuloksetRoot));
                    tuloksetStage.setTitle("Simuloinnin tulokset");
                    // Ikoni ikkunan "title bariin"
                    Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/src/main/resources/icon.png")));
                    tuloksetStage.getIcons().add(icon);
                    // Näytetään uusi stage
                    tuloksetStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // TODO: Boarding aukeaminen

    }
    private void aloitaSimulointi() {
        kontrolleri.kaynnistaSimulointi();
        xml.lookup("#aloita").setDisable(true);
    }
    private void lopetaSimulointi() {
        kontrolleri.lopetaSimulointi();
        xml.lookup("#aloita").setDisable(false);
    }


    @Override
    public void increment_asiakkaat() {
        Label asiakkaat = (Label) xml.lookup("#asiakas_lkm");
        int i = Integer.parseInt(asiakkaat.getText());
        i++;
        asiakkaat.setText(String.valueOf(i));
    }

    @Override
    public void setKeskiaika(double keskiaika) {
        Label keskiaika_label = (Label) xml.lookup("#asiakas_keskiarvo");
        keskiaika_label.setText(String.valueOf(keskiaika));
    }

    @Override
    public void setKokonaisaika(double kokonaisaika) {
        Label kokonaisaika_label = (Label) xml.lookup("#kokonaisaika");
        kokonaisaika_label.setText(String.valueOf(kokonaisaika));
    }

    @Override
    public void UpdateCheckinLukumaara(int checkin_lukum) {
        Label checkin_lukum_label = (Label) xml.lookup("#checkin_lukum");
        checkin_lukum_label.setText(String.valueOf(checkin_lukum));
    }

    @Override
    public void UpdateTarkistusLukumaara(int tarkistus_lukum) {
        Label tarkistus_lukum_label = (Label) xml.lookup("#tarkastus_lukum");
        tarkistus_lukum_label.setText(String.valueOf(tarkistus_lukum));
    }

    @Override
    public void AulaLukumaara(int aula_lukum) {
        Label aula_lukum_label = (Label) xml.lookup("#aula_lukum");
        aula_lukum_label.setText(String.valueOf(aula_lukum));
    }

    @Override
    public void KauppaLukumaara(int kauppa_lukum) {
        Label kauppa_lukum_label = (Label) xml.lookup("#kauppa_lukum");
        kauppa_lukum_label.setText(String.valueOf(kauppa_lukum));
    }

    @Override
    public void BoardingLukumaara(int boarding_lukum) {
        Label boarding_lukum_label = (Label) xml.lookup("#boarding_lukum");
        boarding_lukum_label.setText(String.valueOf(boarding_lukum));
    }

    @Override
    public void increment_lentokone() {
        Label lentokone_lukum_label = (Label) xml.lookup("#lentokone_lukum");
        int i = Integer.parseInt(lentokone_lukum_label.getText());
        i++;
        lentokone_lukum_label.setText(String.valueOf(i));
    }

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

    @Override
    public double getAika() {
        TextField aika = (TextField) xml.lookup("#simulointiaika");
        return Double.parseDouble(aika.getText());
    }

    @Override
    public long getViive() {
        Slider viive = (Slider) xml.lookup("#nopeusarvo");
        return (long) viive.getValue();
    }
}

