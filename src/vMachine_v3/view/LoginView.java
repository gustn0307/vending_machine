package vMachine_v3.view;

import vMachine_v3.dto.MemberDto;
import vMachine_v3.service.MemberService;

import java.util.Scanner;

public class LoginView {
    private final MemberService memberService;
    private final Scanner sc;

    public LoginView(MemberService memberService, Scanner sc) {
        this.memberService = memberService;
        this.sc = sc;
    }

    public MemberDto login() {
        System.out.print("ID: ");
        String userId = sc.nextLine();
        System.out.print("비밀번호: ");
        String password = sc.nextLine();
        return memberService.login(userId, password);
    }
}
