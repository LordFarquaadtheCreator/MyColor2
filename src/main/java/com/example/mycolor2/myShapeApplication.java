package com.example.mycolor2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.Scene.*;
import java.io.LineNumberInputStream;
import java.util.*;

public class myShapeApplication extends Application{ // formerly "testMyColor"
    public HBox addTopHBox(double widthTopCanvas, double heightTopCanvas, double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, myColorPalette CP, TilePane TP) throws FileNotFoundException {
        HBox HB = new HBox();
        HB.setPrefWidth(widthTopCanvas);
        HB.setPrefHeight(heightTopCanvas);
        HB.setStyle("-fx-background-color: #B2A4FF;");

        String [] nameImages = new String [] {"Oval", "Rectangle", "Intersection"};
        String pathFile = "src/main/java/com/example/mycolor2/";
        Deque<myShape> stackMyShapes = new ArrayDeque<>();
        HB.setSpacing(50);
        HB.setAlignment(Pos.CENTER);

        for (String nameImage : nameImages){
            String nameFile = pathFile + nameImage + ".png";
            ImageView geometricImage = new ImageView(new Image(new FileInputStream(nameFile), heightTopCanvas, heightTopCanvas, true, false));

            //draw a geometric shape on mouse click: lambda expression
            geometricImage.setOnMouseClicked(e -> {
                switch (nameImage){
                    case "Oval":  //MyOval object
                        dialogOval(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;
                    case "Rectangle": //Myrectangle object
                        dialogRectangle(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                        break;
                    case "Intersection": //Intersection of 2 myshape objects
                        dialogIntersection(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes);
                }
            });
            HB.getChildren().add(geometricImage);
        }
        return HB;
    }

    public VBox addLeftVBox(double widthLeftCanvas, double heightLeftCanvas, TilePane TP, myColor color){
        //make a vbox node
        VBox VB = new VBox();
        VB.setPrefWidth(widthLeftCanvas);
        VB.setPrefHeight(heightLeftCanvas);
        VB.setPadding(new Insets(5));
        VB.setStyle("-fx-background-color: #FFB4B4;");

        //make label my color palette
        Label lblMyColorPalette = new Label("MyColor Palette");
        lblMyColorPalette.setPrefWidth(widthLeftCanvas);
        lblMyColorPalette.setTextFill(myColor.WHITE.getJavaFXColor());
        lblMyColorPalette.setBackground(new Background(new BackgroundFill(Optional.ofNullable(color).orElse(myColor.GREY).getJavaFXColor(), CornerRadii.EMPTY, Insets.EMPTY)));

        //make a mycolorpalette of all mycolor objects and add into the vbox together with the label
        VB.getChildren().addAll(lblMyColorPalette, TP);
        return VB;
    }

    public Canvas addCenterCanvas(double widthCenterCanvas, double heightCenterCanvas, myShape s1, myShape s2, myColor color){
        return s1.drawIntersectMyShapes(widthCenterCanvas, heightCenterCanvas, s1,s2,color);
    }

    public void dialogOval(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, myColorPalette CP, TilePane TP, Deque<myShape> stackMyShape){
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("MyOval");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10); gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        TextField xCenter = new TextField();
        TextField yCenter = new TextField();
        TextField width = new TextField();
        TextField height = new TextField();

        gridDialog.add(new Label("Center"), 0, 0);
        gridDialog.add(xCenter,1,0);
        gridDialog.add(new Label("x-Coordinate as fraction of canvas width"), 2,0);
        gridDialog.add(yCenter,1,1);
        gridDialog.add(new Label("y-Coordinate as fraction of canvas width"), 2, 1);
        gridDialog.add(new Label("Width"), 0,2);
        gridDialog.add(width,1,2);
        gridDialog.add(new Label("Width as fraction of canvas width"), 2,2);
        gridDialog.add(new Label("Height"),0,3);
        gridDialog.add(height,1,3);
        gridDialog.add(new Label("Width as fraction of canvas width"), 2,3);

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> xCenter.requestFocus());

        List<String> geometricImageInputs = new ArrayList();
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == ButtonType.OK){
                geometricImageInputs.add(xCenter.getText());
                geometricImageInputs.add(yCenter.getText());
                geometricImageInputs.add(width.getText());
                geometricImageInputs.add(height.getText());
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
                String tileId = color.toString();
                for (Node tile : TP.getChildren()) {
                    if (tile.getId() == tileId) {
                        myOval O = new myOval(pTLC, w, h, color);

                        GC.clearRect(0, 0, widthCenterCanvas, heightCenterCanvas);
                        O.draw(GC);
                        O.getMyBoundingRectangle().stroke(GC);

                        stackMyShape.push(O);
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
                for(Node tile : TP.getChildren()) {
                    if (tile.getId() == tileId) {
                        myRectangle R = new myRectangle(pTLC, w, h, color);
                        GC.clearRect(0, 0, widthCenterCanvas, heightCenterCanvas);
                        R.draw(GC);
                        R.getMyBoundingRectangle().stroke(GC);

                        stackMyShapes.push(R);
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

        gridDialog.add(new Label("Draw the intersection of the last two myShape objects"),0,0);

        dialog.getDialogPane().setContent(gridDialog);
        dialog.showAndWait().ifPresent(response ->{
            if(response == ButtonType.OK){
                TP.setOnMouseClicked(e->{
                    myColor color = CP.getColorPicked();
                    String tileId = color.toString();
                    for(Node tile : TP.getChildren()) {
                        if (tile.getId() == tileId) {
                            Pane centerPane = new Pane();

                            myShape s1 = stackMyShapes.pop();
                            myShape s2 = stackMyShapes.pop();
                            centerPane.getChildren().add(addCenterCanvas(widthCenterCanvas, heightCenterCanvas, s1, s2, color));
                            BP.setCenter(centerPane);
                            break;
                        }
                    }
                });
            }
        });

    }

    @Override
    public void start(Stage PS) throws FileNotFoundException {
        double widthCanvas = 800.0;
        double heightCanvas = 600.0;

        BorderPane BP = new BorderPane();
        Pane topPane = new Pane();
        Pane leftPane = new Pane();
        Pane centerPane = new Pane(); // apparently that's normal

        double widthLeftCanvas = 0.3 * widthCanvas;
        double heightTopCanvas = 0.15 * heightCanvas;
        double widthCenterCanvas = widthCanvas - widthLeftCanvas;
        double heightCenterCanvas = heightCanvas - heightTopCanvas;
        myColorPalette CP = new myColorPalette(widthLeftCanvas, heightCenterCanvas);
        TilePane TP = CP.getPalette();

        Image icon = new Image("C:\\Users\\Fahad\\IdeaProjects\\MyColor2\\src\\main\\resources\\musicMakeYouLoseControl.png");
        PS.getIcons().add(icon);

        Scene SC = new Scene(BP, widthCanvas, heightCanvas, myColor.WHITE.getJavaFXColor());
        Deque<myShape> stackMyShapes;
        PS.setTitle("MyShape!");
        PS.setScene(SC);

        topPane.getChildren().add(addTopHBox(widthCanvas, heightTopCanvas, widthCenterCanvas, heightCenterCanvas, BP, CP, TP));
        BP.setTop(topPane);

        leftPane.getChildren().add(addLeftVBox(widthLeftCanvas, heightCenterCanvas, TP, myColor.ORANGE));
        BP.setLeft(leftPane);
        PS.setResizable(false);
        PS.show();

    }

    public static void main(String[] args) {launch(args);}
}
