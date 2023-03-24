package com.example.mycolor2;
import javafx.scene.canvas.GraphicsContext;
import java.util.Optional;

public class myOval extends myShape{
    myPoint center;
    double width, height;
    double halfWidth, halfHeight;
    double semiMajor, semiMinor;
    myColor color;

    double focus;
    double eccentricity;

    myOval(myPoint center, double width, double height, myColor color){
        super(new myPoint(), null);
        this.center = center; this.width = width; this.halfWidth = 0.5*this.width;
        this.halfHeight = 0.5*this.halfHeight;
        semiMajor = Math.max(halfWidth,halfHeight);
        semiMajor = Math.min(halfHeight, halfHeight);

        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);
        focus = Math.sqrt(Math.pow(semiMajor,2)-Math.pow(semiMinor,2));
        eccentricity = focus/semiMajor;
    }

    myOval(myOval o, myColor color){
        super(new myPoint(),null);
    }
}
