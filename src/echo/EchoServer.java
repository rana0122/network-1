package echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private static final int PORT = 4000;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		try {
			//1.서버소켓 생성
			serverSocket = new ServerSocket();
			
			//2. 바인딩(소켓주소)
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhost = inetAddress.getHostAddress();
			serverSocket.bind( new InetSocketAddress( localhost, PORT ) );
			System.out.println( "[서버] 바인딩 " + localhost + ":" + PORT );
			
			while( true ) {
				//3. accept(연결 요청 기다림)
				Socket socket = serverSocket.accept(); //block
			
				Thread thread = new EchoServerReceiveThread( socket );
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if( serverSocket != null && 
					serverSocket.isClosed() == false ){
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}