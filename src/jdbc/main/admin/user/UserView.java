package jdbc.main.admin.user;

import java.util.Scanner;

import jdbc.dao.user.vo.UserVo;
import jdbc.manager.UserManager;
import jdbc.util.DuplicateIDException;

public class UserView {
	Scanner sc = new Scanner(System.in);
	UserManager userManager = new UserManager();

	public void createUser() {
		String id = makeString("���̵�", 15);
		String pwd = makeString("��й�ȣ", 15);
		String name = makeString("�̸�", 10);
		String phone = makeString("��ȭ��ȣ", 15);

		UserVo vo = new UserVo(id, pwd, name, phone, 0);

		try {
			userManager.createUser(vo);
			System.out.println("ȸ�������� �Ϸ�Ǿ����ϴ�.");
			System.out.println("ȸ�����Լ����� 10000 ����Ʈ�� �帳�ϴ�.");
		} catch (DuplicateIDException e) {
			System.out.println("�̹� �����ϴ� ���̵��Դϴ�.");
		}
	}

	private String makeString(String key, int length) {
		String result = "";
		while (true) {
			System.out.print(key + ": ");
			result = sc.next();
			if (result.length() > length) {
				System.out.println(key + "�� ����..�ش�" + key + "�� �Ұ��մϴ�.");
			} else {
				break;
			}
		}
		return result;
	}
}
