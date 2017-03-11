package com.isa.GoogleIO2017.UnderStudies;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * Created by isa on 2017-03-11.
 */
public class UnderStudies {

    public static double successful(double[] probs){

        double maxProb = maxProb(probs);
        return maxProb;
    }

    public static double analyze(double[] probs){
        Arrays.sort(probs);
        Double min = 1.0;

        int forward = 0;
        int backward = probs.length-1;

        while(backward > forward){
            double c = 1 - probs[forward]*probs[backward];
            min *= c;
            backward--;
            forward++;
        }

        return min;
    }

    public static double maxProb(double[] nums){
        double max = Double.MIN_VALUE;

        for(int i = 0; i < nums.length; i++){
            if(nums[i] == -1) continue;
            for(int j = i+1; j < nums.length; j++){
                if(nums[j] == -1) continue;

                double c = nums[i]*nums[j];

                double tempi = nums[i];
                double tempj = nums[j];
                nums[i] = -1;
                nums[j] = -1;
                double m = (1-c);
                if(maxProb(nums) != Double.MIN_VALUE){
                    m *= maxProb(nums);
                }

                nums[i] = tempi;
                nums[j] = tempj;

                if(m > max){
                    max = m;
                }
            }
        }
        return max;
    }

    public static void main(String[] args){

        // read from file
        List<String> lines = null;
        String testcaseInputPath = System.getProperty("user.dir")
                + "/src/com/isa/GoogleIO2017/UnderStudies/B-small-attempt0.in";
        String testcaseOutputPath = System.getProperty("user.dir")
                + "/src/com/isa/GoogleIO2017/UnderStudies/B-small-attempt0-analyzed.out";

        try{
            lines = Files.readAllLines(Paths.get(testcaseInputPath));
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        int numCases = Integer.parseInt(lines.get(0));
        StringBuilder output = new StringBuilder();
        int count = 1;

        for(int i = 1; i < lines.size(); count++, i++){

            // int array of mixed prices
            String s = lines.get(++i);
            String[] splitted = s.split("\\s+"); // split using whitespace as delimiter

            double[] inputs = new double[splitted.length];
            for(int j = 0; j < inputs.length; j++){
                inputs[j] = Double.parseDouble(splitted[j]);
            }

            double result = analyze(inputs);
            output.append(String.format("Case #%d: %.6f\n", count, result));
        }


        // write to a file
        try{
            Files.write(Paths.get(testcaseOutputPath),
                    output.toString().getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e){
            e.printStackTrace();
        }

        double[] probs = {0.0000,0.0000,0.0000,0.0009,0.0013,0.1776};
        System.out.println(analyze(probs));

    }
}
