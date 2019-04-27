package uk.ac.glos.ct5055.assignment.s1609415.ui;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.model.Node;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import uk.ac.glos.ct5055.assignment.s1609415.population.GenerationResult;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;

import static guru.nidi.graphviz.model.Factory.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


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
    private ImageView networkImageView;
    @FXML
    private TableView genomeTableView;

    @FXML
    private void initialize() {
        // Handle Button event.
        newRegion.setOnMouseClicked(this::backRegionHandle);
    }

    public void drawResult(GenerationResult result) {
        // update UI based on result
        drawNetwork(result);
        drawGenome(result);
    }

    private void drawNetwork(GenerationResult result) {

        ArrayList<String> layer1 = new ArrayList<>(Arrays.asList("1_0", "1_1", "1_2", "1_3", "1_4", "1_5"));

        ArrayList<String> namesIn = new ArrayList<>();
        namesIn.addAll(layer1);

        ArrayList<Pair<String,Double>> links1 = new ArrayList<>();
        links1.add( new Pair<>("1_0",0.95) );
        links1.add( new Pair<>("1_1",0.9) );
        links1.add( new Pair<>("1_2",0.85) );
        links1.add( new Pair<>("1_3",0.8) );
        links1.add( new Pair<>("1_4",0.75) );
        links1.add( new Pair<>("1_5",0.7) );

        ArrayList<Pair<String,Double>> links2 = new ArrayList<>();
        links2.add( new Pair<>("1_0",0.65) );
        links2.add( new Pair<>("1_1",0.6) );
        links2.add( new Pair<>("1_2",0.55) );
        links2.add( new Pair<>("1_3",0.5) );
        links2.add( new Pair<>("1_4",0.45) );
        links2.add( new Pair<>("1_5",0.4) );

        ArrayList<Pair<String,Double>> links3 = new ArrayList<>();
        links3.add( new Pair<>("1_0",0.35) );
        links3.add( new Pair<>("1_1",0.3) );
        links3.add( new Pair<>("1_2",0.25) );
        links3.add( new Pair<>("1_3",0.2) );
        links3.add( new Pair<>("1_4",0.15) );
        links3.add( new Pair<>("1_5",0.1) );

        ArrayList<Pair<String,Double>> links4 = new ArrayList<>();
        links4.add( new Pair<>("1_0",0.05) );
        links4.add( new Pair<>("1_1",1.0) );
        links4.add( new Pair<>("1_2",0.95) );
        links4.add( new Pair<>("1_3",0.9) );
        links4.add( new Pair<>("1_4",0.85) );
        links4.add( new Pair<>("1_5",0.8) );

        ArrayList<Pair<String,Double>> links5 = new ArrayList<>();
        links5.add( new Pair<>("1_0",0.75) );
        links5.add( new Pair<>("1_1",0.7) );
        links5.add( new Pair<>("1_2",0.65) );
        links5.add( new Pair<>("1_3",0.6) );
        links5.add( new Pair<>("1_4",0.55) );
        links5.add( new Pair<>("1_5",0.5) );

        ArrayList<Pair<String,Double>> links6 = new ArrayList<>();
        links6.add( new Pair<>("1_0",0.45) );
        links6.add( new Pair<>("1_1",0.4) );
        links6.add( new Pair<>("1_2",0.35) );
        links6.add( new Pair<>("1_3",0.3) );
        links6.add( new Pair<>("1_4",0.25) );
        links6.add( new Pair<>("1_5",0.2) );

        ArrayList<Pair<String,Double>> links7 = new ArrayList<>(Arrays.asList( new Pair<>("relative_direction",1.0) ));
        ArrayList<Pair<String,Double>> links8 = new ArrayList<>(Arrays.asList( new Pair<>("relative_direction",0.8) ));
        ArrayList<Pair<String,Double>> links9 = new ArrayList<>(Arrays.asList( new Pair<>("relative_direction",0.6) ));
        ArrayList<Pair<String,Double>> links10 = new ArrayList<>(Arrays.asList( new Pair<>("relative_direction",0.4) ));
        ArrayList<Pair<String,Double>> links11 = new ArrayList<>(Arrays.asList( new Pair<>("relative_direction",0.2) ));
        ArrayList<Pair<String,Double>> links12 = new ArrayList<>(Arrays.asList( new Pair<>("relative_direction",0.1) ));

        ArrayList<ArrayList< Pair<String,Double> >> links = new ArrayList<>(Arrays.asList(
                links1, links2, links3, links4, links5, links6, links7, links8, links9, links10, links11, links12));



        Graph g = graph("Neural Network")
                .graphAttr().with(
                        RankDir.LEFT_TO_RIGHT,
                        Color.rgb("F4F4F4").background()
                )
                .graphAttr().with("splines", "line")
                .graphAttr().with("ranksep", "2")
                .nodeAttr().with(
                        Shape.CIRCLE,
                        Style.FILLED,
                        Color.WHITE.fill()
                );

        ArrayList<String> input = new ArrayList<>(Arrays.asList("sight_0", "sight_1", "sight_2", "sight_3", "sight_4"));
        ArrayList<String> names = new ArrayList<>();
        names.addAll(input);
        names.addAll(namesIn);
        ArrayList<String> output = new ArrayList<>(Arrays.asList("new_direction"));

        g = addLayer(g, input);
        g = addLayer(g, layer1);
        g = addLayer(g, output);

        g = addLinks(g, names, links);

        Graphviz viz = Graphviz.fromGraph(g);
        BufferedImage capture = viz.height(700).render(Format.PNG).toImage();

        Image image = SwingFXUtils.toFXImage(capture, null);
        networkImageView.setImage(image);

        centerImage();
    }

    private void drawGenome(GenerationResult result) {

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

    private void centerImage() {
        Image img = networkImageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = networkImageView.getFitWidth() / img.getWidth();
            double ratioY = networkImageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            networkImageView.setX((networkImageView.getFitWidth() - w) / 2);
            networkImageView.setY((networkImageView.getFitHeight() - h) / 2);

        }
    }

    private Graph addLayer(Graph g, ArrayList<String> names) {
        ArrayList<Node> nodesAL = new ArrayList<>();

        for (String name: names) {
            nodesAL.add(node(name));
        }

        Node[] nodesAdd = new Node[nodesAL.size()];
        nodesAdd = nodesAL.toArray(nodesAdd);

        g = g.with(
            graph().graphAttr().with(Rank.SAME).with(
                    nodesAdd
            )
        );

        for (int i = 0; i < names.size()-1; i++) {
            g = g.with(
                    node( names.get(i) ).link(to(node( names.get(i+1) )).with(Style.INVIS))
            );
        }
        return g;
    }

    private Graph addLinks(Graph g, ArrayList<String> names, ArrayList<ArrayList< Pair<String,Double> >> links) {

        ArrayList<Node> nodes = new ArrayList<>();
        int i = 0;
        ArrayList< Pair<String,Double> > link;

        for (String name: names) {
            link = links.get(i);
            nodes.add( createLinks(name, link) );
            i++;
        }

        Node[] arr = new Node[nodes.size()];
        arr = nodes.toArray(arr);

        g = g.with( arr );

        return g;
    }

    private Node createLinks(String name, ArrayList< Pair<String,Double> > links) {

        Node node = node(name);

        for (Pair<String, Double> link: links) {
            node = node.link(to(node( link.getKey() )).with(Color.rgba( String.format("000000%02X", Math.round(255*link.getValue())) )));
        }

        return node;
    }
}
