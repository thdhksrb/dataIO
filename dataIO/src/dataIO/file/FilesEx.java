package dataIO.file;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesEx
{

	public static void main(String[] args) throws Exception
	{
		
		String data = "id : myload \npass : 1234";
		
		//Path 객체 생성
		Path path = Paths.get("C:/test/user.txt");
		
		//파일 생성 쓰기
		Files.writeString(path, data, Charset.forName("UTF-8"));
		
		//파일의 정보 얻기
		//파일의 유형
		System.out.println("파일의 유형: "+ Files.probeContentType(path));
		//파일의 크기
		System.out.println("파일의 크기: "+ Files.size(path)+ "bytes");
		//파일 읽기
		String content = Files.readString(path,Charset.forName("UTF-8"));
		
		System.out.println(content);
		
		
		
		

	}

}
