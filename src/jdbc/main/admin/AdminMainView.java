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
			System.out.println("1.킥보드 초기배치시키기 2.킥보드 추가 3.킥보드 조회 4.사용자 조회 5.매출조회 6.로그아웃 7.전체종료");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				System.out.println("몇대의 킥보드를 배치하시겠습니까?");
				int num = sc.nextInt();
				scooterManager.createScooterInfo(num);
				break;
			case 2:
				System.out.println("몇대의 킥보드를 추가하시겠습니까?");
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
				System.out.println("로그아웃합니다.");
				MainView.borrowSystem();
				break;
			case 7:
				System.out.println("시스템을 종료합니다..");
				System.exit(0);
			default:
				System.out.println("잘못입력하셨습니다.");
			}

		}
	}
}
