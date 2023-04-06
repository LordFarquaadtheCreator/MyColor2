package com.example.mycolor2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public interface myShapeInterface {

    myRectangle getMyBoundingRectangle();
    boolean pointInMyShape(myPoint p);

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

    static List<myPoint> intersectMyShapes(myShape s1, myShape s2) {
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

    static myRectangle overlapMyShapes(myShape s1,myShape s2) {
        myRectangle r1 = s1.getMyBoundingRectangle();
        myRectangle r2 = s2.getMyBoundingRectangle();
        return intersectMyRectangles(r1,r2);
    }

    default Canvas drawIntersectMyShapes(myShape s1, myShape s2, double w, double h, myColor color) {
        List<myPoint> intersect = intersectMyShapes(s1,s2);
        Canvas overlayCV = new Canvas(w,h);
        GraphicsContext overlayGC = overlayCV.getGraphicsContext2D();

        s1.draw(overlayGC);
        s2.draw(overlayGC);

        myRectangle r = overlapMyShapes(s1,s2);
        myColor colorR = myColor.AQUA;
        r.setColor(colorR);

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

    static myRectangle intersectMyRectangles(myRectangle R1, myRectangle R2) {
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