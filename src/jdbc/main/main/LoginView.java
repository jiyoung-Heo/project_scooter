package jdbc.main.main;

import java.util.Scanner;

import jdbc.main.admin.AdminMainView;
import jdbc.main.user.UserMainView;
import jdbc.manager.UserManager;

public class LoginView {
	Scanner sc = new Scanner(System.in);
	UserManager userManager = new UserManager();

	public void login() {
		System.out.print("아이디: ");
		String id = sc.next();
		System.out.print("비밀번호: ");
		String pwd = sc.next();
		String userId = userManager.login(id, pwd);
		if (userId.equals("")) {
			System.out.println("아이디또는 비밀번호가 일치하지 않습니다.");
		} else if (userId.equals("admin")) {
			System.out.println("--관리자권한으로 접속하였습니다--");
			AdminMainView.adminMainMenu();
		} else {
			System.out.println(userId + " 님이 로그인하였습니다.");
			UserMainView.userMainMenu(userId);
		}
	}
}
