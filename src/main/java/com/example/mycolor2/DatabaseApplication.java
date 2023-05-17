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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;


public class DatabaseApplication extends Application{ // formerly "testMyColor"
    Integer N,M;
    double startAngle, scale;
    String Title;
    Scanner input; //he dont have this
    Boolean isPiechart = true;
    List<String > barChartInputs = new ArrayList<>();
    List<String> pieChartInputs = new ArrayList<>();

    public void dialogPieChart() {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Pie Chart");
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridDialog = new GridPane();
        gridDialog.setHgap(10);
        gridDialog.setVgap(10);
        gridDialog.setPadding(new Insets(20, 150, 10, 10));

        TextField numberEvents = new TextField();
        TextField totalNumberEvents = new TextField();
        TextField startAngle = new TextField();

        ComboBox title = new ComboBox();
        title.getItems().addAll("CSc 22100 Spring 2023");

        gridDialog.add(new Label("Number of Characters to Show"), 0, 0);
        gridDialog.add(numberEvents, 1, 0);
        gridDialog.add(new Label("Number of Unique Characters (26)"), 2, 0); //isnt this redundant?
        gridDialog.add(totalNumberEvents, 3, 0);
        gridDialog.add(new Label("Starting Angle of First Slice"), 0, 1);
        gridDialog.add(startAngle, 1, 1);
        gridDialog.add(new Label("Title"), 0, 2);
        gridDialog.add(title, 1, 2);

        dialog.getDialogPane().setContent(gridDialog);

        Platform.runLater(() -> numberEvents.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                pieChartInputs.add(numberEvents.getText());
                pieChartInputs.add(totalNumberEvents.getText());
                pieChartInputs.add(startAngle.getText()); //
                pieChartInputs.add(title.getValue().toString());
                return pieChartInputs;
            }
            return null;
        });
        Optional<List<String>> Result = dialog.showAndWait();
        Result.ifPresent(event -> {
            this.N = Integer.parseInt(pieChartInputs.get(0));
            this.M = Integer.parseInt(pieChartInputs.get(1));
            this.startAngle = Double.parseDouble(pieChartInputs.get(2));
            this.Title = pieChartInputs.get(3);
        });
    }

    public void openFile(){
        try{
            input = new Scanner(Paths.get(Title));
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

    public Canvas addCanvasPieChart(double widthCenterCanvas, double heightCenterCanvas, HistogramAlphaBet H){
        Canvas CV = new Canvas(widthCenterCanvas, heightCenterCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();

        MyPoint center = new MyPoint(0.5*widthCenterCanvas, 0.5*heightCenterCanvas, null);
        HistogramAlphaBet.MyPieChart pieChart = H.new MyPieChart(N,M, center, 0.5*widthCenterCanvas, 0.5*heightCenterCanvas, startAngle);
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

    @Override
    public void start(Stage PS) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/?user=root";
        String username = "root";
        String password = "Ldo5tp9A";
        //intantializes database -> makes connection
        MyDatabase DB = new MyDatabase(url, username, password); //gucci

        String scheduleFileName = "/Users/fahadfaruqi/IdeaProjects/MyColor2/src/main/resources/com/example/mycolor2/ScheduleFall2023.txt";
        String nameTable = "Schedule";

        //creates a table called schedule
        DB.new Schedule(scheduleFileName, nameTable); //gucci

        String nameToTable = "Courses";
        String nameFromTable = "Schedule";
        DB.new Courses(nameToTable, nameFromTable); //gucci

        nameTable = "Students";
        DB.new Students(nameTable);

        nameTable = "Classes";
        DB.new Classes(nameTable);

        nameToTable = "AggregateGrades";
        nameFromTable = "Classes";
        MyDatabase.AggregateGrades aggregateGrades = DB.new AggregateGrades(nameToTable, nameFromTable);
        Map<Character, Integer> AG = aggregateGrades.getAggregateGrades(nameToTable);
        System.out.println("\nAggregate Grades: " + AG);


        double widthCanvas = 800.0;
        double heightCanvas = 600.0;
        MyPoint center = new MyPoint(0.5*widthCanvas, 0.5*heightCanvas, null);
        Canvas CV = new Canvas(widthCanvas, heightCanvas);
        GraphicsContext GC = CV.getGraphicsContext2D();

        dialogPieChart();
        HistogramAlphaBet H = new HistogramAlphaBet(AG);
        Pane P = new Pane();
        if (isPiechart){
            P.getChildren().add(addCanvasPieChart(widthCanvas,heightCanvas,H));
        }
        else {
            P.getChildren().add(addCanvasPieChart(widthCanvas,heightCanvas,H));
        }
        Scene SC = new Scene(P, widthCanvas, heightCanvas, MyColor.WHITE.getJavaFXColor());
        PS.setTitle("Auda COde" + Title);
        PS.setResizable(false);
        PS.setScene(SC);
        PS.show();
    }

    public static void main(String[] args) {launch(args);}
}
