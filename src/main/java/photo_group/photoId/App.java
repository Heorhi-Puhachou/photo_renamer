package photo_group.photoId;

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
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String folderName = "D:\\1";

		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				File file = new File(folderName + "\\" + listOfFiles[i].getName());
				Metadata metadata = null;
				try {
					metadata = ImageMetadataReader.readMetadata(file);

					Date date = new Date(0);
					ExifSubIFDDirectory exif = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
					if (exif != null) {
						date = exif.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
						dateFormat.setTimeZone(TimeZone.getTimeZone("MSK"));

						if (date != null) {
							// ------------------------------------------------------------------------------------------
							File newFile = new File(
									folderName + "\\" + dateFormat.format(date) + ".JPG");
							// ------------------------------------------------------------------------------------------

							System.out.println(listOfFiles[i].getName());
							if (file.renameTo(newFile)) {
								System.out.println("Файл переименован успешно");
							} else {
								System.out.println("Файл не был переименован");
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
