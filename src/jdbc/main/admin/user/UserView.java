package jdbc.main.admin.user;

import java.util.Scanner;

import jdbc.dao.user.vo.UserVo;
import jdbc.manager.UserManager;
import jdbc.util.DuplicateIDException;

public class UserView {
	Scanner sc = new Scanner(System.in);
	UserManager userManager = new UserManager();

	public void createUser() {
		String id = makeString("아이디", 15);
		String pwd = makeString("비밀번호", 15);
		String name = makeString("이름", 10);
		String phone = makeString("전화번호", 15);

		UserVo vo = new UserVo(id, pwd, name, phone, 0);

		try {
			userManager.createUser(vo);
			System.out.println("회원가입이 완료되었습니다.");
			System.out.println("회원가입선물로 10000 포인트를 드립니다.");
		} catch (DuplicateIDException e) {
			System.out.println("이미 존재하는 아이디입니다.");
		}
	}

	private String makeString(String key, int length) {
		String result = "";
		while (true) {
			System.out.print(key + ": ");
			result = sc.next();
			if (result.length() > length) {
				System.out.println(key + "가 길어요..해당" + key + "는 불가합니다.");
			} else {
				break;
			}
		}
		return result;
	}
}
