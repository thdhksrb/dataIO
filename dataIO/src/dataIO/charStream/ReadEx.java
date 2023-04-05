package dataIO.charStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ReadEx
{

	public static void main(String[] args)
	{
		try
		{
			InputStream is = new FileInputStream("C:/test/test.txt");
			//1. test.txt 파일을 대상으로 문자 하나씩 읽기
//			while(true) {
//				int data = is.read();
//				if(data == -1) {
//					break;
//				}
//				System.out.println((char)data);
//
//			}
//			is.close();
			//2. test.txt 파일을 대상으로 문자 배열로 읽기
			byte[] data = new byte[100];
			
			while(true) {
				int num = is.read(data);
				if(num==-1)break;
				
				for(int i=0;i<num;i++) {
					System.out.println((char)data[i]);
				}
			}
			
			is.close();
			
		} catch (FileNotFoundException fe)
		{
			fe.printStackTrace();
		}catch(IOException ie) {
			ie.printStackTrace();
		}
			
		

	}

}
