package jdbc.main.admin.user;

import java.util.List;
import java.util.Scanner;

import jdbc.main.admin.AdminMainView;
import jdbc.manager.UserManager;

public class FeeListView {

	public static void feeListMenu() {
		Scanner sc = new Scanner(System.in);
		UserManager userManager = new UserManager();
		while (true) {
			System.out.println("1.�Ѹ�����ȸ 2.�뿩����� 3.��ü����� 4.�ڷΰ���");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				List<String> allFeeList = userManager.allSales();
				if (allFeeList.isEmpty()) {
					System.out.println("������ �����ϴ�.");
				} else {
					System.out.println("�� ���� ��ȸ");
					System.out.println("��¥\t\t�Ѹ���");
					for (String list : allFeeList) {
						System.out.println(list);
					}
				}
				break;
			case 2:
				List<String> borrowFeeList = userManager.feeSales();
				if (borrowFeeList.isEmpty()) {
					System.out.println("�뿩�� ������ �����ϴ�.");
				} else {
					System.out.println("�뿩�� ���� ��ȸ");
					System.out.println("��¥\t\t�뿩�����");
					for (String list : borrowFeeList) {
						System.out.println(list);
					}
				}
				break;
			case 3:
				List<String> lateFeeList = userManager.lateFeeSales();
				if (lateFeeList.isEmpty()) {
					System.out.println("��ü�� ������ �����ϴ�.");
				} else {
					System.out.println("��ü�� ���� ��ȸ");
					System.out.println("��¥\t\t��ü�����");
					for (String list : lateFeeList) {
						System.out.println(list);
					}
				}
				break;
			case 4:
				System.out.println("�����޴��� ���ư��ϴ�.");
				AdminMainView.adminMainMenu();
				break;
			default:
				System.out.println("��ȣ�� �߸��Է��ϼ̽��ϴ�.");
			}
		}
	}
}
