package jdbc.main.user;

import java.util.List;
import java.util.Scanner;

import jdbc.manager.UserManager;

public class UserListView {

	public static void userlistview(String id) {
		Scanner sc = new Scanner(System.in);
		UserManager userManager = new UserManager();

		while (true) {
			System.out.println("1.잔액조회 2.킥보드사용내역조회 3.결제내역 4.총결제금액 5.회원정보보기 6.회원정보수정 7.뒤로가기");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				int currentMoney = userManager.currentMoney(id);
				System.out.println(id + "님의 잔액: " + currentMoney + "원");
				break;
			case 2:
				List<String> list = userManager.userScooterHistory(id);
				if (list.isEmpty()) {
					System.out.println("킥보드를 사용한적이 한번도 없습니다.");
				} else {
					System.out.println("사용내역조회");
					System.out.println("킥보드번호\t시작시간\t반납시간\t연체여부\t연체시간\t지불내역");
					for (String useList : list) {
						System.out.println(useList);
					}
				}
				break;
			case 3:
				List<String> list1 = userManager.userpayList(id);
				if (list1.isEmpty()) {
					System.out.println("결제내역이 없습니다.");
				} else {
					System.out.println("결제내역");
					System.out.println("대여료/연체료\t금액\t킥보드번호\t결제시간");
					for (String useList : list1) {
						System.out.println(useList);
					}
				}
				break;
			case 4:
				int idSales = userManager.idSales(id);
				System.out.println("총결제금액: " + idSales + "원");
				break;
			case 5:
				System.out.println("회원정보보기");
				System.out.println(userManager.userinfo(id));
				break;
			case 6:
				System.out.println("회원정보수정");
				UserModifyMenu.userModifyMenu(id);
				break;
			case 7:
				System.out.println("상위 메뉴로 돌아갑니다...");
				UserMainView.userMainMenu(id);

			default:
				System.out.println("번호를 잘못입력하셨습니다.");
			}
		}
	}
}
