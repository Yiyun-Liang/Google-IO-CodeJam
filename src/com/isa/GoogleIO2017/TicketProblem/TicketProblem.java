package com.isa.GoogleIO2017.TicketProblem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by isa on 2017-03-11.
 */
public class TicketProblem {

    static class Ticket{
        int row;
        int col;

        public Ticket(int row, int col){
            this.row = row;
            this.col = col;
        }
    }

    public static int maxInRow(int numFriends, int grid, ArrayList<Ticket> tickets){
        int[] numInRow = new int[grid+1];

        Map<Integer, Set<Integer>> map = new HashMap<>();

        for(int i = 0; i < numFriends; i++){
            int num = 0;

            if(map.containsKey(tickets.get(i).row)){
                Set s = map.get(tickets.get(i).row);
                if(s.contains(tickets.get(i).col)){
                    continue;
                }else{
                    s.add(tickets.get(i).col);
                    map.put(tickets.get(i).row, s);
                    if(tickets.get(i).row != tickets.get(i).col){
                        numInRow[tickets.get(i).row]++;
                        numInRow[tickets.get(i).col]++;
                    }else{
                        numInRow[tickets.get(i).row]++;
                    }
                }
            }else{
                Set s = new HashSet();
                s.add(tickets.get(i).col);
                map.put(tickets.get(i).row, s);
                if(tickets.get(i).row != tickets.get(i).col){
                    numInRow[tickets.get(i).row]++;
                    numInRow[tickets.get(i).col]++;
                }else{
                    numInRow[tickets.get(i).row]++;
                }
            }
        }

        int max = 0;
        for(int i: numInRow){
            if(i > max){
                max = i;
            }
        }

        return max;
    }

    public static void main(String[] args){

        // read from file
        List<String> lines = null;
        String testcaseInputPath = System.getProperty("user.dir")
                + "/src/com/isa/GoogleIO2017/A-large.in";
        String testcaseOutputPath = System.getProperty("user.dir")
                + "/src/com/isa/GoogleIO2017/A-large.out";

        try{
            lines = Files.readAllLines(Paths.get(testcaseInputPath));
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        int numCases = Integer.parseInt(lines.get(0));
        StringBuilder output = new StringBuilder();
        int count = 1;

        for(int i = 1; i < lines.size(); count++){
            // int array of mixed prices
            String s = lines.get(i);
            String[] splitted = s.split("\\s+"); // split using whitespace as delimiter
            int num = Integer.parseInt(splitted[0]);
            int grid = Integer.parseInt(splitted[1]);
            int next = i + num+1;
            i++;

            ArrayList<Ticket> tickets = new ArrayList<>();
            while(i < next){
                String k = lines.get(i);
                String[] arr = k.split("\\s+");
                int row = Integer.parseInt(arr[0]);
                int col = Integer.parseInt(arr[1]);;

                tickets.add(new Ticket(row, col));
                i++;
            }

            int result = maxInRow(num, grid, tickets);

            output.append(String.format("Case #%d: %d\n", count, result));
        }


        // write to a file
        try{
            Files.write(Paths.get(testcaseOutputPath),
                    output.toString().getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e){
            e.printStackTrace();
        }

        /*ArrayList<Ticket> t = new ArrayList<>();
        t.add(new Ticket(1, 2));
        t.add(new Ticket(2, 3));
        t.add(new Ticket(2, 2));

        int r = maxInRow(3, 3, t);
        System.out.println(r);*/

    }
}
