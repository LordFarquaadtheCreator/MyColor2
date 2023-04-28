package com.example.myshape;

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
        Map<Character,Slice>slices = new HashMap<>();
        int N,M;
        MyPoint center;
        double width,height;
        double rotateAngle;

        MyPieChart(int N, int M, MyPoint center, double width, double height, double rotateAngle){
            this.N = N; this.M = M;
            this.center = center;
            this.width = width; this.height = height;
            this.rotateAngle = rotateAngle < 360? rotateAngle :rotateAngle-360;
            slices = getMyPieChart();
        }

        public Map<Character, Slice> getMyPieChart() {
            MyColor [] colors = MyColor.getMyColors();
            int colorsSize = colors.length;

            Map<Character, Double> sortedProbability = sortDownProbability();
            Random rand = new Random();
            double sliceStartAngle = this.rotateAngle;

            for(Character key : sortedProbability.keySet()){

                double sliceArcAngle = 360.0 * sortedProbability.get(key);
                MyColor color = colors[rand.nextInt(colorsSize)];

                String sliceInformation = key +": " + String.format("%.4f",sortedProbability.get(key));
                slices.put(key,new Slice(center,width,height,sliceStartAngle,sliceArcAngle,color,sliceInformation));
                sliceStartAngle = sliceStartAngle+sliceArcAngle;
                sliceStartAngle = sliceArcAngle < 360.0? sliceStartAngle : sliceStartAngle-360;
            }
            return slices;
        }
        public void draw(GraphicsContext gc){
            Map<Character, Double> sortedProbability = sortDownProbability();

            gc.clearRect(0.0,0.0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
            gc.setFill(MyColor.GRAY.getJavaFXColor());
            gc.fillRect(0.0,0.0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());

            int n = 0;
            double probabilityAllOtherCharacters = 1.0;
            for(Character key : sortedProbability.keySet()){
                double sliceStartAngle = slices.get(key).getStartAngle();
                double sliceArcAngle = slices.get(key).getArcAngle();
                if(n < N){
                    slices.get(key).draw(gc);
                    probabilityAllOtherCharacters -= sortedProbability.get(key);
                    n++;
                }
                else{
                    if(N!=M) {
                        String information = "All other characters: " + String.format("%.4f", probabilityAllOtherCharacters);
                        if(sliceStartAngle < rotateAngle) {
                            Slice sliceAllOtherCharacters = new Slice(center, width, height, sliceStartAngle,
                                    rotateAngle-sliceStartAngle, MyColor.getRandomColor(), information);
                            sliceAllOtherCharacters.draw(gc);
                        }
                        else {
                            Slice sliceAllOtherCharacters = new Slice(center, width, height, sliceStartAngle,
                                    360.0 - sliceStartAngle+rotateAngle, MyColor.getRandomColor(), information);
                            sliceAllOtherCharacters.draw(gc);
                        }
                        break;
                    }
                }
            }
        }
    }

}
