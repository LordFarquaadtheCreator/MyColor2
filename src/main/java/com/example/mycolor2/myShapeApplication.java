package com.example.mycolor2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import static com.example.mycolor2.myColor.*;

public class myShapeApplication extends Application{ // formerly "testMyColor"

    myColor[] myColors = getMyColors();
    int sizeMyColor = myColors.length; // my attempt at scaling the boxes

    public HBox addTopPane(double widthTopCanvas, double heightTopCanvas, double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, myColorPalette CP, TilePane TP) throws FileNotFoundException {
        HBox HB = new HBox();
        HB.setPrefWidth(widthTopCanvas);
        HB.setPadding(new Insets(heightTopCanvas*.02));

        String [] nameImages = new String[] {
                "Arc", "Line", "Oval", "Polygon", "Rectangle", "Triangle", "Intersection"
        };
        Deque stackMyShapes = new ArrayDeque<myShape>();
        String pathFile = "src/main/java/com/example/mycolor2/";

        for(String nameImage : nameImages){
            String nameFile = pathFile + nameImage + ".PNG";
            ImageView geometryImage = new ImageView(new Image(new FileInputStream(nameFile), heightTopCanvas, heightTopCanvas, true, false));

            geometryImage.setOnMouseClicked(e->{
                switch (nameImage){
                    case "Arc":
                        dialogArc(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;
                    case "Line":
                        dialogLine(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;
                    case "Oval":
                        dialogOval(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;
                    case "Polygon":
                        dialogPolygon(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;
                    case "Rectangle":
                        dialogRectangle(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;
                    case "Triangle":
                        dialogTriangle(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;
                    case "Intersection":
                        dialogIntersection(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;
                }
            });
            HB.getChildren().add(geometryImage);
        }

        return HB;
    }

    public VBox addLeftVBox(double widthLeftCanvas, double heightCanvas, TilePane TP, myColor color){
        VBox VB = new VBox();
        VB.setPrefWidth(widthLeftCanvas);
        VB.setPadding(new Insets(widthLeftCanvas*.02)); // make it proportional to the size of the boxes of colors

        CornerRadii corner = new CornerRadii(10);
//        Insets inset = new Insets(10);
        Label lblMyColorPalette = new Label("Fahad's Color Palette"); //the label for the left box is text
        lblMyColorPalette.setPrefWidth(widthLeftCanvas); // the width is set to the size of the left box (with padding?)
        lblMyColorPalette.setPadding(new Insets(5)); //  i wanted the text to not be so squeezed
        lblMyColorPalette.setTextFill(WHITE.getJavaFXColor()); //text is white
        lblMyColorPalette.setBackground(new Background(new BackgroundFill(GRAY.getJavaFXColor(),corner, Insets.EMPTY)));

        VB.getChildren().addAll(lblMyColorPalette, TP);

        return VB;
    }

    public Canvas addCenterCanvas(double widthCanvas, double heightCanvas, myColor color){
        myColor colorPicked = Optional.ofNullable(color).orElse(WHITE);
        Canvas CV = new Canvas(widthCanvas, heightCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();

        GC.clearRect(0,0,widthCanvas, heightCanvas);
        GC.setFill(colorPicked.getJavaFXColor());
        GC.fillRect(0,0,widthCanvas,heightCanvas);

        double xText = 5.0; double yText = 20.0;
        GC.setStroke(colorPicked.invertColor());
        GC.setFont(Font.font("Comic Sans MS", 13));
        GC.strokeText(colorPicked.toString(), xText, yText);

        return CV;
    }

    // functions describing the dialog boxes for all of the shapes are described here

    public void dialogArc(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, myColorPalette CP, TilePane TP, Deque<myShape> stackMyShapes){
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("myOval");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        TextField xCenter = new TextField();
        TextField yCenter = new TextField();
        TextField width = new TextField();
        TextField height = new TextField();
        TextField startingAngle = new TextField();
        TextField extentAngle = new TextField();

        gridDialog.add(new Label("Oval Center"),0,0);
        gridDialog.add(xCenter,1,0);
        gridDialog.add(new Label("x-cord as a fraction of canvas width"), 2,0);
        gridDialog.add(yCenter, 1,1);
        gridDialog.add(new Label("y-cord as a fraction of canvas width"), 2,1);
        gridDialog.add(new Label("Oval Width"),0,2);
        gridDialog.add(width,1,2);
        gridDialog.add(new Label("Width as a fraction of canvas width"), 2,2);
        gridDialog.add(new Label("Oval Height"),0,3);
        gridDialog.add(height, 1,3);
        gridDialog.add(new Label("Arc Starting Angle"),0,4);
        gridDialog.add(startingAngle, 1,4);
        gridDialog.add(new Label("In Degrees"), 2,4);
        gridDialog.add(new Label("Arc (extent) angle"),0,5);
        gridDialog.add(extentAngle,1,5);
        gridDialog.add(new Label("In Degrees"), 2,5);

        dialog.getDialogPane().setContent(gridDialog);
        Platform.runLater(() -> xCenter.requestFocus());

        List<String> geometryImageInputs = new ArrayList<>();
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == ButtonType.OK){
                geometryImageInputs.add(xCenter.getText());
                geometryImageInputs.add(yCenter.getText());
                geometryImageInputs.add(width.getText());
                geometryImageInputs.add(height.getText());
                geometryImageInputs.add(startingAngle.getText());
                geometryImageInputs.add(extentAngle.getText());
                return geometryImageInputs;
            }
            return null;
        });

        Optional<List<String>> Result = dialog.showAndWait();
        Pane centerPane = new Pane();

        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event->{
            myPoint pTLC = new myPoint(Double.parseDouble(geometryImageInputs.get(0)) * widthCenterCanvas, Double.parseDouble(geometryImageInputs.get(1)) * heightCenterCanvas, null);
            double w = Double.parseDouble(geometryImageInputs.get(2))*widthCenterCanvas;
            double h = Double.parseDouble(geometryImageInputs.get(3))*heightCenterCanvas;
            double startAngle = Double.parseDouble(geometryImageInputs.get(4));
            double arcAngle = Double.parseDouble(geometryImageInputs.get(5));

            TP.setOnMouseClicked(e->{
                myColor color = CP.getColorPicked();
                String tileID = color.toString();
                for(Node tile : TP.getChildren()) {
                    if (tile.getId() == tileID) {
                        myOval O = new myOval(pTLC, w, h, color);
                        myArc A = new myArc(0, startingAngle, extentAngle, color);

                        GC.clearRect(0, 0, widthCenterCanvas, heightCenterCanvas);
                        O.stroke(GC);
                        A.draw(GC);
                        A.getBoundingRectangle().stroke(GC);

                        stackMyShapes.push(A);
                        break;
                    }
                }
            });
            centerPane.getChildren().add(CV);
            BP.setCenter(centerPane);
        });
    }

    @Override
    public void start(Stage stage){

        BorderPane BP = new BorderPane();
        Pane leftPane = new Pane();
        Pane centerPane = new Pane();
        Pane topPane = new Pane();

        double widthCanvas = 600;
        double heightCanvas = 400;
        double widthLeftCanvas = 0.4 * widthCanvas;
        double widthCenterCanvas = widthCanvas - widthLeftCanvas;
        double heightTopCanvas = 0.1 *heightCanvas;

        myColorPalette CP = new myColorPalette(widthLeftCanvas, heightCanvas);
        TilePane TP = CP.getPalette();

        leftPane.getChildren().add(addLeftVBox(widthLeftCanvas, heightCanvas, TP, BLACK));
        BP.setLeft(leftPane);

        centerPane.getChildren().add(addCenterCanvas(widthCenterCanvas, heightCanvas, null));
        BP.setCenter(centerPane);

        topPane.getChildren().add(addTopPane(heightTopCanvas));
        BP.setTop(topPane);


        TP.setOnMouseClicked(e->{

            myColor color = CP.getColorPicked();
            String tileID = color.toString();
            for(Node tile : TP.getChildren()){
                if (tile.getId() == tileID) {
                    centerPane.getChildren().add(addCenterCanvas(widthCenterCanvas, heightCanvas, color));
                    BP.setCenter(centerPane);
                    break;
                }
            }
        });

        Scene SC = new Scene(BP, widthCanvas, heightCanvas);
        stage.setTitle("My Color Project");
        stage.setScene(SC);
        stage.show();
    }

    public static void main(String[] args) {launch(args);}
}
