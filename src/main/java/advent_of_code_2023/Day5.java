package advent_of_code_2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day5 {


    public static void main(String[] args) throws FileNotFoundException {
        //part1();
        part2();
    }

    public static void part1() throws FileNotFoundException {
        String inputFileName = "day5-part1.txt";
        Scanner sc = new Scanner(new File("src/main/resources/" + inputFileName));
        long res = Long.MAX_VALUE;

        List<Long> seeds = new ArrayList<>();
        Map<Long, long[]> seedtosoil = new HashMap<>();
        Map<Long, long[]> soiltofertilizer = new HashMap<>();
        Map<Long, long[]> fertilizertowater = new HashMap<>();
        Map<Long, long[]> watertolight = new HashMap<>();
        Map<Long, long[]> lighttotemperature = new HashMap<>();
        Map<Long, long[]> temperaturetohumidity = new HashMap<>();
        Map<Long, long[]> humiditytolocation = new HashMap<>();

        Category c = null;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if(line.isEmpty() || line.isBlank()){
                continue;
            }

            if (line.startsWith("seeds")){
                c = Category.seeds;

                String[] sp = line.split(" ");
                for(int i=1;i<sp.length;i++){
                    seeds.add(Long.parseLong(sp[i]));
                }
                continue;
            }
            if (line.startsWith("seed-to-soil")){
                c = Category.seed_to_soil;
                continue;
            }
            if (line.startsWith("soil-to-fertilizer")){
                c = Category.soil_to_fertilizer;
                continue;
            }
            if (line.startsWith("fertilizer-to-water")){
                c = Category.fertilizer_to_water;
                continue;
            }
            if (line.startsWith("water-to-light")){
                c = Category.water_to_light;
                continue;
            }
            if (line.startsWith("light-to-temperature")){
                c = Category.light_to_temperature;
                continue;
            }
            if (line.startsWith("temperature-to-humidity")){
                c = Category.temperature_to_humidity;
                continue;
            }
            if (line.startsWith("humidity-to-location")){
                c = Category.humidity_to_location;
                continue;
            }

            switch (c){
                case seed_to_soil -> buildMap(seedtosoil, line);
                case soil_to_fertilizer -> buildMap(soiltofertilizer, line);
                case fertilizer_to_water -> buildMap(fertilizertowater, line);
                case water_to_light -> buildMap(watertolight, line);
                case light_to_temperature -> buildMap(lighttotemperature, line);
                case temperature_to_humidity -> buildMap(temperaturetohumidity, line);
                case humidity_to_location -> buildMap(humiditytolocation, line);
            }
        }

        for(Long seed : seeds){

            Long soil = getConversion(seedtosoil, seed);
            Long fertilizer = getConversion(soiltofertilizer, soil);
            Long water = getConversion(fertilizertowater, fertilizer);
            Long light = getConversion(watertolight, water);
            Long temp = getConversion(lighttotemperature, light);
            Long humide = getConversion(temperaturetohumidity, temp);
            Long location = getConversion(humiditytolocation, humide);

            System.out.println(
                    "seed "+seed+", soil "+soil+ ", water "+water+
                    ",light "+light+", temp "+temp+ ", humide "+humide+
                    ", location "+location
            );

            if(location<res){
                res = location;
            }

        }
        System.out.println(res);



    }

    public static void part2() throws FileNotFoundException {
        String inputFileName = "day5-part1.txt";
        Scanner sc = new Scanner(new File("src/main/resources/" + inputFileName));
        long res = Long.MAX_VALUE;

        List<Long[]> seeds = new ArrayList<>();
        Map<Long, long[]> seedtosoil = new HashMap<>();
        Map<Long, long[]> soiltofertilizer = new HashMap<>();
        Map<Long, long[]> fertilizertowater = new HashMap<>();
        Map<Long, long[]> watertolight = new HashMap<>();
        Map<Long, long[]> lighttotemperature = new HashMap<>();
        Map<Long, long[]> temperaturetohumidity = new HashMap<>();
        Map<Long, long[]> humiditytolocation = new HashMap<>();

        Category c = null;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if(line.isEmpty() || line.isBlank()){
                continue;
            }

            if (line.startsWith("seeds")){
                c = Category.seeds;

                String[] sp = line.split(" ");
                for(int i=1;i<sp.length;i+=2){
                    long start = Long.parseLong(sp[i]);
                    long range = Long.parseLong(sp[i+1]);
                    seeds.add(new Long[]{start, range});
                }
                continue;
            }
            if (line.startsWith("seed-to-soil")){
                c = Category.seed_to_soil;
                continue;
            }
            if (line.startsWith("soil-to-fertilizer")){
                c = Category.soil_to_fertilizer;
                continue;
            }
            if (line.startsWith("fertilizer-to-water")){
                c = Category.fertilizer_to_water;
                continue;
            }
            if (line.startsWith("water-to-light")){
                c = Category.water_to_light;
                continue;
            }
            if (line.startsWith("light-to-temperature")){
                c = Category.light_to_temperature;
                continue;
            }
            if (line.startsWith("temperature-to-humidity")){
                c = Category.temperature_to_humidity;
                continue;
            }
            if (line.startsWith("humidity-to-location")){
                c = Category.humidity_to_location;
                continue;
            }

            switch (c){
                case seed_to_soil -> buildMap(seedtosoil, line);
                case soil_to_fertilizer -> buildMap(soiltofertilizer, line);
                case fertilizer_to_water -> buildMap(fertilizertowater, line);
                case water_to_light -> buildMap(watertolight, line);
                case light_to_temperature -> buildMap(lighttotemperature, line);
                case temperature_to_humidity -> buildMap(temperaturetohumidity, line);
                case humidity_to_location -> buildMap(humiditytolocation, line);
            }
        }

        /* le min des map */

        Comparator<Map.Entry<Long, long[]>> entryComparator = Comparator.comparing(entry -> entry.getValue()[0]);

        // location le plus petit, ca veut dire hors map location < 324 294 413
        // temp le plus bas entre (1) 1 369 964 423 et 1 589 493 605 ou entre (2) 731 674 447 et 964 402 346

        //print("light","temperature", lighttotemperature, 1_369_964_423L, 1_589_493_605L);
        //print("light","temperature", lighttotemperature, 731_674_447L, 964_402_346L);
        /* temperature -> light          1369964423 - 1589493605 -> 913089818 - 1132619000*/
        /* temperature -> light          731674447 - 964402346 -> 2087900022 - 2174489282   */
        /*
            soit light le plus bas entre (1) 913_089_818 - 1_132_619_000      ou    (2) 2_087_900_022 - 2_174_489_282
         */
        //print("water","light", watertolight, 913_089_818L, 1_132_619_000L);
        /* light -> water 913089818 - 1132619000 -> 2702312246 - 2725849236 */

        //print("water","light", watertolight, 2_087_900_022L, 2_174_489_282L);
        /* light -> water   2087900022 - 2174489282 -> 1529936824 - 1616526084*/

        //TODO reste pour fert

        /* water le plus bas dans 2_702_312_246 - 2_725_849_236*/
        //print("fertilizer","water", fertilizertowater, 2_702_312_246L, 2_725_849_236L);
        /* water -> fertilizer   2702312246 - 2725849236 -> 2378057139 - 2401594129 */
        //print("fertilizer","water", fertilizertowater, 1529936824L, 1616526084L);
        /* water -> fertilizer   1529936824 - 1616526084 -> 2951784152 - 2961420861*/

        //print("soil","fertilizer", soiltofertilizer, 2378057139L, 2401594129L);
        /* not found */
        //print("soil","fertilizer", soiltofertilizer, 2951784152L, 2961420861L);
        /* fertilizer -> soil 2951784152 - 2961420861 -> 2763441764 - 2773078473 */

        //print("seed","soil", seedtosoil, 2763441764L, 2773078473L);
        /* soil -> seed 2763441764 - 2773078473 -> 1563508792 - 1573145501 */

        a: for(Long[] s : seeds){

            long depart = s[0];
            long range = s[1];

            // seed doit etre entre 1563508792 - 1573145501   -> trouvé au dessus
            if(depart+range < 1563508792){continue a;}
            if(depart > 1573145501){continue a;}
            Long realDep = Math.max(depart,1563508792);
            Long realEnd = Math.min(depart+range, 1573145501);
            System.out.println("scope accepté "+ realDep+" "+realEnd);

            for(long seed = realDep; seed<=realEnd; seed++){


                Long soil = getConversion(seedtosoil, seed);
                Long fertilizer = getConversion(soiltofertilizer, soil);
                Long water = getConversion(fertilizertowater, fertilizer);
                Long light = getConversion(watertolight, water);
                Long temp = getConversion(lighttotemperature, light);
                Long humide = getConversion(temperaturetohumidity, temp);
                Long location = getConversion(humiditytolocation, humide);
                /*
                System.out.println(
                        "seed "+seed+", soil "+soil+ ", water "+water+
                                ",light "+light+", temp "+temp+ ", humide "+humide+
                                ", location "+location
                );
*/
                if(location<res){
                    res = location;
                }
            }
        }
        System.out.println(res);



    }

    private static void print( String nomColSeed, String nomColSoil, Map<Long, long[]> map, Long realBorneInfSoil, Long realBorneSupSoil ){
        System.out.println(nomColSoil+" -> "+nomColSeed);
        for(Map.Entry<Long, long[]> entry : map.entrySet()){

            long borneInfSeed = entry.getKey();
            long[] l = entry.getValue();
            long borneInfSoil = l[0];
            long range = l[1];
            long borneSupSeed = borneInfSeed + range;
            long borneSupSoil = borneInfSoil + range;


            if(realBorneInfSoil >= borneInfSoil && realBorneInfSoil< borneSupSoil){

                Long realBorneInfSeed = (realBorneInfSoil-borneInfSoil) + borneInfSeed;
                Long realBorneSupSeed;
                if(realBorneSupSoil < borneSupSoil){
                    realBorneSupSeed = (realBorneSupSoil-borneSupSoil) + borneSupSeed;
                }else{
                    realBorneSupSeed = borneSupSeed;
                }

                System.out.println( realBorneInfSoil+" - "+ realBorneSupSoil + " -> "+ realBorneInfSeed +" - "+ realBorneSupSeed);
            }




        }


    }


    private static Long getConversion( Map<Long, long[]> map, Long key ){

        long plusGrand = map.keySet().stream().sorted(Comparator.reverseOrder()).filter(k -> k <= key).findFirst().orElse(-1L);

        if (plusGrand == -1) {
            return key;
        }

        long[] conversion = map.get(plusGrand);

        if(key > plusGrand+conversion[1]){
            return key;
        }else{
            return conversion[0]+ key-plusGrand ;
        }
    }


    private static void buildMap( Map<Long, long[]> map, String line ){
        String[] l = line.split(" ");
            map.put( Long.parseLong(l[1]),new long[]{Long.parseLong(l[0]),Long.parseLong(l[2]) } );
    }



}

enum Category {

    seeds,
    seed_to_soil,
    soil_to_fertilizer,
    fertilizer_to_water,
    water_to_light,
    light_to_temperature,
    temperature_to_humidity,
    humidity_to_location

}