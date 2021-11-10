package jdbc.main.admin.user;

import java.util.List;
import java.util.Scanner;

import jdbc.main.admin.AdminMainView;
import jdbc.manager.UserManager;

public class UserListView {

	public static void userListView() {
		Scanner sc = new Scanner(System.in);
		UserManager userManager = new UserManager();
		System.out.println("1.������� ��ȸ 2.�뿩���λ���� ��ȸ 3.��ü���λ���� ��ȸ 4.�ڷΰ���");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			List<String> list = userManager.allScooterList();
			if (list.isEmpty()) {
				System.out.println("����ڰ� �������� �ʽ��ϴ�.");
			} else {
				System.out.println("��� ����� ��ȸ");
				System.out.println("���̵�\t��й�ȣ\t�̸�\t��ȭ��ȣ\t�ܾ�\t�뿩�߿���\t�Ѵ뿩Ƚ��\t�����ұݾ�");
				for (String allList : list) {
					System.out.println(allList);
				}
			}
			break;
		case 2:
			List<String> list1 = userManager.rentalScooterList();
			if (list1.isEmpty()) {
				System.out.println("�뿩���� ����ڰ� �������� �ʽ��ϴ�.");
			} else {
				System.out.println("�뿩���λ���� ��ȸ");
				System.out.println("���̵�\t��й�ȣ\t�̸�\t��ȭ��ȣ\t�ܾ�\t����ű�����ȣ\t�����ѽð�\t���ۺ����帥�ð�\t��ü����");
				for (String rentalList : list1) {
					System.out.println(rentalList);
				}
			}
			break;
		case 3:
			List<String> list2 = userManager.lateScooterList();
			if (list2.isEmpty()) {
				System.out.println("��ü���� ����ڰ� �������� �ʽ��ϴ�.");
			} else {
				System.out.println("��ü���λ���� ��ȸ");
				for (String lateList : list2) {
					System.out.println(lateList);
				}
			}
			break;
		case 4:
			System.out.println("�����޴��� ���ư��ϴ�.");
			AdminMainView.adminMainMenu();
			break;
		default:
			System.out.println("�߸��Է��ϼ̽��ϴ�.");
		}
	}
}
