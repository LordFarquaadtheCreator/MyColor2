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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.LineNumberInputStream;
import java.util.*;

import static com.example.mycolor2.myColor.*;

public class myShapeApplication extends Application{ // formerly "testMyColor"

    myColor[] myColors = getMyColors();
    int sizeMyColor = myColors.length; // my attempt at scaling the boxes

    public HBox addTopHBox(double widthTopCanvas, double heightTopCanvas, double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, myColorPalette CP, TilePane TP) throws FileNotFoundException {
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
//                    case "Arc":
//                        dialogArc(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
//                        break;
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

//    public void dialogArc(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, myColorPalette CP, TilePane TP, Deque<myShape> stackMyShapes){
//        Dialog<List<String>> dialog = new Dialog<>();
//        dialog.setTitle("myOval");
//        dialog.setHeaderText(null);
//
//        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
//
//        GridPane gridDialog = new GridPane();
//        gridDialog.setHgap(10);
//        gridDialog.setVgap(10);
//        gridDialog.setPadding(new Insets(20,100,10,10));
//
//        TextField xCenter = new TextField();
//        TextField yCenter = new TextField();
//        TextField width = new TextField();
//        TextField height = new TextField();
//        TextField startingAngle = new TextField();
//        TextField extentAngle = new TextField();
//
//        gridDialog.add(new Label("Oval Center"),0,0);
//        gridDialog.add(xCenter,1,0);
//        gridDialog.add(new Label("x-cord as a fraction of canvas width"), 2,0);
//        gridDialog.add(yCenter, 1,1);
//        gridDialog.add(new Label("y-cord as a fraction of canvas width"), 2,1);
//        gridDialog.add(new Label("Oval Width"),0,2);
//        gridDialog.add(width,1,2);
//        gridDialog.add(new Label("Width as a fraction of canvas width"), 2,2);
//        gridDialog.add(new Label("Oval Height"),0,3);
//        gridDialog.add(height, 1,3);
//        gridDialog.add(new Label("Arc Starting Angle"),0,4);
//        gridDialog.add(startingAngle, 1,4);
//        gridDialog.add(new Label("In Degrees"), 2,4);
//        gridDialog.add(new Label("Arc (extent) angle"),0,5);
//        gridDialog.add(extentAngle,1,5);
//        gridDialog.add(new Label("In Degrees"), 2,5);
//
//        dialog.getDialogPane().setContent(gridDialog);
//        Platform.runLater(() -> xCenter.requestFocus());
//
//        List<String> geometryImageInputs = new ArrayList<>();
//        dialog.setResultConverter(dialogButton -> {
//            if(dialogButton == ButtonType.OK){
//                geometryImageInputs.add(xCenter.getText());
//                geometryImageInputs.add(yCenter.getText());
//                geometryImageInputs.add(width.getText());
//                geometryImageInputs.add(height.getText());
//                geometryImageInputs.add(startingAngle.getText());
//                geometryImageInputs.add(extentAngle.getText());
//                return geometryImageInputs;
//            }
//            return null;
//        });
//
//        Optional<List<String>> Result = dialog.showAndWait();
//        Pane centerPane = new Pane();
//
//        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
//        GraphicsContext GC = CV.getGraphicsContext2D();
//        Result.ifPresent(event->{
//            myPoint pTLC = new myPoint(Double.parseDouble(geometryImageInputs.get(0)) * widthCenterCanvas, Double.parseDouble(geometryImageInputs.get(1)) * heightCenterCanvas, null);
//            double w = Double.parseDouble(geometryImageInputs.get(2))*widthCenterCanvas;
//            double h = Double.parseDouble(geometryImageInputs.get(3))*heightCenterCanvas;
//            double startAngle = Double.parseDouble(geometryImageInputs.get(4));
//            double arcAngle = Double.parseDouble(geometryImageInputs.get(5));
//
//            TP.setOnMouseClicked(e->{
//                myColor color = CP.getColorPicked();
//                String tileID = color.toString();
//                for(Node tile : TP.getChildren()) {
//                    if (tile.getId() == tileID) {
//                        myOval O = new myOval(pTLC, w, h, color);
//                        myArc A = new myArc(0, startingAngle, extentAngle, color);
//
//                        GC.clearRect(0, 0, widthCenterCanvas, heightCenterCanvas);
//                        O.stroke(GC);
//                        A.draw(GC);
//                        A.getBoundingRectangle().stroke(GC);
//
//                        stackMyShapes.push(A);
//                        break;
//                    }
//                }
//            });
//            centerPane.getChildren().add(CV);
//            BP.setCenter(centerPane);
//        });
//    }

    public void dialogLine(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, myColorPalette CP, TilePane TP, Deque<myShape> stackMyShapes){
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("MyTriangle");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10); gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        TextField x1 = new TextField(); TextField y1 = new TextField();
        TextField x2 = new TextField(); TextField y2 = new TextField();

        gridDialog.add(new Label("End points as fraction of canvas width and height"), 0,0);
        gridDialog.add(new Label("Point 1"), 0, 1);
        gridDialog.add(x1,1,1);
        gridDialog.add(y1,2,1);
        gridDialog.add(new Label("Point 2"), 0, 2);
        gridDialog.add(x2,1,2);
        gridDialog.add(y2,2,2);

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> x1.requestFocus());

        List<String> geometricImageInput = new ArrayList();
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == ButtonType.OK){
                geometricImageInput.add(x1.getText()); geometricImageInput.add(y1.getText());
                geometricImageInput.add(x2.getText()); geometricImageInput.add(y2.getText());
                return geometricImageInput;
            }
            return null;
        });

        Optional<List<String>> Result = dialog.showAndWait();

        Pane centerPane = new Pane();

        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event -> {
            myPoint p = new myPoint(Double.parseDouble(geometricImageInput.get(0)) * widthCenterCanvas, Double.parseDouble(geometricImageInput.get(1))*heightCenterCanvas, null);
            myPoint q = new myPoint(Double.parseDouble(geometricImageInput.get(2)) * widthCenterCanvas, Double.parseDouble(geometricImageInput.get(3))*heightCenterCanvas, null);

            TP.setOnMouseClicked(e-> {
                myColor color = CP.getColorPicked();
                String tileId = color.toString();

                for(Node tile : TP.getChildren()){
                    if(tile.getId() == tileId){
                        myLine L = new myLine(p,q,color);

                        GC.clearRect(0,0,widthCenterCanvas,heightCenterCanvas);
                        L.draw(GC);
                        L.getMyBoundingRectangle().stroke(GC);

                        stackMyShapes.push(L);
                        break;
                    }
                }
            });

            centerPane.getChildren().add(CV);
            BP.setCenter(centerPane);
        });
    }

    public void dialogOval(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, myColorPalette CP, TilePane TP, Deque<myShape> stackMyShape){
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("MyOval");
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

        gridDialog.add(new Label("Center"), 0, 0);
        gridDialog.add(xCenter,1,0);
        gridDialog.add(new Label("x-Coordiante as fraction of canvas width"), 2,0);
        gridDialog.add(yCenter,1,1);
        gridDialog.add(new Label("y-Coordinate as fraction of canvas width"), 2, 1);
        gridDialog.add(new Label("Width"), 1,2);
        gridDialog.add(width,0,2);
        gridDialog.add(new Label("Width as fraction of canvas width"), 2,1);
        gridDialog.add(new Label("Height"),0,3);
        gridDialog.add(height,1,3);
        gridDialog.add(new Label("Width as fraction of canvas width"), 2,2);
        gridDialog.add(new Label("Height"), 0,3);
        gridDialog.add(height,1,3);
        gridDialog.add(new Label("Height as fraction of canvas height"), 2, 3);

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> xCenter.requestFocus());

        List<String> geometricImageInputs = new ArrayList();
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == ButtonType.OK){
                geometricImageInputs.add(xCenter.getText());
                geometricImageInputs.add(yCenter.getText());
                geometricImageInputs.add(width.getText());
                return geometricImageInputs;
            }
            return null;
        });

        Optional<List<String>> Result = dialog.showAndWait();

        Pane centerPane = new Pane();

        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event -> {
            myPoint pTLC = new myPoint(Double.parseDouble(geometricImageInputs.get(0))*widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(1))*heightCenterCanvas, null);
            double w = Double.parseDouble(geometricImageInputs.get(2))*widthCenterCanvas;
            double h = Double.parseDouble(geometricImageInputs.get(3))*heightCenterCanvas;

            TP.setOnMouseClicked(e-> {
                myColor color = CP.getColorPicked();
                String tileId = myColors.toString();
                for (Node tile : TP.getChildren()) {
                    if (tile.getId() == tileId) {
                        myOval O = new myOval(pTLC, w, h, color);

                        GC.clearRect(0, 0, widthCenterCanvas, heightCenterCanvas);
                        O.draw(GC);
                        O.getMyBoundingRectangle().stroke(GC);

                        stackMyShape.push(0);
                        break;
                    }
                }
            });
        centerPane.getChildren().add(CV);
        BP.setCenter(centerPane);
        });
    }

    public void dialogPolygon(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, myColorPalette CP, TilePane TP, Deque<myShape> stackMyShape){
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("myOval");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        TextField numberSides = new TextField();
        TextField xCenter = new TextField(); TextField yCenter = new TextField();
        TextField radius = new TextField();

        gridDialog.add(new Label("Number or Sides"),0,0);
        gridDialog.add(numberSides,1,0);
        gridDialog.add(new Label("Maximum number of sides"+ myShapeInterface.maxNumberPolygonSides), 2,0);
        gridDialog.add(new Label("Center"),0,1);
        gridDialog.add(xCenter,1,1);
        gridDialog.add(new Label("x-Coordiante as a fraction of canvas width"), 2,1);
        gridDialog.add(yCenter,0,3);
        gridDialog.add(new Label("y-Coordiante as a fraction of canvas height"), 2,2);
        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> numberSides.requestFocus());

        List<String> geometricImageInput = new ArrayList<>();
        dialog.setResultConverter(dialogButton ->{
            if(dialogButton == ButtonType.OK){
                geometricImageInput.add(numberSides.getText());
                geometricImageInput.add(xCenter.getText());
                geometricImageInput.add(yCenter.getText());
            }
            return null;
        });

        Optional<List<String>> Result = dialog.showAndWait();

        Pane centerPane = new Pane();

        Canvas CV = new Canvas(widthCenterCanvas,heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event -> {
            int N = Integer.parseInt(geometricImageInput.get(0));
            myPoint center = new myPoint(Double.parseDouble(geometricImageInput.get(1)*widthCenterCanvas, Double.parseDouble(2)*heightCenterCanvas));
            double r = Double.parseDouble(geometricImageInput.get(3)*Math.min(widthCenterCanvas, heightCenterCanvas));

            TP.setOnMouseClicked(e->{
                myColor color = CP.getColorPicked();
                String tileId = color.toString();
                for(Node tile : TP.getChildren()){
                    if(tile.getId() == tileId){
                        myPolygon Y = new myPolygon(N, center, r, color);
                        GC.clearRect(0,0,widthCenterCanvas,heightCenterCanvas);
                        Y.draw(GC);
                        Y.getMyBoundingRectangle().stroke(GC);

                        stackMyShape.push(Y);
                        break;
                    }
                }
            });
            centerPane.getChildren().add(CV);
            BP.setCenter(centerPane);
        });
    }

    public void dialogRectangle(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, myColorPalette CP, TilePane TP, Deque<myShape> stackMyShapes){
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("myRectangle");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10); gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        TextField xPTLC = new TextField(); TextField yPTLC = new TextField();
        TextField width = new TextField(); TextField height = new TextField();

        gridDialog.add(new Label("Top Left Corner Point"),0,0);
        gridDialog.add(xPTLC,1,0);
        gridDialog.add(new Label("x-Coordiante as fraction of canvas width"),2,0);
        gridDialog.add(yPTLC,1,1);
        gridDialog.add(new Label("y-Coordinate as fraction of canvas width"),2,1);
        gridDialog.add(new Label("Width"),0,2);
        gridDialog.add(width,1,2);
        gridDialog.add(new Label("As fraction of canvas width"),2,2);
        gridDialog.add(new Label("Height"),0,3);
        gridDialog.add(height,1,3);
        gridDialog.add(new Label("as fraction of canvas height"),2,3);

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> xPTLC.requestFocus());

        List<String> geometericImageInputs = new ArrayList<>();
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == ButtonType.OK){
                geometericImageInputs.add(xPTLC.getText()); geometericImageInputs.add(yPTLC.getText());
                geometericImageInputs.add(width.getText()); geometericImageInputs.add(height.getText());
                return geometericImageInputs;
            }
            return null;
        });

        Optional<List<String>> Result = dialog.showAndWait();

        Pane centerPane = new Pane();

        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event ->{
            myPoint pTLC = new myPoint(Double.parseDouble(geometericImageInputs.get(0))*widthCenterCanvas, Double.parseDouble(geometericImageInputs.get(1))*heightCenterCanvas, null);
            double w = Double.parseDouble(geometericImageInputs.get(2))*widthCenterCanvas;
            double h = Double.parseDouble(geometericImageInputs.get(3))*heightCenterCanvas;

            TP.setOnMouseClicked(e->{
                myColor color = CP.getColorPicked();
                String tileId = color.toString();
                for(Node tile : TP.getChildren()){
                    myRectangle R = new myRectangle(pTLC, w,h,color);
                    GC.clearRect(0,0,widthCenterCanvas,heightCenterCanvas);
                    R.draw(GC);
                    R.getMyBoundingRectangle().stroke(GC);

                    stackMyShapes.push(R);
                    break;
                }
            });

            centerPane.getChildren().add(CV);
            BP.setCenter(centerPane);
        });
    }

    public void dialogTriangle(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, myColorPalette CP, TilePane TP, Deque<myShape> stackMyShapes){
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("myTriangle");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        TextField x1 = new TextField(); TextField y1 = new TextField();
        TextField x2 = new TextField(); TextField y2 = new TextField();
        TextField x3 = new TextField(); TextField y3 = new TextField();

        gridDialog.add(new Label("Triangle verticies as fraction of canvas width and height"), 0,0);
        gridDialog.add(new Label("Vertex 1"), 0,1);
        gridDialog.add(x1,1,1);
        gridDialog.add(y1,2,1);
        gridDialog.add(new Label("Vertex 2"),0,2);
        gridDialog.add(x2,1,2);
        gridDialog.add(y2,2,2);
        gridDialog.add(new Label("Vertex 2"),0,3);
        gridDialog.add(x3,1,3);
        gridDialog.add(y3,2,3);

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(()-> x1.requestFocus());

        List<String> geometricImageInputs = new ArrayList<>();
        dialog.setResultConverter(dialogButtton ->{
            if(dialogButtton == ButtonType.OK){
                geometricImageInputs.add(x1.getText()); geometricImageInputs.add(y1.getText());
                geometricImageInputs.add(x2.getText()); geometricImageInputs.add(y2.getText());
                geometricImageInputs.add(x3.getText()); geometricImageInputs.add(y3.getText());
            }
            return null;
        });

        Optional<List<String>> Result = dialog.showAndWait();

        Pane centerPane = new Pane();

        Canvas CV = new Canvas(widthCenterCanvas,heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event ->{
            myPoint p = new myPoint(Double.parseDouble(geometricImageInputs.get(0))*widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(1))*heightCenterCanvas, null);
            myPoint q = new myPoint(Double.parseDouble(geometricImageInputs.get(2))*widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(3))*heightCenterCanvas, null);
            myPoint r = new myPoint(Double.parseDouble(geometricImageInputs.get(4))*widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(5))*heightCenterCanvas, null);

            TP.setOnMouseClicked(e->{
                myColor color = CP.getColorPicked();
                String tileId = color.toString();
                for(Node tile : TP.getChildren()){
                    if(tile.getId() == tileId){
                        myTriangle T = new myTriangle(p,q,r,color);

                        GC.clearRect(0,0,widthCenterCanvas,heightCenterCanvas);
                        T.draw(GC);
                        T.getMyBoundingRectangle().stroke(GC);

                        stackMyShapes.push(T);
                        break;
                    }
                }
            });
            centerPane.getChildren().add(CV);
            BP.setCenter(centerPane);
        });
    }

    public void dialogIntersection(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, myColorPalette CP, TilePane TP, Deque<myShape> stackMyShapes){
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Intersection of 2 myShape Objects");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        gridDialog.add(new Label("Draw the intersection of the last two myShape obkects"),0,0);

        dialog.getDialogPane().setContent(gridDialog);
        dialog.showAndWait().ifPresent(response ->{
            if(response == ButtonType.OK){
                TP.setOnMouseClicked(e->{
                    myColor color = CP.getColorPicked();
                    String tileId = color.toString();
                    for(Node tile : TP.getChildren()){
                        Pane centerPane = new Pane();

                        myShape s1 = stackMyShapes.pop();
                        myshape s2 = stackMyShapes.pop();
                        centerPane.getChildren().add(addCenterCanvas(widthCenterCanvas, heightCenterCanvas, s1,s2,color));
                        BP.setCenter(centerPane);
                        break;
                    }
                });
            }
        });

    }

    public Canvas addCenterCanvas(double widthCenterCanvas, double heightCenterCanvas, myShape s1, myShape s2, myColor color){
        return s1.drawIntersectMyShapes(widthCenterCanvas, heightCenterCanvas, s1,s2,color);
    }

    @Override
    public void start(Stage PS) throws FileNotFoundException {

        BorderPane BP = new BorderPane();
        Pane leftPane = new Pane();
        Pane centerPane = new Pane();
        Pane topPane = new Pane();

        double widthCanvas = 800; double heightCanvas = 600;

        double widthLeftCanvas = 0.3*widthCanvas; double heightTopCanvas = 0.15*heightCanvas;
        double widthCenterCanvas = widthCanvas - widthLeftCanvas;
        double heightCenterCanvas = heightCanvas - heightTopCanvas;

        myColorPalette CP = new myColorPalette(widthLeftCanvas, heightCanvas);
        TilePane TP = CP.getPalette();

//        leftPane.getChildren().add(addLeftVBox(widthLeftCanvas, heightCanvas, TP, BLACK));
//        BP.setLeft(leftPane);
//
//        centerPane.getChildren().add(addCenterCanvas(widthCenterCanvas, heightCanvas, null));
//        BP.setCenter(centerPane);

        Scene SC = new Scene(BP, widthCanvas, heightCanvas, myColor.WHITE.getJavaFXColor());
        PS.setTitle("myShape!");
        PS.setScene(SC);

        myPoint r = new myPoint(widthCenterCanvas, 0.5*heightCenterCanvas, null);
        myPoint n = new myPoint(widthCenterCanvas, heightCenterCanvas, null);
        myPoint v = new myPoint(0.5*widthCenterCanvas,heightCenterCanvas, null);
        myPoint h = new myPoint(0,widthCenterCanvas, null);
        myPoint u = new myPoint(0,0.5*heightCenterCanvas,null);
        myPoint t = new myPoint();
        myPoint p = new myPoint(0.5*widthCenterCanvas,0,null);
        myPoint k = new myPoint(widthCenterCanvas, 0,null);

        myPoint q = new myPoint(0.5*widthCenterCanvas, 0.5*heightCenterCanvas,null);

        System.out.println("\nAngle of the line extending from " + r + " to origin " + q + ": " + r.angleX(q));
        //theres more printlines here

        myLine l1 = new myLine(p,q,null);
        System.out.println("\n"+l1);

        myLine l2 = new myLine(r,q,null);
        System.out.println("\n"+l2);

        myRectangle R = new myRectangle(p,0.5*widthCenterCanvas, 0.5*widthCenterCanvas,myColor.LIME);
        System.out.println("\n"+R);

        myOval O = new myOval(q,0.5*widthCenterCanvas, 0.5*widthCenterCanvas, GOLD);
        System.out.println("\n"+O);

        double radius = 0.25*Math.min(widthCenterCanvas,heightCenterCanvas);
        myCircle C = new myCircle(q,radius, GREEN);
        System.out.println("\n"+C);

        myTriangle T = new myTriangle(t,r,v, CYAN);
        System.out.println("\n"+T);

        double startAngle = 40;
        double arcAngle = 210;
//        myArc A = new myArc(O,startAngle, arcAngle, GREY);
        System.out.println("\n"+A);

        myShapeInterface [] shapes = new myShape[7];
        shapes[0] = l1; shapes[1]=l2; shapes[2]=R; shapes[3]=O; shapes[4]=C; shapes[5]=T; shapes[6]=A;

        for(myShapeInterface shape : shapes){
            System.out.println("\n"+shape);
            System.out.println(shape.getMyBoundingRectangle());
        }

        myRectangle RR = (myRectangle) shapes[2];
        myOval OO = (myOval) shapes[3];
        myCircle CC = (myCircle) shapes[4];
        myTriangle TT = (myTriangle) shapes[5];
//        myArc AA = (myArc) shapes[6];

        System.out.println("\n"+myShapeInterface.overlapMyShapes(TT,AA));

        topPane.getChildren().add(addTopHBox(widthCanvas, heightTopCanvas, widthCenterCanvas,TP, BLACK));
        BP.setTop(topPane);

        leftPane.getChildren().add(addLeftVBox(widthLeftCanvas, heightCenterCanvas,TP, BLACK));
        BP.setLeft(topPane);

        PS.show();


//        TP.setOnMouseClicked(e->{
//
//            myColor color = CP.getColorPicked();
//            String tileID = color.toString();
//            for(Node tile : TP.getChildren()){
//                if (tile.getId() == tileID) {
//                    centerPane.getChildren().add(addCenterCanvas(widthCenterCanvas, heightCanvas, color));
//                    BP.setCenter(centerPane);
//                    break;
//                }
//            }
//        });

//        Scene SC = new Scene(BP, widthCanvas, heightCanvas);
//        stage.setTitle("My Color Project");
//        stage.setScene(SC);
//        stage.show();
    }

    public static void main(String[] args) {launch(args);}
}
