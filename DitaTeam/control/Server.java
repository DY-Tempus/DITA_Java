package control;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import entity.Account;
import entity.Menu;
import entity.Protocol;
import entity.DataType;

public class Server {
	public static final int PORT = 8003;
	public ServerSocket server;
	public Vector<Client> clients;
	
	public AccountMgr accMgr;
	public CategoryMgr ctgMgr;
	public GuestMgr guestMgr;
	public MenuMgr menuMgr;
	public Order_detailMgr detailMgr;
	public OrderMgr orderMgr;
	

	public Server() {
		try {
			server = new ServerSocket(PORT);
			clients = new Vector<Client>();
			accMgr=new AccountMgr();
			ctgMgr=new CategoryMgr();
			guestMgr=new GuestMgr();
			menuMgr=new MenuMgr();
			detailMgr=new Order_detailMgr();
			orderMgr=new OrderMgr();
			
		} catch (Exception e) {
			System.err.println("Err in Server");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("server is open..");
		
		try {
			while(true) {
				Socket sock= server.accept();
				Client client = new Client(sock);
				client.start();
				clients.addElement(client);
			}
		}catch(Exception e) {
			System.out.println("err2");
			e.printStackTrace();
		}
	}//--생성자 끝
	
	//접속이 끊어진 client를 벡터에서 제거
	public void removeClient(Client c) {
		clients.remove(c);
	}
	
	//내부클래스 client
	class Client extends Thread{
		Socket sock;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		
		public Client(Socket sock) {
			this.sock=sock;
			try {
				oos=new ObjectOutputStream(sock.getOutputStream());
				ois=new ObjectInputStream(sock.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//--생성자 끝
		
		@Override
		public void run() {
			try {
				while(true) {
					DataType data=(DataType)ois.readObject();

					switch(data.protocol) {
					case Protocol.loginAcc:
						loginAcc(data);
						break;
					case Protocol.loginGuest:
						break;
					case Protocol.menulist:
						break;
					case Protocol.order:
						break;
					}
				}//--while
			}catch(Exception e){
				removeClient(this);
			}
		}//--run
		
		public void loginAcc(DataType data) {
			boolean bool=accMgr.selectAccount((Account)data.obj);
			if(bool) {//-로그인 성공
				try {
					oos.writeObject((Account)data.obj);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {//-로그인 실패
				
			}
		}
		
		public void loginGuest(DataType data) {
			
		}
		
		public void menulist(DataType data) {
			Vector<Menu> v=menuMgr.selectAllMenu();
			data.obj=v;
			try {
				oos.writeObject(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void order(DataType data) {
			
		}

		
	}//--client
	
	public static void main(String[] args) {
		new Server();
	}

}


