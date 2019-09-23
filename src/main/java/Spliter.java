import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floor;
import static java.lang.Math.log;

public class Spliter {
    private File inputFile;
    private String baseFileName;
    private boolean d;
    private List<String> text;
    //String outputLocation = "output\\";

    public Spliter(File inputFile, String baseFileName, boolean d){
        this.inputFile = inputFile;
        this.baseFileName = baseFileName;
        this.d = d;
        text = FileManager.readFile(inputFile);
    }

    private  String getStrId(int n){
        char[] buf = new char[(int) floor(log(25 * (n + 1)) / log(26))];
        for (int i = buf.length - 1; i >= 0; i--) {
            n--;
            buf[i] = (char) ('A' + n % 26);
            n /= 26;
        }
        return new String(buf);
    }

    public void splitByLines(int lNum){
        int count = 0;
        File output;
        String name;
        int intId = 1;
        if (d) {
            name = baseFileName+intId;
        } else {
            name = baseFileName+getStrId(intId);
        }
        output = new File( name +".txt");
        List<String> list = new ArrayList<>();
        for (String string : text){
            count++;
            if (count == lNum) {
                FileManager.writeToFile(list, output);
                list = new ArrayList<>();
                count = 0;
                intId++;
                if (d) {
                    name = baseFileName + intId;
                } else {
                    name = baseFileName + getStrId(intId);
                }
                output = new File(name+".txt");
            } else {
                list.add(string+"\n");
            }
        }
    }

    public void splitByCharacters(int cNum){
        int count = 0;
        File output;
        String name;
        int intId = 1;
        if (d) {
            name = baseFileName+intId;
        } else {
            name = baseFileName+getStrId(intId);
        }
        output = new File(name +".txt");
        List<String> list = new ArrayList<>();
        for (String string : text){
            for (Character ch : string.toCharArray()) {
                count++;
                if (count == cNum+1) {
                    FileManager.writeToFile(list, output);
                    list = new ArrayList<>();
                    count = 0;
                    intId++;                    if (d) {
                        name = baseFileName + intId;
                    } else {
                        name = baseFileName + getStrId(intId);
                    }
                    output = new File(name + ".txt");
                } else {
                    list.add(ch.toString());
                }
            }
            list.add("\n");
        }
    }

    public void splitByFilesCount(int nNum){
        splitByLines(text.size()/nNum);
    }

}
