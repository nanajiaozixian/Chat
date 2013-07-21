import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.io.*;

public class ChatClient extends Frame {
	
	Socket s = null;
	DataOutputStream dos = null;
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	
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
				System.exit(0);
			}
			
		});
		
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
		connect();
		
	}
	
	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String strInput = tfTxt.getText().trim();	
			//send data to server
			try{
				dos = new DataOutputStream(s.getOutputStream());
				dos.writeUTF(strInput);
				dos.flush();
				taContent.setText(strInput);
				tfTxt.setText("");
			}catch(IOException e_io){
				e_io.printStackTrace();
			}
		}
		
	}
	
	public void connect(){
		try{
			s = new Socket("127.0.0.1", 8888);
		}catch(IOException e){
			e.printStackTrace();
		}
	
	}
	
	public void disconnect(){
		try {
			dos.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}