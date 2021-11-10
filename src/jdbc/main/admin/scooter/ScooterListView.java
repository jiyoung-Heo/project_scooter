package jdbc.main.admin.scooter;

import java.util.List;
import java.util.Scanner;

import jdbc.main.admin.AdminMainView;
import jdbc.manager.ScooterManager;

public class ScooterListView {

	public static void scooterListView() {
		Scanner sc = new Scanner(System.in);
		ScooterManager scooterManager = new ScooterManager();
		System.out.println("1.���ű���� ��ȸ 2.�뿩����ű���� ��ȸ 3.��ü����ű���� ��ȸ 4.�ڷΰ���");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			List<String> list = scooterManager.allScooterList();
			if (list.isEmpty()) {
				System.out.println("ű���尡 �����ϴ�.");
			} else {
				System.out.println("--��� ű���� ���� ��ȸ--");
				System.out.println("ű�����ȣ\t�뿩����");
				for (String allList : list) {
					System.out.println(allList);
				}
			}
			break;

		case 2:
			List<String> list1 = scooterManager.rentalScooterList();
			if (list1.isEmpty()) {
				System.out.println("�뿩���� ű���尡 �����ϴ�.");
			} else {
				System.out.println("--�뿩 ���� ű���� ���� ��ȸ--");
				System.out.println("ű�����ȣ\t�뿩��ȸ��\t�Ѵ뿩�ð�\t�뿩���۽ð�\t\t�帥�ð�\t��ü����");
				for (String rentalList : list1) {
					System.out.println(rentalList);
				}
			}
			break;

		case 3:
			List<String> list2 = scooterManager.lateScooterList();
			if (list2.isEmpty()) {
				System.out.println("��ü���� ű���尡 �����ϴ�.");
			} else {
				System.out.println("--��ü�� ű���� ��ȸ--");
				System.out.println("ű�����ȣ\t�뿩��ȸ��\t��ü�ð�");
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
