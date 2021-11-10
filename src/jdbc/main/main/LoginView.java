package jdbc.main.main;

import java.util.Scanner;

import jdbc.main.admin.AdminMainView;
import jdbc.main.user.UserMainView;
import jdbc.manager.UserManager;

public class LoginView {
	Scanner sc = new Scanner(System.in);
	UserManager userManager = new UserManager();

	public void login() {
		System.out.print("���̵�: ");
		String id = sc.next();
		System.out.print("��й�ȣ: ");
		String pwd = sc.next();
		String userId = userManager.login(id, pwd);
		if (userId.equals("")) {
			System.out.println("���̵�Ǵ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
		} else if (userId.equals("admin")) {
			System.out.println("--�����ڱ������� �����Ͽ����ϴ�--");
			AdminMainView.adminMainMenu();
		} else {
			System.out.println(userId + " ���� �α����Ͽ����ϴ�.");
			UserMainView.userMainMenu(userId);
		}
	}
}
