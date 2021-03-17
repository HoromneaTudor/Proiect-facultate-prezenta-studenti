package sample;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class CitireCSV {

    static String primaLinie;
    static SortedSet<String> listaSemigrupe=new TreeSet<>();
    static char delimitator;

    public static List<Student> CitireCSVMethod(String inFile)
    {
        Path path = Paths.get(inFile);
        List<String> lines = null;
        ArrayList<Student> ListaStudenti=new ArrayList<>();
        long count1 = 0;
        long count2 = 0;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String[] buf;
        assert lines != null;
        primaLinie=lines.get(0);
        for(int i=1;i<lines.size();i++)
        {
            buf=lines.get(i).trim().split("[,;]");
            count1+=lines.get(i).chars().filter(num -> num == ',').count();
            count2+=lines.get(i).chars().filter(num -> num == ';').count();
            //System.out.println(buf.length);
            /*for (String s:buf) {
                System.out.print(s+" ");
            }
            System.out.println();*/
            Student s=new Student(buf[1],buf[3],buf[4],buf[2].toUpperCase(),buf[0],false);
            if(buf[3].contains(" - ")||buf[3].contains("-")||buf[3].contains("- ")||buf[3].contains(" "))
            {
                s.setPrenumeDublu(true);
            }
            if(buf.length!=5) {
                s.addPrezenteCSV(buf);
                System.out.println(s);
            }
            ListaStudenti.add(s);
            listaSemigrupe.add(buf[4]);
        }
        if(count1>count2)
        {
            delimitator=',';
        }
        else
            delimitator=';';
    return ListaStudenti;

    }



}
