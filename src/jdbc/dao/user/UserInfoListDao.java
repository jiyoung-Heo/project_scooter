package jdbc.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.util.DBConnection;

//모든 사용자 조회
public class UserInfoListDao {
	DBConnection db = new DBConnection();

	/**
	 * 전체 사용자 정보 조회
	 * 
	 * @return 전체 사용자 정보
	 */
	public ArrayList<String> allUserInfoList() {
		String sql = "select id,pwd,name,phone,currentmoney from users order by id";
		String sqlBorrow = "select id from borrowlist where isreturned is null and id= ?";
		String sqlPay = "select sum(payment),count(*) from transactionList where id = ?";
		ArrayList<String> list = new ArrayList<>();
		try (Connection con = db.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				PreparedStatement pstmtBorrow = con.prepareStatement(sqlBorrow);
				PreparedStatement pstmtPay = con.prepareStatement(sqlPay);) {
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					String id = rs.getString("id");
					if (id.equals("admin")) {
					} else {
						String pwd = rs.getString("pwd");
						String name = rs.getString("name");
						String phone = rs.getString("phone");
						int correntmoney = rs.getInt("currentmoney");
						String isBorrow = "";
						int allBorrowCount = 0;
						int allPayment = 0;

						pstmtBorrow.setString(1, id);
						try (ResultSet rsBorrow = pstmtBorrow.executeQuery();) {
							if (rsBorrow.next()) {
								isBorrow = "대여중";
							}
						}
						pstmtPay.setString(1, id);
						try (ResultSet rsPay = pstmtPay.executeQuery();) {
							if (rsPay.next()) {
								allBorrowCount = rsPay.getInt("count(*)");
								allPayment = rsPay.getInt("sum(payment)");
							}
						}
						list.add(id + "\t" + pwd + "\t" + name + "\t" + phone + "\t" + correntmoney + "\t" + isBorrow
								+ "\t" + allBorrowCount + "\t" + allPayment);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 킥보드를 빌린 사용자 정보 조회
	 * 
	 * @return 킥보드를 빌린 사용자 정보
	 */
	public ArrayList<String> rentalMemberInfoList() {
		String sql = "select u.id,u.pwd,u.name,u.phone,u.currentmoney,b.num,b.time,"
				+ "(sysdate-to_date(b.startTime,'yyyy-mm-dd hh24:mi:ss'))*24*60 howLongTime "
				+ "from users u ,borrowList b where u.id = b.id and b.isreturned is null order by howLongTime desc";
		ArrayList<String> list = new ArrayList<>();
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					String id = rs.getString("id");
					String pwd = rs.getString("pwd");
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					int currentMoney = rs.getInt("currentMoney");
					int num = rs.getInt("num");
					int time = rs.getInt("time");
					int howLongTime = rs.getInt("howLongTime");
					String isLate = "";
					if (howLongTime > time) {
						isLate = "Y";
					}
					list.add(id + "\t" + pwd + "\t" + name + "\t" + phone + "\t" + currentMoney + "\t" + num + "\t"
							+ time + "\t" + howLongTime + "\t" + isLate);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 킥보드를 연체중인 사용자 정보 조회
	 * 
	 * @return 킥보드를 연체중인 사용자 정보
	 */
	public ArrayList<String> lateMemberInfoList() {
		String sql = "select u.id,u.pwd,u.name,u.phone,u.currentmoney,b.num,b.time,"
				+ "(sysdate-to_date(startTime,'yyyy-mm-dd hh24:mi:ss'))*24*60 lateTime "
				+ "from users u ,borrowList b where u.id = b.id and b.isreturned is null order by lateTime desc";
		ArrayList<String> list = new ArrayList<>();
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					String id = rs.getString("id");
					String pwd = rs.getString("pwd");
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					int num = rs.getInt("num");
					int time = rs.getInt("time");
					int lateTime = rs.getInt("lateTime");
					if (lateTime > time) {
						list.add(id + "\t" + pwd + "\t" + name + "\t" + phone + "\t" + num + "\t" + time + "\t"
								+ lateTime);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
