package dataIO.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileEx
{

	public static void main(String[] args) throws Exception
	{
		File dir = new File("C:/test/image");
		File file1 = new File("C:/test/file1.txt");
		File file2 = new File("C:/test/file2.txt");
		File file3 = new File("C:/test/file3.txt");
		

		if(!(dir.exists())){
			dir.mkdirs();
		}
		if(!(file1.exists())){
			file1.createNewFile();
		}
		if(!(file2.exists())){
			file2.createNewFile();
		}
		if(!(file3.exists())){
			file3.createNewFile();
		}
		
		File test = new File("C:/test");
		File[] contents = test.listFiles();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a HH:mm");
		for(File file : contents) {
			System.out.printf("%-25s",sdf.format(new Date(file.lastModified())));
			if(file.isDirectory()) {
				System.out.printf("%-10s%-20s","<DIR>",file.getName());
			}else {
				System.out.printf("%-10s%-20s",file.length(),file.getName());
			}
			System.out.println();
		}

	}

}
