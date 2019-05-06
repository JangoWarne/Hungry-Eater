package uk.ac.glos.ct5055.assignment.s1609415.ui;

import de.jensd.fx.button.JavaFXUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.encog.neural.neat.NEATPopulation;

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
    private ToggleGroup scaleToggleGroup;
    @FXML
    private Button startButton;
    @FXML
    private TextField stepsTextField;
    @FXML
    private TextField populationTextField;
    @FXML
    private TextField survivalRateTextField;


    @FXML
    private void initialize() {
        // Handle Button event.
        startButton.setOnAction(this::startButtonHandle);
        stepsTextField.setOnAction(this::stepsTextFieldHandle);
        populationTextField.setOnAction(this::populationTextFieldHandle);
        survivalRateTextField.setOnAction(this::survivalRateTextFieldHandle);

        survivalRateTextField.setText( Double.toString(NEATPopulation.DEFAULT_SURVIVAL_RATE) );

        // Ensure that one button is always selected
        JavaFXUtil.get().addAlwaysOneSelectedSupport(scaleToggleGroup);
    }

    private void startButtonHandle(ActionEvent event) {
        // Confirm inputs
        boolean valid1 = stepsTextFieldHandle(new ActionEvent());
        boolean valid2 = populationTextFieldHandle(new ActionEvent());
        boolean valid3 = survivalRateTextFieldHandle(new ActionEvent());

        if (valid1 && valid2 && valid3) {
            // Create configuration object from UI data
            Config config = new Config(getMultiplier(), getInt(stepsTextField), getInt(populationTextField), getDouble(survivalRateTextField));

            // Go to simulation scene
            try {
                // Change Scene
                Scene scene = startButton.getScene();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Simulation.fxml"));
                Pane pane = loader.load();
                scene.setRoot(pane);

                // Add config data to controller
                SimulationController controller = loader.getController();
                controller.setConfig(config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean stepsTextFieldHandle(ActionEvent event) {
        boolean valid = true;

        // lower limit
        if(getInt(stepsTextField) < 10) {
            valid = false;
            stepsTextField.setText("10");
            Alert minAlert = new Alert(Alert.AlertType.CONFIRMATION, "Minimum value for steps is 10");
            minAlert.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }

        // upper warning
        if(getInt(stepsTextField) > 10000) {
            Alert maxAlert = new Alert(Alert.AlertType.CONFIRMATION, "Steps values above 10,000 may take too long to complete");
            maxAlert.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }
        return valid;
    }

    private boolean populationTextFieldHandle(ActionEvent event) {
        boolean valid = true;

        // lower limit
        if(getInt(populationTextField) < 10) {
            valid = false;
            populationTextField.setText("10");
            Alert minAlert = new Alert(Alert.AlertType.CONFIRMATION, "Minimum value for population is 10");
            minAlert.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }

        // upper warning
        if(getInt(populationTextField) > 99000) {
            Alert maxAlert = new Alert(Alert.AlertType.CONFIRMATION, "Population values above 99,000 may take too long to complete");
            maxAlert.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }
        return valid;
    }

    private boolean survivalRateTextFieldHandle(ActionEvent event) {
        boolean valid = true;

        // lower limit
        if(getDouble(survivalRateTextField) < 0.01) {
            valid = false;
            survivalRateTextField.setText("0.01");
            Alert minAlert = new Alert(Alert.AlertType.CONFIRMATION, "Minimum population survival rate is 0.01");
            minAlert.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }

        // upper warning
        if(getDouble(survivalRateTextField) > 0.99) {
            valid = false;
            survivalRateTextField.setText("0.99");
            Alert maxAlert = new Alert(Alert.AlertType.CONFIRMATION, "Maximum population survival rate is 0.99");
            maxAlert.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }
        return valid;
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

    private int getInt(TextField field) {
        int number = 0;
        String text = field.getCharacters().toString();
        if(!text.equals("")) {
            number = Integer.parseInt( text );
        }
        return number;
    }

    private double getDouble(TextField field) {
        double number = 0.0;
        String text = field.getCharacters().toString();
        if(!text.equals("")) {
            number = Double.parseDouble( text );
        }
        return number;
    }
}
