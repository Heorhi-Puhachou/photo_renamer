package by.heorhi.puhachou;;

import org.junit.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.s
 */
public class AppTest
{
    @Test
    public void getAllUsers() throws IOException {



        ClassLoader classLoader = getClass().getClassLoader();
        File oldFile = new File("src/test/resources/photos/0.JPG");
        File file = new File("src/test/resources/photos/0.JPG");

        String parent = file.getParent();

        File newFile = new File("src/test/resources/photos/2015.12.19_11.13.32.JPG");

        File folder = new File(parent);
        File[] listOfFiles = folder.listFiles();

        boolean flag = false;

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() &&  listOfFiles[i].getName().contains("2015.12.19_11.13.32.JPG")) {
               flag=true;
            }
        }

        Assert.assertEquals(flag, false);



        App.renameFile(file);
        listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() &&  listOfFiles[i].getName().contains("2015.12.19_11.13.32.JPG")) {
                flag=true;
            }
        }

        Assert.assertEquals(flag, true);

        newFile.renameTo(oldFile);
    }
}
