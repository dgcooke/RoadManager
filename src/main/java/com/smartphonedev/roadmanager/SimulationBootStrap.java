package com.smartphonedev.roadmanager;

import com.smartphonedev.roadmanager.exceptions.InvalidInputException;
import lombok.var;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

public final class SimulationBootStrap
{

    public SimulationBootStrap(String[] args) throws InvalidInputException {
        if((args != null) &&(args.length == 1))
        {
            var siteFile = new File(args[0]);
            try
            {
                var inputStream = new FileInputStream(siteFile);
                startSimulation(inputStream);

            }catch (FileNotFoundException fnf)
            {
                throw new InvalidInputException("Error opening data file");
            }
        }else
        {
            throw new InvalidInputException("Data file was not found");
        }
    }

    private List<String> readFile(BufferedReader reader)
    {
        var inputList = new ArrayList<String>();
        try
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                inputList.add(line);
            }
        }catch (IOException ioe)
        {
            return null;
        }
        return inputList;
    }

    private Integer calculateTotalOfAllCars(List<String> list)
    {
        return list.stream()
                .map(string -> string.split(" "))
                .map(array ->array[1])
                .map(Integer::valueOf)
                .reduce(0,Integer::sum);
    }



    private HashMap<String,Integer> retrieveDatesFromList(List<String> list)
    {
        var resultsMap = new HashMap<String,Integer>();
        list.stream()
                .map(string -> string.split(" "))
                .forEach(array ->
                {
                    var date = LocalDate.parse(array[0], Reading.formatter).toString();
                    if(resultsMap.containsKey(date))
                    {
                        var value = (resultsMap.get(date) + Integer.valueOf(array[1]));
                        resultsMap.put(date, value);
                    }else
                    {
                        resultsMap.put(date,Integer.valueOf(array[1]));
                    }
        });
        return resultsMap;
    }

    private void topThreeThirtyMinutePeriods(List<String> inputList)
    {
        inputList.stream()
                .map(string -> string.split(" "))
                .map(array -> {
                    return new Reading(Integer.valueOf(array[1]), array[0]);
                }).sorted(Collections.reverseOrder())
                .limit(3)
                .forEach(reading -> {
                    System.out.println(reading.getTimeString()+" "+reading.getCount());
                });

    }

    private void quietestThreeContiguousReadings(List<String> inputList)
    {
        Reading[] lowestThreeContiguousReadings = new Reading[3];

        var minimumValue = Integer.MAX_VALUE;
        var currentIndex = 0;
        var readingList = inputList.stream()
                .map(string -> string.split(" "))
                .map(array -> {
                    return new Reading(Integer.valueOf(array[1]), array[0]);
                })
                .sorted(new Comparator<Reading>() {

                    @Override
                    public int compare(Reading readingA, Reading readingB) {
                        return readingA.getDateTime().compareTo(readingB.getDateTime());
                    }
                })
                .collect(Collectors.toList());

        for(;currentIndex < inputList.size();currentIndex++)
        {
            if(currentIndex+2 < inputList.size())
            {
                var value = getPeriodsCount(Arrays.asList(readingList.get(currentIndex), readingList.get(currentIndex+1), readingList.get(currentIndex+2)));
                if(value < minimumValue)
                {
                    lowestThreeContiguousReadings[0] = readingList.get(currentIndex);
                    lowestThreeContiguousReadings[1] = readingList.get(currentIndex+1);
                    lowestThreeContiguousReadings[2] = readingList.get(currentIndex+2);
                    minimumValue = value;
                }
            }
        }
        Arrays.asList(lowestThreeContiguousReadings).forEach(System.out::println);
    }

    private Integer getPeriodsCount(List<Reading> inputList)
    {
        if(inputList.size() == 3)
        {
            return inputList.stream()
                    .mapToInt(reading -> reading.getCount())
                    .reduce(0,Integer::sum);
        }
        return Integer.MAX_VALUE;
    }

    public void startSimulation(InputStream input)  {
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            var inputList = readFile(reader);
            var totalCars = calculateTotalOfAllCars(inputList);

            System.out.println("Total Number of cars = "+totalCars);
            var resultsMap = retrieveDatesFromList(inputList);

            resultsMap.entrySet().stream().forEach(entrySet ->{
                System.out.println(entrySet.getKey()+" "+entrySet.getValue());
            });

            System.out.println("\n===============Top 3 30Min Period =====================");
            topThreeThirtyMinutePeriods(inputList);


            System.out.println("\n===============3 contiguous readings with least cars =====================");
            quietestThreeContiguousReadings(inputList);


        } catch (UnsupportedEncodingException uee)
        {
            System.out.println("Sitemap could not be loaded from the input provided");
        }

    }
}
