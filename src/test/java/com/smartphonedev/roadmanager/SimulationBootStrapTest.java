package com.smartphonedev.roadmanager;

import com.smartphonedev.roadmanager.exceptions.InvalidInputException;
import lombok.var;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SimulationBootStrapTest {
    @Test
    protected void bootStrapFailsIfFileIsNotProvided()
    {
        //given
        Executable executable = () -> new SimulationBootStrap(null);
        //when
        //then
        assertThrows(InvalidInputException.class ,executable);
    }

    @Test
    protected void calculateTotalOfAllCarsReturnsValidResponseWhenValidArguments() throws InvalidInputException {
        var args = new String[]{"sample.txt"};
        var values = new String[]{"2016-12-05T12:30:00 6", "2016-12-05T13:30:00 9", "2016-12-05T14:30:00 11"};
        final var simulatorInstance = new SimulationBootStrap(args);
        final Class<?> simulatorClass = simulatorInstance.getClass();

        //when
        try
        {
            final var calculateTotalOfAllCars = simulatorClass.getDeclaredMethod("calculateTotalOfAllCars", List.class);
            calculateTotalOfAllCars.setAccessible(true);



            var result = (Integer) calculateTotalOfAllCars.invoke(simulatorInstance, Arrays.asList(values));

            //then
            assertThat(result).isNotNull().isInstanceOf(Integer.class);
            assertThat(result).isEqualTo(26);

        }catch (NoSuchMethodException nsme)
        {
            fail();
        }catch (IllegalAccessException iae)
        {
            fail();
        }catch (InvocationTargetException ite)
        {
            fail();
        }
    }

    @Test
    protected void getPeriodsCountReturnsTrueWhenCountIsCorrect() throws InvalidInputException {
        //given
        var args = new String[]{"sample.txt"};
        var values = new String[]{"2016-12-05T12:30:00 6", "2016-12-05T13:30:00 9", "2016-12-05T14:30:00 11"};
        final var simulatorInstance = new SimulationBootStrap(args);
        final Class<?> simulatorClass = simulatorInstance.getClass();

        //when
        try
        {
            final var getPeriodsCount = simulatorClass.getDeclaredMethod("getPeriodsCount", List.class);
            getPeriodsCount.setAccessible(true);

            var readingList = Arrays.stream(values).map(string -> string.split(" "))
                .map(array -> {
                    return new Reading(Integer.valueOf(array[1]), array[0]);
                }).collect(Collectors.toList());

            var result = (Integer) getPeriodsCount.invoke(simulatorInstance, readingList);

            //then
            assertThat(result).isNotNull().isInstanceOf(Integer.class);
            assertThat(result).isEqualTo(26);

        }catch (NoSuchMethodException nsme)
        {
            fail();
        }catch (IllegalAccessException iae)
        {
            fail();
        }catch (InvocationTargetException ite)
        {
            fail();
        }

    }

    @Test
    protected void sfkgfdkgldfgj() throws InvalidInputException, IOException
    {
        //given
        var args = new String[]{"sample.txt"};
        final var simulatorInstance = new SimulationBootStrap(args);
        final Class<?> simulatorClass = simulatorInstance.getClass();

        //when
        var reader = new BufferedReader(new StringReader("2016-12-01T08:00:00 42\n2016-12-05T11:30:00 7\n2016-12-05T14:30:00 11"));

        try
        {
            final var readFile = simulatorClass.getDeclaredMethod("readFile", BufferedReader.class);
            readFile.setAccessible(true);
            var result = (List<String>)readFile.invoke(simulatorInstance, reader);

            //then
            assertThat(result).isNotNull().isInstanceOf(ArrayList.class);
            assertThat(result.stream().count()).isEqualTo(3L);

        }catch (NoSuchMethodException nsme)
        {
            fail();
        }catch (IllegalAccessException iae)
        {
            fail();
        }catch (InvocationTargetException ite)
        {
            fail();
        }

    }
}