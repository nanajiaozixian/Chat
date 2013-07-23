import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.io.*;

public class ChatClient extends Frame {
	
	Socket s = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	boolean bConnected = false;
	
	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}
	
	public void launchFrame(){
		setBounds(50, 50, 500, 500);
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
			
		this.addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent e){
				disconnect();
				System.exit(0);
			}
			
		});
		
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
		connect();
		readInput();
		
	}
	
	
	
	public void connect(){
		try{
			s = new Socket("127.0.0.1", 8899);
			bConnected = true;
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
		}catch(IOException e){
			e.printStackTrace();
		}
	
	}
	
	public void disconnect(){
		try {
			bConnected = false;
			dos.close();
			dis.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
	}
	
	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String strInput = tfTxt.getText().trim();	
			//send data to server
			try{
				
				dos.writeUTF(strInput);
				dos.flush();
				tfTxt.setText("");
				
			}catch(IOException e_io){
				e_io.printStackTrace();
			}
		}
		
	}

	public void readInput() {
		ReadInputThread ri = new ReadInputThread();
		Thread riThread = new Thread(ri);
		riThread.start();
	}
	class ReadInputThread implements Runnable {

		@Override
		public void run() {
			try {
				
				while(bConnected){
					String str = dis.readUTF();		
					taContent.setText(taContent.getText() +str+"\n");
				}
			} catch(SocketException e){
				System.out.println("Log out! Bye!");
			}catch (IOException e1) {
			
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}
}
