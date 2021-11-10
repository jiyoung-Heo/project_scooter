package jdbc.main.user;

import java.util.Scanner;

import jdbc.manager.UserManager;

public class UserModifyMenu {
	public static void userModifyMenu(String id) {
		Scanner sc = new Scanner(System.in);
		UserManager userManager = new UserManager();
		while (true) {
			System.out.println("1.��й�ȣ���� 2.�̸����� 3.��ȭ��ȣ���� 4.�ڷΰ���");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				System.out.println("������ ��й�ȣ�� �Է��ϼ���");
				String pwd = sc.next();
				userManager.modifyPwd(id, pwd);
				System.out.println("��й�ȣ�� ����Ǿ����ϴ�.");
				break;
			case 2:
				System.out.println("������ �̸��� �Է��ϼ���");
				String name = sc.next();
				userManager.modifyName(id, name);
				System.out.println("�̸��� ����Ǿ����ϴ�.");
				break;
			case 3:
				System.out.println("������ ��ȭ��ȣ�� �Է��ϼ���");
				String phone = sc.next();
				userManager.modifyPhone(id, phone);
				System.out.println("��ȭ��ȣ�� ����Ǿ����ϴ�.");
				break;
			case 4:
				System.out.println("���� �޴��� ���ư��ϴ�...");
				UserListView.userlistview(id);

			default:
				System.out.println("��ȣ�� �߸��Է��ϼ̽��ϴ�.");
			}
		}
	}
}
