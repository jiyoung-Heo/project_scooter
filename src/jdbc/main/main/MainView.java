package jdbc.main.main;

import java.util.Scanner;

import jdbc.main.admin.user.UserView;
import jdbc.manager.UserManager;

public class MainView {
	UserManager userManager = new UserManager();

	public static void main(String[] args) {
		borrowSystem();
	}

	public static void borrowSystem() {
		Scanner sc = new Scanner(System.in);
		System.out.println("<����ű���� �뿩 �ý���>");
		while (true) {
			System.out.println("1.�α��� 2.ȸ������ 3.����");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				LoginView login = new LoginView();
				login.login();
				break;
			case 2:
				UserView user = new UserView();
				user.createUser();
				break;
			case 3:
				System.out.println("�����մϴ�...");
				System.exit(0);
			default:
				System.out.println("�߸��� ��ȣ�� �Է��߽��ϴ�.");
			}
		}
	}

}
