package jdbc.main.user;

import java.util.Scanner;

import jdbc.manager.UserManager;

public class UserModifyMenu {
	public static void userModifyMenu(String id) {
		Scanner sc = new Scanner(System.in);
		UserManager userManager = new UserManager();
		while (true) {
			System.out.println("1.비밀번호변경 2.이름변경 3.전화번호변경 4.뒤로가기");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				System.out.println("변경할 비밀번호를 입력하세요");
				String pwd = sc.next();
				userManager.modifyPwd(id, pwd);
				System.out.println("비밀번호가 변경되었습니다.");
				break;
			case 2:
				System.out.println("변경할 이름을 입력하세요");
				String name = sc.next();
				userManager.modifyName(id, name);
				System.out.println("이름이 변경되었습니다.");
				break;
			case 3:
				System.out.println("변경할 전화번호를 입력하세요");
				String phone = sc.next();
				userManager.modifyPhone(id, phone);
				System.out.println("전화번호가 변경되었습니다.");
				break;
			case 4:
				System.out.println("상위 메뉴로 돌아갑니다...");
				UserListView.userlistview(id);

			default:
				System.out.println("번호를 잘못입력하셨습니다.");
			}
		}
	}
}
