/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conwaysgameoflife;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author blj0011
 */
public class FXMLDocumentController implements Initializable
{

    @FXML
    private TilePane tilePane;

    Timeline timeline;
    List<List<Rectangle>> rectangles = new ArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        tilePane.setHgap(1);
        tilePane.setVgap(1);

        for (int row = 0; row < 100; row++) {
            List<Rectangle> rectanglesRow = new ArrayList();

            for (int column = 0; column < 100; column++) {

                Rectangle rectangle = new Rectangle(5, 5, Color.TRANSPARENT);
                rectangle.setOnMouseClicked(rectangleActionEvent);

                if (row > 95 && row < 100 && column > 0) {
                    rectangle.setFill(Color.BLUE);
                }
                rectanglesRow.add(rectangle);
            }
            rectangles.add(rectanglesRow);
            tilePane.getChildren().addAll(rectanglesRow);

        }

        checkLiveNeighborCount();
        // TODO
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), (event) -> {
            System.out.println("Timeline running!");
            checkLiveNeighborCount();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);

    }

    @FXML
    void handleBtnOnactionStart(ActionEvent actionEvent)
    {
        System.out.println("Playing timeline");
        timeline.play();
    }

    @FXML
    void handleBtnOnactionPause(ActionEvent actionEvent)
    {
        System.out.println("Pausing timeline");
        timeline.pause();
    }

    @FXML
    void handleBtnOnactionStop(ActionEvent actionEvent)
    {
        System.out.println("Stopping timeline");
        timeline.stop();
    }

    private final EventHandler<MouseEvent> rectangleActionEvent = (event) -> {
        Rectangle rectangle = (Rectangle) event.getSource();

        switch (rectangle.getFill().toString()) {
            case "0x0000ffff":
                rectangle.setFill(Color.TRANSPARENT);
                break;
            case "0x00000000":
                rectangle.setFill(Color.BLUE);
                break;
        }
    };

    public void checkLiveNeighborCount()
    {
        for (int row = 0; row < 100; row++) {
            for (int column = 0; column < 100; column++) {
                int counter = 0;

                if ((row - 1) > -1 && (column - 1) > -1 && (row + 1) < 100 && (column + 1) < 100) {

                    if (rectangles.get(row - 1).get(column - 1).getFill().toString().equals("0x0000ffff")) {
                        System.out.println("counter: " + counter + "  state: " + rectangles.get(row).get(column).getFill());
                        counter++;
                    }

                    if (rectangles.get(row - 1).get(column).getFill().toString().equals("0x0000ffff")) {
                        System.out.println("counter: " + counter + "  state: " + rectangles.get(row).get(column).getFill());
                        counter++;
                    }

                    if (rectangles.get(row).get(column - 1) != null && rectangles.get(row).get(column - 1).getFill().toString().equals("0x0000ffff")) {
                        System.out.println("counter: " + counter + "  state: " + rectangles.get(row).get(column).getFill());
                        counter++;
                    }

                    if (rectangles.get(row + 1).get(column + 1) != null && rectangles.get(row + 1).get(column + 1).getFill().toString().equals("0x0000ffff")) {
                        System.out.println("counter: " + counter + "  state: " + rectangles.get(row).get(column).getFill());
                        counter++;
                    }

                    if (rectangles.get(row).get(column + 1) != null && rectangles.get(row + 1).get(column + 1).getFill().toString().equals("0x0000ffff")) {
                        System.out.println("counter: " + counter + "  state: " + rectangles.get(row).get(column).getFill());
                        counter++;
                    }
                }

                if (counter < 2 && rectangles.get(row).get(column).toString().equals("0x0000ffff")) {
                    rectangles.get(row).get(column).setFill(Color.TRANSPARENT);
                }
                else if ((counter == 2 || counter == 3) && rectangles.get(row).get(column).toString().equals("0x0000ffff")) {
                    //do nothing but stay alive
                }
                else if (counter > 3 && rectangles.get(row).get(column).toString().equals("0x0000ffff")) {
                    rectangles.get(row).get(column).setFill(Color.TRANSPARENT);
                }
                else if (counter == 3 && rectangles.get(row).get(column).toString().equals("0x00000000")) {
                    rectangles.get(row).get(column).setFill(Color.BLUE);
                }

            }

        }
    }

}
