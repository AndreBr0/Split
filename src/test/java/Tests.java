import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Tests {
    Spliter spliter;
    Main main;
    @BeforeEach
    public void before(){
        main = new Main();
        String outputName = "out";
        spliter = new Spliter(new File("input1.txt"), outputName, true);
    }

    @Test
    public void splitByLines(){
        String[] args1 = new String[]{"-d", "-l", "10", "-o", "-", "input1.txt"};
        main.start(args1);
        List<String> text1 = FileManager.readFile(new File("output/input11.txt"));
        List<String> expected = new ArrayList<>();
        expected.add("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        expected.add("ffffffffffffffffffffffffffffffff");
        expected.add("ffffffffffffffffffffffffffff");
        expected.add("ffffffffffffffffffffffffffffffffffffff");
        expected.add("ggggggggggggggggggggggggggggggg");
        expected.add("hhhhhhhhhhhhhhhhhhhhhhh");
        expected.add("jjjjjjjjjjjjjjjjjjjjjjjjj");
        expected.add("kkkkkkkkkkkkkkkkkkkkkkkk");
        expected.add("hhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        Assertions.assertEquals(expected, text1);
    }

    @Test
    public void splitByFilesCount(){
        String[] args1 = new String[]{"-n", "7", "-o", "output2/output", "input1.txt"};
        main.start(args1);
        List<String> text1 = FileManager.readFile(new File("output2/outputA.txt"));
        List<String> expected = new ArrayList<>();
        expected.add("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        expected.add("ffffffffffffffffffffffffffffffff");
        expected.add("ffffffffffffffffffffffffffff");
        expected.add("ffffffffffffffffffffffffffffffffffffff");
        expected.add("ggggggggggggggggggggggggggggggg");
        expected.add("hhhhhhhhhhhhhhhhhhhhhhh");
        expected.add("jjjjjjjjjjjjjjjjjjjjjjjjj");
        Assertions.assertEquals(expected, text1);

        File[] outputDir = new File("output2").listFiles();
        int count = 0;
        for (File file : outputDir){
            count++;
        }
        Assertions.assertEquals(7, count);
    }

    @Test
    public void splitByCharacters(){
        String[] args1 = new String[]{"-c", "60", "input1.txt"};
        main.start(args1);
        List<String> text1 = FileManager.readFile(new File("output/xA.txt"));
        List<String> expected = new ArrayList<>();
        expected.add("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        expected.add("fffffffffffffffffffffffffffff");
        Assertions.assertEquals(expected, text1);
    }

    @Test
    public void illegalComandInputTests(){
        final String[] args1 = new String[]{"-d", "-l", "10", "-c", "40", "input1.txt"};
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            main.start(args1);
        }, "должен быть только один параметр для размера");
        final String[] args2 = new String[]{"-d", "-n", "5", ""};
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            main.start(args2);
        }, "имя файла не задано");
        final String[] args3 = new String[]{"-d", "-n", "5", "404.txt"};
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            main.start(args3);
        }, "такого файла не существует");
    }

    @AfterEach
    public void clearOutputAfterEach(){
        File[] outputDir = new File("output").listFiles();
        if (outputDir == null) return;
        for (File file : outputDir){
            file.delete();
        }
        File[] outputDir2 = new File("output2").listFiles();
        if (outputDir2 == null) return;
        for (File file : outputDir2){
            file.delete();
        }
    }

}
