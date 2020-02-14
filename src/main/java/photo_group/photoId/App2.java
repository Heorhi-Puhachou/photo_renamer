package photo_group.photoId;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class App2 {
	public static void main(String[] args) throws IOException {

		String folderName = "D:\\GR";

		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				File file = new File(folderName + "\\" + listOfFiles[i].getName());

				Path path = Paths.get(folderName + "\\" + listOfFiles[i].getName());
				
				double bytes = file.length();
				double kilobytes = (bytes / 1024);
				
				//System.out.println(file.getName() + " "+ kilobytes);
				
				int nameLength = file.getName().length();
				
				String nameWithoutFormat = file.getName().substring(0, nameLength-4);
				
				
				
				if(nameWithoutFormat.substring(nameLength-5).equals(")")){
					if(file.delete()){
						System.out.println(nameWithoutFormat);
					}
				
				}

			}
		}
	}
}
