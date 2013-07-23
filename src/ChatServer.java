import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;
/**
 * @author his86918
 *
 */
/**
 * @author his86918
 *
 */
/**
 * @author his86918
 *
 */
public class ChatServer {
	
	List<ClientThread> clients = new  ArrayList<ClientThread>();
	ServerSocket ss = null;	
	boolean bServerSocket = false;
	
	public static void main(String[] args) {
		new ChatServer().start();
	}
	
	public void start(){
		try {
			ss = new ServerSocket(8899);
			bServerSocket = true;
			while (bServerSocket) {
				Socket s = ss.accept();
				System.out.println("a client come in");
				ClientThread client = new ClientThread(s);
				Thread tClient = new Thread(client);
				tClient.start();
				clients.add(client);
			}
		} catch (BindException e) {
			System.out.println("The port is in use");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	class ClientThread implements Runnable {
		
		Socket sClient = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		boolean bSocket = false;
		
		ClientThread(Socket s){
			if(s != null){
				sClient = s;
				bSocket = true;
			}
			
		}
		public void run() {
			try {
				dis = new DataInputStream(sClient.getInputStream());
				dos = new DataOutputStream(sClient.getOutputStream());
			}catch(IOException e){
				e.printStackTrace();
			}
			try{
				while (bSocket) {
					String str = dis.readUTF();
					System.out.println(str);
					sent(str);
				}
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("Client closed!");
			}finally{
				try {
					dis.close();
					dos.close();
					sClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		
		public void sent(String strSend) {
			try {
				for(int i=0; i<clients.size(); i++){
					
					DataOutputStream dosOtherClients = clients.get(i).dos;
					dosOtherClients.writeUTF(strSend);
					dosOtherClients.flush();
					System.out.println(dosOtherClients);
				}
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

