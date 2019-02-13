package uk.ac.glos.ct5055.assignment.s1609415.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import uk.ac.glos.ct5055.assignment.s1609415.population.GenerationResult;

import java.io.IOException;

/**
 * This class is the controller for Simulation.fxml
 * it sets the handles for UI events/actions
 * it loads the Results.fxml scene if user selects to stop the simulation
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class SimulationController {

    private Config config;
    private GenerationResult result = new GenerationResult();

    @FXML
    private Region stopRegion;


    @FXML
    private void initialize() {
        // Handle Button event.
        stopRegion.setOnMouseClicked(this::backRegionHandle);
    }

    public void setConfig( Config config ) {
        this.config = config;
    }

    private void backRegionHandle(MouseEvent event) {
        // Go to the results scene
        try {
            // Change Scene
            Scene scene = stopRegion.getScene();
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/Results.fxml" ) );
            Pane pane = (Pane) loader.load();
            scene.setRoot(pane);

            // Add config and result data to controller
            ResultsController controller = loader.getController();
            controller.setConfig( config );
            controller.setResult( result );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
