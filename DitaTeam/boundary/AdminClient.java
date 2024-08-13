package boundary;

import java.awt.BorderLayout;
import java.io.*;
import java.net.*;
import javax.swing.*;

import entity.DataType;
import entity.Order_detail;

public class AdminClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private JTextArea ta;
    private Socket socket;
    private ObjectInputStream in;

    public AdminClient() {
        // JFrame과 JTextArea 초기화
        JFrame frame = new JFrame("관리자 클라이언트");
        frame.setLayout(new BorderLayout()); // BorderLayout 설정
        
        ta = new JTextArea();
        ta.setEditable(false); // 텍스트 편집 불가 설정

        JScrollPane scrollPane = new JScrollPane(ta);
        frame.add(scrollPane, BorderLayout.CENTER); // JTextArea를 중앙에 배치
        
        frame.setSize(400, 300); // 프레임 크기 설정
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // 소켓 연결 및 알림 수신을 별도의 스레드에서 처리
        new Thread(this::startListening).start();
    }

    private void startListening() {
        try {
            // 서버와 연결
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new ObjectInputStream(socket.getInputStream());
            
            while (true) {
                try {
                    DataType dt = (DataType) in.readObject();
                    if (dt.obj instanceof Order_detail) {
                        Order_detail orderDetail = (Order_detail) dt.obj;
                        SwingUtilities.invokeLater(() -> {
                            ta.append("새 주문: " + orderDetail.getMenu_Name() + "\n");
                        });
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            ta.append("알 수 없는 데이터 수신\n");
                        });
                    }
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                    break; // 데이터 수신 중 오류 발생 시 루프 종료
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 리소스 정리
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // AdminClient 인스턴스 생성 및 GUI 초기화
        new AdminClient();
    }
}
