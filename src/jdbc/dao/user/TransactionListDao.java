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
	 * �뿩�� �߰�
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
			pstmt.setString(4, "�뿩��");
			pstmt.setInt(5, payment);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WrongNumberException(e.getMessage());
		}
	}

	/**
	 * ��ü�� �߰�
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
			pstmt.setString(4, "��ü��");
			pstmt.setInt(5, latePayment);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WrongNumberException(e.getMessage());
		}
	}

	/**
	 * @return ��¥,�� ����
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
	 * @return ��¥,�뿩�� ����
	 */
	public List<String> feeSales() {
		List<String> list = new ArrayList<>();
		String sql = "select substr(b.starttime,1,10), sum(t.payment) from transactionList t, borrowlist b where t.justcount=b.justcount and t.paymenttype='�뿩��' group by substr(b.starttime,1,10)";
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
	 * @return ��¥,��ü�� ����
	 */
	public List<String> lateFeeSales() {
		List<String> list = new ArrayList<>();
		String sql = "select substr(b.starttime,1,10), sum(t.payment) from transactionList t, borrowlist b where t.justcount=b.justcount and t.paymenttype='��ü��' group by substr(b.starttime,1,10)";
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
	 * @return �ش���̵��� �Ѱ����ݾ�
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
	 * �ش������� ���ҳ���
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
					if (paymenttype.equals("�뿩��")) {
						time = rs.getString("startTime");
					} else if (paymenttype.equals("��ü��")) {
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
