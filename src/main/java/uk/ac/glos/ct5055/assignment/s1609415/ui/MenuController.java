package uk.ac.glos.ct5055.assignment.s1609415.ui;

import de.jensd.fx.button.JavaFXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

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
    private ToggleGroup scaleToggleGroup;
    @FXML
    private Button startButton;
    @FXML
    private TextField stepsTextField;
    @FXML
    private TextField populationTextField;
    @FXML
    private TextField hidden1TextField;
    @FXML
    private TextField hidden2TextField;
    @FXML
    private TextField hidden3TextField;


    @FXML
    private void initialize() {
        // Handle Button event.
        startButton.setOnAction(this::startButtonHandle);
        stepsTextField.setOnAction(this::stepsTextFieldHandle);
        populationTextField.setOnAction(this::populationTextFieldHandle);

        // Ensure that one button is always selected
        JavaFXUtil.get().addAlwaysOneSelectedSupport(scaleToggleGroup);
    }

    private void startButtonHandle(ActionEvent event) {
        // Create configuration object from UI data
        ArrayList<Integer> layers = new ArrayList<>();
        addLayer(layers, getInt(hidden1TextField) );
        addLayer(layers, getInt(hidden2TextField) );
        addLayer(layers, getInt(hidden3TextField) );

        Config config = new Config(getMultiplier(), getInt(stepsTextField), getInt(populationTextField), layers);

        // Go to simulation scene
        try {
            // Change Scene
            Scene scene = startButton.getScene();
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/Simulation.fxml" ) );
            Pane pane = (Pane) loader.load();
            scene.setRoot(pane);

            // Add config data to controller
            SimulationController controller = loader.getController();
            controller.setConfig( config );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stepsTextFieldHandle(ActionEvent event) {
        // lower limit
        if(getInt(stepsTextField) < 10) {
            stepsTextField.setText("10");
            Alert minAlert = new Alert(Alert.AlertType.CONFIRMATION, "Minimum value for steps is 10");
            minAlert.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }

        // upper warning
        if(getInt(stepsTextField) > 10000) {
            Alert maxAlert = new Alert(Alert.AlertType.CONFIRMATION, "Values above 10,000 may take too long to complete");
            maxAlert.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }
    }

    private void populationTextFieldHandle(ActionEvent event) {
        // lower limit
        if(getInt(populationTextField) < 10) {
            populationTextField.setText("10");
            Alert minAlert = new Alert(Alert.AlertType.CONFIRMATION, "Minimum value for population is 10");
            minAlert.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }

        // upper warning
        if(getInt(populationTextField) > 10000) {
            Alert maxAlert = new Alert(Alert.AlertType.CONFIRMATION, "Values above 10,000 may take too long to complete");
            maxAlert.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }
    }

    private float getMultiplier() {
        int index = scaleToggleGroup.getToggles().indexOf( scaleToggleGroup.getSelectedToggle() );

        switch (index) {
            case 0: return (float)0.25;
            case 1: return (float)0.5;
            case 2: return (float)1;
            case 3: return (float)2;
            default: return (float)1;
        }
    }

    private void addLayer(ArrayList<Integer> layers, Integer nodes) {
        if(nodes != 0) {
            layers.add(nodes);
        }
    }

    private int getInt(TextField field) {
        return Integer.parseInt( field.getCharacters().toString() );
    }
}
