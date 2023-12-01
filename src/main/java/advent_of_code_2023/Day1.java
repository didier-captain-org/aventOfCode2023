package advent_of_code_2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 {

    //public static final String[] numbers = Arrays.asList("1","2","3","4","5","6","7","8","9","one","two","three","four","five","six","seven","eight","nine");

    public static void main(String[] args) throws FileNotFoundException {
        //part1();
        part2();
    }

    public static void part1() throws FileNotFoundException {
        String inputFileName = "day1-part1.txt";
        Scanner sc = new Scanner(new File("src/main/resources/"+inputFileName));
        long somme=0;
        while (sc.hasNext()){
            String line = sc.nextLine();
            System.out.println(line);
            String[] withoutLetter = line.replaceAll("[a-zA-Z]","").split("");
            somme+= Long.parseLong(withoutLetter[0]+withoutLetter[withoutLetter.length-1]);
            System.out.println(somme);
        }
    }

    public static void part2() throws FileNotFoundException {
        String inputFileName = "day1-part1.txt";
        Scanner sc = new Scanner(new File("src/main/resources/"+inputFileName));
        long somme=0;
        while (sc.hasNext()){
            String line = sc.nextLine();
            System.out.println(line);
            line = line.replace("twone","21")
                    .replace("oneight","18")
                    .replace("fiveight","58")
                    .replace("sevenine","79")
                    .replace("eighthree","83")
                    .replace("eightwo","82")
                    .replace("nineight","98");

            Pattern p = Pattern.compile("(one|two|three|four|five|six|seven|eight|nine|[1-9])");
            Matcher m = p.matcher(line);
            List<String> numbers = new ArrayList<>();

            while (m.find()){
                numbers.add(m.group());
            }

            List<Integer> realNumbers =numbers.stream().map(
                    s-> Integer.parseInt(
                          s.replace("one","1")
                                  .replace("two","2")
                                  .replace("three","3")
                                  .replace("four","4")
                                  .replace("five","5")
                                  .replace("six","6")
                                  .replace("seven","7")
                                  .replace("eight","8")
                                  .replace("nine","9")
                    )
            ).toList();

            System.out.println(realNumbers.get(0)+""+realNumbers.get(realNumbers.size()-1));
            somme+= Long.parseLong(realNumbers.get(0)+""+realNumbers.get(realNumbers.size()-1));
            System.out.println(somme);
        }
    }


}
