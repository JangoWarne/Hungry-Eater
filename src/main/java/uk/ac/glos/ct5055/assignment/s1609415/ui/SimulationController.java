package uk.ac.glos.ct5055.assignment.s1609415.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

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

    @FXML
    private Region stopRegion;


    @FXML
    private void initialize() {
        // Handle Button event.
        stopRegion.setOnMouseClicked(this::backRegionHandle);
    }

    public void setGame() {
        // Set DrawUI scene
//        game.getDrawClass().setScene( game, backRegion.getScene(), winTextArea, boardTilePane );

        // Start Game
//        this.game = game;
//        game.startGame();
    }

    private void backRegionHandle(MouseEvent event) {
        // Go to the results scene
        try {
            // Change Scene
            Scene scene = stopRegion.getScene();
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/Results.fxml" ) );
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
