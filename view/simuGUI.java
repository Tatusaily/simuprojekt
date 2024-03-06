package view;

import controller.IKontrolleriForV;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.OmaMoottori;
import simu.framework.Moottori;

import java.util.Objects;

public class simuGUI extends Application implements ISimulaattorinUI {
    private Parent xml;
    //Kontrollerin esittely (tarvitaan käyttöliittymässä)
    private IKontrolleriForV kontrolleri;

    @Override
    public void start(Stage stage) throws Exception {
        // Kontrollerin luonti
        kontrolleri = new controller.Kontrolleri(this);
        // aloitellaan näkymä.
        this.xml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("simuGUI.fxml")));
        stage.setScene(new Scene(xml, 900, 600));
        stage.show();
        this.initializebuttons(); // napeille funktiot
    }
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
    private void aloitaSimulointi() {
        kontrolleri.kaynnistaSimulointi();
        xml.lookup("#aloita").setDisable(true);
    }
    private void lopetaSimulointi() {
        kontrolleri.lopetaSimulointi();
        xml.lookup("#aloita").setDisable(false);
    }

    public void increment_asiakkaat() {
        Label asiakkaat = (Label) xml.lookup("#asiakas_lkm");
        int i = Integer.parseInt(asiakkaat.getText());
        i++;
        asiakkaat.setText(String.valueOf(i));
    }
    public void setkeskiarvo(double keskiarvo) {
        Label keskiarvo_label = (Label) xml.lookup("#asiakas_keskiarvo");
        keskiarvo_label.setText(String.valueOf(keskiarvo));
    }
    public void setkokonaisaika(int kokonaisaika) {
        Label kokonaisaika_label = (Label) xml.lookup("#kokonaisaika");
        kokonaisaika_label.setText(String.valueOf(kokonaisaika));
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

    @Override
    public void setLoppuaika(double aika) {
        Label tulos = (Label) xml.lookup("#tulos");
        tulos.setText(String.valueOf(aika));
    }

}
