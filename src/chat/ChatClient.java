package chat;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient implements ActionListener {
	private static final String SERVER_ADDRESS = "192.168.1.5";
	private static final int SERVER_PORT = 9090;
	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	private Scanner scanner = null;
	private Socket socket = null;
	private BufferedReader bufferedReader = null;
	private PrintWriter printWriter = null;
	

	
	public static void main(String[] args) {
/*		Scanner scanner = null;
		System.out.print("닉네임>>");
		String name = scanner.nextLine();*/
		new ChatClient().connect();
			
		
}

	public ChatClient() {
		frame = new Frame();
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);
		

		// Button
		buttonSend.addActionListener(this);
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);

		// Textfield
		textField.setColumns(80);
		textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if (keyCode == KeyEvent.VK_ENTER) {
					actionPerformed(null);
				}
			}
		});

		// Pannel
		pannel.setBackground(Color.LIGHT_GRAY);
		pannel.add(textField);
		pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// TextArea
		textArea.setEditable(false);
		frame.add(BorderLayout.CENTER, textArea);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
		frame.pack();
	}




	 public void connect() {
		 try {

				// 2. socket 생성
				socket = new Socket();

				// 3. 연결
				socket.connect( new InetSocketAddress( SERVER_ADDRESS, SERVER_PORT ) );

				// 4. reader/ writer 생성
				bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream(), StandardCharsets.UTF_8 ) );
				printWriter = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), StandardCharsets.UTF_8 ), true );

				// 5. join 프로토콜
				
				printWriter.println("join:" + "닉네임 넣어야되");
				printWriter.flush();
				bufferedReader.readLine();
				//System.out.println( data );
				
				// 6. ChatClientRecevieThread 시작
				Thread thread = new ChatClientReceiveThread(bufferedReader);
				thread.start();
				// 7. 키보드 입력 처리
				while (true) {
					//System.out.print(">>");
					String input = scanner.nextLine();

					if ("quit".equals(input) == true) {
						// 8. quit 프로토콜 처리
						printWriter.println("quit");
						printWriter.flush();
						break;
					} else {
						// 9. 메시지 처리
						printWriter.println( "message:" + input );
						// socket.getOutputStream().write( ("message:" + input + "\r\n").getBytes( "UTF-8" )  );
						// socket.getOutputStream().flush();
						printWriter.flush();
					}
				}
	   }catch (Exception ex) {
			log("error:" + ex);
		} finally {
			// 10. 자원정리
			try {
				if( scanner != null ) {
					scanner.close();
				}
				if( bufferedReader != null ) {
					bufferedReader.close();
				}
				if( printWriter != null ) {
					printWriter.close();
				}
				if( socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch( IOException ex ) {
				log( "error:" + ex );
			}
		}
	 }
	@Override
	public void actionPerformed(ActionEvent e) {
		String message = textField.getText();

		textArea.append( "닉네임 넣어야되"+": "+ message );
		textArea.append("\n");

		textField.setText("");
		textField.requestFocus();
		 
	}

	public static void log(String log) {
		System.out.println("[chat-client] " + log);
	}
}
