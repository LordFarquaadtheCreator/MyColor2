package com.example.mycolor2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

public class myLine extends myShape{
    myPoint p1,p2;
    myPoint[] pLine = new myPoint[2];
    myColor color;

    myLine(myPoint p1, myPoint p2, myColor color){
//        super(new myPoint(), null);
        this.p1 = p1; this.p2 = p2;
        pLine[0] = p1; pLine[1] =p2;
        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);
    }
    myLine(myLine l, myColor color){
//        super(new myPoint(), null);
        this.p1 = (l.getLine())[0]; this.p2 = (l.getLine())[1];
        pLine[0] = p1; pLine[1] = p2;
        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);
    }

//    @Override
    public void setColor(myColor color){this.color = color;}
    public myPoint[] getLine(){return pLine;}
//    @Override
    public myColor getColor(){return color;}
    public double angleX(){return p1.angleX(p2);}
    public double length(){return p1.distance(p2);}
//    @Override
    public double perimeter(){return length();}
//    @Override
    public double area(){return 0;}
//    @Override
    public void stroke(GraphicsContext GC){
        GC.setStroke(color.getJavaFXColor());
        GC.strokeLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());
    }
//    @Override
    public void draw(GraphicsContext GC){
        GC.setStroke(color.getJavaFXColor());
        GC.strokeLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());
    }
    public myRectangle getMyBoundingRectangle(){
        double x1 = p1.getX(); double y1 = p1.getY();
        double x2 = p2.getX(); double y2 = p2.getY();
        myPoint pTLC = new myPoint(Math.min(x1,x2), Math.min(y1,y2),null);
        return new myRectangle(pTLC, Math.abs(x1-x2), Math.abs(y1-y2), null);
    }
    public boolean containsMyPoint(myPoint p){return (p1.distance(p)+p2.distance(p) == length());}
    public boolean similarObject(myShape s){
        if(s.getClass().toString().equals("class myLine")){
            myLine l = (myLine) s;
            return (this.length() == l.length());
        }
        else {return false;}
    }
    @Override
    public String toString(){return "Line [" + p1 + ", " + p2 +"] Length: " + length();}
}
