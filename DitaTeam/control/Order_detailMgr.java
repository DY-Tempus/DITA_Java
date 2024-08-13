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
        String sql = "INSERT INTO Order_detail (Order_No, Menu_Name, Ctg_Name, Order_Num) VALUES (?, ?, ?, ?)";
        boolean flag = false;

        try {
            con = pool.getConnection();
            con.setAutoCommit(false); // 배치 작업을 위해 수동 커밋 모드로 설정

            pstmt = con.prepareStatement(sql);

            for (Order_detail order : list) {
                pstmt.setInt(1, order.getOrder_No());
                pstmt.setString(2, order.getMenu_Name());
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
            pool.closeResources(con, pstmt, null);
        }

        return flag;
    }
	
	// 주문 상세 목록 삭제.
	public boolean delete_Detail(Vector <Order_detail> list) {
			Connection con = null;
	        PreparedStatement pstmt = null;
	        String sql = "DELETE From Order_detail where Order_No = ? and Menu_Name = ?";
	        boolean flag = false;

	        try {
	            con = pool.getConnection();
	            con.setAutoCommit(false); // 배치 작업을 위해 수동 커밋 모드로 설정

	            pstmt = con.prepareStatement(sql);

	            for (Order_detail order : list) {
	                pstmt.setInt(1, order.getOrder_No());
	                pstmt.setString(2, order.getMenu_Name());
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
	            pool.closeResources(con, pstmt, null);
	        }

	        return flag;
	    }
	
	// 상세 주문 목록 변경.
	public boolean update_Order_Detail(Vector <Order_detail> list) {
		Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE From Order_detail where Order_No = ? and Menu_Name = ?";
        boolean flag = false;

        try {
            con = pool.getConnection();
            con.setAutoCommit(false); // 배치 작업을 위해 수동 커밋 모드로 설정

            pstmt = con.prepareStatement(sql);

            for (Order_detail order : list) {
                pstmt.setInt(1, order.getOrder_No());
                pstmt.setString(2, order.getMenu_Name());
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
			pool.closeResources(con, pstmt, null);
        }

        return flag;
	}
	
	// 상세 주문 목록 조회.
	public Vector<Order_detail> select_Order_Detail(int Order_No){
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Order_Detail WHERE Order_No = ?";
        
        Vector<Order_detail> list = new Vector<Order_detail>();
        
        boolean flag = false;
        try {
            con = pool.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, Order_No);
            rs = pstmt.executeQuery();
            
            while(rs.next()) { 
            	Order_detail bean = new Order_detail();
            	bean.setMenu_Name(rs.getString("MENU_Name"));
            	bean.setOrder_No(rs.getInt("ORDER_NO"));
            	bean.setCtg_Name(rs.getString("CTG_NAME"));
            	bean.setOrder_Num(rs.getInt("ORDER_NUM"));
            	list.add(bean);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 자원 해제
        	pool.closeResources(con, pstmt, rs);
        }
        
        return list;
	}
		
 	public static void main(String[] args) {
		
		// 주문 상세 삽입, 삭제 예시.
		Vector <Order_detail> list = new Vector();
		Order_detailMgr mgr = new Order_detailMgr();
		
		Order_detail order1 = new Order_detail();
		order1.setOrder_No(1);
		order1.setMenu_Name("삽겹살");
		order1.setCtg_Name("메인 메뉴");
		order1.setOrder_Num(2);
		list.add(order1);
		
		Order_detail order2 = new Order_detail();
		order2.setOrder_No(1);
		order2.setMenu_Name("삽겹살+불고기");
		order2.setCtg_Name("세트 메뉴");
		order2.setOrder_Num(3);
		list.add(order2);
		
		
		 if(mgr.insert_Detail(list)) { 
			 System.out.println("삽입 되었습니다."); 
		 }
		 
		
		
	}

}
