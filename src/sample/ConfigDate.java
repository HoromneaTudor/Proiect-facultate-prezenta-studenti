package sample;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ConfigDate {
    ArrayList<GregorianCalendar> configDates=new ArrayList<>();
    ArrayList<GregorianCalendar> dateConfigPrelucrate=new ArrayList<>();
    static ArrayList<GregorianCalendar> copy;
    //ArrayList<GregorianCalendar> dateVacanta=new ArrayList<>();

    public void CitireConfigDates(String inFile)
    {
        //File configIn=new File(inFile);

        Path path = Paths.get(inFile);
        List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            boolean ok=true;
            int nrlines=1;
            String inceputSemestru=lines.get(0);
            System.out.println(lines.get(0));
            try {
                //System.out.println(lines.get(0));
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(inceputSemestru);
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(date);
                configDates.add(cal);
                for (GregorianCalendar d:configDates)
                {
                    System.out.println(d.toZonedDateTime());
                }


            }
            catch (ParseException e)
            {
                System.out.println("Data pus incorecta in folderul de configurare");
            }

            while(ok)
            {
                //System.out.println(lines.size());
                if(lines.size()==nrlines)
                {
                    ok=false;
                }
                else
                {
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(lines.get(nrlines));
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(date);
                    configDates.add(cal);
                    System.out.println(cal.toZonedDateTime());
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(lines.get(nrlines+1));
                    GregorianCalendar cal2 = new GregorianCalendar();
                    cal2.setTime(date);
                    configDates.add(cal2);
                    System.out.println(cal2.toZonedDateTime());
                    nrlines+=2;
                }
            }
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }
        /*for (GregorianCalendar d:configDates)
        {
            System.out.println(d.toZonedDateTime());
        }*/
    }
    public void prelucrareDateConfig()
    {
        /*GregorianCalendar c = new  GregorianCalendar();
        System.out.println("___"+c.get(Calendar.DAY_OF_MONTH));
        c.add(Calendar.DAY_OF_MONTH, 21);
        System.out.println(c.get(Calendar.DAY_OF_MONTH));*/

        /*System.out.println();
        for(int i=0;i<configDates.size();i++)
        {
            System.out.println(configDates.get(i).toZonedDateTime());
        }
        System.out.println();*/

        //System.out.println(configDates.size());
        GregorianCalendar c=configDates.get(0);
        //System.out.println(configDates.size());
        dateConfigPrelucrate.add(c);
        //int size=configDates.size();
        int index=1;
        int nrSaptamani=1;
        /*while(nrSaptamani<=13)
        {
            GregorianCalendar buf=c;

            if(configDates.size()==1)
            {

                buf.add(buf.DAY_OF_MONTH,7);
                //System.out.println(c.toZonedDateTime());
                nrSaptamani++;
            }
            *//*else {
                buf = configDates.get(index);

                //nu ii tocmai corect dar sa zicem
                if (index< configDates.size()) {
                    if (buf.equals(configDates.get(index))) {
                        buf = configDates.get(index + 1);
                        index += 2;
                        nrSaptamani++;
                    }
                }
                //asta pare ok
                else {
                    buf.add(c.DAY_OF_MONTH, 7);
                    //System.out.println(c.toZonedDateTime());
                    nrSaptamani++;
                }
            }*//*
            for(GregorianCalendar d:dateConfigPrelucrate)
            {
                System.out.println(d.toZonedDateTime());
            }
            System.out.println();
            dateConfigPrelucrate.add(buf);

        }*/
        GregorianCalendar prevBuf=c;
        while(nrSaptamani<=14)
        {

            GregorianCalendar buf = (GregorianCalendar) prevBuf.clone();
            buf.add(buf.DAY_OF_MONTH,7);
            //System.out.println(configDates.size());
            if(index<configDates.size())    //in caz de vacanta
            {
                /*System.out.println("ok");
                System.out.println(index);
                System.out.println(configDates.get(index).toZonedDateTime());
                System.out.println(buf.toZonedDateTime());
                System.out.println(configDates.get(index+1).toZonedDateTime());
                System.out.println(configDates.get(index).compareTo(buf));
                System.out.println(configDates.get(index+1).compareTo(buf));
                System.out.println();*/
                //System.out.println(configDates.get(0).toZonedDateTime()+" "+configDates.get(1).toZonedDateTime()+" "+configDates.get(2).toZonedDateTime());
                if((configDates.get(index).compareTo(buf)<0 && configDates.get(index+1).compareTo(buf)>0) || configDates.get(index).compareTo(buf)==0)
                {
                    GregorianCalendar bufVacanta= (GregorianCalendar) configDates.get(index+1).clone();
                    //System.out.println(bufVacanta.toZonedDateTime());
                    //bufVacanta.setTime((Date) configDates.get(index+1).clone());
                    //int daysBetween = (int) ChronoUnit.DAYS.between(configDates.get(index).toInstant(), configDates.get(index+1).toInstant());
                    //buf.add(buf.DAY_OF_MONTH,daysBetween);
                    //System.out.println(daysBetween);
                    prevBuf=bufVacanta;
                    buf=bufVacanta;
                    //System.out.println(buf.toZonedDateTime());

                    index+=2;
                }
                else
                {
                    //buf.add(buf.DAY_OF_MONTH,7);
                    prevBuf=buf;
                }

            }
            else //(configDates.size()==1)
            {

                //buf=prevBuf;
                //buf.add(buf.DAY_OF_MONTH,7);
                prevBuf=buf;
            }
            nrSaptamani++;

            dateConfigPrelucrate.add(buf);
            System.out.println();
            System.out.println(buf.toZonedDateTime());
        }
        int in=1;
        for(GregorianCalendar d:dateConfigPrelucrate)
        {
            System.out.println(in+"->"+d.toZonedDateTime());
            in++;
        }

        copy=dateConfigPrelucrate;

    }
}
