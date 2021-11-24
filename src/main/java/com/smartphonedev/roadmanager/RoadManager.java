package com.smartphonedev.roadmanager;

import com.smartphonedev.roadmanager.exceptions.InvalidInputException;
import lombok.var;

public class RoadManager {
    public static void main(String[] args)
    {
        try
        {
            var bootStrapper = new SimulationBootStrap(args);
        }catch (InvalidInputException iie)
        {
            System.out.printf("System was unable to bootstrap: "+iie.getLocalizedMessage());
        }
    }
}
