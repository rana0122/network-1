package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static void main(String[] args) {
		try {
			//1.서버소켓 생성
			ServerSocket serverSocket = new ServerSocket();
			
			//2. 바인딩(소켓주소)
			serverSocket.bind( new InetSocketAddress( "192.168.1.30", 4000 ) );
		
			//3. accept(연결 요청 기다림)
			Socket socket = serverSocket.accept(); //block
			System.out.println( "[서버] 연결됨" );
			
			try {
				//4. 연결 성공
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
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
