package sample;

import java.util.ArrayList;
import java.util.List;

public class Filtrare {
    public static List<String> InlocuireDiacritice(List<String> lst)
    {
        List<String> bufList=new ArrayList<>();
        for(String s:lst)
        {
            StringBuilder buf=new StringBuilder();
            for(Character c:s.toLowerCase().toCharArray())
            {
                switch (c) {
                    case 'ă', 'â' -> buf.append("a");
                    case 'î' -> buf.append("i");
                    case (char)351,(char)537 -> buf.append("s");    //cele doua S
                    //case (char)537 -> buf.append("s");
                    case 'ț', 'ţ' -> buf.append("t");
                    default -> buf.append(c);
                }
            }
            bufList.add(buf.toString());
        }
        return bufList;
    }
}
