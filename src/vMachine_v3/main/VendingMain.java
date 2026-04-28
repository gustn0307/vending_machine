package vMachine_v3.main;

import vMachine_v3.db.DBConn;
import vMachine_v3.dto.MemberDto;
import vMachine_v3.repository.Repository;
import vMachine_v3.service.MemberService;
import vMachine_v3.view.AdminView;
import vMachine_v3.view.LoginView;
import vMachine_v3.view.MemberView;

import java.sql.Connection;
import java.util.Scanner;

public class VendingMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection conn = DBConn.getConnection();
        Repository repository = new Repository(conn);
        MemberService memberService = new MemberService(repository);
        MemberView memberView = new MemberView(memberService, sc);
        LoginView loginView = new LoginView(memberService, sc);
        AdminView adminView = new AdminView();

        while (true) {
            int input;
            do {
                System.out.println("=====================================");
                System.out.println("  \uD83E\uDD64 IVE 자판기에 오신 걸 환영합니다");
                System.out.println("=====================================");
                System.out.println("1. 회원가입  2. 로그인  3. 종료");
                System.out.print(">  ");
                input = sc.nextInt(); // ## 타입 다른 입력했을 때 예외처리 필요 ##
                sc.nextLine(); // 버퍼 비우기
                if (input < 0 || input > 3) {
                    System.out.println("1 ~ 3 중 하나를 입력해주세요.");
                }
            } while (input < 0 || input > 3);

            switch (input) {
                case 1: // 회원가입
                    memberView.register();
                    break;
                case 2: // 로그인(사용자or관리자)
                    MemberDto memberDto = loginView.login(); // 사용자인지 관리자인지 판별
                    if (!memberDto.getIsAdmin()) {
                        memberView.login(memberDto); // 사용자 로그인 된 후 화면
                    } else {
                        adminView.login(memberDto); // 관리자 로그인 된 후 화면
                    }
                    break;
                case 3: // 종료
                    System.out.println("종료합니다.");
                    DBConn.close();
                    return;
                default: // 위의 do-while문때문에 input값은 0~5 중 하나이므로 default 필요없음(생략 가능)
            }
        }
    }
}
