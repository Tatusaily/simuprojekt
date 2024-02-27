package simu.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.OmaMoottori;
import simu.framework.Moottori;

import java.util.Objects;

public class simuGUI extends Application {
    Parent xml;
    Moottori moottori = new OmaMoottori(this);

    @Override
    public void start(Stage stage) throws Exception {
        // aloitellaan näkymä.
        this.xml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("simuGUI.fxml")));
        stage.setScene(new Scene(xml, 602, 400));
        stage.show();
        this.initializebuttons(); // napeille funktiot
    }
    private void initializebuttons(){
        Button startbutton = (Button) xml.lookup("#aloita");
        startbutton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                TextField aika = (TextField) xml.lookup("#simulointiaika");
                try {
                    moottori.setSimulointiaika(Integer.parseInt(aika.getText()));
                    Trace.setTraceLevel(Level.INFO);
                    moottori.aja();
                    startbutton.setDisable(true);   // Lukitaan nappi kun simulointi on käynnissä (ei voi käynnistää kahta simulointia).
                } catch (Exception e) {
                    System.out.println("ERROR (START BUTTON) " + e);
                }
            }
        });

        Button lopetabutton = (Button) xml.lookup("#stopnappi");
        lopetabutton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //TODO: lopeta simulointi napista
            }
        });

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
}
