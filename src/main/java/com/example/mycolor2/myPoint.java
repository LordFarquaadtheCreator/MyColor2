package com.example.mycolor2;

import javafx.scene.canvas.GraphicsContext;
import java.util.Optional;

public class myPoint {
    double x,y;
    myColor color;

    myPoint(){
        setPoint(0,0);
        this.color = myColor.YELLOW;
    }
    myPoint(double x, double y, myColor color){
        setPoint(x,y);
        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);
    }
    myPoint(myPoint p, myColor color){
        setPoint(p); this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);
    }

    public void setPoint(double x, double y){
        this.x = x; this.y = y;
    }
    public void setPoint(myPoint P){this.x = p.getX(); this.y=p.getY();}
    public void setColor(myColor color){this.color = color;}

    public double getX(){return x;}
    public double getY(){return y;}
    public myColor getColor(){return color;}

    public void translate(double u, double v){setPoint(x+u,y+v);}
    public double distanceFromOrigin(){return Math.sqrt(x*x+y*y);}
    public double distance(myPoint p) {
        double dx = p.getX() - x;
        double dy = p.getY() -y;
        return Math.sqrt(dx*dx + dy+dy);
    }

    public double angleX(myPoint p){
        double dx = x-p.getX();
        double dy = y-p.getY();
        double angle = Math.toDegrees(Math.atan2(dy,dx));
        return angle >=0? angle : 360+angle;
    }

    public void draw(GraphicsContext GC){
        GC.setFill(color.getJavaFXColor());
        GC.fillRect(x,y,1,1);
    }

    @Override
    public String toString(){return "point(" +x+ " "+y+" )";}
}
