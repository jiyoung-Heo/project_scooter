package jdbc.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.util.DBConnection;
import jdbc.util.WrongNumberException;

public class TransactionListDao {
	DBConnection db = new DBConnection();

	/**
	 * 대여료 추가
	 * 
	 * @param id
	 * @param num
	 * @param payment
	 */
	public void payment(int count, String id, int num, int payment) {
		String sql = "insert into transactionList values(?,?,?,?,?)";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, count);
			pstmt.setString(2, id);
			pstmt.setInt(3, num);
			pstmt.setString(4, "대여료");
			pstmt.setInt(5, payment);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WrongNumberException(e.getMessage());
		}
	}

	/**
	 * 연체료 추가
	 * 
	 * @param id
	 * @param num
	 * @param latePayment
	 */
	public void latepayment(int count, String id, int num, int latePayment) {
		String sql = "insert into transactionList values(?,?,?,?,?)";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, count);
			pstmt.setString(2, id);
			pstmt.setInt(3, num);
			pstmt.setString(4, "연체료");
			pstmt.setInt(5, latePayment);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WrongNumberException(e.getMessage());
		}
	}

	/**
	 * @return 날짜,총 매출
	 */
	public List<String> allSales() {
		List<String> list = new ArrayList<>();
		String sql = "select substr(b.starttime,1,10), sum(t.payment) from transactionList t, borrowlist b where t.justcount=b.justcount group by substr(b.starttime,1,10)";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					String date = rs.getString("substr(b.starttime,1,10)");
					int sum = rs.getInt("sum(t.payment)");
					list.add(date + "\t" + sum);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @return 날짜,대여료 매출
	 */
	public List<String> feeSales() {
		List<String> list = new ArrayList<>();
		String sql = "select substr(b.starttime,1,10), sum(t.payment) from transactionList t, borrowlist b where t.justcount=b.justcount and t.paymenttype='대여료' group by substr(b.starttime,1,10)";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					String date = rs.getString("substr(b.starttime,1,10)");
					int sum = rs.getInt("sum(t.payment)");
					list.add(date + "\t" + sum);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @return 날짜,연체료 매출
	 */
	public List<String> lateFeeSales() {
		List<String> list = new ArrayList<>();
		String sql = "select substr(b.starttime,1,10), sum(t.payment) from transactionList t, borrowlist b where t.justcount=b.justcount and t.paymenttype='연체료' group by substr(b.starttime,1,10)";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					String date = rs.getString("substr(b.starttime,1,10)");
					int sum = rs.getInt("sum(t.payment)");
					list.add(date + "\t" + sum);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @return 해당아이디의 총결제금액
	 */
	public int idSales(String id) {
		int allSales = 0;
		String sql = "select sum(payment) from transactionList where id = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					allSales = rs.getInt("sum(payment)");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allSales;
	}

	/**
	 * 해당사용자의 지불내역
	 * 
	 * @param id
	 * @return
	 */
	public List<String> userpayList(String id) {
		String sql = "select t.paymentType,t.payment,t.scooterNum,b.startTime,b.endTime from transactionList t, borrowList b"
				+ " where t.justcount=b.justcount and t.id = ? order by paymenttype asc, startTime desc,endTime desc";
		ArrayList<String> list = new ArrayList<>();
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					String paymenttype = rs.getString("paymenttype");
					int payment = rs.getInt("payment");
					int scooternum = rs.getInt("scooternum");
					String time = "";
					if (paymenttype.equals("대여료")) {
						time = rs.getString("startTime");
					} else if (paymenttype.equals("연체료")) {
						time = rs.getString("endTime");
					}
					list.add(paymenttype + "\t" + payment + "\t" + scooternum + "\t" + time);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
