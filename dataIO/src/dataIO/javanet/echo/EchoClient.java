package dataIO.javanet.echo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class EchoClient
{

	public static void main(String[] args)
	{
		try {
			Socket socket = new Socket("localhost",50001);
			System.out.println("[클라이언트] 연결 성공");
			
			//데이터 보내기
			String data = "Hello World";
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(data);
			dos.flush();
			//데이터 받기
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			String returnData = dis.readUTF();
			System.out.println("다시 받은 데이터 : "+ returnData);
			
			
			
			
			
			socket.close();
			System.out.println("[클라이언트 연결 끊음]");
			
		}catch(Exception e) {
			
		}

	}

}
