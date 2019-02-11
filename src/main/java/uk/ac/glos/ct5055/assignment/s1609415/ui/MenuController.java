package uk.ac.glos.ct5055.assignment.s1609415.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * This class is the controller for Menu.fxml
 * it accesses the handles for UI events/actions
 * it loads the Simulation.fxml scene if the user starts the simulation
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class MenuController {

    @FXML
    private Button startButton;


    @FXML
    private void initialize() {
        // Handle Button event.
        startButton.setOnAction(this::startButtonHandle);
    }

    private void startButtonHandle(ActionEvent event) {
        // Go to simulation scene
        try {
            // Change Scene
            Scene scene = startButton.getScene();
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/Simulation.fxml" ) );
            Pane pane = (Pane) loader.load();
            scene.setRoot(pane);

            // Add game to controller
//            PlayersController controller = loader.getController();
//            controller.setGame( game );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
