package com.example.mycolor2;

import javafx.scene.canvas.GraphicsContext;

import java.util.Optional;

public class MyLine extends MyShape {
    MyPoint p1,p2;
    MyPoint[] pLine = new MyPoint[2];
    MyColor color;

    MyLine(MyPoint p1, MyPoint p2, MyColor color){
//        super(new MyPoint(), null);
        this.p1 = p1; this.p2 = p2;
        pLine[0] = p1; pLine[1] =p2;
        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);
    }
    MyLine(MyLine l, MyColor color){
//        super(new MyPoint(), null);
        this.p1 = (l.getLine())[0]; this.p2 = (l.getLine())[1];
        pLine[0] = p1; pLine[1] = p2;
        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);
    }

//    @Override
    public void setColor(MyColor color){this.color = color;}
    public MyPoint[] getLine(){return pLine;}
//    @Override
    public MyColor getColor(){return color;}
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
    public MyRectangle getMyBoundingRectangle(){
        double x1 = p1.getX(); double y1 = p1.getY();
        double x2 = p2.getX(); double y2 = p2.getY();
        MyPoint pTLC = new MyPoint(Math.min(x1,x2), Math.min(y1,y2),null);
        return new MyRectangle(pTLC, Math.abs(x1-x2), Math.abs(y1-y2), null);
    }
    public boolean containsMyPoint(MyPoint p){return (p1.distance(p)+p2.distance(p) == length());}
    public boolean similarObject(MyShape s){
        if(s.getClass().toString().equals("class MyLine")){
            MyLine l = (MyLine) s;
            return (this.length() == l.length());
        }
        else {return false;}
    }
    @Override
    public String toString(){return "Line [" + p1 + ", " + p2 +"] Length: " + length();}
}
