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
//        super(new myPoint(), null);
        this.center = center; this.width = width; this.halfWidth = 0.5*this.width;
        this.halfHeight = 0.5*this.halfHeight;
        semiMajor = Math.max(halfWidth,halfHeight);
        semiMajor = Math.min(halfHeight, halfHeight);

        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);
        focus = Math.sqrt(Math.pow(semiMajor,2)-Math.pow(semiMinor,2));
        eccentricity = focus/semiMajor;
    }

    myOval(myOval o, myColor color){
//        super(new myPoint(),null);
        this.center = o.getCenter();
        this.width = o.getWidth();
        this.height = o.getHeight();
        halfWidth = 0.5*this.width;
        halfHeight = 0.5*this.height;
        semiMajor = Math.max(halfWidth, halfHeight);
        semiMinor = Math.min(halfWidth, halfHeight);

        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);

        focus = o.getFocus();
        eccentricity = o.getEccentricity();
    }

//    @Override
    public void setColor(myColor color){this.color = color;}
    public myPoint getCenter(){return center;}
    public double getWidth(){return width;}
    public double getHeight(){return height;}
    public double getSemiMajor(){return semiMajor;}
    public double getSemiMinor(){return semiMinor;}
//    @Override
    public myColor getColor(){return color;}
    public double getFocus(){return focus;}
    public double getEccentricity(){return eccentricity;}

//    @Override
    public double perimeter(){return (Math.sqrt(2)*Math.PI*Math.sqrt(Math.pow(semiMajor,2)+Math.pow(semiMinor,2)));}

    @Override
    public void draw(GraphicsContext gc) {

    }

    //    @Override
    public double area(){return Math.PI*semiMajor*semiMinor;}
//    @Override
    public void stroke(GraphicsContext GC){
        GC.setStroke(color.getJavaFXColor());
        GC.fillOval(center.getX()-halfWidth, center.getY()-halfHeight,width,height);
    }

    public myRectangle getMyBoundingRectangle(){
        double x = center.getX()-halfWidth;
        double y = center.getY()-halfHeight;
        myPoint pLTC = new myPoint(x,y,null);
        return new myRectangle(pLTC,width,height,null);
    }
    public boolean containsMyPoint(myPoint p){
        if(halfWidth==halfHeight){
            return p.distance(center) <= halfWidth;
        }
        else{
            double hxh = halfWidth*halfHeight;
            double dx = halfHeight*(p.getX()-center.getX());
            double dy = halfWidth*(p.getY()-center.getY());
            return dx*dx+dy*dy<=hxh*hxh;
        }
    }
    public boolean similarObject(myShape s){
        if(s.getClass().toString().equals("class myOval")){
            myOval o = (myOval) s;
            return semiMajor == o.getSemiMajor() && semiMinor == o.getSemiMinor();
        }
        else {return false;}
    }
     @Override
    public String toString(){
        return "Oval Center (" + center.getX() + " , " + center.getY() + ")\n" +
                "Major Axis " + semiMajor + " Minor Axis " + semiMinor + "\n"+
                "Area " + area();
     }
}