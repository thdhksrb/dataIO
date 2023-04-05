package dataIO.byteStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyEx
{

	public static void main(String[] args) throws Exception
	{
		String ofn = "C:/test/pic.png";             //원본파일이름
		String tfn = "C:/test/copyPic2.png";         //copy파일이름

		InputStream is = new FileInputStream(ofn);
		OutputStream os = new FileOutputStream(tfn);
		
//		byte[] data = new byte[1024]; 
//		while(true) {
//			int num = is.read(data);
//			if(num == -1)break;
//			
//			os.write(data,0,num);
//		}
		
		is.transferTo(os); //위에 여섯줄과 같은 뜻 java9부터
		os.flush();
		os.close();
		is.close();
		System.out.println("복사 완료");
		
		
		
	}

}
