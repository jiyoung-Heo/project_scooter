package jdbc.dao.scooter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.util.DBConnection;

public class ScooterInfoListDao {
	DBConnection db = new DBConnection();

	/**
	 * 킥보드 정보 조회
	 * 
	 * @return 전체 킥보드정보
	 */
	public List<String> scooterInfoList() {
		String sql = "select c.num,c.isborrow,b.id,b.time,(sysdate-to_date(b.startTime,'yyyy-mm-dd hh24:mi:ss'))*24*60 howLongTime "
				+ "from  borrowlist b right outer join scooters c on b.num = c.num where b.isreturned is null order by isborrow desc, num asc";
		List<String> list = new ArrayList<>();
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					int num = rs.getInt("num");
					String isborrow = rs.getString("isborrow");
					if (isborrow.equals("대여가능")) {
						list.add(num + "\t" + isborrow);
					} else {
						String id = rs.getString("id");
						int time = rs.getInt("time");
						int howLongTime = rs.getInt("howLongTime");
						list.add(num + "\t" + isborrow + "\t" + id + "\t" + time + "\t" + howLongTime);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 렌탈중인 킥보드 정보 조회
	 * 
	 * @return 렌탈중인 킥보드 정보
	 */
	public List<String> rentalscooterInfoList() {
		String sql = "select num,id,time,startTime,(sysdate-to_date(startTime,'yyyy-mm-dd hh24:mi:ss'))*24*60 howLongTime"
				+ " from borrowlist where isreturned is null order by num asc";
		List<String> list = new ArrayList<>();
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					int num = rs.getInt("num");
					String id = rs.getString("id");
					int time = rs.getInt("time");
					String startTime = rs.getString("startTime");
					int howLongTime = rs.getInt("howLongTime");
					String isLate = "";
					if (howLongTime > time) {
						isLate = "Y";
					}
					list.add(num + "\t" + id + "\t" + time + "\t" + startTime + "\t" + howLongTime + "\t" + isLate);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 연체된 킥보드 정보 조회
	 * 
	 * @return 연체된 킥보드 정보
	 */
	public List<String> latescooterInfoList() {
		String sql = "select num,id,time, (sysdate-to_date(startTime,'yyyy-mm-dd hh24:mi:ss'))*24*60 howLongTime"
				+ " from borrowlist order by howLongTime desc";
		List<String> list = new ArrayList<>();
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					int num = rs.getInt("num");
					String id = rs.getString("id");
					int time = rs.getInt("time");
					int howLongTime = rs.getInt("howLongTime");
					if (howLongTime > time) {
						int lateTime = howLongTime - time;
						list.add(num + "\t" + id + "\t" + lateTime);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
