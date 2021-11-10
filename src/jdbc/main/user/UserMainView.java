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
			System.out.println("1.킥보드대여 2.킥보드반납 3.내정보 보기 4.대여중인킥보드정보 5. 로그아웃 6.종료");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				try {
					System.out.println("킥보드 번호: ");
					int num = sc.nextInt();
					System.out.println("빌릴시간: ");
					int min = sc.nextInt();
					scooterManager.borrowScooter(id, num, min);
					System.out.println("킥보드 대여 완료! 시간차감이 시작됩니다.");
				} catch (WrongNumberException e) {
					System.out.println("킥보드가 존재하지 않습니다. 킥보드를 추가해주세요");
				} catch (InputMismatchException ei) {
					System.out.println("숫자를 입력해주세요.");
					sc.nextLine();
				} catch (AlreadyRentalException ex) {
					System.out.println("대여중인 킥보드 번호입니다.");
				}
				break;
			case 2:
				System.out.println("반납할 킥보드 번호: ");
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
					System.out.println("대여중인 킥보드가 없습니다.");
				} else {
					System.out.println("--대여중인 킥보드 정보--");
					System.out.println("킥보드번호\t대여시간\t시작시간\t지난시간\t연체여부\t연체시간");
					for (String rentalList : list) {
						System.out.println(rentalList);
					}
				}
				break;
			case 5:
				System.out.println("로그아웃");
				MainView.borrowSystem();
			case 6:
				System.out.println("종료합니다...");
				System.exit(0);
			default:
				System.out.println("잘못된 번호를 입력했습니다.");

			}
		}
	}
}
