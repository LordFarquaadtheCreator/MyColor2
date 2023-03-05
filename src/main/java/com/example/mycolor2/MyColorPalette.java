package com.example.mycolor2;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.util.Arrays;

//left box that contains the color palette that the user can click on
public class MyColorPalette {

    myColor colorPicked;
    myColor[]colors = myColor.getMyColors();
    int sizeMyColor = colors.length;
    double widthTile, heightTile;

    public MyColorPalette(double widthPalette, double heightPalette){
        this.widthTile = widthPalette / 12.0 - 1.0;
        this.heightTile = this.widthTile;
    }

    public void setColorsPicked(myColor color){colorPicked = color;}
    public myColor getColorPicked(){return colorPicked;}
    public TilePane getPalette(){
        TilePane TP = new TilePane();
        TP.setPrefTileWidth(widthTile);
        TP.setPrefTileHeight(heightTile);
        TP.setPrefRows(12);
        TP.setOrientation(Orientation.HORIZONTAL);
        TP.setPadding(new Insets(1));

        for (int j = 0; j < sizeMyColor; j++){
            myColor color = colors[j];
            String tileId = color.toString();

            Pane tileMyColor = new Pane();
            tileMyColor.setId(tileId);
            tileMyColor.setBackground((new Background(new BackgroundFill(color.getJavaFXColor(), CornerRadii.EMPTY, Insets.EMPTY))));

            tileMyColor.setOnMouseClicked(e->{
                myColor colorClicked = colors[Arrays.asList(myColor.getMyColorIds()).indexOf(tileId)];
                setColorsPicked(colorClicked);
            });

            tileMyColor.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent mouseEvent){
                    myColor colorClicked = colors[Arrays.asList(myColor.getMyColorIds()).indexOf(tileId)];
                    setColorsPicked(colorClicked);
                }
            });

            TP.getChildren().add(tileMyColor);
        }
        return TP;
    }
    public ObservableList<Node> getTiles(){return getPalette().getChildren();}
}
