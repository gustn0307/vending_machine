package vMachine_v3.view;

import vMachine_v3.dto.MemberDto;
import vMachine_v3.service.MemberService;

import java.util.List;
import java.util.Scanner;

public class MemberView {
    private final MemberService memberService;
    private final Scanner sc;

    public MemberView(MemberService memberService, Scanner sc) {
        this.memberService = memberService;
        this.sc = sc;
    }

    public void register() {
        List<MemberDto> memberDtoList = memberService.getAll();
        boolean userIdCheck = true;
        String userId = "";
        System.out.println("회원가입 창입니다.");

        do { // 중복 아이디 체크
            System.out.print("아이디: ");
            userId = sc.nextLine();
            for (MemberDto memberDto : memberDtoList) {
                if (memberDto.getUserId().equals(userId)) {
                    userIdCheck = false;
                }
            }
        } while (!userIdCheck);

        System.out.print("비밀번호: ");
        String password = sc.nextLine();
        System.out.print("이름: ");
        String name = sc.nextLine();
        System.out.print("전화번호: ");
        String tel = sc.nextLine();
        System.out.print("카드번호: ");
        String cardNum = sc.nextLine();

        MemberDto memberDto = new MemberDto(userId, password, name, tel, cardNum);
        boolean check = memberService.register(memberDto);

        if (check) {
            System.out.println("회원가입 완료");
        } else {
            System.out.println("회원가입 실패");
        }
    }

    public void login(MemberDto memberDto) {
        System.out.println("===========================================");
        System.out.println("안녕하세요, [" + memberDto.getName() + "]님! 잔액: [" + memberDto.getBalance()+"]원");
        System.out.println("===========================================");
        System.out.println("1. 메뉴보기");
        System.out.println("2. 음료 구매");
        System.out.println("3. 금액 충전");
        System.out.println("4. 구매 내역");
        System.out.println("5. 로그아웃");
        System.out.print(">  ");

    }
}