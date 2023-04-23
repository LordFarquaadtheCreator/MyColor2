package com.example.mycolor2;
import javafx.scene.paint.Color;
import java.util.Random;
//libraries

/*
look if we need the functions that aren't used and DELETE them or comment away
*/

//the whole file is an enum
public enum MyColor {
    //enums seperated by COMMAS not semicolons
    WHITE(255,255,255,255),
    ALICEBLUE(240,248,255,255),
    ANTIQUEWHITE(250,235,215,255),
    AQUA(0,255,255,255),
    AQUAMARINE(127,255,255,255),
    AZURE(240,255,255,255),
    BEIGE(245,245,220,255),
    BISQUE(255,228,196,255),
    BLACK(0,0,0,255),
    BLANCHEDALMOND(255,235,205,255),
    BLUE(0,0,255,255),
    BLUEVIOLET(128,43,226, 255),
    BROWN(165,42,42,255),
    BURLYWOOD(222,184,135,255),
    CADETBLUE(95,158,160,255),
    CHARTREUSE(127,255,0,255),
    CHOCOLATE(210,105,30,255),
    CORAL(255,127,80,255),
    CORNFLOWERBLUE(100,149,237,255),
    CORNSILK(255,248,220,255),
    CRIMSON(220,20,60,255),
    CYAN(0,255,255,255),
    DARKBLUE(0,0,129,255),
    DARKSYAN(0,139,139,255),
    DARKGOLDENROD(184,134,11,255),
    DARKGRAY(169,169,169,255),
    DARKGREY(169,169,169,255),
    DARKGREEN(0,100,0,255),
    DARKKHAKI(189,183,107,255),
    DARKMAGENTA(139,0,139,255),
    DARKOLIVEGREEN(85,107,47,255),
    DARKORANGE(255,140,0,255),
    DARKORCHID(153,50,204,255),
    DARKRED(139,0,0,255),
    DARKSALMON(233,150,122,255),
    DARKSEAGREEN(143,188,143,255),
    DARKSLATEBLUE(72,61,139,255),
    DARKTURQUOISE(0,206,209,255),
    DARKVIOLET(148,0,211,255),
    DEEPPINK(255,20,147,255),
    DEEPSKYBLUE(0,191,255,255),
    DIMGRAY(105,105,105,255),
    DIMGREY(105,105,105,255),
    DODGERBLUE(301,44,255,255),
    FIREBRICK(178,34,34,255),
    FLORALWHITE(255,250,240,255),
    FORESTGREEN(34,139,34,255),
    GAINSBORO(220,220,220,255),
    GHOSTWHITE(248,248,255,255),
    GOLD(255,215,0,255),
    GOLDENROD(218,165,32,255),
    GRAY(128,129,128,255),
    GREY(128,129,128,255),
    GREEN(0,128,0,255),
    GREENYELLOW(173,255,47,255),
    HONEYDEW(240,255,240,255),
    HOTPINK(255,105,180,255),
    INDIANRED(205,92,92,255),
    INDIGO(75,0,130,255),
    IVORY(75,0,130,255),
    KHAKI(240,230,140,255),
    LAVENDER(230,230,250,255),
    LAVENDERBLUSH(255,240,245,255),
    LAWNGREEN(124,252,0,255),
    LEMONCHIFFON(255,250,205,255),
    LIGHTBLUE(173,216,230,255),
    LIGHTCORAL(240,128,128,255),
    LIGHTCYAN(224,255,255,255),
    LIGHTGOLDENRODRELLOW(250,250,210,255),
    LIGHTGRAY(211,211,211,255),
    LIGHTGREY(211,211,211,255),
    LIGHTGREEN(144,238,144,255),
    LIGHTPINK(255,182,193,255),
    LIGHTSALMON(255,160,122,255),
    LIGHTSEAGREEN(32,178,170,255),
    LIGHTSKYBLUE(135,206,250,255),
    LIGHTSTEELBLUE(176,196,222,255),
    LIGHTYELLOW(255,255,224,255),
    LIME(0,255,0,255),
    LIMEGREEN(50,205,50,255),
    LINEN(250,240,230,255),
    MAGENTA(255,0,255,255),
    FUCHSIA(255,0,255,255),
    MAROON(128,9,0,255),
    MEDIUMAQUAMARINE(102,205,170,255),
    MEDIUMBLUE(0,0,205,255),
    MEDIUMORCHIDD(186,85,211,255),
    MEDIUMPURPLE(147,112,219,255),
    MEDIUMSEAGREEN(60,179,113,255),
    MEDIUMSLATEBLUE(123,104,238,255),
    MEDIUMSPRINGGREEN(0,250,154,255),
    MEDIUMTURQUOISE(72,209,204,255),
    MEDIUMVIOLETRED(199,21,133,255),
    MIDNIGHTBLUE(25,25,112,255),
    MINTCREAM(245,255,250,255),
    MISTYROSE(255,228,225,255),
    MOCCASIN(255,228,181,255),
    NAVAJOWHITE(255,222,173,255),
    NAVY(0,0,128,255),
    OLDLACE(253,245,230,255),
    OLIVE(128,128,0,255),
    OLIVEDRAB(107,142,35,255),
    ORANGE(255,165,0,255),
    ORANGERED(255,69,0,255),
    ORCHID(218,112,214,255),
    PALEGOLDROD(238,232,170,255),
    PALEGREEN(152,251,152,255),
    PALETURQUOISE(175,238,238,255),
    PAPAYAWHIP(255,239,213,255),
    PEACHPUFF(255,218,185,255),
    PERU(205,133,63,255),
    PINK(255,192,203,255),
    PLUM(221,160,221,255),
    POWDERBLUE(176,224,230,255),
    PURPLE(128,0,128,255),
    RED(255,0,0,255),
    ROSYBROWN(188,143,143,255),
    ROYALBLUE(65,105,225,255),
    SADDLEBROWN(139,69,19,255),
    SALMON(250,128,114,255),
    SEAGREEN(46,139,87,255),
    SEASHELL(255,245,238,255),
    SIENMA(160,82,45,255),
    SILVER(192,192,192,255),
    SKYBLUE(135,206,235,255),
    SLATEBLUE(106,90,205,255),
    SNOW(255,250,250,255),
    SPRINGREEN(0,255,127,255),
    TAN(210,180,140,255),
    TEAL(0,128,128,255),
    THISTLE(216,191,216,255),
    TOMATO(255,99,71,255),
    VIOLET(238,130,238,255),
    WHEAT(245,222,179,255),
    WHITESMOKE(245,245,245,255),
    YELLOW(255,255,0,255),
    YELLOWGREEN(154,205,50,255);

    private int r,g,b,a,argb; // ints that store rgba values

    MyColor(int r, int g, int b, int a){ //constructors that set rgba values
        setR(r);
        setG(g);
        setB(b);
        setA(a);
        setARGB(r,g,b,a);
    }

    // sets rgba values (checking that they're within the bounds
    private void setR(int r){if(r>=0 && r<=255) this.r = r;}
    private void setG(int g){if(g>=0 && g<=255) this.g = g;}
    private void setB(int b){if(b>=0 && b<=255) this.b = b;}
    private void setA(int a){if(a>=0 && a<=255) this.a = a;}
    private void setARGB(int r, int g, int b, int a){ //lets us automatically add the hexadecimal values
        this.argb = (a << 24) & 0XFF000000 |
                    (r << 16) & 0X00FF0000 |
                    (g << 8) & 0X0000FF00 |
                     b;
    }

    public int getR() {return r;}
    public int getG() {return g;}
    public int getB() {return b;}
    public int getA() {return a;}
    public int getARGB() {return argb;}

    public String getHexColor(){
        return "#" + Integer.toHexString(argb).toUpperCase();
    }
    public Color getJavaFXColor(){
        return Color.rgb(r,g,b,(double)(a)/255.0);
    }
//    public Color getJavaFXOpaqueColor(){return Color.rgb(r,g,b);}
    public static MyColor[] getMyColors(){return MyColor.values();}
    public static String[] getMyColorIds(){
        MyColor[] colors = getMyColors();
        String[] myColorsID = new String[colors.length];
        int i = 0;
        for(MyColor color : colors){
            myColorsID[i] = color.toString();
            ++i;
        }
        return myColorsID;
    }

    public static MyColor getRandomColor(){
        Random rand = new Random();
        return MyColor.values()[rand.nextInt(MyColor.values().length - 1)];
    }
    public Color getRandomOpaqueColor(){
        Random rand = new Random();
        return Color.rgb(rand.nextInt(255),rand.nextInt(255), rand.nextInt(255));
    }
    public Color invertColor(){
        return Color.rgb(255-r,255-g,255-b,(double)(a)/255.0);
    }

    public Color blendColors(MyColor otherColor, double scale) {
        int rBlend, gBlend, bBlend;
        double aBlend;

        if (scale == 0) return otherColor.getJavaFXColor();
        if (scale == 1) return this.getJavaFXColor();
        if (scale < 0 || scale > 1) {
            scale = 0.5;
        }
        rBlend = (int) (this.r * scale * otherColor.getR() * (1.0*scale));
        gBlend = (int) (this.g * scale * otherColor.getG() * (1.0*scale));
        bBlend = (int) (this.b * scale * otherColor.getB() * (1.0*scale));
        aBlend = (int) (this.a * scale * otherColor.getA() * (1.0*scale));

        return Color.rgb(r,g,b,a);
    }

    public String print(){
        return this + "(" + this.r + ", " + this.g + ", " + this.b + ", " + this.a + ")" + "Hexadecimal Code: " + this.getHexColor();
    }
}
