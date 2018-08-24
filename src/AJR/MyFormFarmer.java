package AJR;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

public class MyFormFarmer {

    File curFile;
    Formatter form;
    Scanner scan;
    String TextCon;

    public MyFormFarmer(File x) {
        this.curFile = x;
    }

    public String getTextContent() {
        try {
            File pathOfCurFile = new File(curFile.getPath());
            scan = new Scanner(pathOfCurFile);
            while (scan.hasNextLine()) {
                TextCon += scan.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return TextCon;
    }

    public void setTextContent(String newContent) {
        try {
            form = new Formatter(curFile);
            form.format("%s", newContent);
            form.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
