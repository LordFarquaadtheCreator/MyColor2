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
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


public class MyShapeAgglomerated extends Application{ // formerly "testMyColor"
    Integer N,M;
    double startAngle;
    String filename;
    Scanner input;
    List<String> pieChartInputs = new ArrayList<>();

    public HBox addTopHBox(double widthTopCanvas, double heightTopCanvas, double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, MyColorPalette CP, TilePane TP, Pane centerPane) throws FileNotFoundException {
        HBox HB = new HBox();
        HB.setPrefWidth(widthTopCanvas);
        HB.setPrefHeight(heightTopCanvas);
        HB.setStyle("-fx-background-color: #B2A4FF;");

        String [] nameImages = new String [] {"Oval", "Rectangle", "Intersection", "pieChart"};
        String pathFile = "src/main/resources/com/example/mycolor2/";
        Deque<MyShape> stackMyShapes = new ArrayDeque<>();
        HB.setSpacing(50);
        HB.setAlignment(Pos.CENTER);

        for (String nameImage : nameImages){
            String nameFile = pathFile + nameImage + ".png";
            ImageView geometricImage = new ImageView(new Image(new FileInputStream(nameFile), heightTopCanvas, heightTopCanvas, true, false));

            //draw a geometric shape on mouse click: lambda expression
            geometricImage.setOnMouseClicked(e -> {
                switch (nameImage){
                    case "Oval":
                        dialogOval(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes, centerPane);
                        break;
                    case "Rectangle":
                        dialogRectangle(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes, centerPane);
                        break;
                    case "Intersection":
                        dialogIntersection(widthCenterCanvas, heightCenterCanvas, BP, CP, TP, stackMyShapes, centerPane);
                    case "pieChart":
                        dialogPieChart(widthCenterCanvas, heightCenterCanvas, 0.2*widthCenterCanvas, BP);
                }
            });
            HB.getChildren().add(geometricImage);
        }
        return HB;
    }

    public VBox addLeftVBox(double widthLeftCanvas, double heightLeftCanvas, TilePane TP, MyColor color){
        //make a vbox node
        VBox VB = new VBox();
        VB.setPrefWidth(widthLeftCanvas);
        VB.setPrefHeight(heightLeftCanvas);
        VB.setPadding(new Insets(5));
        VB.setStyle("-fx-background-color: #FFB4B4;");

        //make label my color palette
        Label lblMyColorPalette = new Label("MyColor Palette");
        lblMyColorPalette.setPrefWidth(widthLeftCanvas);
        lblMyColorPalette.setTextFill(MyColor.WHITE.getJavaFXColor());
        lblMyColorPalette.setBackground(new Background(new BackgroundFill(Optional.ofNullable(color).orElse(MyColor.GREY).getJavaFXColor(), CornerRadii.EMPTY, Insets.EMPTY)));

        //make a mycolorpalette of all mycolor objects and add into the vbox together with the label
        VB.getChildren().addAll(lblMyColorPalette, TP);
        return VB;
    }

    public Canvas addCenterCanvas(double widthCenterCanvas, double heightCenterCanvas, MyShape s1, MyShape s2, MyColor color){
        return s1.drawIntersectMyShapes(widthCenterCanvas, heightCenterCanvas, s1,s2,color);
    }
//gotta change "text" option to chose between the text and the sql data
    // maybe have a check box be like, do u wanna chose text or upload your own?
    // then from there you can launch a file explorer? or maybe hardcode it to only open that text file
    public void dialogPieChart(double widthCenterCanvas, double heightCenterCanvas, double widthRightCanvas, BorderPane BP) {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Pie Chart");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,150,10,10));

        TextField numberEvents = new TextField();
        TextField totalNumberEvents = new TextField();
        TextField startAngle = new TextField();

//        ComboBox title = new ComboBox();
//        title.getItems().addAll( "Moby Dick");

        gridDialog.add(new Label("Number of Characters to Show"), 0,0);
        gridDialog.add(numberEvents,1,0);
        gridDialog.add(new Label("Number of Unique Characters (26)"),2,0); //isnt this redundant?
        gridDialog.add(totalNumberEvents,3,0);
        gridDialog.add(new Label("Starting Angle of First Slice"),0,1);
        gridDialog.add(startAngle,1,1);
//        gridDialog.add(new Label("Title"), 0,2);
//        gridDialog.add(title, 1,2); // i can remove the need for a combobox altogether

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> numberEvents.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == ButtonType.OK){
                pieChartInputs.add(numberEvents.getText()); pieChartInputs.add(totalNumberEvents.getText()); pieChartInputs.add(startAngle.getText()); //pieChartInputs.add(title.getValue().toString());
                return pieChartInputs;
            }
            return null;
        });
        Optional<List<String>> Result = dialog.showAndWait();
        Result.ifPresent(event -> {
            this.N = Integer.parseInt(pieChartInputs.get(0));
            this.M = Integer.parseInt(pieChartInputs.get(1));
            this.startAngle = Double.parseDouble(pieChartInputs.get(2));
//            this.Title = pieChartInputs.get(3);
            this.filename = "src/main/resources/com/example/mycolor2/Moby Dick.txt";

            openFile();
            String w = readFile();
            closeFile();

            HistogramAlphaBet H = new HistogramAlphaBet(w);
            Map<Character, Integer> sortedFrequency = H.sortDownFrequency();

            Pane centerPane = new Pane();
            centerPane.getChildren().add(addCanvasPieChart(widthCenterCanvas-widthRightCanvas,heightCenterCanvas,H, sortedFrequency));
            BP.setCenter(centerPane);

            Pane rightPane = new Pane();
            rightPane.getChildren().add(addCanvasLegend(widthRightCanvas,heightCenterCanvas,H, sortedFrequency));
            BP.setRight(rightPane);
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

    public Canvas addCanvasPieChart(double widthCenterCanvas, double heightCenterCanvas, HistogramAlphaBet H, Map<Character, Integer> sortedFrequency){
        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();

        MyPoint center = new MyPoint(0.5*widthCenterCanvas, 0.5*heightCenterCanvas, null);
        double diameterPieChart = 0.50*Math.min(widthCenterCanvas,heightCenterCanvas);
        HistogramAlphaBet.MyPieChart pieChart = H.new MyPieChart(N,M, center, diameterPieChart, diameterPieChart, startAngle);
        Map<Character, Slice> slices = pieChart.getMyPieChart();

        System.out.println("\nPie Chart");
        slices.forEach((K,V) -> System.out.println(K+": " + slices.get(K)));

        double sumOfAngles = 0.0;
        for(Character key : slices.keySet()){
            sumOfAngles += slices.get(key).getArcAngle();
        }
        System.out.println("\nSum of Angles: " + sumOfAngles);
        pieChart.draw(GC);
        return CV;
    }

    public Canvas addCanvasLegend(double widthRightCanvas, double heightCanvas, HistogramAlphaBet H, Map<Character, Integer> sortedFrequency){
        String information;
        Canvas CV = new Canvas(widthRightCanvas, heightCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();

        MyColor colorLeftCanvas = MyColor.LINEN;
        GC.setFill(colorLeftCanvas.getJavaFXColor());
        GC.fillRect(0.0,0.0, widthRightCanvas, heightCanvas);

        double xText = 20; double yText = 0.03625*heightCanvas; //makes sure text is within bounds
        MyColor colorStroke = MyColor.GRAY;
        GC.setStroke(colorStroke.getJavaFXColor());
        GC.setFont(Font.font("Comic Sans MS", 13));
        GC.strokeText(" Frequency", xText, yText);

        double yStep = 0.03625*heightCanvas;
        for(Character K : sortedFrequency.keySet()){
            yText += yStep;
            information = K+":\t"+sortedFrequency.get(K);
            GC.strokeText(information,xText,yText);
        }
        return CV;
    }

    public void dialogOval(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, MyColorPalette CP, TilePane TP, Deque<MyShape> stackMyShape, Pane centerPane){
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
        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event -> {
            MyPoint pTLC = new MyPoint(Double.parseDouble(geometricImageInputs.get(0))*widthCenterCanvas, Double.parseDouble(geometricImageInputs.get(1))*heightCenterCanvas, null);
            double w = Double.parseDouble(geometricImageInputs.get(2))*widthCenterCanvas;
            double h = Double.parseDouble(geometricImageInputs.get(3))*heightCenterCanvas;

            TP.setOnMouseClicked(e-> {
                MyColor color = CP.getColorPicked();
                String tileId = color.toString();
                for (Node tile : TP.getChildren()) {
                    if (tile.getId() == tileId) {
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
        BP.setCenter(centerPane);
        });
    }

    public void dialogRectangle(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, MyColorPalette CP, TilePane TP, Deque<MyShape> stackMyShapes, Pane centerPane){
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("MyRectangle");
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

        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();
        Result.ifPresent(event ->{
            MyPoint pTLC = new MyPoint(Double.parseDouble(geometericImageInputs.get(0))*widthCenterCanvas, Double.parseDouble(geometericImageInputs.get(1))*heightCenterCanvas, null);
            double w = Double.parseDouble(geometericImageInputs.get(2))*widthCenterCanvas;
            double h = Double.parseDouble(geometericImageInputs.get(3))*heightCenterCanvas;

            TP.setOnMouseClicked(e->{
                MyColor color = CP.getColorPicked();
                String tileId = color.toString();
                for(Node tile : TP.getChildren()) {
                    if (tile.getId() == tileId) {
                        MyRectangle R = new MyRectangle(pTLC, w, h, color);
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

    public void dialogIntersection(double widthCenterCanvas, double heightCenterCanvas, BorderPane BP, MyColorPalette CP, TilePane TP, Deque<MyShape> stackMyShapes, Pane centerPane){
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Intersection of 2 MyShape Objects");
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20,100,10,10));

        gridDialog.add(new Label("Click OK and chose a color for the intersection of the last two shapes (only works once)"),1,0);

        dialog.getDialogPane().setContent(gridDialog);
        dialog.showAndWait().ifPresent(response ->{
            if(response == ButtonType.OK){
                TP.setOnMouseClicked(e->{
                    MyColor color = CP.getColorPicked();
                    String tileId = color.toString();
                    for(Node tile : TP.getChildren()) {
                        if (tile.getId() == tileId) {
                            MyShape s1 = stackMyShapes.pop();
                            MyShape s2 = stackMyShapes.pop();
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
        String url = "jdbc:mysql://localhost:3306/Students?allowLoadLocalInfile=true";
        String username = "root";
        String password = "Ldo5tp9A";
        StudentsDatabase DB = new StudentsDatabase (urt, username, password);

        String ddlCreateTable;
        String filename, nameTable;

        filename = "C: /Users/Admin/Desktop/CCNY Classes/CSc 22100 Software Design Laboratory/Lectures/Lectures/Lecture 12-14 Database
        nameTable = "Students. Schedule";
        StudentsDatabase.Schedule schedule = DB.new Schedule (filename, nameTable);

        String nameToTable = "Students Courses";
        String nameFromTable = "Students.Schedule";
        StudentsDatabase.Courses courses = DB.new Courses (nameToTable, nameFromTable);

        nameTable = "Students.Students";
        StudentsDatabase.Students students = DB. new Students (nameTable);

        nameTable = "Students.Classes";
        StudentsDatabase.Classes classes = DB. new Classes (nameTable);

        nameToTable = "Students.AggregateGrades";
        namefromTable = "Students Classes";
        StudentsDatabase.AggregateGrades aggregateGrades = DB.new AggregateGrades (nameToTable, nameFromTable);
        Map<Character, Integer> AG = aggregateGrades.getAggregateGrades(nameToTable);
        System.out.println("\nAggregate Grades: " + AG);

        //creates histogram, center and creates the pie chart (should go in dialog pie chart)
        HistogramAlphaBet H = new HistogramAlphaBet (AG);
        MyPoint center = new MyPoint( 0.5 * widthCanvas, 8.5 * heightCanvas, null);
        P.getChildren ().add(addCanvasPieChart (widthCanvas, heightCanvas, H));

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
        MyColorPalette CP = new MyColorPalette(widthLeftCanvas, heightCenterCanvas);
        TilePane TP = CP.getPalette();

        Scene SC = new Scene(BP, widthCanvas, heightCanvas, MyColor.WHITE.getJavaFXColor());
        PS.setTitle("MyShape!");
        PS.setScene(SC);

        topPane.getChildren().add(addTopHBox(widthCanvas, heightTopCanvas, widthCenterCanvas, heightCenterCanvas, BP, CP, TP, centerPane));
        BP.setTop(topPane);

        leftPane.getChildren().add(addLeftVBox(widthLeftCanvas, heightCenterCanvas, TP, MyColor.ORANGE));
        BP.setLeft(leftPane);
        PS.setResizable(false);
        PS.show();
    }

    public static void main(String[] args) {launch(args);}
}
