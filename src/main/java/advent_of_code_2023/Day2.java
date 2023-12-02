package advent_of_code_2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {

    public static void main(String[] args) throws FileNotFoundException {
        //part1();
        part2();
    }

    public static void part1() throws FileNotFoundException {
        String inputFileName = "day2-part1.txt";
        Scanner sc = new Scanner(new File("src/main/resources/"+inputFileName));

        int redCube = 12;
        int greenCube = 13;
        int blueCube = 14;

        long somme=0;
        while (sc.hasNext()){
            String game = sc.nextLine();
            System.out.println(game);
            String[] lances = game.split("(Game|:|;)");

            int numeroGame = Integer.parseInt(lances[1].trim());
            Map<String,Integer> color = new HashMap<>();
            color.put("red",0);
            color.put("green",0);
            color.put("blue",0);

            for (int i=2;i<lances.length;i++){

                String[] essai = lances[i].split(",");
                for(String compo : essai){
                    String[]c = compo.trim().split(" ");
                    color.computeIfPresent(c[1],(k,ov)-> Math.max(ov,Integer.parseInt(c[0])) );
                }

            }

            if(color.get("red")<=redCube && color.get("green")<=greenCube && color.get("blue")<=blueCube){
                System.out.print("game "+ numeroGame + " est eligible");
                color.forEach((key, value) -> System.out.print(" | "+ key + " " + value ));
                System.out.println(".");
                somme+=numeroGame;

            }

            System.out.println("somme = "+somme);
        }
    }

    public static void part2() throws FileNotFoundException {
        String inputFileName = "day2-part1.txt";
        Scanner sc = new Scanner(new File("src/main/resources/"+inputFileName));

        long somme=0;
        while (sc.hasNext()){
            String game = sc.nextLine();
            System.out.println(game);
            String[] lances = game.split("(Game|:|;)");

            int numeroGame = Integer.parseInt(lances[1].trim());
            Map<String,Integer> color = new HashMap<>();
            color.put("red",0);
            color.put("green",0);
            color.put("blue",0);

            for (int i=2;i<lances.length;i++){

                String[] essai = lances[i].split(",");
                for(String compo : essai){
                    String[]c = compo.trim().split(" ");
                    color.computeIfPresent(c[1],(k,ov)-> Math.max(ov,Integer.parseInt(c[0])) );
                }

            }

            /* le zero ? */
            int puissance = color.get("red")*color.get("green")*color.get("blue");
            System.out.print("game "+ numeroGame);
            color.forEach((key, value) -> System.out.print(" | "+ key + " " + value ));
            System.out.println(". a pour puissance "+puissance);
            somme+= puissance;
            System.out.println("somme = "+somme);
        }
    }


}
