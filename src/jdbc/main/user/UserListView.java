package jdbc.main.user;

import java.util.List;
import java.util.Scanner;

import jdbc.manager.UserManager;

public class UserListView {

	public static void userlistview(String id) {
		Scanner sc = new Scanner(System.in);
		UserManager userManager = new UserManager();

		while (true) {
			System.out.println("1.�ܾ���ȸ 2.ű�����볻����ȸ 3.�������� 4.�Ѱ����ݾ� 5.ȸ���������� 6.ȸ���������� 7.�ڷΰ���");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				int currentMoney = userManager.currentMoney(id);
				System.out.println(id + "���� �ܾ�: " + currentMoney + "��");
				break;
			case 2:
				List<String> list = userManager.userScooterHistory(id);
				if (list.isEmpty()) {
					System.out.println("ű���带 ��������� �ѹ��� �����ϴ�.");
				} else {
					System.out.println("��볻����ȸ");
					System.out.println("ű�����ȣ\t���۽ð�\t�ݳ��ð�\t��ü����\t��ü�ð�\t���ҳ���");
					for (String useList : list) {
						System.out.println(useList);
					}
				}
				break;
			case 3:
				List<String> list1 = userManager.userpayList(id);
				if (list1.isEmpty()) {
					System.out.println("���������� �����ϴ�.");
				} else {
					System.out.println("��������");
					System.out.println("�뿩��/��ü��\t�ݾ�\tű�����ȣ\t�����ð�");
					for (String useList : list1) {
						System.out.println(useList);
					}
				}
				break;
			case 4:
				int idSales = userManager.idSales(id);
				System.out.println("�Ѱ����ݾ�: " + idSales + "��");
				break;
			case 5:
				System.out.println("ȸ����������");
				System.out.println(userManager.userinfo(id));
				break;
			case 6:
				System.out.println("ȸ����������");
				UserModifyMenu.userModifyMenu(id);
				break;
			case 7:
				System.out.println("���� �޴��� ���ư��ϴ�...");
				UserMainView.userMainMenu(id);

			default:
				System.out.println("��ȣ�� �߸��Է��ϼ̽��ϴ�.");
			}
		}
	}
}
