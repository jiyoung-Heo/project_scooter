package jdbc.main.admin.user;

import java.util.List;
import java.util.Scanner;

import jdbc.main.admin.AdminMainView;
import jdbc.manager.UserManager;

public class UserListView {

	public static void userListView() {
		Scanner sc = new Scanner(System.in);
		UserManager userManager = new UserManager();
		System.out.println("1.모든사용자 조회 2.대여중인사용자 조회 3.연체중인사용자 조회 4.뒤로가기");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			List<String> list = userManager.allScooterList();
			if (list.isEmpty()) {
				System.out.println("사용자가 존재하지 않습니다.");
			} else {
				System.out.println("모든 사용자 조회");
				System.out.println("아이디\t비밀번호\t이름\t전화번호\t잔액\t대여중여부\t총대여횟수\t총지불금액");
				for (String allList : list) {
					System.out.println(allList);
				}
			}
			break;
		case 2:
			List<String> list1 = userManager.rentalScooterList();
			if (list1.isEmpty()) {
				System.out.println("대여중인 사용자가 존재하지 않습니다.");
			} else {
				System.out.println("대여중인사용자 조회");
				System.out.println("아이디\t비밀번호\t이름\t전화번호\t잔액\t빌린킥보드번호\t충전한시간\t시작부터흐른시간\t연체여부");
				for (String rentalList : list1) {
					System.out.println(rentalList);
				}
			}
			break;
		case 3:
			List<String> list2 = userManager.lateScooterList();
			if (list2.isEmpty()) {
				System.out.println("연체중인 사용자가 존재하지 않습니다.");
			} else {
				System.out.println("연체중인사용자 조회");
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
