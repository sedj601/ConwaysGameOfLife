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
    List<Rectangle> rectanglesNeedColorSwap = new ArrayList();
    int size = 10;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        for (int row = 0; row < size; row++) {
            List<Rectangle> rectanglesRow = new ArrayList();

            for (int column = 0; column < size; column++) {
                Rectangle rectangle = new Rectangle(60, 60, Color.TRANSPARENT);
                rectangle.setOnMouseClicked(rectangleActionEvent);
                rectanglesRow.add(rectangle);
            }
            rectangles.add(rectanglesRow);
            tilePane.getChildren().addAll(rectanglesRow);

        }

        //checkLiveNeighborCount();
        // TODO
        timeline = new Timeline(new KeyFrame(Duration.seconds(.5), (event) -> {
            System.out.println("Timeline running!");
            checkLiveNeighborCount();
            rectanglesNeedColorSwap.forEach(rectangle -> switchColors(rectangle));
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);

    }

    @FXML
    void handleBtnOnactionStart(ActionEvent actionEvent)
    {
        System.out.println("Playing timeline");
        timeline.play();
    }

//    @FXML
//    void handleBtnOnactionPause(ActionEvent actionEvent)
//    {
//        System.out.println("Pausing timeline");
//        timeline.pause();
//    }
    @FXML
    void handleBtnOnactionStop(ActionEvent actionEvent)
    {
        System.out.println("Stopping timeline");
        timeline.stop();
    }

    private final EventHandler<MouseEvent> rectangleActionEvent = (event) -> {
        Rectangle rectangle = (Rectangle) event.getSource();
        switchColors(rectangle);
    };

    public void checkLiveNeighborCount()
    {
        rectanglesNeedColorSwap.clear();

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                int counter = 0;

                try {
                    if (rectangles.get(row - 1).get(column - 1).getFill().toString().equals("0x0000ffff")) {
                        counter++;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    System.out.println("do nothing!");
                }

                try {
                    if (rectangles.get(row - 1).get(column).getFill().toString().equals("0x0000ffff")) {
                        counter++;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    System.out.println("do nothing!");
                }

                try {
                    if (rectangles.get(row - 1).get(column + 1).getFill().toString().equals("0x0000ffff")) {
                        counter++;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    System.out.println("do nothing!");
                }
                try {
                    if (rectangles.get(row + 1).get(column - 1).getFill().toString().equals("0x0000ffff")) {
                        counter++;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    System.out.println("do nothing!");
                }
                try {
                    if (rectangles.get(row + 1).get(column).getFill().toString().equals("0x0000ffff")) {
                        counter++;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    System.out.println("do nothing!");
                }

                try {
                    if (rectangles.get(row + 1).get(column + 1).getFill().toString().equals("0x0000ffff")) {
                        counter++;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    System.out.println("do nothing!");
                }

                try {
                    if (rectangles.get(row).get(column - 1).getFill().toString().equals("0x0000ffff")) {
                        counter++;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    System.out.println("do nothing!");
                }

                try {
                    if (rectangles.get(row).get(column + 1).getFill().toString().equals("0x0000ffff")) {
                        counter++;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    System.out.println("do nothing!");
                }

                try {
                    rectangles.get(row).get(column + 1).setUserData(counter);
                }
                catch (IndexOutOfBoundsException ex) {
                    System.out.println("do nothing!");
                }
                // System.out.println("row: " + row + "  column: " + column + "  counter: " + counter + "  state: " + rectangles.get(row).get(column).getFill());
                if (counter < 2 && rectangles.get(row).get(column).getFill().toString().equals("0x0000ffff")) {
                    System.out.println("Found 1!");
                    //rectangles.get(row).get(column).setFill(Color.TRANSPARENT);
                    rectanglesNeedColorSwap.add(rectangles.get(row).get(column));
                }
                else if ((counter == 2 || counter == 3) && rectangles.get(row).get(column).getFill().toString().equals("0x0000ffff")) {
                    System.out.println("Found 2!");
                    //rectangles.get(row).get(column).setFill(Color.BLUE);
                }
                else if (counter > 3 && rectangles.get(row).get(column).getFill().toString().equals("0x0000ffff")) {
                    System.out.println("Found 3!");
                    rectanglesNeedColorSwap.add(rectangles.get(row).get(column));
                    //rectangles.get(row).get(column).setFill(Color.TRANSPARENT);
                }
                else if (counter == 3 && rectangles.get(row).get(column).getFill().toString().equals("0x00000000")) {
                    System.out.println("Found 4!");
                    //rectangles.get(row).get(column).setFill(Color.BLUE);
                    rectanglesNeedColorSwap.add(rectangles.get(row).get(column));
                }

            }

        }
    }

    private void switchColors(Rectangle rectangle)
    {
        switch (rectangle.getFill().toString()) {
            case "0x0000ffff":
                rectangle.setFill(Color.TRANSPARENT);
                break;
            case "0x00000000":
                rectangle.setFill(Color.BLUE);
                break;
        }
    }

}
