package jdbc.main.user;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import jdbc.main.main.MainView;
import jdbc.manager.ScooterManager;
import jdbc.util.AlreadyRentalException;
import jdbc.util.WrongNumberException;

public class UserMainView {

	public static void userMainMenu(String id) {
		Scanner sc = new Scanner(System.in);
		ScooterManager scooterManager = new ScooterManager();

		while (true) {
			System.out.println("1.ű����뿩 2.ű����ݳ� 3.������ ���� 4.�뿩����ű�������� 5. �α׾ƿ� 6.����");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				try {
					System.out.println("ű���� ��ȣ: ");
					int num = sc.nextInt();
					System.out.println("�����ð�: ");
					int min = sc.nextInt();
					scooterManager.borrowScooter(id, num, min);
					System.out.println("ű���� �뿩 �Ϸ�! �ð������� ���۵˴ϴ�.");
				} catch (WrongNumberException e) {
					System.out.println("ű���尡 �������� �ʽ��ϴ�. ű���带 �߰����ּ���");
				} catch (InputMismatchException ei) {
					System.out.println("���ڸ� �Է����ּ���.");
					sc.nextLine();
				} catch (AlreadyRentalException ex) {
					System.out.println("�뿩���� ű���� ��ȣ�Դϴ�.");
				}
				break;
			case 2:
				System.out.println("�ݳ��� ű���� ��ȣ: ");
				try {
					int returnnum = sc.nextInt();
					scooterManager.returnScooter(id, returnnum);
				} catch (WrongNumberException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				UserListView.userlistview(id);
				break;
			case 4:
				List<String> list = scooterManager.rentalScooterListById(id);
				if (list.isEmpty()) {
					System.out.println("�뿩���� ű���尡 �����ϴ�.");
				} else {
					System.out.println("--�뿩���� ű���� ����--");
					System.out.println("ű�����ȣ\t�뿩�ð�\t���۽ð�\t�����ð�\t��ü����\t��ü�ð�");
					for (String rentalList : list) {
						System.out.println(rentalList);
					}
				}
				break;
			case 5:
				System.out.println("�α׾ƿ�");
				MainView.borrowSystem();
			case 6:
				System.out.println("�����մϴ�...");
				System.exit(0);
			default:
				System.out.println("�߸��� ��ȣ�� �Է��߽��ϴ�.");

			}
		}
	}
}
