package com.example.myshape;

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
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class MyApplication extends Application {
    Integer N,M;
    double startAngle;
    String Title;
    String filename;
    Scanner input;
    List<String> piechartInputs = new ArrayList<>();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        double widthCenterCanvas = 500.0;
        double heightCenterCanvas = 400.0;
        BorderPane bp = new BorderPane();
        Pane topPane = new Pane();
        Pane leftPane =new Pane();

        double widthLeftCanvas = 0.5 * widthCenterCanvas;
        double heightTopCanvas = heightCenterCanvas * 0.225;
        double widthRightCanvas = 0.4 *widthCenterCanvas;
        double widthCanvas = widthCenterCanvas + widthLeftCanvas + widthRightCanvas;
        double heightCanvas = heightCenterCanvas + heightTopCanvas;

        MyColorPaletter cp = new MyColorPaletter(widthLeftCanvas,heightCenterCanvas);
        TilePane tp = cp.getPalette();

        Scene sc = new Scene(bp,widthCanvas,heightCanvas,MyColor.WHITE.getJavaFXColor());
        stage.setTitle("MyShape!");
        stage.setScene(sc);

        topPane.getChildren().add(addTopHBox(widthCanvas,heightTopCanvas,widthCenterCanvas,
                heightCenterCanvas,widthRightCanvas,bp,cp,tp));
        bp.setTop(topPane);
        leftPane.getChildren().add(addLeftVBox(widthLeftCanvas,heightCenterCanvas,tp,MyColor.BLACK));
        bp.setLeft(leftPane);
        stage.show();
    }
    public VBox addLeftVBox(double widthLeftCanvas, double heightLeftCanvas, TilePane TP, MyColor color){
        VBox VB = new VBox();
        VB.setPrefWidth(widthLeftCanvas);
        VB.setPadding(new Insets(5));
        Label label = new Label("MyColor Palette");
        label.setPrefWidth(widthLeftCanvas);
        label.setTextFill(MyColor.WHITE.getJavaFXColor());
        label.setBackground(new Background(new BackgroundFill(
                Optional.ofNullable(color).orElse(MyColor.NAVY).getJavaFXColor(),
                CornerRadii.EMPTY,Insets.EMPTY)));
        VB.getChildren().addAll(label,TP);
        return VB;
    }
    public HBox addTopHBox(double widthTopCanvas, double heightTopCanvas, double widthCenterCanvas,
                           double heightCenterCanvas,double widthRightCanvas, BorderPane BP,
                           MyColorPaletter CP, TilePane TP)throws FileNotFoundException{
        HBox HB = new HBox();
        HB.setPrefWidth(widthCenterCanvas);
        HB.setPadding(new Insets(5,5,5,5));
        String [] nameImages = new String[]{"Oval", "Rectangle", "Circle", "Intersection","pieChart"};
        String pathFile = "C:\\Users\\chaoh\\Documents\\CCNY\\CSC221\\New folder\\";
        Deque<MyShape> stackMyShape = new ArrayDeque<MyShape>();
        for(String nameImage :nameImages) {
            String nameFile = pathFile + nameImage + ".PNG";
            ImageView geometricImage = new ImageView(new Image(new FileInputStream(nameFile),
                    heightTopCanvas,heightCenterCanvas,true,false));
            geometricImage.setOnMouseClicked(e ->{
                switch (nameImage){
                    case"Oval":
                        dialogOval(widthCenterCanvas,heightCenterCanvas,BP,CP,TP,stackMyShape);
                        break;
                    case"Rectangle":
                        dialogRectangle(widthCenterCanvas,heightCenterCanvas,BP,CP,TP,stackMyShape);
                        break;
                    case"Circle":
                        dialogCircle(widthCenterCanvas,heightCenterCanvas,BP,CP,TP,stackMyShape);
                        break;
                    case"Intersection":
                        dialogIntersection(widthCenterCanvas,heightCenterCanvas,BP,CP,TP,stackMyShape);
                        break;
                    case"pieChart":
                        dialogPiechart(widthCenterCanvas,heightCenterCanvas,widthRightCanvas,BP);
                        break;
                }
            });
            HB.getChildren().add(geometricImage);
        }
        return HB;
    }

    public void dialogPiechart(double widthCenterCanvas, double heightCenterCanvas, double widthRightCanvas, BorderPane bp) {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Pie Chart");
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES,ButtonType.CANCEL);
        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,150,10,10));

        TextField numberEvents = new TextField();
        TextField totalNumberEvents = new TextField();
        TextField startingAngle = new TextField();

        ComboBox title = new ComboBox();
        title.getItems().addAll("Moby Dick");
        gridDialog.add(new Label("Display"),0,0);
        gridDialog.add(numberEvents,1,0);
        gridDialog.add(new Label("total"),2,0);
        gridDialog.add(totalNumberEvents,3,0);
        gridDialog.add(new Label("Starting Angle"),0,1);
        gridDialog.add(startingAngle,1,1);
        gridDialog.add(new Label("Title"),0,2);
        gridDialog.add(title,1,2);
        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(()->numberEvents.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == ButtonType.YES){
                piechartInputs.clear();
                piechartInputs.add(numberEvents.getText());
                piechartInputs.add(totalNumberEvents.getText());
                piechartInputs.add(startingAngle.getText());
                piechartInputs.add(title.getValue().toString());
                return piechartInputs;
            }
            return null;
        });

        Optional<List<String>>Result = dialog.showAndWait();
        Result.ifPresent(event -> {
            this.N = Integer.parseInt(piechartInputs.get(0));
            this.M = Integer.parseInt(piechartInputs.get(1));
            this.startAngle = Double.parseDouble(piechartInputs.get(2));
            this.Title = piechartInputs.get(3);
            this.filename = "C:\\Users\\chaoh\\Documents\\CCNY\\CSC221\\New folder\\"+ Title + ".txt";
            openFile();
            String w =readFile();
            closeFile();

            HistogramAlphaBet H = new HistogramAlphaBet(w);
            Map<Character, Integer> sortedFrequency = H.sortDownFrequency();

            Pane rightPane = new Pane();
            rightPane.getChildren().add(addCanvasLegend(widthRightCanvas,heightCenterCanvas,H));
            bp.setRight(rightPane);

            Pane centerPane = new Pane();
            centerPane.getChildren().add(addCanvasPieChart(widthCenterCanvas,heightCenterCanvas,H));
            bp.setCenter(centerPane);
        });
    }
    public void openFile(){
        try{
             input = new Scanner(Paths.get(filename));
        }catch(IOException ioException){
            System.err.println("File is not found");
        }
    }
    public String readFile(){
        String w ="";
        try{
            while(input.hasNext()){
                w += input.nextLine().replaceAll("[^a-zA-Z]","").toLowerCase();
            }
        }catch (NoSuchElementException elementException){
            System.err.println("Invalid input! Terminating.....");
        }catch (IllegalStateException stateException){
            System.out.println("Error processing file! Terminating....");
        }
        return w;
    }
    public void closeFile(){
        if(input !=null) input.close();
    }

    public Canvas addCanvasLegend(double widthCanvas, double heightCanvas,HistogramAlphaBet h){
        String information;
        Canvas CV = new Canvas(widthCanvas,heightCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        MyColor colorLeftCanvas = MyColor.LINEN;
        GC.setFill(colorLeftCanvas.getJavaFXColor());
        GC.fillRect(0.0,0.0,widthCanvas,heightCanvas);

        double xText = 15; double yText = 0.03625 * heightCanvas;
        MyColor colorStroke = MyColor.WHITE;
        GC.setStroke(colorStroke.invertColor());
        GC.setFont(Font.font("Calibri",13));
        GC.strokeText("Frequency: Cumulative "+ h.getCumulativeFrequency(),xText,yText);

        Map<Character,Integer> sortedFrequency = h.sortDownFrequency();

        double yStep = 0.03625 * heightCanvas;
        for(Character K : sortedFrequency.keySet()){
            yText += yStep;
            information = K + ":\t" + sortedFrequency.get(K);
            GC.strokeText(information,xText,yText);
        }
        return CV;
    }
    public Canvas addCanvasPieChart(double widthCanvas, double heightCanvas, HistogramAlphaBet h){
        Canvas CV = new Canvas(widthCanvas,heightCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        MyPoint center = new MyPoint(0.4*widthCanvas,0.5*heightCanvas,null);

        double diameterPieChart = 0.6 * Math.min(widthCanvas,heightCanvas);

        HistogramAlphaBet.MyPieChart pieChart = h.new MyPieChart(N,M,center,diameterPieChart, diameterPieChart, startAngle);
        Map<Character,Slice> slices = pieChart.getMyPieChart();


        System.out.println("\nPie Chart");
        slices.forEach((K,V) ->System.out.println(K+": "+slices.get(K)));

        double sumOfAngles = 0.0;
        for(Character key : slices.keySet()){
            sumOfAngles += slices.get(key).getArcAngle();
        }
        System.out.println("\nSum of Angles: "+sumOfAngles);
        pieChart.draw(GC);

        return CV;
    }



    public void dialogIntersection(double widthCenterCanvas, double heightCenterCanvas,
                                   BorderPane bp, MyColorPaletter cp, TilePane tp, Deque<MyShape> stackMyShape){
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Intersection of 2 MyShape Objects");
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        gridDialog.add(new Label("Draw the intersection of the last two MyShape objects"),0,0);
        dialog.getDialogPane().setContent(gridDialog);
        dialog.showAndWait().ifPresent(response->{
            if(response == ButtonType.OK){
                tp.setOnMouseClicked(e->{
                    MyColor color = cp.getColorPicked();
                    String tileId = color.toString();
                    for(Node tile :tp.getChildren()){
                        if(tile.getId() == tileId){
                            Pane centerPane = new Pane();
                            MyShape S1 = stackMyShape.pop();
                            MyShape S2 = stackMyShape.pop();
                            centerPane.getChildren().add(
                                    addCenterCanvas(widthCenterCanvas,
                                            heightCenterCanvas,S1,S2,color));
                            bp.setCenter(centerPane);
                            break;
                        }
                    }

                });
            }
        });

    }

    public Canvas addCenterCanvas(double widthCenterCanvas, double heightCenterCanvas, MyShape s1, MyShape s2, MyColor color){
        return s1.drawIntersectMyShapes(widthCenterCanvas,heightCenterCanvas,s1,s2,color);
    }

    public void dialogCircle(double widthCenterCanvas, double heightCenterCanvas,
                             BorderPane bp, MyColorPaletter cp, TilePane tp, Deque<MyShape> stackMyShape) {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("MyCircle");
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));
        TextField xCenter = new TextField();
        TextField yCenter = new TextField();
        TextField radius = new TextField();
        gridDialog.add(new Label("Center"), 0,0);
        gridDialog.add(xCenter,1,0);
        gridDialog.add(new Label("X-Coordinate as fraction of canvas width"),2,0);
        gridDialog.add(yCenter,1,1);
        gridDialog.add(new Label("Y-Coordinate as fraction of canvas width"),2,1);
        gridDialog.add(new Label("Radius"),0,2);
        gridDialog.add(radius,1,2);
        gridDialog.add(new Label("Radius of circle"),2,2);
        dialog.getDialogPane().setContent(gridDialog);
        Platform.runLater(()->xCenter.requestFocus());

        List<String>geometeriImageInputs = new ArrayList<>();
        dialog.setResultConverter(dialogButton ->{
            if(dialogButton == ButtonType.YES){
                geometeriImageInputs.add(xCenter.getText());
                geometeriImageInputs.add(yCenter.getText());
                geometeriImageInputs.add(radius.getText());
                return geometeriImageInputs;
            }
            return null;
        });

        Optional<List<String>> Result = dialog.showAndWait();
        Pane centerPane = new Pane();
        Canvas CV = new Canvas(widthCenterCanvas , heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event ->{
            MyPoint pTLC = new MyPoint(Double.parseDouble(geometeriImageInputs.get(0))*widthCenterCanvas,
                    Double.parseDouble(geometeriImageInputs.get(1))*heightCenterCanvas,null);

            ////////////////////////**************
            double r = Double.parseDouble(geometeriImageInputs.get(2))*10;
            tp.setOnMouseClicked(e->{
                MyColor color = cp.getColorPicked();
                String tileId = color.toString();
                for(Node tile :tp.getChildren()){
                    if(tile.getId() == tileId) {
                        MyCircle C = new MyCircle(pTLC, r, color);
                        GC.clearRect(0, 0, widthCenterCanvas, heightCenterCanvas);
                        C.draw(GC);
                        C.getMyBoundingRectangle().stroke(GC);
                        stackMyShape.push(C);
                        break;
                    }
                }
            });
            centerPane.getChildren().add(CV);
            bp.setCenter(centerPane);
        });
    }
    public void dialogRectangle(double widthCenterCanvas, double heightCenterCanvas,
                                BorderPane bp, MyColorPaletter cp, TilePane tp, Deque<MyShape> stackMyShape) {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("MyRectangle");
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));
        TextField xPTLC = new TextField();
        TextField yPTLC = new TextField();
        TextField width = new TextField();
        TextField height = new TextField();
        gridDialog.add(new Label("Top Left Corner Point"), 0,0);
        gridDialog.add(xPTLC,1,0);
        gridDialog.add(new Label("X-Coordinate as fraction of canvas width"),2,0);
        gridDialog.add(yPTLC,1,1);
        gridDialog.add(new Label("Y-Coordinate as fraction of canvas width"),2,1);
        gridDialog.add(new Label("Width"),0,2);
        gridDialog.add(width,1,2);
        gridDialog.add(new Label("as fraction of canvas width"),2,2);
        gridDialog.add(new Label("Height"),0,3);
        gridDialog.add(height,1,3);
        gridDialog.add(new Label(" as fraction of canvas height "),2,3);

        dialog.getDialogPane().setContent(gridDialog);
        Platform.runLater(()->xPTLC.requestFocus());
        List<String>geometeriImageInputs = new ArrayList();
        dialog.setResultConverter(dialogButton ->{
            if(dialogButton == ButtonType.YES){
                geometeriImageInputs.add(xPTLC.getText());
                geometeriImageInputs.add(yPTLC.getText());
                geometeriImageInputs.add(width.getText());
                geometeriImageInputs.add(height.getText());
                return geometeriImageInputs;
            }
            return null;
        });
        Optional<List<String>> Result = dialog.showAndWait();
        Pane centerPane = new Pane();
        Canvas CV = new Canvas(widthCenterCanvas , heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event ->{
            MyPoint pTLC = new MyPoint(Double.parseDouble(geometeriImageInputs.get(0))*widthCenterCanvas,
                    Double.parseDouble(geometeriImageInputs.get(1))*heightCenterCanvas,null);
            double w = Double.parseDouble(geometeriImageInputs.get(2))*widthCenterCanvas;
            double h = Double.parseDouble(geometeriImageInputs.get(3))*heightCenterCanvas;
            tp.setOnMouseClicked(e->{
                MyColor color = cp.getColorPicked();
                String tileId = color.toString();
                for(Node tile :tp.getChildren()){
                    if(tile.getId() == tileId) {
                        MyRectangle R = new MyRectangle(pTLC, w, h, color);
                        GC.clearRect(0, 0, widthCenterCanvas, heightCenterCanvas);
                        R.draw(GC);
                        R.getMyBoundingRectangle().stroke(GC);
                        stackMyShape.push(R);
                        break;
                    }
                }
            });
            centerPane.getChildren().add(CV);
            bp.setCenter(centerPane);
        });
    }

    public void dialogOval(double widthCenterCanvas, double heightCenterCanvas,
                           BorderPane bp, MyColorPaletter cp, TilePane tp, Deque<MyShape> stackMyShape) {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("MyOval");
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        TextField xCenter = new TextField();
        TextField yCenter = new TextField();
        TextField width = new TextField();
        TextField height = new TextField();

        gridDialog.add(new Label("Center"), 0,0);
        gridDialog.add(xCenter,1,0);
        gridDialog.add(new Label("X-Coordinate as fraction of canvas width"),2,0);
        gridDialog.add(yCenter,1,1);
        gridDialog.add(new Label("Y-Coordinate as fraction of canvas width"),2,1);
        gridDialog.add(new Label("Width"),0,2);
        gridDialog.add(width,1,2);
        gridDialog.add(new Label("Width as fraction of canvas width"),2,2);
        gridDialog.add(new Label("Height"),0,3);
        gridDialog.add(height,1,3);
        gridDialog.add(new Label("Height as fraction of canvas height "),2,3);

        dialog.getDialogPane().setContent(gridDialog);
        Platform.runLater(()->xCenter.requestFocus());
        List<String>geometeriImageInputs = new ArrayList<>();
        dialog.setResultConverter(dialogButton ->{
            if(dialogButton == ButtonType.YES){
                geometeriImageInputs.add(xCenter.getText());
                geometeriImageInputs.add(yCenter.getText());
                geometeriImageInputs.add(width.getText());
                geometeriImageInputs.add(height.getText());
                return geometeriImageInputs;
            }
            return null;
        });
        Optional<List<String>> Result = dialog.showAndWait();
        Pane centerPane = new Pane();
        Canvas CV = new Canvas(widthCenterCanvas , heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event ->{
            MyPoint pTLC = new MyPoint(Double.parseDouble(geometeriImageInputs.get(0))*widthCenterCanvas,
                    Double.parseDouble(geometeriImageInputs.get(1))*heightCenterCanvas,null);
            double w = Double.parseDouble(geometeriImageInputs.get(2))*widthCenterCanvas;
            double h = Double.parseDouble(geometeriImageInputs.get(3))*heightCenterCanvas;
            tp.setOnMouseClicked(e->{
                MyColor color = cp.getColorPicked();
                String tileId = color.toString();
                for(Node tile :tp.getChildren()){
                    if(tile.getId() == tileId) {
                        MyOval O = new MyOval(pTLC, w, h, color);
                        GC.clearRect(0, 0, widthCenterCanvas, heightCenterCanvas);
                        O.draw(GC);
                        O.getMyBoundingRectangle().stroke(GC);
                        stackMyShape.push(O);
                        break;
                    }
                }
            });
            centerPane.getChildren().add(CV);
            bp.setCenter(centerPane);
        });
    }

}
