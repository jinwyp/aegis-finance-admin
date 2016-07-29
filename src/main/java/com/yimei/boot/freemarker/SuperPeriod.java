package com.yimei.boot.freemarker;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.*;

/**
 * Created by joe on 1/13/15.
 */
public class SuperPeriod implements TemporalAmount{
    protected long years;
    protected long months;
    protected long days;
    protected long hours;
    protected long minutes;
    protected long seconds;

    public SuperPeriod(long years, long months, long days, long hours, long minutes, long seconds) {
        this.years = years;
        this.months = months;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public long getYears() {
        return years;
    }

    public void setYears(long years) {
        this.years = years;
    }

    public long getMonths() {
        return months;
    }

    public void setMonths(long months) {
        this.months = months;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public long getHours() {
        return hours;
    }

    public void setHours(long hours) {
        this.hours = hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public static SuperPeriod between(Temporal begin, Temporal end){
        LocalDateTime tempDateTime = LocalDateTime.from( begin );

        long years = tempDateTime.until( end, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears( years );

        long months = tempDateTime.until( end, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths( months );

        long days = tempDateTime.until( end, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays( days );


        long hours = tempDateTime.until( end, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours( hours );

        long minutes = tempDateTime.until( end, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes( minutes );

        long seconds = tempDateTime.until( end, ChronoUnit.SECONDS);
        return new SuperPeriod(years, months, days, hours, minutes, seconds);
    }

    private static final List<TemporalUnit> SUPPORTED_UNITS =
            Collections.unmodifiableList(Arrays.<TemporalUnit>asList(YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS));
    @Override
    public long get(TemporalUnit unit) {
        if(unit.equals(YEARS))
            return years;
        if(unit.equals(MONTHS))
            return months;
        if(unit.equals(DAYS))
            return days;
        if(unit.equals(HOURS))
            return hours;
        if(unit.equals(MINUTES))
            return minutes;
        if(unit.equals(SECONDS))
            return seconds;
        return 0;
    }

    @Override
    public List<TemporalUnit> getUnits() {
        return SUPPORTED_UNITS;
    }

    @Override
    public Temporal addTo(Temporal temporal) {
        return temporal.plus(years, YEARS).plus(months, MONTHS).plus(days, DAYS).plus(hours, HOURS).plus(minutes, MINUTES).plus(seconds, SECONDS);
    }

    @Override
    public Temporal subtractFrom(Temporal temporal) {
        return temporal.minus(years, YEARS).minus(months, MONTHS).minus(days, DAYS).minus(hours, HOURS).minus(minutes, MINUTES).minus(seconds, SECONDS);
    }

    @Override
    public String toString() {
        return "SuperPeriod{" +
                "years=" + years +
                ", months=" + months +
                ", days=" + days +
                ", hours=" + hours +
                ", minutes=" + minutes +
                ", seconds=" + seconds +
                '}';
    }
}
