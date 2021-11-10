package jdbc.dao.scooter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import jdbc.util.DBConnection;
import jdbc.util.WrongNumberException;

public class BorrowListDao {
	DBConnection db = new DBConnection();

	/**
	 * borrowlist에 추가하기
	 * 
	 * @param id
	 * @param num
	 * @param min
	 */
	public void rental(String id, int num, int min) {
		String sql = "insert into borrowList(justcount,id,num,time,startTime) "
				+ "values(blist.nextval,?,?,?,to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			pstmt.setInt(2, num);
			pstmt.setInt(3, min);
			pstmt.execute();
		} catch (SQLIntegrityConstraintViolationException ei) {
			throw new WrongNumberException(ei.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 선결제시 입력한 빌릴시간
	 * 
	 * @param num
	 * @return 선결제시 입력한 빌릴시간
	 */
	public int borrowTime(int num) {
		int borrowTime = 0;
		String sql = "select time from borrowList where num = ? and isreturned is null";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, num);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					borrowTime = rs.getInt("time");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return borrowTime;
	}

	/**
	 * 시작시간부터 얼마나 흘렀는지 체크하는 메소드
	 * 
	 * @param id
	 * @param num
	 * @return 흐른시간
	 */
	public int howLongTime(String id, int num) {
		int time = 0;
		String sql = "select (sysdate-to_date(startTime,'yyyy-mm-dd hh24:mi:ss'))*24*60 흐른시간 from borrowList where id= ? and num = ? and isreturned is null";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			pstmt.setInt(2, num);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					time = rs.getInt("흐른시간");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 킥보드 반납시간 저장해주는 메소드
	 * 
	 * @param num
	 */
	public void userEndTime(int num) {
		String sql = "update borrowList set endtime = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') where num = ? and isreturned is null";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, num);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 반납때까지 총 킥보드 탄 시간
	 * 
	 * @param id
	 * @param num
	 * @return 반납때까지 총 킥보드 탄 시간
	 */
	public int howLong(String id, int num) {
		int result = 0;
		String sql = "select (to_date(endTime,'yyyy-mm-dd hh24:mi:ss')"
				+ "-to_date(startTime,'yyyy-mm-dd hh24:mi:ss'))*24*60 총사용시간 from borrowList where num = ? and id = ? and isreturned is null";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, num);
			pstmt.setString(2, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					result = rs.getInt("총사용시간");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 늦었으면 늦었다고 체크
	 * 
	 * @param id
	 * @param num
	 * @return 늦었으면 true 안늦었으면 false
	 */
	public boolean isLate(String id, int num) {
		boolean flag = false;
		String sql = "update borrowList set isLate = ? where num = ? and isreturned is null";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			if (borrowTime(num) < howLong(id, num)) {
				pstmt.setString(1, "Y");
				flag = true;
			} else {
				pstmt.setString(1, "N");
			}
			pstmt.setInt(2, num);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 대여중인 킥보드 정보
	 * 
	 * @param id
	 * @return 대여중인 킥보드 정보
	 */
	public ArrayList<String> userBorrowed(String id) {
		String sql = "select * from borrowlist where id= ? and isreturned is null";
		ArrayList<String> list = new ArrayList<>();
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					int num = rs.getInt("num");
					int min = rs.getInt("time");
					String startTime = rs.getString("startTime");
					int howLongTime = howLongTime(id, num);
					String isLate = "";
					int lateTime = 0;
					if (late(id, num)) {
						lateTime = howLongTime - borrowTime(num);
						isLate = "Y";
					}
					list.add(
							num + "\t" + min + "\t" + startTime + "\t" + howLongTime + "\t" + isLate + "\t" + lateTime);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 사용사가 현재 해당 킥보드를 대여중인지 확인해주는메소드
	 * 
	 * @param id
	 * @param num
	 * @return 대여중이면 true
	 */
	public boolean rentalScooterById(String id, int num) {
		boolean result = false;
		String sql = "select * from borrowlist where id= ? and num =? and isreturned is null";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			pstmt.setInt(2, num);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					result = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 킥보드 반납완료체크하기
	 * 
	 * @param id
	 * @param num
	 */
	public void returnScooter(String id, int num) {
		String sql = "update borrowlist set isreturned = 'Y' where id = ? and num=? and isreturned is null";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			pstmt.setInt(2, num);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 반납전에 연체여부 알수있는메소드
	 * 
	 * @param id
	 * @param num
	 * @return 연체되면 true
	 */
	public boolean late(String id, int num) {
		boolean late = false;
		if (howLongTime(id, num) > borrowTime(num)) {
			late = true;
		}
		return late;
	}

	/**
	 * 반납전에 연체시간 알수있는메소드
	 * 
	 * @param id
	 * @param num
	 * @return 연체시간
	 */
	public int lateTime(String id, int num) {
		int time = 0;
		if (late(id, num)) {
			time = howLongTime(id, num) - borrowTime(num);
		}
		return time;
	}

	/**
	 * 내역조회를 위한 총 대여시간 구하는 메소드
	 * 
	 * @return
	 */
	public int lateTimeFroHistory(int num, String id, String startTime) {
		int lateTime = 0;
		String sql = "select (to_date(endTime,'yyyy-mm-dd hh24:mi:ss')-to_date(startTime,'yyyy-mm-dd hh24:mi:ss'))*24*60 allborrowtime "
				+ "from borrowList where num = ? and id = ? and starttime= ? and isreturned is not null";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, num);
			pstmt.setString(2, id);
			pstmt.setString(3, startTime);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					lateTime = rs.getInt("allborrowtime");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lateTime;
	}

	/**
	 * 해당 유저의 킥보드 사용내역 조회
	 * 
	 * @param id
	 * @return 해당 유저의 킥보드 사용내역 조회
	 */
	public List<String> userScooterHistory(String id) {
		String sql = "select num,starttime,endtime,islate,time "
				+ "from borrowlist where id= ? and isreturned is not null order by starttime asc";
		ArrayList<String> list = new ArrayList<>();
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					int num = rs.getInt("num");
					String startTime = rs.getString("startTime");
					String endTime = rs.getString("endTime");
					String isLate = rs.getString("isLate");
					int time = rs.getInt("time");
					int useTime = time * 50;
					int lateTime = 0;
					if (isLate.equals("Y")) {
						lateTime = lateTimeFroHistory(num, id, startTime) - time;
					}
					String fee = useTime + lateTime * 100 + "원";
					list.add(num + "\t" + startTime + "\t" + endTime + "\t" + isLate + "\t" + lateTime + "분\t" + fee);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 대여료 관련 count받아오는메소드
	 * 
	 * @param id
	 * @param num
	 * @return
	 */
	public int selectCount(String id, int num) {
		int result = 0;
		String sql = "select justcount from borrowlist where id= ? and num = ? and isreturned is null";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			pstmt.setInt(2, num);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					int justcount = rs.getInt("justcount");
					result = justcount;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
