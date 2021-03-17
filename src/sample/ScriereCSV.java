package sample;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ScriereCSV {
    public static boolean ScreieFisier(String outFile, List<Student> studList) throws IOException {
        FileWriter writer=new FileWriter(outFile);
        StringBuilder buf=new StringBuilder();
        buf.append(CitireCSV.primaLinie).append("\n");
        for(Student s:studList)
        {
            buf.append(s.creareString()).append("\n");
        }
        writer.write(buf.toString());
        writer.flush();
        writer.close();
        return true;
    }
}
