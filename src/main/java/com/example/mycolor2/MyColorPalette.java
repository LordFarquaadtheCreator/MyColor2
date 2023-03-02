package com.example.mycolor2;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.util.Arrays;

public class MyColorPalette {

    MyColor colorPicked;
    MyColor []colors = MyColor.getMyColors();
    int sizeMyColor = colors.length;
    double widthTile, heightTile;

    public MyColorPalette(double widthPalette, double heightPalette){
        this.widthTile = widthPalette / 12.0 - 1.0;
        this.heightTile = this.widthTile;
    }

    public void setColorsPicked(MyColor color){colorPicked = color;}
    public MyColor getColorPicked(){return colorPicked;}
    public TilePane getPalette(){
        TilePane TP = new TilePane();
        TP.setPrefTileWidth(widthTile);
        TP.setPrefTileHeight(heightTile);
        TP.setPrefRows(12);
        TP.setOrientation(Orientation.HORIZONTAL);
        TP.setPadding(new Insets(1));

        for (int j = 0; j < sizeMyColor; j++){
            MyColor color = colors[j];
            String tileId = color.toString();

            Pane tileMyColor = new Pane();
            tileMyColor.setId(tileId);
            tileMyColor.setBackground((new Background(new BackgroundFill(color.getJavaFxColor(), CornerRadii.EMPTY))));

            tileMyColor.setOnMouseClicked(e->{
                MyColor colorClicked = colors[Arrays.asList(MyColor.getMyColorIds()).indexOf(tileId)];
                setColorsPicked(colorClicked);
            });

            tileMyColor.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent mouseEvent){
                    MyColor colorClicked = colors[Arrays.asList(MyColor.getMyColorIds()).indexOf(tileId)];
                    setColorsPicked(colorClicked);
                }
            });

            TP.getChildren().add(tileMyColor);
        }
        return TP;
    }
    public ObservableList<Node> getTiles(){return getPalette().getChildren();}
}
