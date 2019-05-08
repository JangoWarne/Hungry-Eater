package uk.ac.glos.ct5055.assignment.s1609415.ui;

import uk.ac.glos.ct5055.assignment.s1609415.population.Simulation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import uk.ac.glos.ct5055.assignment.s1609415.population.Status;

import java.io.IOException;

/**
 * This class is the controller for Simulation.fxml
 * it sets the handles for UI events/actions
 * it loads the Menu.fxml scene if user selects to stop the simulation
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class SimulationController {

    private Status status;
    private XYChart.Series<Number, Number> genBest;
    private XYChart.Series<Number, Number> genMean;

    @FXML
    private Region stopRegion;
    @FXML
    private Circle creatureCircle;
    @FXML
    private Circle foodCircle;
    @FXML
    private Label waitingLabel;
    @FXML
    private Label progressGenLabel;
    @FXML
    private Label progressLabel;
    @FXML
    private Label populationLabel;
    @FXML
    private Label currentGenLabel;
    @FXML
    private LineChart<Number, Number> historyLineChart;


    @FXML
    private void initialize() {
        // Handle Button events
        stopRegion.setOnMouseClicked(this::backRegionHandle);

        // reset chart
        genBest = new XYChart.Series<>();
        genMean = new XYChart.Series<>();

        historyLineChart.setTitle("History");
        historyLineChart.getData().addAll(genMean, genBest);
    }

    public SimulationController() {
        status = new Status();
    }

    protected void setConfig( Config config ) {
        // This is used by Menu to transfer configuration values
        drawPopulationSize(config.getPopulationSize());

        creatureCircle.setLayoutX(0);
        creatureCircle.setLayoutY(0);
        creatureCircle.setRadius(config.getCreatureRadius());

        foodCircle.setLayoutX(0);
        foodCircle.setLayoutY(0);
        foodCircle.setRadius(config.getFoodRadius());

        Progress progress = new Progress(this, config.getPopulationSize() );

        drawSceneVisibility(false);

        Simulation simulation = new Simulation(progress, config);
        simulation.startSimulation( this, status );

    }

    private void backRegionHandle(MouseEvent event) {
        // Go back to main menu
        try {
            status.stopSimulation();
            Scene scene = stopRegion.getScene();
            scene.setRoot(FXMLLoader.load(getClass().getResource("/Menu.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawVisibleGen(int generation) {
        currentGenLabel.setText(Integer.toString(generation));
    }

    public void drawSceneVisibility(boolean visible) {
        // show/hide creature viewer
        creatureCircle.setVisible(visible);
        foodCircle.setVisible(visible);

        waitingLabel.setVisible(!visible);
    }

    public void drawCreatureLocation( Pair<Double, Double> location ) {
        creatureCircle.setCenterX( location.getKey() );
        creatureCircle.setCenterY( location.getValue() );
    }

    public void drawFoodLocation( Pair<Double, Double> location ) {
        foodCircle.setCenterX( location.getKey() );
        foodCircle.setCenterY( location.getValue() );
    }

    public void drawCompletedGen(int gen, double best, double mean) {
        //if (gen <= 200 || (gen % 10 == 0)) {
            genBest.getData().add(new XYChart.Data<>(gen, best));
            genMean.getData().add(new XYChart.Data<>(gen, mean));
        //}
    }

    protected void drawProgressGeneration(int generation) {
        progressGenLabel.setText(Integer.toString(generation));
    }

    protected void drawProgressValue(int progress) {
        progressLabel.setText(Integer.toString(progress));
    }

    private void drawPopulationSize(int population) {
        populationLabel.setText(Integer.toString(population));
    }
}
