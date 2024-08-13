package control;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import entity.DataType;
import entity.Order_detail;
import entity.Protocol;

public class TableServer {
    private static final int PORT = 12345;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private static Object obj;
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("서버가 시작되었습니다. 포트: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        
        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                this.in = new ObjectInputStream(socket.getInputStream());        
                this.out = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
        	try {
        		   		
				DataType dt = (DataType) in.readObject();

				if(dt.protocol == Protocol.menulist) {
					Vector<Order_detail> sc = (Vector<Order_detail>) dt.obj;
					for (Order_detail order_detail : sc) {
						System.out.println(order_detail.getMenu_Name());
					}
					out.writeObject(dt);
					out.flush();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
        	
        }


    }
}
