package dataIO.javanet;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerEx
{
	private static ServerSocket serverSocket = null;

	public static void main(String[] args) throws Exception
	{
		System.out.println("====================");
		System.out.println("서버를 종료하면 q 또는 Q 입력하고 Enter 키를 입력하세요");
		System.out.println("====================");

		startServer();
		Scanner sc = new Scanner(System.in);
		while(true) {
			String key = sc.nextLine();
			if(key.toLowerCase().equals("q")) break;
			
			
		}
		sc.close();
		stopServer();
	}
	
	//스레드 정의
	public static void startServer() {
		Thread thread = new Thread() {
			public void run() {
				try {
					//Server socket 생성 및 바인딩
					serverSocket = new ServerSocket(50001);
					System.out.println("[서버 시작]");
					//연결 수락
					while(true) {
						System.out.println("\n[서버]연결 요청을 기다림");
						Socket socket = serverSocket.accept();
						
						//연결된 클라이언트 정보를 얻기
						
						InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
						System.out.println("[서버]"+isa.getHostName()+"의 연결 요청을 수락하였습니다.");
						//연결 끊기
						socket.close();
						System.out.println("[서버]"+isa.getHostName()+"의 연결을 끊음");
					}
					
					
					
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		};
		thread.start();
	}
	
	public static void stopServer() {
		try {
			serverSocket.close();
			System.out.println("[서버] 종료");
		}catch(Exception e) {
			
		}
		
	}

}
