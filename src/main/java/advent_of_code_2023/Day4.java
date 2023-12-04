package advent_of_code_2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day4 {


    public static void main(String[] args) throws FileNotFoundException {
        //part1();
        part2();
    }

    public static void part1() throws FileNotFoundException {
        String inputFileName = "day4-part1.txt";
        Scanner sc = new Scanner(new File("src/main/resources/" + inputFileName));
        long res = 0;
       while (sc.hasNextLine()){

          String line = sc.nextLine().replace("  "," ");
          System.out.println(line);
          String[] cards = line.split("[:|]");

          List<Integer> winningCards = Arrays.stream(cards[1].trim().split(" ")).map(s-> Integer.parseInt(s.trim())).toList();
          List<Integer> myCards = Arrays.stream(cards[2].trim().split(" ")).map(s-> Integer.parseInt(s.trim())).toList();


          long count = myCards.stream().filter(winningCards::contains).count();
          res += (long) Math.pow(2,count-1);
          System.out.println(res);
       }

    }


    public static void part2() throws FileNotFoundException {
        String inputFileName = "day4-part1.txt";
        Scanner sc = new Scanner(new File("src/main/resources/" + inputFileName));

        int nbCard = 218;
        long[] nbMatch = new long[nbCard];
        Map<Integer, Long> main = new HashMap<>();
        while (sc.hasNextLine()){

            String line = sc.nextLine().replaceAll("\\s+"," ");
            System.out.println(line);
            String[] cards = line.split("[:|]");
            int cardNumber = Integer.parseInt(cards[0].replace("Card ",""));
            List<Integer> winningCards = Arrays.stream(cards[1].trim().split(" ")).map(s-> Integer.parseInt(s.trim())).toList();
            List<Integer> myCards = Arrays.stream(cards[2].trim().split(" ")).map(s-> Integer.parseInt(s.trim())).toList();

            nbMatch[cardNumber-1] = myCards.stream().filter(winningCards::contains).count();
            main.put(cardNumber,1L);
        }

        for(int i = 0 ; i< nbMatch.length ; i++){
            long nbMat = nbMatch[i];
            Long nbInstance = main.get(i+1);
            System.out.println("card "+ (i+1) + "has "+nbMat+" match. Number of card "+nbInstance);
            for(int j = i+1; j<i+1+nbMat ; j++ ){
                if(j<nbCard+1){
                    main.put(j+1,main.get(j+1)+nbInstance);
                }
            }
        }

        System.out.println( main.values().stream().mapToLong(x->x).sum());

    }










}
