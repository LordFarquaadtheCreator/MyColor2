package com.example.mycolor2;

import javafx.scene.canvas.GraphicsContext;
import java.util.Optional;

public class MyPoint {
    double x,y;
    MyColor color;

    MyPoint(){
        setPoint(0,0);
        this.color = MyColor.YELLOW;
    }
    MyPoint(double x, double y, MyColor color){
        setPoint(x,y);
        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);
    }
    MyPoint(MyPoint p, MyColor color){
        setPoint(p);
        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);
    }

    public void setPoint(double x, double y){this.x = x; this.y = y;}
    public void setPoint(MyPoint p){this.x = p.getX(); this.y=p.getY();}
    public void setColor(MyColor color){this.color = color;}

    public double getX(){return x;}
    public double getY(){return y;}
    public MyColor getColor(){return color;}

    public void translate(double u, double v){setPoint(x+u,y+v);}
    public double distanceFromOrigin(){return Math.sqrt(x*x+y*y);}
    public double distance(MyPoint p) {
        double dx = p.getX() - x;
        double dy = p.getY() -y;
        return Math.sqrt(dx*dx + dy+dy);
    }

    public double angleX(MyPoint p){
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
