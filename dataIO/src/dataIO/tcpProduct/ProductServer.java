package dataIO.tcpProduct;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProductServer {
	//필드
	private ServerSocket serverSocket;
	private ExecutorService threadPool;
	private Vector<Product> products;		//백터= 동화되어있는 Arraylist / 하지만 Arraylist보단 느리다.
	private int sequenceNo; 			// 넘버 체크를 위한 시퀀스

	//메소드: 서버 시작
	public void start() throws IOException {
		//products = new ArrayList<>();
		products = new Vector<Product>();//동기화 되어 있는 ArrayList 이를 통해 여러 클라이언트가 동시에 접근해도 안전하게 요청 가능
		serverSocket = new ServerSocket(50001);
		threadPool = Executors.newFixedThreadPool(100);

		
		System.out.println( "[서버] 시작됨");	
		
		while(true) {
			Socket socket = serverSocket.accept();
			SocketClient sc = new SocketClient(socket);
		}
	}
	public void stop() {// 서버 종료
		try {
			serverSocket.close();
			threadPool.shutdownNow();
			System.out.println( "[서버] 종료됨 ");
		} catch (IOException e1) {}
	}		
	public class SocketClient {//중첩 클래스 선언 및 요청 처리
		private Socket socket;
		private DataInputStream dis;
		private DataOutputStream dos;

		public SocketClient(Socket socket) {
			try {
				this.socket = socket;
				this.dis = new DataInputStream(socket.getInputStream());
				this.dos = new DataOutputStream(socket.getOutputStream());
				receive();
			} catch(IOException e) {
				close();
			}
		}
		
		public void receive() {
			threadPool.execute(() -> {
				try {
					while(true) {
						String receiveJson = dis.readUTF();		
						JSONObject request = new JSONObject(receiveJson);
						int menu = request.getInt("menu");
						switch(menu) {
							case 0 : list(request);break;
							case 1 : create(request);break;
							case 2 : update(request);break;
							case 3 : delete(request);break;
						}
					}
				} catch(IOException e) {
					close();
				}
			});
		}
		
		public void list(JSONObject request) throws IOException {
			//응답 보내기
			JSONArray data = new JSONArray();
			for(Product p : products) {
				JSONObject product = new JSONObject();
				product.put("no", p.getNo());
				product.put("name", p.getName());
				product.put("price", p.getPrice());
				product.put("stock", p.getStock());
				data.put(product);
			}			
			
			JSONObject response = new JSONObject();
			response.put("status", "success");
			response.put("data", data);
			dos.writeUTF(response.toString());
			dos.flush();
		}
		
		public void create(JSONObject request) throws IOException {

			JSONObject data = request.getJSONObject("data");
			Product product = new Product();
			product.setNo(++sequenceNo);
			product.setName(data.getString("name"));
			product.setPrice(data.getInt("price"));
			product.setStock(data.getInt("stock"));
			products.add(product);
			
			JSONObject response = new JSONObject();
			response.put("status", "success");
			response.put("data", new JSONObject());
			dos.writeUTF(response.toString());
			dos.flush();
		}
		
		public void update(JSONObject request) throws IOException {

			JSONObject data = request.getJSONObject("data");
			int no = data.getInt("no");
			for(int i=0; i<products.size(); i++) {
				Product product = products.get(i);
				if(product.getNo() == no) {
					product.setName(data.getString("name"));
					product.setPrice(data.getInt("price"));
					product.setStock(data.getInt("stock"));
				}
			}


			JSONObject response = new JSONObject();
			response.put("status", "success");
			response.put("data", new JSONObject());
			dos.writeUTF(response.toString());
			dos.flush();
		}
		
		public void delete(JSONObject request) throws IOException {
			JSONObject data = request.getJSONObject("data");
			int no = data.getInt("no");
			products.removeIf(product -> product.getNo() == no); //removeIf,람다식을 이용해서 인자값 no 번째를 삭제
			/*
			JSONObject data = request.getJSONObject("data");
			int no = data.getInt("no");
			for(int i=0; i<products.size(); i++) {
				Product product = products.get(i);
				if(product.getNo() == no) {
					products.remove(no);
				}
			}*/ //오류코드
			
			JSONObject response = new JSONObject();
			response.put("status", "success");
			response.put("data", new JSONObject());
			dos.writeUTF(response.toString());
			dos.flush();
		}
		
		//메소드: 연결 종료
		public void close() {
			try { 
				socket.close();
			} catch(Exception e) {}
		}
	}	


	//메소드: 메인
	public static void main(String[] args) {
		ProductServer productServer = new ProductServer();
		try {
			productServer.start();
		} catch(IOException e) {
			System.out.println(e.getMessage());
			productServer.stop();
		}
	}
}




