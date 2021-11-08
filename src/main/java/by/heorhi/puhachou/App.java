package by.heorhi.puhachou;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class App {

    private static final String PHOTO_FOLDER = "D:\\1";

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String... args) throws IOException {
        File folder = new File(PHOTO_FOLDER);
        File[] listOfFiles = folder.listFiles();

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                File file = new File(PHOTO_FOLDER + "\\" + listOfFile.getName());
                try {
                    Metadata metadata = ImageMetadataReader.readMetadata(file);

                    ExifSubIFDDirectory exif = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                    if (exif != null) {
                        Date date = exif.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
                        dateFormat.setTimeZone(TimeZone.getTimeZone("MSK"));

                        if (date != null) {
                            // ------------------------------------------------------------------------------------------
                            File newFile = new File(
                                    PHOTO_FOLDER + "\\" + dateFormat.format(date) + ".JPG");
                            // ------------------------------------------------------------------------------------------

                            System.out.println(listOfFile.getName());
                            if (file.renameTo(newFile)) {
                                System.out.println("Пасьпяховае перайменаваньне");
                            } else {
                                System.out.println("Не атрымалася перайменаваць файл");
                            }
                        }
                    }
                } catch (ImageProcessingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
