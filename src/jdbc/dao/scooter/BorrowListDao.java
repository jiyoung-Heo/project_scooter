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
	 * borrowlist�� �߰��ϱ�
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
	 * �������� �Է��� �����ð�
	 * 
	 * @param num
	 * @return �������� �Է��� �����ð�
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
	 * ���۽ð����� �󸶳� �귶���� üũ�ϴ� �޼ҵ�
	 * 
	 * @param id
	 * @param num
	 * @return �帥�ð�
	 */
	public int howLongTime(String id, int num) {
		int time = 0;
		String sql = "select (sysdate-to_date(startTime,'yyyy-mm-dd hh24:mi:ss'))*24*60 �帥�ð� from borrowList where id= ? and num = ? and isreturned is null";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			pstmt.setInt(2, num);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					time = rs.getInt("�帥�ð�");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * ű���� �ݳ��ð� �������ִ� �޼ҵ�
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
	 * �ݳ������� �� ű���� ź �ð�
	 * 
	 * @param id
	 * @param num
	 * @return �ݳ������� �� ű���� ź �ð�
	 */
	public int howLong(String id, int num) {
		int result = 0;
		String sql = "select (to_date(endTime,'yyyy-mm-dd hh24:mi:ss')"
				+ "-to_date(startTime,'yyyy-mm-dd hh24:mi:ss'))*24*60 �ѻ��ð� from borrowList where num = ? and id = ? and isreturned is null";
		try (Connection con = db.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, num);
			pstmt.setString(2, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					result = rs.getInt("�ѻ��ð�");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * �ʾ����� �ʾ��ٰ� üũ
	 * 
	 * @param id
	 * @param num
	 * @return �ʾ����� true �ȴʾ����� false
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
	 * �뿩���� ű���� ����
	 * 
	 * @param id
	 * @return �뿩���� ű���� ����
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
	 * ���簡 ���� �ش� ű���带 �뿩������ Ȯ�����ִ¸޼ҵ�
	 * 
	 * @param id
	 * @param num
	 * @return �뿩���̸� true
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
	 * ű���� �ݳ��Ϸ�üũ�ϱ�
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
	 * �ݳ����� ��ü���� �˼��ִ¸޼ҵ�
	 * 
	 * @param id
	 * @param num
	 * @return ��ü�Ǹ� true
	 */
	public boolean late(String id, int num) {
		boolean late = false;
		if (howLongTime(id, num) > borrowTime(num)) {
			late = true;
		}
		return late;
	}

	/**
	 * �ݳ����� ��ü�ð� �˼��ִ¸޼ҵ�
	 * 
	 * @param id
	 * @param num
	 * @return ��ü�ð�
	 */
	public int lateTime(String id, int num) {
		int time = 0;
		if (late(id, num)) {
			time = howLongTime(id, num) - borrowTime(num);
		}
		return time;
	}

	/**
	 * ������ȸ�� ���� �� �뿩�ð� ���ϴ� �޼ҵ�
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
	 * �ش� ������ ű���� ��볻�� ��ȸ
	 * 
	 * @param id
	 * @return �ش� ������ ű���� ��볻�� ��ȸ
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
					String fee = useTime + lateTime * 100 + "��";
					list.add(num + "\t" + startTime + "\t" + endTime + "\t" + isLate + "\t" + lateTime + "��\t" + fee);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * �뿩�� ���� count�޾ƿ��¸޼ҵ�
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
