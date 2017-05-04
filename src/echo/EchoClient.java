package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

	public static void main(String[] args) {
		Scanner scanner = new Scanner( System.in );
		Socket socket = null;
		
		try {
			//1. 소켓 생성
			socket = new Socket();
			
			//2. 서버 연결
			socket.connect( new InetSocketAddress( "192.168.1.30", 4000 ) );

			//3. IOStream 받아오기
			BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream(), "utf-8") );
			PrintWriter pw = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), "utf-8"), true 	);					
			
			while( true ) {
				System.out.print( ">>" );
				String message = scanner.nextLine();
				
				if( "exit".equals( message ) ) {
					break;
				}
				
				//메세지 보내기
				pw.println( message );
				
				//에코 메세지 읽어 오기
				String echoMessage = br.readLine();
				if( echoMessage == null ) {
					System.out.println( "[클라이언트] 연결이 끊어짐(서버가 연결을 끊음)" );
					break;
				}
				
				//출력
				System.out.println( "<<" + echoMessage );
			}
		} catch( IOException e ) {
			e.printStackTrace();
		} finally {
			scanner.close();
			try {
				if( socket != null && socket.isClosed() == false ){
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
