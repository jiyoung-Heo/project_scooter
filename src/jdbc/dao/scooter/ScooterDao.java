package jdbc.dao.scooter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.util.DBConnection;

public class ScooterDao {
	DBConnection db = new DBConnection();

	/**
	 * scooters db �ȿ� ���ִ°� �ִ��� Ȯ���ϴ� �޼ҵ�
	 * 
	 * @return
	 */
	public boolean isHaveData(int num) {
		boolean result = false;
		String sql = "select num from scooters where num = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, num);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					result = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ű���� �뿩������ �ٲ��ִ� �޼ҵ�
	 * 
	 * @param num
	 */
	public void BorrowIng(int num) {
		String sql = "update scooters set isborrow = '�뿩��' where num = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, num);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ű���� �뿩�������� �ٲ��ִ� �޼ҵ�
	 * 
	 * @param num
	 */
	public void checkRetrun(int num) {
		String sql = "update scooters set isborrow = '�뿩����' where num = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, num);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �뿩�������� Ȯ�����ִ� �޼ҵ�
	 * 
	 * @param num
	 * @return �뿩�����ϸ� true
	 */
	public boolean checkBorrow(int num) {
		boolean result = false;
		String sql = "select num from scooters where num = ? and isborrow ='�뿩����'";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, num);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					result = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ű���� ��ġ���ִ� �޼ҵ�
	 * 
	 * @param num
	 */
	public void createScooterInfo(int num) {
		String sqlIsborrow = "select isborrow from scooters";
		String sqlDel = "delete from scooters";
		String sql = "insert into scooters values(upNum.nextval,?)";
		try (Connection con = db.getConnection();
				PreparedStatement pstmtIs = con.prepareStatement(sqlIsborrow);
				PreparedStatement pstmtDel = con.prepareStatement(sqlDel);
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			try (ResultSet rs = pstmtIs.executeQuery();) {
				if (rs.next()) {
					String isborrow = rs.getString("isborrow");
					if (isborrow.equals("�뿩��")) {
						System.out.println("�뿩���� ű���尡 �־� ��ġ�� �� �����ϴ�.");
					} else {
						System.out.println("�̹� ű���尡 ��ġ�Ǿ� �־� ��ġ�� �� �����ϴ�. �߰���ġ�� �����մϴ�.");
					}
				} else {
					for (int i = 1; i <= num; i++) {
						pstmt.setString(1, "�뿩����");
						pstmt.execute();
					}
					System.out.println("��ġ�Ϸ�");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ű���� �߰���ġ���ִ� �޼ҵ�
	 * 
	 * @param num
	 */
	public void addScooterInfo(int num) {
		String sql = "insert into scooters values(upNum.nextval,?)";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			for (int i = 1; i <= num; i++) {
				pstmt.setString(1, "�뿩����");
				pstmt.execute();
			}
			System.out.println("��ġ�Ϸ�");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
