package com.example.mycolor2;

import javafx.scene.canvas.GraphicsContext;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

public class myTriangle extends myShape{
    myPoint p1,p2,p3;
    myColor color;
    double s1,s2,s3;
    double s;
    double a1,a2,a3;

    myTriangle(myPoint p1, myPoint p2, myPoint p3, myColor color){
//        super(new myPoint(),null);
        this.p1 = p1; this.p2 = p2; this.p3 = p3;
        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);

        s1 = p2.distance(p3);
        s2 = p3.distance(p1);
        s3 = p1.distance(p2);
        s=0.5*(s1+s2+s3);

        a1 = Math.toDegrees(Math.acos((s2*s2+s3*s3-s1*s1)/(2.0*s2*s3)));
        a2 = Math.toDegrees(Math.acos((s3*s3+s1*s1-s2*s2)/(2.0*s3*s1)));
        a3 = Math.toDegrees(Math.acos((s2*s2+s1*s1-s3*s3)/(2.0*s1*s2)));
    }

    myTriangle(double s1, double s2, double s3, myColor color){
//        super(new myPoint(),null);
        this.s1 = s1; this.s2 = s2; this.s3 = s3;
        s = 0.5*(s1+s2+s3);
        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);

        if(!isTriangle()){
            System.out.println("\nThe side lenghts (" + s1 + ", " + s2 + ", " + s3 + ") do not form a proper triangle");
            System.exit(1);
        }

        a1 = Math.toDegrees(Math.acos((s2*s2+s3*s3-s1*s1)/(2.0*s2*s3)));
        a2 = Math.toDegrees(Math.acos((s3*s3+s1*s1-s2*s2)/(2.0*s3*s1)));
        a3 = Math.toDegrees(Math.acos((s2*s2+s1*s1-s3*s3)/(2.0*s1*s2)));
    }

    myTriangle(double side, double angle, myColor color){
        this.s1 = side;
        this.s2 = side;
        this.s3 = 2.0*s*Math.sin(Math.PI*angle/360.0);
        s=0.5*(s1+s2+s3);
        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);

        a1 = 0.5*(100-angle);
        a2 = a1;
        a3 = angle;
    }

    myTriangle(double side, myColor color){
//        super(new myPoint(),null);
        this.s1 =side; this.s2 = side; this.s3 = side;
        s = 1.5 * side;
        this.color = Optional.ofNullable(color).orElse(myColor.YELLOW);

        a1=60.0; a2=60.0; a3=60.0;
    }

    public void setColor(myColor color){this.color = color;}
    public myPoint[] getVertices(){
        myPoint[] vertices = new myPoint[4];
        vertices[0] = p1; vertices[1] = p2; vertices[2] = p3; vertices[3] = p1;
        return vertices;
    }
    public double[] getSides(){
        double [] sides = new double[3];
        sides[0] = s1; sides[1] = s2; sides[2] = s3;
        return sides;
    }
    public double[] getAngle(){
        double []angles = new double[3];
        angles[0] = a1; angles[1] = a2; angles[2] = a3;
        return angles;
    }
//    @Override
    public myColor getColor(){return color;}
    public boolean isTriangle(){return (s-s1)>0 && (s-s2) >0 && (s-s3)>0;}
    public myPoint centroid(){
        return new myPoint(p1.getX()+p2.getX()+p3.getX()/3.0,
                        p2.getY()+p1.getY()+p3.getY()/3.0, null);
    }
//    @Override
    public double perimeter(){return 2.0*s;}

    @Override
    public void draw(GraphicsContext gc) {

    }

    //    @Override
    public double area(){return Math.sqrt(s*(s-s1)*(s-s2)*(s-s3));}
    public myRectangle getMyBoundingRectangle(){
        double x1 = p1.getX(); double y1 = p1.getY();
        double x2 = p2.getX(); double y2 = p2.getY();
        double x3 = p3.getX(); double y3 = p3.getY();
        double xmax = Math.max(Math.max(x1,x2),x3);
        double ymax = Math.max(Math.max(y1,y2),y3);
        double xmin = Math.min(Math.min(x1,x2),x3);
        double ymin = Math.min(Math.min(y1,y2),y3);

        myPoint pTLC = new myPoint(xmin, ymin, null);
        return new myRectangle(pTLC, Math.abs(xmax-xmin), Math.abs(ymax-ymin),null);
    }
    public boolean containsMyPoint(myPoint p){
        double x = p.getX(); double y =p.getY();
        int windingNumber =0;
        myPoint[] verticies = this.getVertices();
        double x1 = verticies[0].getX();
        double y1 = verticies[0].getY();
        for(int i =1;i<4; ++i){
            double x2 = verticies[i].getX();
            double y2 = verticies[i].getY();
            double ccw = (x2-x1)*(y-y1)-(x-x1)*(y2-y1);

            if(y2 > y && y >= y1){
                if(ccw >0) ++windingNumber;
            }
            if(y2 >y && y < y1){
                if(ccw <0) --windingNumber;
            }

            x1=x2; y1=y2;
        }
        return windingNumber !=0;
    }
    public boolean similiarObject(myShape S){
        if(S.getClass().toString().equals("class myTriangle")){
            myTriangle T = (myTriangle) S;
            double [] sideThis = this.getSides(); Arrays.sort(sideThis);
            double [] sidesT = T.getSides(); Arrays.sort(sidesT);
            return (sideThis[0] == sidesT[0] && sideThis[1] == sidesT[1] && sideThis[2] == sidesT[2]);
        }
        else {return false;}
    }
    public void stroke(GraphicsContext GC){
        double [] xCoord = new double[3];
        double [] yCoord = new double[3];

        xCoord[0] = p1.getX(); xCoord[1] = p2.getX(); xCoord[2] = p3.getX();
        yCoord[0] = p1.getY(); yCoord[1] = p2.getY(); yCoord[2] = p3.getY();

        GC.setStroke(color.getJavaFXColor());
        GC.strokePolygon(xCoord,yCoord,3);
    }
    @Override
    public String toString(){
        return "Triangle Vertices " + Arrays.toString(getVertices()) + "\n" +
                "Sides: " + Arrays.toString(getSides()) + "\n" +
                "Angles: " + Arrays.toString(getAngle()) + "\n" +
                "Centroid " + centroid() + "\n" +
                "Area: " + area() + "\n" +
                "Perimeter: " + perimeter() + "\n" +
                "Color: " + getColor();
    }
}
