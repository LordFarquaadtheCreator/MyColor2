package com.example.mycolor2;
import javafx.scene.canvas.GraphicsContext;
import java.util.Optional;

public class MyOval extends MyShape {
    MyPoint center;
    double width, height;
    double halfWidth, halfHeight;
    double semiMajor, semiMinor;
    MyColor color;

    double focus;
    double eccentricity;

    MyOval(MyPoint center, double width, double height, MyColor color){
        super(new MyPoint(), null);
        this.center = center; this.width = width; this.height = height;
        this.halfWidth = 0.5*this.width; this.halfHeight = 0.5*this.height;
        semiMajor = Math.max(halfWidth,halfHeight);
        semiMinor = Math.min(halfHeight, halfWidth);

        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);
        focus = Math.sqrt(Math.pow(semiMajor,2)-Math.pow(semiMinor,2));
        eccentricity = focus/semiMajor;
    }

    MyOval(MyOval o, MyColor color){
        super(new MyPoint(),null);
        this.center = o.getCenter();
        this.width = o.getWidth();
        this.height = o.getHeight();
        halfWidth = 0.5*this.width;
        halfHeight = 0.5*this.height;
        semiMajor = Math.max(halfWidth, halfHeight);
        semiMinor = Math.min(halfWidth, halfHeight);

        this.color = Optional.ofNullable(color).orElse(MyColor.YELLOW);

        focus = o.getFocus();
        eccentricity = o.getEccentricity();
    }

    @Override
    public void setColor(MyColor color){this.color = color;}
    public MyPoint getCenter(){return center;}
    public double getWidth(){return width;}
    public double getHeight(){return height;}
    public double getSemiMajor(){return semiMajor;}
    public double getSemiMinor(){return semiMinor;}
//    @Override
    public MyColor getColor(){return color;}
    public double getFocus(){return focus;}
    public double getEccentricity(){return eccentricity;}

    @Override
    public double perimeter(){return (Math.sqrt(2)*Math.PI*Math.sqrt(Math.pow(semiMajor,2)+Math.pow(semiMinor,2)));}

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color.getJavaFXColor());
        gc.fillOval(center.getX()-halfWidth,center.getY()-halfHeight,width,height);
    }

    @Override
    public double area(){return Math.PI*semiMajor*semiMinor;}
    @Override
    public void stroke(GraphicsContext GC){
        GC.setStroke(color.getJavaFXColor());
        GC.fillOval(center.getX()-halfWidth, center.getY()-halfHeight,width,height);
    }

    public MyRectangle getMyBoundingRectangle(){
        double x = center.getX()-halfWidth;
        double y = center.getY()-halfHeight;
        MyPoint pLTC = new MyPoint(x,y,null);
        return new MyRectangle(pLTC,width,height,null);
    }
    public boolean containsMyPoint(MyPoint p){
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
    public boolean similarObject(MyShape s){
        if(s.getClass().toString().equals("class MyOval")){
            MyOval o = (MyOval) s;
            return semiMajor == o.getSemiMajor() && semiMinor == o.getSemiMinor();
        }
        else {return false;}
    }
    @Override
    public String toString(){
        return "Oval Center (" + center.getX() + " , " + center.getY() + ")\n" +
                "Major Axis " + semiMajor + " Minor Axis " + semiMinor + "\n"+
                "Perimeter" + perimeter() + "\n" +
                "Area " + area();
        }
}