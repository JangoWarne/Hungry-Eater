package uk.ac.glos.ct5055.assignment.s1609415.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class extends application
 * it launches the Hungry Eater application
 * it loads the Menu.fxml scene
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent menuScene = FXMLLoader.load(getClass().getResource("/Menu.fxml"));
        stage.setTitle("Neural Network Optimisation Using a Genetic Algorithm");
        stage.setResizable(false);
        stage.setScene(new Scene(menuScene, 1400, 1000));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
