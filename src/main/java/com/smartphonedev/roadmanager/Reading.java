package com.smartphonedev.roadmanager;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

final class Reading implements Comparable<Reading>
{
    @Getter
    private final Integer count;
    @Getter
    private final String timeString;

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public Reading(Integer count, String timeString)
    {
        this.count = count;
        this.timeString = timeString;
    }

    public LocalDateTime getDateTime()
    {
        return LocalDateTime.parse(timeString, formatter);
    }

    @Override
    public String toString() {
        return timeString + " " +count;
    }

    @Override
    public int compareTo(Reading reading) {
        if(reading != null)
        {
            if(this.count > reading.getCount())
            {
                return 1;

            }else if(this.count == reading.getCount())
            {
                return 0;

            }else if(this.count < reading.getCount())
            {
                return -1;
            }
        }
       return -1;
    }
}
