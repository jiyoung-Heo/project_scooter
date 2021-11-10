package jdbc.dao.scooter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.util.DBConnection;

public class ScooterDao {
	DBConnection db = new DBConnection();

	/**
	 * scooters db 안에 들어가있는게 있는지 확인하는 메소드
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
	 * 킥보드 대여중으로 바꿔주는 메소드
	 * 
	 * @param num
	 */
	public void BorrowIng(int num) {
		String sql = "update scooters set isborrow = '대여중' where num = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, num);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 킥보드 대여가능으로 바꿔주는 메소드
	 * 
	 * @param num
	 */
	public void checkRetrun(int num) {
		String sql = "update scooters set isborrow = '대여가능' where num = ?";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, num);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 대여가능한지 확인해주는 메소드
	 * 
	 * @param num
	 * @return 대여가능하면 true
	 */
	public boolean checkBorrow(int num) {
		boolean result = false;
		String sql = "select num from scooters where num = ? and isborrow ='대여가능'";
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
	 * 킥보드 배치해주는 메소드
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
					if (isborrow.equals("대여중")) {
						System.out.println("대여중인 킥보드가 있어 배치할 수 없습니다.");
					} else {
						System.out.println("이미 킥보드가 배치되어 있어 배치할 수 없습니다. 추가배치만 가능합니다.");
					}
				} else {
					for (int i = 1; i <= num; i++) {
						pstmt.setString(1, "대여가능");
						pstmt.execute();
					}
					System.out.println("배치완료");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 킥보드 추가배치해주는 메소드
	 * 
	 * @param num
	 */
	public void addScooterInfo(int num) {
		String sql = "insert into scooters values(upNum.nextval,?)";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			for (int i = 1; i <= num; i++) {
				pstmt.setString(1, "대여가능");
				pstmt.execute();
			}
			System.out.println("배치완료");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
