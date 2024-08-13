package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.net.ssl.SSLContext;

import entity.Order_detail;

public class Order_detailMgr {
	
	private DBConnectionMgr pool;
	
	public Order_detailMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	
	// 주문 상세 목록 삽입.
	public boolean insert_Detail(Vector <Order_detail> list) {
		Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO Order_detail (Order_No, Menu_No, Ctg_Name, Order_Num) VALUES (?, ?, ?, ?)";
        boolean flag = false;

        try {
            con = pool.getConnection();
            con.setAutoCommit(false); // 배치 작업을 위해 수동 커밋 모드로 설정

            pstmt = con.prepareStatement(sql);

            for (Order_detail order : list) {
                pstmt.setInt(1, order.getOrder_No());
                pstmt.setInt(2, order.getMenu_No());
                pstmt.setString(3, order.getCtg_Name());
                pstmt.setInt(4, order.getOrder_Num());
                pstmt.addBatch(); // 배치에 추가
            }

            int[] result = pstmt.executeBatch(); // 배치 실행
            con.commit(); // 트랜잭션 커밋

            // 확인 작업: 모든 삽입이 성공했는지 확인
            flag = true;
            for (int count : result) {
                if (count != PreparedStatement.SUCCESS_NO_INFO && count != 1) {
                    flag = false;
                    break;
                }
            }
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback(); // 에러 발생 시 롤백
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            
        }

        return flag;
    }
	
	// 주문 상세 목록 삭제.
	public boolean delete_Detail(Vector <Order_detail> list) {
			Connection con = null;
	        PreparedStatement pstmt = null;
	        String sql = "DELETE From Order_detail where Order_No = ? and Menu_No = ?";
	        boolean flag = false;

	        try {
	            con = pool.getConnection();
	            con.setAutoCommit(false); // 배치 작업을 위해 수동 커밋 모드로 설정

	            pstmt = con.prepareStatement(sql);

	            for (Order_detail order : list) {
	                pstmt.setInt(1, order.getOrder_No());
	                pstmt.setInt(2, order.getMenu_No());
	                pstmt.addBatch(); // 배치에 추가
	            }

	            int[] result = pstmt.executeBatch(); // 배치 실행
	            con.commit(); // 트랜잭션 커밋

	            // 확인 작업: 모든 삭제가 성공했는지 확인
	            flag = true;
	            for (int count : result) {
	                if (count != PreparedStatement.SUCCESS_NO_INFO && count != 1) {
	                    flag = false;
	                    break;
	                }
	            }
	        } catch (SQLException e) {
	            try {
	                if (con != null) {
	                    con.rollback(); // 에러 발생 시 롤백
	                }
	            } catch (SQLException se) {
	                se.printStackTrace();
	            }
	            e.printStackTrace();
	        } finally {
	            
	        }

	        return flag;
	    }
	
		
	public static void main(String[] args) {
		
		// 주문 상세 삽입, 삭제 예시.
		Vector <Order_detail> list = new Vector();
		Order_detailMgr mgr = new Order_detailMgr();
		
		Order_detail order1 = new Order_detail();
		order1.setOrder_No(1);
		order1.setMenu_No(1);
		order1.setCtg_Name("메인 메뉴");
		order1.setOrder_Num(2);
		list.add(order1);
		
		Order_detail order2 = new Order_detail();
		order2.setOrder_No(1);
		order2.setMenu_No(2);
		order2.setCtg_Name("세트 메뉴");
		order2.setOrder_Num(3);
		list.add(order2);
		
		
		 if(mgr.insert_Detail(list)) { 
			 System.out.println("삽입 되었습니다."); 
		 }
		 
		
		
	}

}
