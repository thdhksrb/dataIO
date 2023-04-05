package dataIO.javanet;

import java.net.InetAddress;

public class InetAddressEx
{

	public static void main(String[] args)
	{
		try {
			InetAddress local = InetAddress.getLocalHost();
			System.out.println("내 컴퓨터IP주소: "+local.getHostAddress());
			InetAddress[] Iadd = InetAddress.getAllByName("www.naver.com");
			for(InetAddress remote:Iadd) {
				System.out.println(remote.getHostAddress());
			}
			
		}catch(Exception e) {
			
		}

	}

}
