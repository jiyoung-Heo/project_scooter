package jdbc.main.main;

import java.util.Scanner;

import jdbc.main.admin.user.UserView;
import jdbc.manager.UserManager;

public class MainView {
	UserManager userManager = new UserManager();

	public static void main(String[] args) {
		borrowSystem();
	}

	public static void borrowSystem() {
		Scanner sc = new Scanner(System.in);
		System.out.println("<전동킥보드 대여 시스템>");
		while (true) {
			System.out.println("1.로그인 2.회원가입 3.종료");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				LoginView login = new LoginView();
				login.login();
				break;
			case 2:
				UserView user = new UserView();
				user.createUser();
				break;
			case 3:
				System.out.println("종료합니다...");
				System.exit(0);
			default:
				System.out.println("잘못된 번호를 입력했습니다.");
			}
		}
	}

}
