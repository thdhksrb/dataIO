package dataIO.byteStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ReadEx
{

	public static void main(String[] args)
	{
		try {
			InputStream is = new FileInputStream("C:/test/test2.db");


			byte[] data = new byte[100];

			while(true) {
				int num = is.read(data);
				if(num == -1) {
					break;
				}
				for(int i=0;i<num;i++) {

					System.out.println(data[i]);
				}
			}
			is.close();

		}catch(FileNotFoundException fe) {
			fe.printStackTrace();
		}catch(IOException ie) {
			ie.printStackTrace();
		}

	}

}
