package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EchoServerReceiveThread extends Thread {
	private Socket socket;
	
	public EchoServerReceiveThread( Socket socket ){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			
			//4. 연결 성공
			InetSocketAddress remoteSocketAddress =
				(InetSocketAddress)socket.getRemoteSocketAddress();
			int remotePort = 
				remoteSocketAddress.getPort();
			String remoteAddress  =
				remoteSocketAddress.getAddress().getHostAddress();
			System.out.println( "[서버] 연결됨 " + remoteAddress + ":" + remotePort );
			
			
			//5. IOStream 받아오기
			BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream(), "utf-8") );
			PrintWriter pw = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), "utf-8"), true 	);					
			
			while( true ) {
				
				String message = br.readLine(); // block
				
				if( message == null ) {
					System.out.println( "[서버]연결 끊어짐(클라이언트가 정상적 종료)" );
					break;
				}
			
				System.out.println( "[서버] 메세지 받음:" + message );
				pw.println( message );
				//pw.print( message + "\n" );
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}
