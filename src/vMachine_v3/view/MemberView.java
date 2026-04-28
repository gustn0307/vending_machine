package vMachine_v3.view;

import vMachine_v3.dto.DrinkDto;
import vMachine_v3.dto.MemberDto;
import vMachine_v3.service.DrinkService;
import vMachine_v3.service.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberView {
    private final MemberService memberService;
    private final DrinkService drinkService;
    private final Scanner sc;


    public MemberView(MemberService memberService, DrinkService drinkService, Scanner sc) {
        this.memberService = memberService;
        this.drinkService = drinkService;
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
                    System.out.println("중복된 아이디 입니다. 다시 입력해주세요.");
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

        // 신용카드 번호 16자리를 입력받아 Luhn 알고리즘으로 유효한지 검증
        // Luhn 알고리즘
        // 1. 오른쪽 끝에서 짝수 번째 자리(끝에서 2번째, 4번째, ...) 숫자를 2배한다.
        // 2. 2배한 값이 9를 초과하면 9를 뺀다.
        // 3. 모든 자리의 숫자를 더한다.
        // 4. 합계가 10의 배수이면 유효한 카드번호
        int sum = 0; // 모든 자리의 숫자를 더한 값의 저장을 위한 변수 sum
        String cardNum = "";
        String[] strCreditDigits;
        do {
            System.out.print("카드번호(16자리): "); // 카드번호 유효성: Luhn 알고리즘(CH 07 실습 10번)

            strCreditDigits = sc.next().split(""); // 입력받아서 한글자씩 배열에 저장
            int[] intCreditDigits = new int[strCreditDigits.length]; // 계산 및 비교를 위해 String 타입을 int형으로 변환하기 위해 필요한 배열

            for (int i = 0; i < intCreditDigits.length; i++) // 계산이나 비교를 위해 String 타입을 int형으로 변환
                intCreditDigits[i] = Integer.parseInt(strCreditDigits[i]);

            for (int i = 1; i <= intCreditDigits.length; i++) { // Luhn 알고리즘
                if (i % 2 == 0) {
                    intCreditDigits[intCreditDigits.length - i] *= 2; // 1번 조건
                    if (intCreditDigits[intCreditDigits.length - i] > 9) {
                        intCreditDigits[intCreditDigits.length - i] -= 9; // 2번 조건
                    }
                }
            }

            for (int intCreditDigit : intCreditDigits) // 3번 조건
                sum += intCreditDigit;

            if ((sum % 10) != 0){ // 4번 조건
                System.out.println("유효하지 않은 카드번호입니다.");
            }
        } while ((sum % 10) != 0);

        for (String str : strCreditDigits){  // (sum % 10) == 0 이면 cardNum에 입력받은 카드번호 저장
            cardNum = cardNum.concat(str);
        }

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
        System.out.println("안녕하세요, [" + memberDto.getName() + "]님! 잔액: [" + memberDto.getBalance() + "]원");
        System.out.println("===========================================");
        while (true){
            System.out.println("1. 메뉴보기");
            System.out.println("2. 음료 구매");
            System.out.println("3. 금액 충전");
            System.out.println("4. 구매 내역");
            System.out.println("5. 로그아웃");
            System.out.print(">  ");
            int choice = sc.nextInt();

            switch (choice){
                case 1: // 메뉴보기
                    List<DrinkDto> drinkDtoList = drinkService.getAll();
                    System.out.println("ID      제품명     가격      재고");
                    System.out.println("-----------------------------");
                    for (DrinkDto dto : drinkDtoList) {
                        System.out.println(dto);
                    }
                    break;
                case 2: // 음료 구매
                    break;
                case 3: // 금액 충전
                    break;
                case 4: // 구매 내역
                    break;
                case 5: // 로그아웃
                    System.out.println("로그아웃 합니다.");
                    return;
                default:
                    System.out.println("1 ~ 5 중 하나를 선택해주세요.");
            }
        }
    }
}