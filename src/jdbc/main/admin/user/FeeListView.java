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
			System.out.println("1.총매출조회 2.대여료매출 3.연체료매출 4.뒤로가기");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				List<String> allFeeList = userManager.allSales();
				if (allFeeList.isEmpty()) {
					System.out.println("매출이 없습니다.");
				} else {
					System.out.println("총 매출 조회");
					System.out.println("날짜\t\t총매출");
					for (String list : allFeeList) {
						System.out.println(list);
					}
				}
				break;
			case 2:
				List<String> borrowFeeList = userManager.feeSales();
				if (borrowFeeList.isEmpty()) {
					System.out.println("대여료 매출이 없습니다.");
				} else {
					System.out.println("대여료 매출 조회");
					System.out.println("날짜\t\t대여료매출");
					for (String list : borrowFeeList) {
						System.out.println(list);
					}
				}
				break;
			case 3:
				List<String> lateFeeList = userManager.lateFeeSales();
				if (lateFeeList.isEmpty()) {
					System.out.println("연체료 매출이 없습니다.");
				} else {
					System.out.println("연체료 매출 조회");
					System.out.println("날짜\t\t연체료매출");
					for (String list : lateFeeList) {
						System.out.println(list);
					}
				}
				break;
			case 4:
				System.out.println("상위메뉴로 돌아갑니다.");
				AdminMainView.adminMainMenu();
				break;
			default:
				System.out.println("번호를 잘못입력하셨습니다.");
			}
		}
	}
}
