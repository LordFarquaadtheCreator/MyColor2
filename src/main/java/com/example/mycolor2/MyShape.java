package com.example.mycolor2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.Optional;

public abstract class MyShape implements MyShapeInterface {
    MyPoint p;
    MyColor color;

    MyShape() {
        this.p = new MyPoint();
        this.color = MyColor.BLACK;
    }
    MyShape(MyPoint p, MyColor color) {
        setPoint(p);
        setColor(color);
    }

    MyShape(double x , double y, MyColor color) {
        setPoint(p);
        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);
    }

    public void setPoint(MyPoint p) {
        this.p = p;
    }
    //public void setPoint(double x,double y){p.setPoint(x,y);}

    public void setColor(MyColor color) {
        this.color = color;
    }

    public MyPoint getPoint() {return p;}
    public MyColor getColor() {return color;}
    public double getX(){return p.getX();}
    public double getY(){return p.getY();}

    public abstract double area();
    public abstract double perimeter();
    public abstract void draw(GraphicsContext gc);
    public abstract void stroke(GraphicsContext GC);

    //public abstract MyRectangle getMyBoundRectangle();
    @Override
    public String toString() {return "This is an object of the MyShape class";}

    //public abstract boolean containsMyPoint(MyPoint p);
    //public abstract boolean similarObject(MyShape S);

//    @Override
    public Canvas drawIntersectMyShapes(double widthCenterCanvas, double heightCenterCanvas, MyShape s1, MyShape s2, MyColor color) {
        return MyShapeInterface.super.drawIntersectMyShapes(s1, s2, widthCenterCanvas, heightCenterCanvas, color);
    }

    @Override
    public MyRectangle getMyBoundingRectangle() {
        return null;
    }

    @Override
    public boolean containsMyPoint(MyPoint p) {
        return false;
    }

    @Override
    public boolean similarObject(MyShape S) {
        return false;
    }
}