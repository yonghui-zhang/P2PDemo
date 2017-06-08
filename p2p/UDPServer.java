package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
	public static void main(String[] args) throws IOException
	{
		System.out.println("服务器已经提起");
		/*********************服务器监听6000端口************************/
		DatagramSocket server = new DatagramSocket(8002);
		byte[] buf = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		String clientA_IP = "";
		String clientB_IP = "";
		int clientA_port = 0;
		int clientB_port = 0;
		InetAddress clientA_address = null;
		InetAddress clientB_address = null;
		while(true)
		{
			server.receive(packet);
			String requestMessage = new String(packet.getData(), 0, packet.getLength());
			System.out.println(requestMessage);
			//接收到clientA的请求信息
			if(requestMessage.contains("clientA"))
			{
				clientA_port = packet.getPort();
				clientA_address = packet.getAddress();
		    //    clientA_address = InetAddress.getByName(GetOutIP.getV4IP());
				System.out.println("clientA:" + clientA_address + ":" + clientA_port);
				clientA_IP = clientA_address.getHostAddress() + ":" + clientA_port;
			}
			//接收到clientB的请求信息
			if(requestMessage.contains("clientB"))
			{
				clientB_port = packet.getPort();
				clientB_address = packet.getAddress();
				System.out.println("clientB:" + clientB_address + ":" + clientB_port);
				clientB_IP = clientB_address.getHostAddress() + ":" + clientB_port;
			}
			//两个都接收到后分别A.B地址交换互发
			if(!clientA_IP.equals("") && !clientB_IP.equals(""))
			{
				sendIP(clientB_IP, clientA_port, clientA_address, server);
				sendIP(clientA_IP, clientB_port, clientB_address, server);
				clientA_IP = "";
				clientB_IP = "";
			}
			
		}
	}
	/*
	 * 服务器发送IP信息给另外的客户端
	 */
		private static void sendIP(String client_IP, int port, InetAddress address, DatagramSocket server)
		{
			byte[] sendBuf = client_IP.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
			try {
				server.send(sendPacket);
				System.out.println("IP地址消息发送成功");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
