package sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DatetoPrezenta {
    /*GregorianCalendar c = new  GregorianCalendar();
        System.out.println("___"+c.get(Calendar.DAY_OF_MONTH));
        c.add(Calendar.DAY_OF_MONTH, 21);
        System.out.println(c.get(Calendar.DAY_OF_MONTH));*/

    private String Data;
    private ArrayList<String> caleFisiere;
    private ArrayList<GregorianCalendar> prezenteCurs;//=new TreeSet<>();
    private Map<String, ArrayList<GregorianCalendar>> prezenteLaborator =new HashMap<>();
    SortedSet<String> listGrupe=new TreeSet<>();


    public void CreareListeDate(ArrayList<String> caleFisiere) throws ParseException {
        for(String s:caleFisiere)
        {
            String substring = s.substring(s.lastIndexOf("_") + 1, s.lastIndexOf("."));
            if(s.contains("_C_")&&s.contains(".sbv"))
            {
                if(prezenteCurs==null)
                {
                    //prezenteCurs=new TreeSet<>(Date::compareTo);
                    prezenteCurs=new ArrayList<>();
                }
                String buf= substring;
                Date date1=new SimpleDateFormat("ddMMyyyy").parse(buf);
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(date1);
                prezenteCurs.add(cal);
                prezenteCurs.sort(GregorianCalendar::compareTo);
            }
            if(s.contains("_L_")&&s.contains(".sbv"))
            {
                String[] buf2=s.split("_");
                String buf=buf2[buf2.length-2];

                String s1= substring.toUpperCase();
                System.out.println(s1);
                ArrayList<GregorianCalendar> lstdp=prezenteLaborator.get(s1);
                Date date1=new SimpleDateFormat("ddMMyyyy").parse(buf);
                if(lstdp==null)
                {
                    lstdp=new ArrayList<>();
                    prezenteLaborator.put(s1,lstdp);
                }
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(date1);
                lstdp.add(cal);
                listGrupe.add(s1);
                lstdp.sort(GregorianCalendar::compareTo);
            }
        }
    }

    public void afisare() {
        for(GregorianCalendar d:prezenteCurs)
        {
            System.out.println(d.toZonedDateTime());
        }
        System.out.println();
        for(String key:prezenteLaborator.keySet())
        {
            ArrayList<GregorianCalendar> buf=prezenteLaborator.get(key);
            System.out.println(key);
            for(GregorianCalendar d:buf)
            {
                System.out.print(d.toZonedDateTime()+" ");
            }
            System.out.println();
        }

    }

    public SortedSet<String> getListGrupe() {
        return listGrupe;
    }

    public ArrayList<GregorianCalendar> getPrezenteCurs() {
        return prezenteCurs;
    }

    public Map<String, ArrayList<GregorianCalendar>> getPrezenteLaborator() {
        return prezenteLaborator;
    }
}
