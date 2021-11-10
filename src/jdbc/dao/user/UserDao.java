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
	 * id,pwd ��ġ�ϴ��� ã���ִ� �޼ҵ�
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
	 * ȸ������
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
	 * �����ϸ� �������ϱ� 1000�� ����
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
	 * �ش� ������� ���� �ܾ� ��ȸ
	 * 
	 * @param id
	 * @return �ܾ�
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
	 * ������ 1�д� 50�� ����
	 * 
	 * @param id
	 * @param min
	 * @return ������ �ݾ�
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
	 * ��ü�� 1�д� 100�� ����
	 * 
	 * @param id
	 * @param num
	 * @param howLong
	 * @return ��ü��
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
	 * �ش�ȸ���� ��й�ȣ ����
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
	 * �ش�ȸ���� �̸� ����
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
	 * �ش�ȸ���� ��ȭ��ȣ ����
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
	 * �ش�ȸ���� ������ȸ
	 * 
	 * @param id
	 * @return ȸ���� ����(���̵�,�н�����,�̸�,��ȭ��ȣ)
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
					result = "���̵�:" + id1 + " ��й�ȣ:" + pwd + " �̸�:" + name + " ��ȭ��ȣ: " + phone + " �ܾ�:" + currentMoney;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
