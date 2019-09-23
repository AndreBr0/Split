import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;

public class Main {

    @Option(name = "-d")
    private boolean d;
    @Option(name = "-l")
    int lNum = -1;
    @Option(name = "-c")
    int cNum = -1;
    @Option(name = "-n")
    int nNum = -1;
    @Option(name = "-o")
    private String baseFileName = "output/x";

    @Argument(required = true)
    private String inputFileName;


    public void start(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }

        if (inputFileName == null || inputFileName.equals("")) throw new IllegalArgumentException("имя файла не задано");
        File inputFile = new File(inputFileName);
        if (!inputFile.exists()) throw new IllegalArgumentException("такого файла не существует");
        boolean l = lNum != -1;
        boolean c = cNum != -1;
        boolean n = nNum != -1;

        if (l && c || l && n || c && n) {
            throw new IllegalArgumentException("должен быть только один параметр для размера");
        }

        if (baseFileName.equals("-")) baseFileName = "output/"+inputFileName.replace(".txt", "");

        Spliter spliter = new Spliter(inputFile, baseFileName, d);

        if (l) spliter.splitByLines(lNum);
        else
        if (c) spliter.splitByCharacters(cNum);
        else
        if (n) spliter.splitByFilesCount(nNum);
        else {
            lNum = 10;
            spliter.splitByLines(lNum);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start(args);
    }

}
