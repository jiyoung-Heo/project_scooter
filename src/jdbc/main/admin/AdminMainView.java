package jdbc.main.admin;

import java.util.Scanner;

import jdbc.main.admin.scooter.ScooterListView;
import jdbc.main.admin.user.FeeListView;
import jdbc.main.admin.user.UserListView;
import jdbc.main.main.MainView;
import jdbc.manager.ScooterManager;

public class AdminMainView {

	public static void adminMainMenu() {
		Scanner sc = new Scanner(System.in);
		ScooterManager scooterManager = new ScooterManager();
		while (true) {
			System.out.println("1.ű���� �ʱ��ġ��Ű�� 2.ű���� �߰� 3.ű���� ��ȸ 4.����� ��ȸ 5.������ȸ 6.�α׾ƿ� 7.��ü����");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				System.out.println("����� ű���带 ��ġ�Ͻðڽ��ϱ�?");
				int num = sc.nextInt();
				scooterManager.createScooterInfo(num);
				break;
			case 2:
				System.out.println("����� ű���带 �߰��Ͻðڽ��ϱ�?");
				int addNum = sc.nextInt();
				scooterManager.addScooterInfo(addNum);
				break;
			case 3:
				ScooterListView.scooterListView();
				break;
			case 4:
				UserListView.userListView();
				break;
			case 5:
				FeeListView.feeListMenu();
				break;
			case 6:
				System.out.println("�α׾ƿ��մϴ�.");
				MainView.borrowSystem();
				break;
			case 7:
				System.out.println("�ý����� �����մϴ�..");
				System.exit(0);
			default:
				System.out.println("�߸��Է��ϼ̽��ϴ�.");
			}

		}
	}
}
