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

    public static void main(String... args) {
        File folder = new File(PHOTO_FOLDER);

        Arrays.stream(folder.listFiles())
                .filter(File::isFile)
                .filter(App::hasExifAndDate)
                .forEach(App::rename);
    }

    public static ExifSubIFDDirectory getExif(File photo) {
        ExifSubIFDDirectory exif;
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(photo);
            exif = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        } catch (ImageProcessingException | IOException e) {
            exif = null;
        }
        return exif;
    }

    public static boolean hasExifAndDate(File photo) {
        return getExif(photo) != null && getExif(photo).getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL) != null;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("MSK"));
        return dateFormat.format(date);
    }

    public static void rename(File oldNamePhoto) {
        ExifSubIFDDirectory exif = getExif(oldNamePhoto);
        Date date = exif.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        File newNamePhoto = new File(PHOTO_FOLDER + "\\" + formatDate(date) + ".JPG");
        oldNamePhoto.renameTo(newNamePhoto);
    }
}
