package jdbc.manager;

import java.util.List;

import jdbc.dao.scooter.BorrowListDao;
import jdbc.dao.scooter.ScooterDao;
import jdbc.dao.scooter.ScooterInfoListDao;
import jdbc.dao.user.TransactionListDao;
import jdbc.dao.user.UserDao;
import jdbc.util.AlreadyRentalException;
import jdbc.util.WrongNumberException;

public class ScooterManager {
	ScooterDao scooterDao = new ScooterDao();
	ScooterInfoListDao scooterInfoList = new ScooterInfoListDao();
	UserDao userDao = new UserDao();
	BorrowListDao borrowListdao = new BorrowListDao();
	TransactionListDao transactionListDao = new TransactionListDao();

	public void createScooterInfo(int num) {
		scooterDao.createScooterInfo(num);
	}

	public void addScooterInfo(int num) {
		scooterDao.addScooterInfo(num);
	}

	public List<String> allScooterList() {
		List<String> list = scooterInfoList.scooterInfoList();
		return list;
	}

	public List<String> rentalScooterList() {
		List<String> list = scooterInfoList.rentalscooterInfoList();
		return list;
	}

	public List<String> lateScooterList() {
		List<String> list = scooterInfoList.latescooterInfoList();
		return list;
	}

	public void borrowScooter(String id, int num, int min) {
		if (scooterDao.isHaveData(num)) {
			if (scooterDao.checkBorrow(num)) {
				borrowListdao.rental(id, num, min);
				scooterDao.BorrowIng(num);
				int payment = userDao.payment(id, min);
				int count = borrowListdao.selectCount(id, num);
				transactionListDao.payment(count, id, num, payment);
				System.out.println(payment + "�� �����Ͽ����ϴ�.");
				System.out.println("���� �� �ܾ�: " + userDao.userMon(id));
			} else {
				throw new AlreadyRentalException();
			}
		} else {
			throw new WrongNumberException();
		}
	}

	public void returnScooter(String id, int returnnum) {
		if (borrowListdao.rentalScooterById(id, returnnum)) {
			borrowListdao.userEndTime(returnnum);
			if (borrowListdao.isLate(id, returnnum)) {
				int howLong = borrowListdao.howLong(id, returnnum) - borrowListdao.borrowTime(returnnum);
				int latePayment = userDao.latePayment(id, returnnum, howLong);
				System.out.println(howLong + "�� �ʾ����ϴ�.");
				System.out.println("��ü��" + latePayment + "���� �����մϴ�.");
				int count = borrowListdao.selectCount(id, returnnum);
				transactionListDao.latepayment(count, id, returnnum, latePayment);
				System.out.println("���� �� �ܾ�: " + userDao.userMon(id));
			}
			borrowListdao.returnScooter(id, returnnum);
			System.out.println(returnnum + "�� ű���尡 �ݳ��Ǿ����ϴ�.");
			scooterDao.checkRetrun(returnnum);
		} else {
			throw new WrongNumberException("���� ű���� ��ȣ�� �ƴմϴ�.");
		}

	}

	public List<String> rentalScooterListById(String id) {
		List<String> list = borrowListdao.userBorrowed(id);
		return list;
	}

}
