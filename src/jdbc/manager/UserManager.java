package jdbc.manager;

import java.util.List;

import jdbc.dao.scooter.BorrowListDao;
import jdbc.dao.user.TransactionListDao;
import jdbc.dao.user.UserDao;
import jdbc.dao.user.UserInfoListDao;
import jdbc.dao.user.vo.UserVo;

public class UserManager {
	UserInfoListDao memberInfoList = new UserInfoListDao();
	UserDao userDao = new UserDao();
	BorrowListDao borrowListDao = new BorrowListDao();
	TransactionListDao transactionListDao = new TransactionListDao();

	public List<String> allScooterList() {
		List<String> list = memberInfoList.allUserInfoList();
		return list;
	}

	public List<String> rentalScooterList() {
		List<String> list = memberInfoList.rentalMemberInfoList();
		return list;
	}

	public List<String> lateScooterList() {
		List<String> list = memberInfoList.lateMemberInfoList();
		return list;
	}

	public void createUser(UserVo vo) {
		userDao.createUser(vo);
		userDao.joinbonusUser(vo);
	}

	public String login(String id, String pwd) {
		return userDao.login(id, pwd);
	}

	public int currentMoney(String id) {
		return userDao.userMon(id);
	}

	public List<String> allSales() {
		return transactionListDao.allSales();
	}

	public List<String> feeSales() {
		return transactionListDao.feeSales();
	}

	public List<String> lateFeeSales() {
		return transactionListDao.lateFeeSales();
	}

	public int idSales(String id) {
		return transactionListDao.idSales(id);
	}

	public List<String> userScooterHistory(String id) {
		return borrowListDao.userScooterHistory(id);
	}

	public List<String> userpayList(String id) {
		return transactionListDao.userpayList(id);
	}

	public void modifyPwd(String id, String pwd) {
		userDao.modifyPwd(id, pwd);
	}

	public void modifyName(String id, String name) {
		userDao.modifyName(id, name);
	}

	public void modifyPhone(String id, String phone) {
		userDao.modifyPhone(id, phone);
	}

	public String userinfo(String id) {
		return userDao.userinfo(id);
	}

}
