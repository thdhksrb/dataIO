package dataIO.tcpProduct;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProductClient {
	//필드
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private Scanner scanner;
	
	//메소드: 서버 연결
	public  void start() throws IOException {
		//서버 연결하기
		socket = new Socket("220.76.51.182", 50001);
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
		System.out.println("[클라이언트] 서버에 연결됨");
		
		scanner = new Scanner(System.in);
		
		//상품 목록 보여주기
		list();
	}
	
	//메소드: 클라이언트 종료
	public void stop() {
		try {
			socket.close();
			scanner.close();
		} catch(Exception e) {}
		System.out.println("[클라이언트] 종료됨");
	}
	
	//메소드: 메뉴
	public void menu() throws IOException {
		System.out.println();
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("메뉴: 1.Create | 2.Update | 3.Delete | 4.Exit");
		System.out.print("선택: ");
		String menuNo = scanner.nextLine();
		System.out.println();
		
		switch(menuNo) {
			case "1" : create();break;
			case "2" : update();break;
			case "3" : delete();break;
			case "4" : exit();break;
		}
	}	
	public void list() throws IOException{	//상품 리스트
		System.out.println();
		System.out.println("[상품 목록]");
		System.out.println("-----------------------------------------------------------------------");
		System.out.printf("%-5s%-10s%-20s%-5s\n","no","name","price","stock");
		System.out.println("-----------------------------------------------------------------------");
		
		JSONObject request = new JSONObject();
		request.put("menu", 0);
		request.put("data", new JSONObject());
		dos.writeUTF(request.toString());
		dos.flush();

		JSONObject response = new JSONObject(dis.readUTF());
		if(response.getString("status").equals("success")) {
			JSONArray data = response.getJSONArray("data");
			for(int i=0; i<data.length(); i++) {
				JSONObject product = data.getJSONObject(i);
				System.out.printf("%-5d%-10s%-20d%-5d\n",product.getInt("no"),product.getString("name"),product.getInt("price"),product.getInt("stock"));
			}
		}
		menu();
	}	
	
	public void create() throws IOException {
		System.out.println("[상품 생성]");
		Product product = new Product();
		System.out.print("상품 이름: ");
		product.setName(scanner.nextLine());
		System.out.print("상품 가격: ");
		product.setPrice(Integer.parseInt(scanner.nextLine()));
		System.out.print("상품 재고: ");
		product.setStock(Integer.parseInt(scanner.nextLine()));
		

		JSONObject data = new JSONObject();	//생성 요청하기
		data.put("name", product.getName());
		data.put("price", product.getPrice());
		data.put("stock", product.getStock());
		JSONObject request = new JSONObject();
		request.put("menu", 1);
		request.put("data", data);
		
		dos.writeUTF(request.toString());
		dos.flush();
		

		JSONObject response = new JSONObject(dis.readUTF()); //받기
		if(response.getString("status").equals("success")) {
			list();
		}
	}

	public void update() throws IOException {
		System.out.println("[상품 수정]");
		Product product = new Product();
		System.out.print("상품 번호: ");
		product.setNo(Integer.parseInt(scanner.nextLine()));		
		System.out.print("이름 변경: ");
		product.setName(scanner.nextLine());
		System.out.print("가격 변경: ");
		product.setPrice(Integer.parseInt(scanner.nextLine()));
		System.out.print("재고 변경: ");
		product.setStock(Integer.parseInt(scanner.nextLine()));

		JSONObject data = new JSONObject(); //위에 생성부분과 같이 요청
		data.put("no", product.getNo());
		data.put("name", product.getName());
		data.put("price", product.getPrice());
		data.put("stock", product.getStock());		
		
		JSONObject request = new JSONObject();
		request.put("menu", 2);
		request.put("data", data);
		
		dos.writeUTF(request.toString());
		dos.flush();
		
		JSONObject response = new JSONObject(dis.readUTF());
		if(response.getString("status").equals("success")) {
			list();
		}		
	}
	
	public void delete() throws IOException {
		System.out.println("[상품 삭제]");
		System.out.print("상품 번호: ");
		int no = Integer.parseInt(scanner.nextLine());
		
		JSONObject data = new JSONObject();
		data.put("no", no);
		
		JSONObject request = new JSONObject();
		request.put("menu", 3);
		request.put("data", data);
		
		dos.writeUTF(request.toString());
		dos.flush();	
		JSONObject response = new JSONObject(dis.readUTF());
		if(response.getString("status").equals("success")) {
			list();
		}	
	}
	

	public void exit() {
		stop();
	}

	public static void main(String[] args) {		
		ProductClient productClient = new ProductClient();
		try {
			productClient.start();
		} catch(IOException e) {
			System.out.println(e.getMessage());
			productClient.stop();
		}
	}
}



