package dataIO.json;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONObject;

public class CreateJsonEx
{

	public static void main(String[] args) throws IOException
	{
		//JSON 객체 생성
		JSONObject root = new JSONObject();
		

		//속성 추가
		root.put("id","white0808");
		root.put("name","백설이");
		root.put("age",25);
		root.put("student",true);
		
		//객체 속성 추가
		JSONObject tel = new JSONObject();
		tel.put("home","02-232-5656");
		tel.put("mobile","010-4547-7878");
		root.put("tel",tel);
		
		//배열 속성 추가
		JSONArray skill = new JSONArray();
		skill.put("java");
		skill.put("cook");
		root.put("skill",skill);
		
		//JSON 열기
		String json = root.toString();
		
		//콘솔에 출력
		System.out.println(json);
		
		//파일로 저장
		Writer writer = new FileWriter("C:/test/member.json",Charset.forName("UTF-8"));
		writer.write(json);
		writer.flush();
		writer.close();

		

	}

}
