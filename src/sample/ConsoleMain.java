package sample;

import java.io.IOException;
import java.util.ArrayList;

public class ConsoleMain {

    static ArrayList<Student> listaStudenti;

    public static void main(String[] args) throws IOException {
        //ArrayList<String> caleFisiere=new ArrayList<>();
        String cai=args[0];
        System.out.println("--"+Thread.currentThread());
        ConfigDate date=new ConfigDate();
        //DatetoPrezenta d=new DatetoPrezenta();
            /*File dir=new File(cai);
            File[] subDir = dir.listFiles ( (f)-> {
                if(f.isDirectory()) return false;
                String ext=f.toString().substring(f.toString().lastIndexOf(".")+1);
                return ext.equalsIgnoreCase("sbv");
            });

            for(File f: subDir) {
                System.out.println("------>" + f);
                caleFisiere.add(f.toString());
            }*/
        String[] bufSBV=cai.split("DELIMITATOR");
        //d.CreareListeDate(caleFisiere);

        //d.afisare();

        /*for(String s:caleFisiere)
            {
                d.CitireConfigDates(s);
            }*/
        //sa vad dc nu merge

        date.CitireConfigDates(args[10]);
            date.prelucrareDateConfig();

        listaStudenti= (ArrayList<Student>) CitireCSV.CitireCSVMethod(args[2]);
        //System.out.println(args[3]);
        for(String s:bufSBV) {
            CitireSBV.CitireSBVMethod(s, listaStudenti, args[1],date,args[3],args[5],args[6],args[7],args[8],args[9]);
            //                 student,listStud,grupecautate,date,grupeacceptate

        }
        if(ScriereCSV.ScreieFisier(args[4],listaStudenti))
            System.out.println("scriere realizata cu succes!!");

    }
}
