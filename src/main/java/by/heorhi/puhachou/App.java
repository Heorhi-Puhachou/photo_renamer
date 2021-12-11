package by.heorhi.puhachou;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

public class App {

    private static final String PHOTO_FOLDER = "D:\\1";
    private static final String DESCRIPTION = "Охота на лис. Брест";

    public static void main(String... args) {
        File folder = new File(PHOTO_FOLDER);

        Arrays.stream(folder.listFiles())
                .filter(File::isFile)
                .filter(App::hasExifAndDate)
                .forEach(App::rename);
    }

    public static ExifSubIFDDirectory getExif(File photo) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(photo);
            return metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        } catch (ImageProcessingException | IOException e) {
            return null;
        }
    }

    public static boolean hasExifAndDate(File file) {
        return getExif(file) != null && getExif(file).getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL) != null;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("MSK"));
        return dateFormat.format(date);
    }

    public static void rename(File oldName) {
        ExifSubIFDDirectory exif = getExif(oldName);
        Date date = exif.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        File newName = new File(PHOTO_FOLDER
                + "\\"
                + formatDate(date)
                + DESCRIPTION
                + "."
                + getExtension(oldName));
        oldName.renameTo(newName);
    }

    public static String getExtension(File file) {
        return file.getName().substring(file.getName().lastIndexOf('.') + 1);
    }
}
