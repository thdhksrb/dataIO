package dataIO.charStream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class WriteEx
{

	public static void main(String[] args)
	{
		try {
			// 문자 기반 출력 스트림 생성
			Writer writer = new FileWriter("C:/test/test.txt");
					
			//1 문자 출력
			char a = 'A';
			writer.write(a);
			
			char b = 'B';
			writer.write(b);
			
			//2 char 배열 출력
			char[] arr = {'C','D','E','F'};
			writer.write(arr);
			
			//3 문자열 출력
			writer.write("zzzz");
			
			//4 버퍼에 잔류하고 있는 문자들을 출력하고, 버퍼 비우기
			writer.flush();
			//5 출력 스트림 닫고, 해제
			writer.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
		

	}

}
