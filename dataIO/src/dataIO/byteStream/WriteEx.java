package dataIO.byteStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class WriteEx
{

	public static void main(String[] args)
	{
		try {
			OutputStream os = new FileOutputStream("C:/test/test3.db");
//			byte a = 10;
//			byte b = 20;
//			byte c = 30;
//			
//			os.write(a);
//			os.write(b);
//			os.write(c);
			
			byte[] array = {10,20,30,40,50};
			os.write(array,1,3);
			
			os.flush();
			os.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}

	}

}
