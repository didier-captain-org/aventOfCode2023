package advent_of_code_2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day3 {

    public static int nbLigne = 140;
    public static int nbColonne = 140;
    public static char[][] input = new char[nbLigne][nbColonne];
    public static char[][] numbersAdjacents = new char[nbLigne][nbColonne];

    public static void main(String[] args) throws FileNotFoundException {
        //part1();
        part2();
    }

    public static void part1() throws FileNotFoundException {
        String inputFileName = "day3-part1.txt";
        Scanner sc = new Scanner(new File("src/main/resources/" + inputFileName));

        boolean valeurChange = true;

        /* readInput and set symbol */
        for (int l = 0; l < nbLigne; l++) {
            String line = sc.nextLine();
            for (int c = 0; c < nbColonne; c++) {
                input[l][c] = line.charAt(c);
                numbersAdjacents[l][c] = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9').contains(line.charAt(c)) ? '.' : line.charAt(c);
            }
        }

        /* propagation */
        while (valeurChange) {
            valeurChange = false;
            for (int l = 0; l < nbLigne; l++) {
                for (int c = 0; c < nbColonne; c++) {
                    if (numbersAdjacents[l][c] != '.') {
                        /* on va propager aux cases d'a coté et en diag */
                        valeurChange = propage(l - 1, c - 1, valeurChange);
                        valeurChange = propage(l - 1, c, valeurChange);
                        valeurChange = propage(l - 1, c + 1, valeurChange);
                        valeurChange = propage(l, c - 1, valeurChange);
                        valeurChange = propage(l, c + 1, valeurChange);
                        valeurChange = propage(l + 1, c - 1, valeurChange);
                        valeurChange = propage(l + 1, c, valeurChange);
                        valeurChange = propage(l + 1, c + 1, valeurChange);
                    }
                }
            }
        }

        /* cherche les valeurs inchangé */
        StringBuilder stringBuilder = new StringBuilder();
        for (int l = 0; l < nbLigne; l++) {
            for (int c = 0; c < nbColonne; c++) {
                if (numbersAdjacents[l][c] == input[l][c] && Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9').contains(input[l][c])) {
                    stringBuilder.append(input[l][c]);
                } else {
                    stringBuilder.append(".");
                }
            }
            stringBuilder.append(".");
        }

        String res = stringBuilder.toString().replaceAll("[.]+", ".");
        System.out.println(res);
        System.out.println(Arrays.stream(res.split("[.]")).filter(x -> !x.isEmpty()).mapToLong(Long::parseLong).sum());

    }

    private static boolean propage(int l, int c, boolean valeurChange) {
        if (l > -1 && l < nbLigne && c > -1 && c < nbColonne && numbersAdjacents[l][c] == '.' && input[l][c] != '.') {
            numbersAdjacents[l][c] = input[l][c];
            valeurChange = true;
        }
        return valeurChange;
    }

    private static char isFilled(int l, int c) {
        char adj = '.';
        if (l > -1 && l < nbLigne && c > -1 && c < nbColonne && Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9').contains(input[l][c])) {
            adj = input[l][c];
        }
        return adj;
    }


    public static void part2() throws FileNotFoundException {
        String inputFileName = "day3-part1.txt";
        Scanner sc = new Scanner(new File("src/main/resources/" + inputFileName));

        /* readInput and set symbol */
        for (int l = 0; l < nbLigne; l++) {
            String line = sc.nextLine();
            for (int c = 0; c < nbColonne; c++) {
                input[l][c] = line.charAt(c);
            }
        }

        List<int[]> listeGear = new ArrayList<>();

        /* on garde les gear avec 2 part number */
        for (int l = 0; l < nbLigne; l++) {
            for (int c = 0; c < nbColonne; c++) {
                if (input[l][c] == '*') {
                    StringBuilder nbAdj = new StringBuilder();
                    nbAdj.append(isFilled(l - 1, c - 1));
                    nbAdj.append(isFilled(l - 1, c));
                    nbAdj.append(isFilled(l - 1, c + 1));
                    nbAdj.append(".");
                    nbAdj.append(isFilled(l, c - 1));
                    nbAdj.append(".");
                    nbAdj.append(isFilled(l, c + 1));
                    nbAdj.append(".");
                    nbAdj.append(isFilled(l + 1, c - 1));
                    nbAdj.append(isFilled(l + 1, c));
                    nbAdj.append(isFilled(l + 1, c + 1));

                    String res = nbAdj.toString().replaceAll("[.]+", ".");
                    if (Arrays.stream(res.split("[.]")).filter(x -> !x.isEmpty()).count() == 2) {
                        listeGear.add(new int[]{l, c});
                    }
                }
            }
        }

        System.out.println("la liste de gears "+ listeGear.stream().map(x->"["+x[0]+","+x[1]+"]").collect(Collectors.joining()));

        /* pour chaque etoile, on calcule son ratio */
        List<Long> ratio = new ArrayList<>();
        for (int[] gear : listeGear) {
            numbersAdjacents = new char[nbLigne][nbColonne];
            for (int l = 0; l < nbLigne; l++) {
                for (int c = 0; c < nbColonne; c++) {
                    numbersAdjacents[l][c] = '.';
                }
            }
            numbersAdjacents[gear[0]][gear[1]] = '*';
            /* propagation */
            boolean valeurChange = true;
            while (valeurChange) {
                valeurChange = false;
                for (int l = 0; l < nbLigne; l++) {
                    for (int c = 0; c < nbColonne; c++) {
                        if (numbersAdjacents[l][c] != '.') {
                            /* on va propager aux cases d'a coté et en diag */
                            valeurChange = propage(l - 1, c - 1, valeurChange);
                            valeurChange = propage(l - 1, c, valeurChange);
                            valeurChange = propage(l - 1, c + 1, valeurChange);
                            valeurChange = propage(l, c - 1, valeurChange);
                            valeurChange = propage(l, c + 1, valeurChange);
                            valeurChange = propage(l + 1, c - 1, valeurChange);
                            valeurChange = propage(l + 1, c, valeurChange);
                            valeurChange = propage(l + 1, c + 1, valeurChange);
                        }
                    }
                }
            }

            /* cherche les valeurs inchangé */
            StringBuilder stringBuilder = new StringBuilder();
            for (int l = 0; l < nbLigne; l++) {
                for (int c = 0; c < nbColonne; c++) {
                    if (numbersAdjacents[l][c] == input[l][c] && Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9').contains(input[l][c])) {
                        stringBuilder.append(input[l][c]);
                    } else {
                        stringBuilder.append(".");
                    }
                }
                stringBuilder.append(".");
            }

            String res = stringBuilder.toString().replaceAll("[.]+", ".");
            System.out.println(res);
            ratio.add(Arrays.stream(res.split("[.]")).filter(x -> !x.isEmpty()).mapToLong(Long::parseLong).reduce(1, (a, b) -> a * b));
        }

        System.out.println(ratio);
        System.out.println(ratio.stream().mapToLong(x -> x).sum());




    }

}
