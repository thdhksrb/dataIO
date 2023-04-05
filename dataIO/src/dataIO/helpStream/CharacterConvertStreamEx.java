package dataIO.helpStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

//UTF-8 문자셋으로 파일에 문자를 저장하고 저장된 문자를 다시 읽는다.
public class CharacterConvertStreamEx
{

	public static void main(String[] args) throws Exception
	{
		write("문자변환 스트림을 사용합니다.");
		String data = read();
		System.out.println(data);
		

	}
	public static String read() throws Exception
	{
		InputStream is = new FileInputStream("C:/test/test1.txt");
		Reader reader = new InputStreamReader(is,"UTF-8");
		char[] data = new char[100];
		int num = reader.read(data);
		reader.close();
		String str = new String(data,0,num);
		return str;
	}
	public static void write(String str) throws Exception{
		OutputStream os = new FileOutputStream("C:/test/test1.txt");
		Writer writer = new OutputStreamWriter(os);
		writer.write(str);
		writer.flush();
		writer.close();
		
		
	}
	
	

}
