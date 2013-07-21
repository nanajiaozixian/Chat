import java.io.IOException;
import java.net.*;
import java.io.*;

public class ChatServer {
	
	public static void main(String[] args) {
		ServerSocket ss = null;
		Socket s = null;
		DataInputStream dis = null;
		boolean bServerSocket = false;
		try {
			ss = new ServerSocket(8888);
			bServerSocket = true;
			
		}catch(BindException e){
			System.out.println("The port is in use");
			System.exit(0);
		}catch (IOException e) {	
			e.printStackTrace();
		}
		
		try{
			while(bServerSocket){
				boolean bSocket = false;				
				s = ss.accept();
				bSocket = true;
System.out.println("a new client!");
				dis = new DataInputStream(s.getInputStream());
				while (bSocket) {
					String str = dis.readUTF();
					System.out.println(str);
				}
				
			}
		}catch(Exception e){
			System.out.println("Client closed!");
		}finally{
			try{
				dis.close();
				s.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}

	}

}
