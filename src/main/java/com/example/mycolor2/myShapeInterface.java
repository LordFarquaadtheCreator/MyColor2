package com.example.mycolor2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public interface myShapeInterface {

    public abstract myRectangle getMyBoundingRectangle();
    public abstract boolean pointInMyShape(myPoint p);

    boolean containsMyPoint(myPoint p);
    boolean similarObject(myShape S);

    static boolean similarObject(myShape S1, myShape S2) {
        String sClassS1 = S1.getClass().toString();
        String sClassS2 = S2.getClass().toString();
        if (sClassS1.equals(sClassS2)) {
            switch (sClassS1) {

                case "class MyRectangle":
                    myRectangle R1 = (myRectangle) S1;
                    myRectangle R2 = (myRectangle) S2;
                    return R1.getWidth() == R2.getWidth() && R1.getHeight() == R2.getHeight();

                case "class MyOval":
                    myOval O1 = (myOval) S1;
                    myOval O2 = (myOval) S2;
                    return O1.getSemiMajor() == O2.getSemiMajor() && O1.getSemiMinor() == O2.getSemiMinor();
                case "class MyCircle":
                    myCircle C1 = (myCircle) S1;
                    myCircle C2 = (myCircle) S2;
                    return C1.getRadius() == C2.getRadius();

                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    static List<myPoint> intersectMyShapes(myShape s1, myShape s2)
    {
        myRectangle r1 = s1.getMyBoundingRectangle();
        myRectangle r2 = s2.getMyBoundingRectangle();
        myRectangle r = overlapMyShapes(r1,r2);

        if(r!=null) {

            double x = r.getTLC().getX();
            double y = r.getTLC().getY();
            double w = r.getWidth();
            double h = r.getHeight();

            List<myPoint> intersect = new ArrayList<>();

            for (double i = 0; i <= w; i++) {
                double xi = x + i;
                for (double j = 0; j <= h; j++) {
                    myPoint p = new myPoint(xi, y + j, null);
                    if (s1.containsMyPoint(p) && s2.containsMyPoint(p)) {
                        intersect.add(p);
                    }
                }
            }
            return intersect;
        }
        else{return null;}
    }

    /*static List<MyPoint> intersectMyShapes(MyShape s1,MyShape s2)
    {
        //MyRectangle r1 = s1.getMyBoundingRectangle();
        //MyRectangle r2 = s2.getMyBoundingRectangle();
        MyRectangle overlap = overlapMyShapes(s1,s2);
        //MyRectangle overlap = overlapMyShapes(r1,r2);

        if(overlap !=null)
        {
            double x = overlap.getTLCPoint().getX();
            double y = overlap.getTLCPoint().getY();
            double w = overlap.getWidth();
            double h = overlap.getHeight();
            List<MyPoint> intersect = new ArrayList<>();
            for(int i = 0; i < w; i++)
            {
                double xi = x + i;
                for(int j = 0; j < h;j++)
                {
                    MyPoint p = new MyPoint(xi, y + j, MyColor.BLACK);
                    if(s1.pointInMyShape(p) && s2.pointInMyShape(p))
                    {
                        intersect.add(p);
                    }
                }
            }
            return intersect;
        }
        else {return null;}
    }*/

    /*static MyRectangle overlapMyShapes(MyShape s1, MyShape s2){

        MyRectangle bound1 = s1.getMyBoundingRectangle();
        MyRectangle bound2 = s2.getMyBoundingRectangle();

        double x1 = bound1.getTLCPoint().getX();
        double y1 = bound1.getTLCPoint().getY();
        double w1 = bound1.getWidth();
        double h1 = bound1.getHeight();

        double x2 = bound2.getTLCPoint().getX();
        double y2 = bound2.getTLCPoint().getY();
        double w2 = bound2.getWidth();
        double h2 = bound2.getHeight();

        if(y1 + h1 < y2 || y2 + h2 < y1)
        {return null;}
        if(x1 + w1 < x2 || x2 + w2 < x1)
        {return null;}
        double x_min = Math.min(x1+w1,x2+w2);
        double x_max = Math.max(x1,x2);
        double y_min = Math.min(y1+h1,y2+h2);
        double y_max = Math.max(y1,y2);

        MyPoint bound_point = new MyPoint(x_max,y_max,null);
        return new MyRectangle(bound_point,Math.abs(x_min-x_max),Math.abs(y_min-y_max),null);
    }*/

    static myRectangle overlapMyShapes(myShape s1,myShape s2) {
        myRectangle r1 = s1.getMyBoundingRectangle();
        myRectangle r2 = s2.getMyBoundingRectangle();
        return intersectMyRectangles(r1,r2);
    }

    public default Canvas drawIntersectMyShapes(myShape s1, myShape s2, double w, double h, myColor color)
    {

        List<myPoint> intersect = intersectMyShapes(s1,s2);
        Canvas overlayCV = new Canvas(w,h);
        GraphicsContext overlayGC = overlayCV.getGraphicsContext2D();

        // s1.getMyBoundingRectangle().stroke(overlayGC);
        s1.draw(overlayGC);
        // s2.getMyBoundingRectangle().stroke(overlayGC);
        s2.draw(overlayGC);

        myRectangle r = overlapMyShapes(s1,s2);
        myColor colorR = myColor.AQUA;
        r.setColor(colorR);
        //r.stroke(overlayGC);

        if(intersect !=null)
        {
            System.out.println("intersect");
            for(myPoint p : intersect)
            {
                p.setColor(color);
                p.draw(overlayGC);
            }
        }
        else{System.out.println("no Intersect");}
        return  overlayCV;
    }
    /*public default Canvas drawIntersectMyShapes(MyShape s1,MyShape s2,double w,double h,MyColor color)
    {
        List<MyPoint> intersect = intersectMyShapes(s1,s2);
        Canvas overlayCV = new Canvas(w,h);
        GraphicsContext overlayGC = overlayCV.getGraphicsContext2D();
        s1.draw(overlayGC);
        s2.draw(overlayGC);

        for(MyPoint p : intersect)
        {
            p.draw(overlayGC);
        }
        return overlayCV;

    }*/
    public static myRectangle intersectMyRectangles(myRectangle R1, myRectangle R2)
    {
        double x1 = R1.getTLC().getX();
        double y1 = R1.getTLC().getY();
        double w1 = R1.getWidth();
        double h1 = R1.getHeight();

        double x2 = R2.getTLC().getX();
        double y2 = R2.getTLC().getY();
        double w2 = R2.getWidth();
        double h2 = R2.getHeight();

        if(y1+h1 < y2 || y1 > y2 + h2) {return null;}

        if(x1 + w1 < x2 || x1 > x2 + w2) {return null;}

        double xMax = Math.max(x1,x2);
        double yMax = Math.max(y1,y2);
        double xMin = Math.min(x1+w1,x2+w2);
        double yMin = Math.min(y1 +h1,y2 + h2);

        myPoint p = new myPoint(xMax,yMax, null);
        return new myRectangle(p,Math.abs(xMax - xMin), Math.abs(yMax - yMin), null);
    }

}

  /*static MyRectangle overlapMyRectangle(MyRectangle R1, MyRectangle R2)
        {
            double x1 = R1.getTLC().getX();
            double y1 = R1.getTLC().getY();
            double w1 = R1.getWidth();
            double h1 = R1.getHeight();

            double x2 = R2.getTLC().getX();
            double y2 = R2.getTLC().getY();
            double w2 = R2.getWidth();
            double h2 = R2.getHeight();

            if(y1+h1 < y2 || y1 > y2 + h2) return null;

            if(x1 + w1 < x2 || x1 > x2 + w2) return null;

            double xmax = Math.max(x1,x2);
            double ymax = Math.max(y1,y2);
            double xmin = Math.min(x1+w1,x2+w2);
            double ymin = Math.min(y1 +h1,y2 + h2);

            MyPoint p = new MyClass(xmax,ymax, null);
            return new MyRectangle(p,Math.abs(xmax - xmin), Math.abs(ymax - ymin), null);
        }

        static MyRectangle overlapMyShapes(MyShape S1, MyShape S2)
        {
            MyRectangle R1 = S1.getMyBoundRectangle();
            MyRectangle R2 = S2.getMyBoundRectangle();
            return overlapMyRectangle(R1,R2);
        }

        static List<MyPoint> intersectMyShapes(MyShape S1, MyShape S2)
        {
            MyRectangle R1 = S1.getMyBoundingRectangle();
            MyRectangle R2 = S2.getMyBoundingRectangle();
            MyRectangle R = overlapMyShapes(R1,R2);

            if(R != null){
                double x = R.getTLCPoint().getX();
                double y = R.getTLCPoint().getY();
                double w = R.getWidth();
                double h = R.getHeight();
                List<MyPoint> areaIntersect = new Arraylist();
                for(double i = 0; i <= w ;i++)
                {
                    double x1 = x+i;
                    for(double i =0; j <= h; j++)
                    {
                        MyPoint p = new MyPoint(x1, y+1,null);
                        if(S1.containsMyPoint(p) && S2.containsMyPoint(p)){areaIntersect.add(p);}
                    }
                }
            }
            else {return null;}
        }
    }

    default Canvas drawIntersectMyShapes(double widthCanvas, double heightCanvas, MyShape S1, MyShape S2, MyColor color)
    {
        List<MyPoint> areaIntersect = intersectMyShapes(S1,S2);
        Canvas overlayCV = new Canvas(widthCanvas,heightCanvas);
        GraphicsContext overlayGC = overlayCV.getGraphicsContext2D();

        S1.getMyBoundingRectangle().stroke(overlayGC);
        S1.draw(overlayGC);
        S2.getMyBoundingRectangle().stroke(overlayGC);
        S2.draw(overlayGC);

        MyRectangle R = overlapMyShapes(S1,S2);
        MyColor colorR = MyColor.FIREBRICK; R.setColor(colorR);
        R.stroke(overlayGC);

        if(areaIntersect != null)
        {
            for(MyPoint p : areaIntersect){
             p.setColor(color);
             p.draw(overlayGC);
            }
        }
        return overlayCV;
    }
}*/