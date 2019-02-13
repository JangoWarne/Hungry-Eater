package uk.ac.glos.ct5055.assignment.s1609415.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import uk.ac.glos.ct5055.assignment.s1609415.population.GenerationResult;

import java.io.IOException;

/**
 * This class is the controller for Results.fxml
 * it sets the handles for UI events/actions
 * it loads the Menu.fxml scene if user selects to setup a new simulation
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class ResultsController {

    @FXML
    private Region newRegion;


    @FXML
    private void initialize() {
        // Handle Button event.
        newRegion.setOnMouseClicked(this::backRegionHandle);
    }

    public void setConfig(Config config) {
        // update UI based on config
    }

    public void setResult(GenerationResult result) {
        // update UI based on result
    }

    private void backRegionHandle(MouseEvent event) {
        // Go back to main menu
        try {
            Scene scene = newRegion.getScene();
            scene.setRoot(FXMLLoader.load(getClass().getResource("/Menu.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
