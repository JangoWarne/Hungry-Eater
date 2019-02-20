package uk.ac.glos.ct5055.assignment.s1609415.ui;

import guru.nidi.graphviz.attribute.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private void initialize() {
        // Handle Button event.
        newRegion.setOnMouseClicked(this::backRegionHandle);
    }

    public void setConfig(Config config) {
        // update UI based on config
    }

    public void setResult(GenerationResult result) {
        // update UI based on result

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
                )
                .with(
                        //TODO split into methods to set programmatically
                        graph().graphAttr().with(Rank.SAME).with(
                                node("sight_0"), node("sight_1"), node("sight_2"), node("sight_3"), node("sight_4")
                        ),
                        graph().graphAttr().with(Rank.SAME).with(
                                node("1_0"), node("1_1"), node("1_2"), node("1_3")
                        ),
                        graph().graphAttr().with(Rank.SAME).with(
                                node("direction")
                        ),

                        node("sight_0").link(to(node("sight_1")).with(Style.INVIS)),
                        node("sight_1").link(to(node("sight_2")).with(Style.INVIS)),
                        node("sight_2").link(to(node("sight_3")).with(Style.INVIS)),
                        node("sight_3").link(to(node("sight_4")).with(Style.INVIS)),
                        node("1_0").link(to(node("1_1")).with(Style.INVIS)),
                        node("1_1").link(to(node("1_2")).with(Style.INVIS)),
                        node("1_2").link(to(node("1_3")).with(Style.INVIS)),

                        node("sight_0")
                                .link(to(node("1_0")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.95)) )))
                                .link(to(node("1_1")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.9)) )))
                                .link(to(node("1_2")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.85)) )))
                                .link(to(node("1_3")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.8)) ))),
                        node("sight_1")
                                .link(to(node("1_0")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.75)) )))
                                .link(to(node("1_1")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.7)) )))
                                .link(to(node("1_2")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.65)) )))
                                .link(to(node("1_3")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.6)) ))),
                        node("sight_2")
                                .link(to(node("1_0")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.55)) )))
                                .link(to(node("1_1")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.5)) )))
                                .link(to(node("1_2")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.45)) )))
                                .link(to(node("1_3")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.4)) ))),
                        node("sight_3")
                                .link(to(node("1_0")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.35)) )))
                                .link(to(node("1_1")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.3)) )))
                                .link(to(node("1_2")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.25)) )))
                                .link(to(node("1_3")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.2)) ))),
                        node("sight_4")
                                .link(to(node("1_0")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.15)) )))
                                .link(to(node("1_1")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.1)) )))
                                .link(to(node("1_2")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.05)) )))
                                .link(to(node("1_3")).with(Color.rgba( String.format("000000%02X", Math.round(255*1)) ))),
                        node("1_0").link(to(node("direction")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.8)) ))),
                        node("1_1").link(to(node("direction")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.6)) ))),
                        node("1_2").link(to(node("direction")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.4)) ))),
                        node("1_3").link(to(node("direction")).with(Color.rgba( String.format("000000%02X", Math.round(255*0.2)) )))
                );

        Graphviz viz = Graphviz.fromGraph(g);
        BufferedImage capture = viz.height(700).render(Format.PNG).toImage();

        Image image = SwingFXUtils.toFXImage(capture, null);
        networkImageView.setImage(image);

        centerImage();
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

    public void centerImage() {
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
}
