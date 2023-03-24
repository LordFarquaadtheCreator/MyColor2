package com.example.mycolor2;

import java.util.Optional;

public class myCircle extends myOval{
    myPoint center; double radius; myColor color;
    myCircle(myPoint p, double r, myColor color){
        super(p,2.0*r,2.0*r,color);
        this.center = p; this.radius = r;
        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);
    }

    @Override
    public void setColor(myColor color){this.color = color;}
    public myPoint getCenter(){return center;}
    public double getRadius(){return radius;}
    @Override
    public myColor getColor(){return color;}
    @Override
    public boolean similarObject(myShape S){
        if(S.getClass().toString().equals("class myCicle")){
            myCircle C = (myCircle) S;
            return radius == C.getRadius();
        }
        else {return false;}
    }
    @Override
    public String toString(){
        return "Circle Center (" +center.getX() + ", " + center.getY() + ")\n"+
                "Radius: " + radius + "\nPermieter: " + perimeter() +"\nArea: " +
                area();
    }
}
