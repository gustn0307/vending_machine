package vMachine_v3.view;

import vMachine_v3.dto.MemberDto;
import vMachine_v3.service.MemberService;

import java.util.Scanner;

public class AdminView {
    MemberService memberService;
    Scanner sc;

    public AdminView(MemberService memberService, Scanner sc) {
        this.memberService = memberService;
        this.sc = sc;
    }

    public void login(MemberDto memberDto) {
        System.out.println("===================");
        System.out.println("            관리자 메뉴");
        System.out.println("===================");

        while (true){
            System.out.println();
            System.out.println("1. 자판기 관리");
            System.out.println("2. 회원 관리");
            System.out.println("3. 판매 관리");
            System.out.println("4. 로그아웃");

            int choice = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1: // 자판기 관리
                    // ###############
                    break;
                case 2: // 회원 관리
                    break;
                case 3: // 판매 관리
                    break;
                case 4: // 로그아웃
                    System.out.println("로그아웃 합니다.");
                    return;
                default:
                    System.out.println("1 ~ 4 중 하나를 입력해 주세요.");

            }
        }
    }
}
