package dataIO.json;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

public class ChatServer {
	//필드
	ServerSocket serverSocket;                                            // 서버 소켓 생성
	ExecutorService threadPool = Executors.newFixedThreadPool(100);       // 스레드풀 100개 생성
	Map<String, SocketClient> chatRoom = Collections.synchronizedMap(new HashMap<>());  //동기화맵   메시지의 무결성을 위해 동기화

	//메소드: 서버 시작
	public void start() throws IOException {
		serverSocket = new ServerSocket(50001);	      //서버 소켓 생성
		System.out.println( "[서버] 시작됨");
		
		Thread thread = new Thread(() -> {
			try {
				while(true) {
					Socket socket = serverSocket.accept();               //연결 계속 수락
					SocketClient sc = new SocketClient(this, socket);    //소켓 클라이언트 객체 생성
				}
			} catch(IOException e) {
			}
		});
		thread.start();
	}
	//메소드: 클라이언트 연결시 SocketClient 생성 및 추가
	public void addSocketClient(SocketClient socketClient) {
		String key = socketClient.chatName + "@" + socketClient.clientIp;
		chatRoom.put(key, socketClient);                                       //chatRoom에 SocketClient 객체 추가
		System.out.println("입장: " + key);
		System.out.println("현재 채팅자 수: " + chatRoom.size() + "\n");
	}

	//메소드: 클라이언트 연결 종료시 SocketClient 제거
	public void removeSocketClient(SocketClient socketClient) {
		String key = socketClient.chatName + "@" + socketClient.clientIp;
		chatRoom.remove(key);                                                  //chatRoom에 SocketClient 객체 제거
		System.out.println("나감: " + key);
		System.out.println("현재 채팅자 수: " + chatRoom.size() + "\n");
	}		
	//메소드: 모든 클라이언트에게 메시지 보냄
	public void sendToAll(SocketClient sender, String message) {
		JSONObject root = new JSONObject();
		root.put("clientIp", sender.clientIp);
		root.put("chatName", sender.chatName);
		root.put("message", message);
		String json = root.toString();
		
		Collection<SocketClient> socketClients = chatRoom.values();
		for(SocketClient sc : socketClients) {
			if(sc == sender) continue;                                        //보낸 사람 제외하고 메세지 전송
			sc.send(json);
		}
	}	
	//메소드: 서버 종료
	public void stop() {
		try {
			serverSocket.close();                                            //서버 소켓닫기
			threadPool.shutdownNow();                                        //스레드풀 종료
			chatRoom.values().stream().forEach(sc -> sc.close());            //클라이언트 닫기
			System.out.println( "[서버] 종료됨 ");
		} catch (IOException e1) {}
	}		
	//메소드: 메인
	public static void main(String[] args) {	
		try {
			ChatServer chatServer = new ChatServer();
			chatServer.start();
			
			System.out.println("----------------------------------------------------");
			System.out.println("서버를 종료하려면 q를 입력하고 Enter.");
			System.out.println("----------------------------------------------------");
			
			Scanner scanner = new Scanner(System.in);
			while(true) {
				String key = scanner.nextLine();
				if(key.equals("q")) 	break;
			}
			scanner.close();
			chatServer.stop();
		} catch(IOException e) {
			System.out.println("[서버] " + e.getMessage());
		}
	}
}