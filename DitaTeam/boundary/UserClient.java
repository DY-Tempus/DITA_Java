package boundary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.Order_detailMgr;
import entity.DataType;
import entity.Order_detail;

public class UserClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private Order_detail order = new Order_detail(); // 주문 상세 정보
    private DataType dt;
    public UserClient() {
        JFrame frame = new JFrame("사용자 클라이언트");
        JPanel panel = new JPanel();
        JButton button = new JButton("숫가락");
        JButton button2 = new JButton("장바구니");

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (order == null) {
                    JOptionPane.showMessageDialog(null, "장바구니에 음식을 담아주세요.");
                    return;
                }
                // 소켓 통신을 별도의 스레드에서 처리
                new Thread(() -> sendOrderDetails()).start();
            }
        });

        panel.add(button);
        panel.add(button2);
        frame.add(panel);

        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void sendOrderDetails() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            // 상세 주문 목록 조회 예시
            Order_detailMgr mgr = new Order_detailMgr();
            Vector<Order_detail> order = mgr.selectOrder_detail(3);

            dt = new DataType();
            dt.protocol = 2;
            dt.obj = order;
            
            out.writeObject(dt);
            out.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "서버와의 통신에 문제가 발생했습니다.");
        }
    }

    public static void main(String[] args) {
        new UserClient();
    }
}
