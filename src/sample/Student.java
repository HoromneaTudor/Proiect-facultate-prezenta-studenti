package sample;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Student {
    private final String nume;
    private String prenume;
    private List<String> prenumeMultiplu;
    private final int[] prezCurs;
    private final int[] prezLab;
    private String semigrupa;
    private String initialeleTatalui;
    private String nrCSV;
    private boolean prenumeDublu;

    public Student(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
        prezCurs = new int[14];
        prezLab = new int[14];
    }

    public Student(String nume, String prenume, String semigrupa, String initialeleTatalui, String nrCSV, boolean prenumeDublu) {
        this.nume = nume;
        this.prenume = prenume;
        prezCurs = new int[14];
        prezLab = new int[14];
        this.semigrupa = semigrupa;
        this.initialeleTatalui = initialeleTatalui;
        this.nrCSV = nrCSV;
        this.prenumeDublu = prenumeDublu;
    }

    //pare corect
    public void addPrezenteCSV(String[] str)
    {
        for(int i=5;i<19;i++)
        {
            if(str[i].equals("")||str[i].equals("0"))
            {
                prezCurs[i-5]=0;
            }
            else if(str[i].equals("1"))
            {
                prezCurs[i-5]=1;
            }
        }
        for(int i=19;i<33;i++)
        {
            if(str[i].equals("")||str[i].equals("0"))
            {
                prezLab[i-19]=0;
            }
            else if(str[i].equals("1"))
            {
                prezLab[i-19]=1;
            }
        }
    }

    public void setPrezCurs(int i) {
        if(i>=0) {
            prezCurs[i]=1;
        }
    }

    public void setPrezLab(int i) {
        if(i>=0) {
            prezLab[i] = 1;
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", prenumeMultiplu=" + prenumeMultiplu +
                ", prezCurs=" + Arrays.toString(prezCurs) +
                ", prezLab=" + Arrays.toString(prezLab) +
                ", semigrupa='" + semigrupa + '\'' +
                ", initialeleTatalui='" + initialeleTatalui + '\'' +
                ", nrCSV='" + nrCSV + '\'' +
                ", prenumeDublu=" + prenumeDublu +
                '}';
    }

    public String creareString()
    {
        StringBuilder buf=new StringBuilder();
        buf.append(nrCSV).append(CitireCSV.delimitator).append(nume).append(CitireCSV.delimitator).append(initialeleTatalui)
                .append(CitireCSV.delimitator).append(prenume).append(CitireCSV.delimitator).append(semigrupa);
        for(int i=0;i<14;i++)
        {
            buf.append(CitireCSV.delimitator).append(prezCurs[i]);
        }
        for(int i=0;i<14;i++)
        {
            buf.append(CitireCSV.delimitator).append(prezLab[i]);
        }
        return buf.toString();
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenumeDublu(boolean prenumeDublu) {
        this.prenumeDublu = prenumeDublu;
        String[] buf=prenume.split(" - |- |-|\\s");
        prenumeMultiplu=new LinkedList<>();
        Collections.addAll(prenumeMultiplu, buf);
    }

    public boolean isPrenumeDublu() {
        return prenumeDublu;
    }

    public List<String> getPrenumeMultiplu() {
        return prenumeMultiplu;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

}
