package jdbc.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import jdbc.dao.user.vo.UserVo;
import jdbc.util.Const;
import jdbc.util.DBConnection;
import jdbc.util.DuplicateIDException;

public class UserDao {
	DBConnection db = new DBConnection();

	/**
	 * id,pwd 일치하는지 찾아주는 메소드
	 * 
	 * @param id
	 * @param pwd
	 * @return
	 */
	public String login(String id, String pwd) {
		String result = "";
		String sql = "select id,pwd from users where id = ? and pwd = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					result = id;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 회원가입
	 * 
	 * @param vo
	 */
	public void createUser(UserVo vo) {
		String sql = "insert into users values(?,?,?,?,?)";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPwd());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getPhone());
			pstmt.setInt(5, vo.getCurrentMoney());
			pstmt.execute();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new DuplicateIDException(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 가입하면 가입축하금 1000원 지급
	 * 
	 * @param vo
	 */
	public void joinbonusUser(UserVo vo) {
		String sql = "update users set currentMoney=10000 where id = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, vo.getId());
			pstmt.execute();
		} catch (SQLException e) {
		}
	}

	/**
	 * 해당 사용자의 현재 잔액 조회
	 * 
	 * @param id
	 * @return 잔액
	 */
	public int userMon(String id) {
		int result = 0;
		String sql = "Select currentMoney from users where id = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					int money = rs.getInt("currentMoney");
					result = money;
				}
			}
		} catch (SQLException e) {
		}
		return result;
	}

	/**
	 * 선결제 1분당 50원 차감
	 * 
	 * @param id
	 * @param min
	 * @return 차감할 금액
	 */
	public int payment(String id, int min) {
		int payment = 0;
		String sql = "update users set currentMoney = ? where id = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			payment = min * Const.FEE;
			pstmt.setInt(1, userMon(id) - payment);
			pstmt.setString(2, id);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return payment;
	}

	/**
	 * 연체료 1분당 100원 차감
	 * 
	 * @param id
	 * @param num
	 * @param howLong
	 * @return 연체료
	 */
	public int latePayment(String id, int num, int howLong) {
		int lastpayment = 0;
		String sql = "update users set currentMoney = ? where id = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			lastpayment = howLong * Const.LATE_FEE;
			pstmt.setInt(1, userMon(id) - lastpayment);
			pstmt.setString(2, id);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastpayment;
	}

	/**
	 * 해당회원의 비밀번호 변경
	 * 
	 * @param id
	 * @param pwd
	 */
	public void modifyPwd(String id, String pwd) {
		String sql = "update users set pwd = ? where id = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, pwd);
			pstmt.setString(2, id);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 해당회원의 이름 변경
	 * 
	 * @param id
	 * @param name
	 */
	public void modifyName(String id, String name) {
		String sql = "update users set name = ? where id = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 해당회원의 전화번호 변경
	 * 
	 * @param id
	 * @param phone
	 */
	public void modifyPhone(String id, String phone) {
		String sql = "update users set phone = ? where id = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, phone);
			pstmt.setString(2, id);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 해당회원의 정보조회
	 * 
	 * @param id
	 * @return 회원의 정보(아이디,패스워드,이름,전화번호)
	 */
	public String userinfo(String id) {
		String result = "";
		String sql = "Select id,pwd,name,phone,currentmoney from users where id = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					String id1 = rs.getString("id");
					String pwd = rs.getString("pwd");
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					int currentMoney = rs.getInt("currentMoney");
					result = "아이디:" + id1 + " 비밀번호:" + pwd + " 이름:" + name + " 전화번호: " + phone + " 잔액:" + currentMoney;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
