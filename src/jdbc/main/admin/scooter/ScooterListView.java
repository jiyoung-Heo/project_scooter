package jdbc.main.admin.scooter;

import java.util.List;
import java.util.Scanner;

import jdbc.main.admin.AdminMainView;
import jdbc.manager.ScooterManager;

public class ScooterListView {

	public static void scooterListView() {
		Scanner sc = new Scanner(System.in);
		ScooterManager scooterManager = new ScooterManager();
		System.out.println("1.모든킥보드 조회 2.대여중인킥보드 조회 3.연체중인킥보드 조회 4.뒤로가기");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			List<String> list = scooterManager.allScooterList();
			if (list.isEmpty()) {
				System.out.println("킥보드가 없습니다.");
			} else {
				System.out.println("--모든 킥보드 정보 조회--");
				System.out.println("킥보드번호\t대여여부");
				for (String allList : list) {
					System.out.println(allList);
				}
			}
			break;

		case 2:
			List<String> list1 = scooterManager.rentalScooterList();
			if (list1.isEmpty()) {
				System.out.println("대여중인 킥보드가 없습니다.");
			} else {
				System.out.println("--대여 중인 킥보드 정보 조회--");
				System.out.println("킥보드번호\t대여한회원\t총대여시간\t대여시작시간\t\t흐른시간\t연체여부");
				for (String rentalList : list1) {
					System.out.println(rentalList);
				}
			}
			break;

		case 3:
			List<String> list2 = scooterManager.lateScooterList();
			if (list2.isEmpty()) {
				System.out.println("연체중인 킥보드가 없습니다.");
			} else {
				System.out.println("--연체된 킥보드 조회--");
				System.out.println("킥보드번호\t대여한회원\t연체시간");
				for (String lateList : list2) {
					System.out.println(lateList);
				}
			}
			break;

		case 4:
			System.out.println("상위메뉴로 돌아갑니다.");
			AdminMainView.adminMainMenu();
			break;

		default:
			System.out.println("잘못입력하셨습니다.");
		}

	}
}
