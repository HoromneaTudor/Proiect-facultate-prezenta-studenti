package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CitireSBV {
    public static void CitireSBVMethod(String inFile, ArrayList<Student> list, String grupeCautate, ConfigDate date, String grupeAcceptate, String cautaDacaAScrisCeva,
                                       String cautaDupaNumePrenume, String cautaDupaNumePrenumeSemigrupa, String cautaDupaUnString, String stringCautat
                                 ) {

            ArrayList<String> ListaStudenti = new ArrayList<>();
            PrelucrareDate prelucrare=new PrelucrareDate();

        File file = new File(inFile);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
            System.out.println(inFile);
                while((line=br.readLine())!=null)
                {
                    String[] buf=line.split(":");
                    //System.out.println(line);
                    if (buf.length==2&&buf[0].contains(" ")&&!buf[0].contains("+")&&!buf[0].contains(":"))
                    {
                        ListaStudenti.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            boolean isCurs=inFile.contains("_C_");

            for(String p:ListaStudenti) {

                prelucrare.GasireStudent(list,p,cautaDacaAScrisCeva,cautaDupaNumePrenume,cautaDupaNumePrenumeSemigrupa,cautaDupaUnString,isCurs,inFile,date,grupeCautate,grupeAcceptate,stringCautat);

            }
            if(prelucrare.listaAnomalii!=null)
            {
                for(String s:prelucrare.listaAnomalii)
                {
                    System.out.println(s.toUpperCase());
                }
            }

    }


}
