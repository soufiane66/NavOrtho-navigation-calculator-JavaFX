package OrthoClasses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time {
    private  int year,month,day;
    private  int hour,minute;
    private Calendar calendar;




    public Time(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;

        calendar = Calendar.getInstance();
        calendar.set(year,month-1,day,hour,minute);

    }

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public double toHours()
    {
        return 0;

    }

    public double timeEcart(Time time)
    {
        return 0;

    }

    /**
     *
     * @param amount int minute
     * @return new Date
     */
    public String addTime(int amount)
    {
       Calendar calendar1 = Calendar.getInstance();
        calendar1.set(year,month-1,day,hour,minute);

       calendar1.add(Calendar.MINUTE,amount);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = calendar1.getTime();

        return dateFormat.format(date);


    }

    public String formatDate(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
    }

    @Override
    public String toString() {
        return String.valueOf(calendar.getTime());
    }


    public String getTime()
    {
        //return +day+"/"+month+"/"+year;
        return toString();
    }
}
