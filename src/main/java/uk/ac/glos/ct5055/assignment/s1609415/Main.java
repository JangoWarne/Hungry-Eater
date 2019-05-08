package uk.ac.glos.ct5055.assignment.s1609415;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
        // open main menu
        Parent menuScene = FXMLLoader.load(getClass().getResource("/Menu.fxml"));
        stage.setTitle("Neural Network Optimisation Using a Genetic Algorithm");
        stage.setResizable(false);
        stage.setScene(new Scene(menuScene, 1400, 1000));
        stage.show();

        // ensure that background threads are closed on application close
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
