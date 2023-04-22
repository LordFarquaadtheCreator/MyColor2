package com.example.mycolor2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.Optional;

public abstract class myShape implements myShapeInterface {
    myPoint p;
    myColor color;

    myShape() {
        this.p = new myPoint();
        this.color = myColor.BLACK;
    }
    myShape(myPoint p, myColor color) {
        setPoint(p);
        setColor(color);
    }

    myShape(double x , double y, myColor color) {
        setPoint(p);
        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);
    }

    public void setPoint(myPoint p) {
        this.p = p;
    }
    //public void setPoint(double x,double y){p.setPoint(x,y);}

    public void setColor(myColor color) {
        this.color = color;
    }

    public myPoint getPoint() {return p;}
    public myColor getColor() {return color;}
    public double getX(){return p.getX();}
    public double getY(){return p.getY();}

    public abstract double area();
    public abstract double perimeter();
    public abstract void draw(GraphicsContext gc);
    public abstract void stroke(GraphicsContext GC);

    //public abstract MyRectangle getMyBoundRectangle();
    @Override
    public String toString() {return "This is an object of the myShape class";}

    //public abstract boolean containsMyPoint(MyPoint p);
    //public abstract boolean similarObject(myShape S);

//    @Override
    public Canvas drawIntersectMyShapes(double widthCenterCanvas, double heightCenterCanvas, myShape s1, myShape s2, myColor color) {
        return myShapeInterface.super.drawIntersectMyShapes(s1, s2, widthCenterCanvas, heightCenterCanvas, color);
    }

    @Override
    public myRectangle getMyBoundingRectangle() {
        return null;
    }

    @Override
    public boolean containsMyPoint(myPoint p) {
        return false;
    }

    @Override
    public boolean similarObject(myShape S) {
        return false;
    }
}