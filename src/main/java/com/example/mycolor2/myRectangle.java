package com.example.mycolor2;

import javafx.scene.canvas.GraphicsContext;

import java.util.Optional;

public class myRectangle extends myShape{
    myPoint pTLC;
    double width, height;
    myColor color;

    myRectangle(myPoint p, double w, double h, myColor color){
//        super(new myPoint(), null);
        this.pTLC = p; this.width = w; this.height = h;
        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);
    }
    myRectangle(myRectangle r, myColor color){
//        super(new myPoint(),null);
        this.pTLC = r.getTLC(); this.width = r.getWidth(); this.height = r.getHeight();
        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);
    }

//    @Override
    public void setColor(myColor color){this.color = color;}
    public myPoint getTLC(){return pTLC;}
    public double getWidth(){return width;}
    public double getHeight(){return height;}
    public myColor getColor(){return color;}
//
//    @Override
    public double perimeter(){return 2*(width+height);}
//    @Override
    public double area(){return width*height;}
//    @Override
    public void stroke(GraphicsContext GC){
        GC.setStroke(color.getJavaFXColor());
        GC.strokeRect(pTLC.getX(),pTLC.getY(),width,height);
    }
//    @Override
    public void draw(GraphicsContext GC){
        GC.setFill(color.getJavaFXColor());
        GC.fillRect(pTLC.getX(),pTLC.getY(),width,height);
    }
    public myRectangle getMyBoundingRectangle(){return new myRectangle(pTLC,width,height,null);}

    @Override
    public boolean pointInMyShape(myPoint p) {
        return false;
    }

    public boolean containsMyPoint(myPoint p){
        double x = p.getX(); double y = p.getY();
        double xR = pTLC.getX(); double yR = pTLC.getY();
        return (xR <= x && x <= xR+width) && (yR <= y && y <= yR+height);
    }
    public boolean similarObject(myShape S){
        if(S.getClass().toString().equals("class myRectangle")){
            myRectangle r = (myRectangle) S;
            return (width == r.getWidth() && height == r.getHeight());
        }
        else{return false;}
    }
    @Override
    public String toString(){
        return "Rectangle Top Left Corner " + pTLC + " Width: " + width
                + " Height " + height + " Perimeter " + perimeter() + " Area " +area();
    }
}
