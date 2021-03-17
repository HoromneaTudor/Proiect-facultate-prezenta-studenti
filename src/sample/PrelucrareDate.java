package sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//gradul de eroare ar trebui sa fie mai mare, la cursul 4 nu a fost pus domnisoara agheorghiesei din cauza ca nu au fost inlocuite bine diacriticele
//in fisierul .sbv
//DACA CRESC EROAREA LA <=2 ATUNCI SOVEA ESTE INLOCUIT DE TOFAN
//SE POATE SI CU GRAD DOI DOAR CA TREBUIE SA MODIFIC FUNCTIA SA IMI IA DOAR NUMELE NU SA VERIFICE SI PRENUMELE

public class PrelucrareDate {

    private static Integer index=1;
    boolean ok=false;
    public  static SortedSet<String> listaAnomalii=new TreeSet<>();
    public boolean VerificareScriere(String s1,String s2)
    {
        int ok=0;

        List<String> bufs1= new ArrayList<>(Arrays.asList(s1.trim().split(" ").clone()));
        bufs1.removeAll(Arrays.asList("", null));
        s2=s2.replace("Grupa","").replace(",Grupa","").replace(", Grupa","").replace("->","").replace("  "," ").replace("  "," ").replace("    "," ").replace(" , "," ");
        List<String> bufs2= new ArrayList<>(Arrays.asList(s2.trim().split(", |,| - |- |-|\\s").clone()));
        bufs2.removeAll(Arrays.asList("", null));
        bufs1=Filtrare.InlocuireDiacritice(bufs1);
        bufs2=Filtrare.InlocuireDiacritice(bufs2);
        for (String str1:bufs1)
        {
            for(String str2:bufs2)
            {
                if(CautaStudent.calculate(str1,str2)<2)
                {
                    ok++;
                }
                if(ok>=2)
                {
                    return true;
                }
            }

        }
        return false;
    }

    public void GasireStudent(ArrayList<Student> studenti, String student, String DacaAScrisCeva, String dupaNume_Prenume, String dupaNume_Prenume_semigrupa, String dupaUnString, boolean isCurs, String inFile, ConfigDate date, String grupeCautate, String grupeAcceptate, String cauta) {
       String[] bufStudent=student.split(":");
       if(DacaAScrisCeva.equals("true"))
       {
           GasesteStudentDupaNume_Prenume(studenti, student, isCurs, inFile, date,0);
       }
        if (dupaUnString.equals("true")) {
            GasesteDupaString(studenti,student,isCurs,inFile,date,cauta);
        }
        if(VerificareScriere(bufStudent[0],bufStudent[1])) {
            if (dupaNume_Prenume.equals("true")) {
                GasesteStudentDupaNume_Prenume(studenti, student, isCurs, inFile, date,0);
            } else if (dupaNume_Prenume_semigrupa.equals("true")) {
                GasesteStudentDupaNume_Prenume_semigrupa(studenti, student, isCurs, inFile, date, grupeAcceptate, grupeCautate);
            }
       }
    }

    public boolean GasesteStudentDupaNume_Prenume(ArrayList<Student> studenti, String student, boolean isCurs, String inFile, ConfigDate date, int caz) {

        List<String> bufStud=prelucrareString(student,caz);

        for(Student stud:studenti)
        {
                for(String nume:bufStud)
                {
                    if(CautaStudent.calculate(stud.getNume(),nume)<2)
                    {
                        if (stud.isPrenumeDublu()) {
                            for (String prenume : bufStud) {
                                if (CautaStudent.calculate(stud.getPrenumeMultiplu().get(0), prenume) < 2
                                        || CautaStudent.calculate(stud.getPrenumeMultiplu().get(1), prenume) < 2) {
                                    ok=adaugaPrezenta(isCurs,inFile,stud,date);
                                    return true;
                                }
                            }
                        } else {
                            for (String prenume : bufStud) {
                                if (CautaStudent.calculate(stud.getPrenume(), prenume) < 2) {

                                    ok=adaugaPrezenta(isCurs,inFile,stud,date);
                                    return true;
                                }
                            }
                        }
                }
            }
        }
        String calea=inFile.substring(inFile.lastIndexOf("\\")+1);
        String mesaj= "\n"+bufStud +"->"+calea;
        listaAnomalii.add(mesaj);
        return false;
    }

    public void GasesteStudentDupaNume_Prenume_semigrupa(ArrayList<Student> studenti, String student, boolean isCurs, String inFile, ConfigDate date, String grupaAcceptata, String grupaCautata) {


        List<String> bufStud=prelucrareString(student,1);


        if(student.contains(grupaCautata)) {

            GasesteStudentDupaNume_Prenume(studenti,student,isCurs,inFile,date,1);

        }

        if((!ok)&&(!student.contains(grupaCautata)))
        {
            String[] bufAcceptate=grupaAcceptata.split(",");
            String[] buffer=student.split(":");
            //System.out.println(buffer[0]);
            String[] bufPrenume=buffer[0].split(" ");
            for(String grupeA:bufAcceptate)
            {
                //System.out.println(grupeA);
                if(student.contains(grupeA))
                {
                    /*List<String> bufNewStud=prelucrareString(student,0);
                    bufNewStud =Filtrare.InlocuireDiacritice(bufNewStud);*/
                    List<String> bufNewStud;//=prelucrareString(student,0);
                    bufNewStud =Filtrare.InlocuireDiacritice(bufStud);
                    var grupa= "";
                    if(!GasesteStudentDupaNume_Prenume(studenti,student,isCurs,inFile,date,0)) {
                        for(String s:bufNewStud)
                        {
                            if(s.contains(grupeA))
                            {
                                grupa=s;
                            }
                        }
                        //Student newStudent = new Student(bufNewStud.get(0).toUpperCase(), bufNewStud.get(1).toUpperCase(), grupeA + "x", "?", index.toString(), false);
                        Student newStudent = new Student(bufNewStud.get(0).toUpperCase(), bufNewStud.get(1).toUpperCase(), bufNewStud.get(bufNewStud.indexOf(grupa)).toUpperCase(), "?", index.toString(), false);
                        //System.out.println(newStudent);
                        if(bufPrenume.length>2) {
                            int marimePrenume = 1;
                            int index=2;
                            while (bufPrenume.length - 1 > marimePrenume) {
                                newStudent.setPrenume(newStudent.getPrenume()+"-"+bufNewStud.get(index).toUpperCase());
                                index++;
                                marimePrenume++;
                            }
                            newStudent.setPrenumeDublu(true);
                        }
                        //System.out.println(newStudent);
                        index++;
                        /*String[] buf2 = inFile.split("_");
                        String bufLab = buf2[buf2.length - 2];
                        Date dateLab = new SimpleDateFormat("ddMMyyyy").parse(bufLab);
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTime(dateLab);

                        //asta merge numai pentru laborator o sa fac sa mearga si pentru curs (poate nu)
                        for(int i=0;i<date.dateConfigPrelucrate.size()-1;i++)
                        {
                            if((date.dateConfigPrelucrate.get(i).compareTo(cal)<0 && date.dateConfigPrelucrate.get(i+1).compareTo(cal)>0) || date.dateConfigPrelucrate.get(i).compareTo(cal)==0)
                            {
                                //stud.setPrezCurs(date.getPrezenteCurs().indexOf(cal));
                                newStudent.setPrezCurs(i);

                            }
                            if(date.dateConfigPrelucrate.get(i+1).compareTo(cal)==0)
                            {
                                newStudent.setPrezCurs(i+1);

                            }
                        }*/

                        studenti.add(newStudent);
                        adaugaPrezenta(isCurs,inFile,newStudent,date);
                        System.out.println(newStudent);
                        System.out.println();
                    }
                }
            }
        }
        ok=false;
    }

    public void GasesteDupaString(ArrayList<Student> studenti, String student, boolean isCurs, String inFile, ConfigDate date, String c) {
        if(student.contains(c))
        {
            GasesteStudentDupaNume_Prenume(studenti,student,isCurs,inFile,date,0);
        }
    }

    public boolean adaugaPrezenta(boolean isCurs, String inFile, Student stud, ConfigDate date) {

        try {
            /*for(int i=0;i<date.dateConfigPrelucrate.size();i++)
            {
                System.out.println(date.dateConfigPrelucrate.get(i).toZonedDateTime());
            }*/
            String substring = inFile.substring(inFile.lastIndexOf("_") + 1, inFile.lastIndexOf("."));
            //substring pentru data in caz de curs sau semigrupa in caz de laborator
            if (isCurs) {

                //asta poate fi facuta ca o functie noua
                Date dateCurs = new SimpleDateFormat("ddMMyyyy").parse(substring);
                //System.out.println(dateCurs);
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(dateCurs);

                for(int i=0;i<date.dateConfigPrelucrate.size()-1;i++)
                {
                    if((date.dateConfigPrelucrate.get(i).compareTo(cal)<0 && date.dateConfigPrelucrate.get(i+1).compareTo(cal)>0)  || date.dateConfigPrelucrate.get(i).compareTo(cal)==0)
                    {
                        //stud.setPrezCurs(date.getPrezenteCurs().indexOf(cal));
                        stud.setPrezCurs(i);
                        System.out.println(stud);
                        System.out.println();
                        return true;
                    }
                    if(date.dateConfigPrelucrate.get(i+1).compareTo(cal)==0)
                    {
                        stud.setPrezCurs(i+1);
                        System.out.println(stud);
                        System.out.println();
                        return true;
                    }
                }


            } else {

                String[] buf2 = inFile.split("_");
                String bufLab = buf2[buf2.length - 2];
                Date dateLab = new SimpleDateFormat("ddMMyyyy").parse(bufLab);
                //System.out.println(dateLab);
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(dateLab);


                    //stud.setPrezLab(date.getPrezenteLaborator().get(stud.getSemigrupa().toUpperCase()).indexOf(dateLab));

                    for(int i=0;i<date.dateConfigPrelucrate.size()-1;i++)
                    {
                        if((date.dateConfigPrelucrate.get(i).compareTo(cal)<0 && date.dateConfigPrelucrate.get(i+1).compareTo(cal)>0) || date.dateConfigPrelucrate.get(i).compareTo(cal)==0)
                        {
                            //stud.setPrezCurs(date.getPrezenteCurs().indexOf(cal));
                            stud.setPrezLab(i);
                            System.out.println(stud);
                            System.out.println();
                            return true;
                        }
                        if(date.dateConfigPrelucrate.get(i+1).compareTo(cal)==0)
                        {
                            stud.setPrezLab(i+1);
                            System.out.println(stud);
                            System.out.println();
                            return true;
                        }
                    }
                }

        }
        catch(ParseException e){
                System.out.println("Va rog formatati fisierele .sbv corect, conform documentatiei!!!!");
                return false;
            }
        System.out.println("aici a returnat false");
            return false;

        }

        public List<String> prelucrareString(String student,int caz)
        {
            student=student.replace("Grupa","").replace(",Grupa","").replace(", Grupa","").replace("->","").replace("  "," ").replace("  "," ").replace("    "," ").replace(" , "," "); //ar trebui sa mearga
            String[] studentFata=student.split(":");

            //cod bun numai ca pentru si partea cu grupam, avand in vedere ca nu am nevoie sa mai verific ce o scris dupa
            //rezolvat cred

            //student=student.replace("Grupa","").replace(",Grupa","").replace(", Grupa","").replace("->","").replace("  "," ").replace("  "," ").replace("    "," "); //ar trebui sa mearga
            List<String> bufStud= new ArrayList<>(Arrays.asList(studentFata[caz].trim().split(", |,| - |- |-|\\s").clone()));
            bufStud.removeAll(Arrays.asList("", null));
            bufStud = Filtrare.InlocuireDiacritice(bufStud);
            return bufStud;
        }




}
