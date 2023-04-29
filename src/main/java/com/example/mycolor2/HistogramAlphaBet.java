package com.example.mycolor2;

import javafx.scene.canvas.GraphicsContext;
import java.util.*;
import java.util.stream.Collectors;

public class HistogramAlphaBet {
    Map<Character, Integer> frequency = new HashMap<>();
    Map<Character, Double> probability = new HashMap<>();

    HistogramAlphaBet(Map<Character, Integer> m){frequency.putAll(m);}
    HistogramAlphaBet(HistogramAlphaBet h){
        frequency.putAll(h.getFrequency());
        probability.putAll(h.getProbability());
    }
    HistogramAlphaBet(String text){
        String w = text.replace("[^a-zA-Z]","").toLowerCase();
        for(int i = 0; i <w.length();i++){
            Character key = w.charAt(i);
            incrementFrequency(frequency,key);
        }
        probability = getProbability();
    }
    public Map<Character, Integer> getFrequency() {return frequency;}
    public Integer getCumulativeFrequency(){return frequency.values().stream().reduce(0,Integer::sum);}

    public Map<Character,Integer>sortUpFrequency(){
        return frequency.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2)->e2,LinkedHashMap::new));
    }
    public Map<Character,Integer> sortDownFrequency(){
        return frequency.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2)->e2,LinkedHashMap::new));
    }
    public Map<Character, Double> getProbability() {
        double inverseCumulativeFrequency = 1.0 / getCumulativeFrequency();
        for(Character Key : frequency.keySet()){
            probability.put(Key,(double)frequency.get(Key)*inverseCumulativeFrequency);
        }
        return probability;
    }
    public Map<Character,Double>sortDownProbability(){
        return getProbability().entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2)->e2,LinkedHashMap::new));
    }
    public Double getSumOfProbability(){return probability.values().stream().reduce(0.0,Double::sum);}
    public boolean checkSumOfProbability(){return getSumOfProbability() == 1;}

    @Override
    public String toString() {
        String output ="Frequency of Characters:\n";
        for(Character K :frequency.keySet()){
            output += K + ": " + frequency.get(K) + "\n";
        }
        return output;
    }
    private static<k> void incrementFrequency(Map<k,Integer>m,k key){
        m.putIfAbsent(key,0);
        m.put(key,m.get(key)+1);
    }

    class MyPieChart{
        Map<Character, Slice> slices = new HashMap<Character, Slice>();
        int numSlices, totalChars;
        MyPoint center;
        double width, height, rotateAngle;

        MyPieChart(int numSlices, int totalChars, MyPoint center, double width, double height, double rotateAngle){
            this.numSlices = numSlices;
            this.totalChars = totalChars;
            this.center = center;
            this.width = width;
            this.height = height;
            this.rotateAngle = rotateAngle < 360 ? rotateAngle : rotateAngle - 360;
            slices = getMyPieChart();
        }

        public Map<Character, Slice> getMyPieChart(){
            MyColor [] colors = MyColor.getMyColors();
            int colorSize = colors.length;
            Map<Character, Double> sortedProbability = sortDownProbability();
            Random rand = new Random();
            double sliceStartAngle = this.rotateAngle;

            for(Character key : sortedProbability.keySet()){ // this does NOT take numSlices, totalChars into account at ALL and this is the main code that prints it
                double sliceArcAngle = 360.0 * sortedProbability.get(key); // this will build the whole pie chart regardless of numSlices
                MyColor keyColor = colors[rand.nextInt(colorSize)]; // each key's random color.

                String sliceInformation = key + ": " + String.format("%.4f", sortedProbability.get(key)); // the little string that displays the slice's value
                slices.put(key, new Slice(center, width, height, sliceStartAngle, sliceArcAngle, keyColor, sliceInformation)); // look into this function
                sliceStartAngle += sliceArcAngle; // remembers our old angle that we just consumed
                sliceStartAngle = sliceStartAngle < 360 ? sliceStartAngle : sliceStartAngle - 360; //basically an if statement that ensures the angles don't add up to more than 360
            }
            return slices;
        }
        public void draw(GraphicsContext GC){
            Map<Character, Double> sortedProbability = sortDownProbability();
            GC.clearRect(0.0,0.0,GC.getCanvas().getWidth(), GC.getCanvas().getHeight());
            GC.setFill(MyColor.GREY.getJavaFXColor());
            GC.fillRect(0.0,0.0,GC.getCanvas().getWidth(), GC.getCanvas().getHeight());

            int n = 0;

            double probabilityAllOtherCharacter = 1.0;
            double i = 0.0;
            for(Character key : sortedProbability.keySet()) {
                i += sortedProbability.get(key);
            }
            System.out.println("total = "+ i);

            for(Character key : sortedProbability.keySet()){
                double sliceStartingAngle = slices.get(key).getStartAngle();
                double sliceArcAngle = slices.get(key).getArcAngle();

                if(n< numSlices){
                    slices.get(key).draw(GC);
                    probabilityAllOtherCharacter -= sortedProbability.get(key);
                    n++;
                }
                else{
                    if(numSlices < totalChars){
                        String information = "All other characters: " + String.format("%.4f", probabilityAllOtherCharacter);
                        if(sliceStartingAngle < rotateAngle){
                            Slice sliceAllOtherCharacters = new Slice(center, width, height, sliceStartingAngle, rotateAngle-sliceStartingAngle, MyColor.getRandomColor(), information);
                            sliceAllOtherCharacters.draw(GC);
                        }
                        else{
                            Slice sliceAllOtherCharacters = new Slice(center, width, height, sliceStartingAngle, 360-sliceStartingAngle+rotateAngle, MyColor.getRandomColor(), information);
                            sliceAllOtherCharacters.draw(GC);
                        }
                        break;
                    }
                }
            }
        }
    }
}

