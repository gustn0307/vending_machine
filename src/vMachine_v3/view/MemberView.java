package vMachine_v3.view;

import vMachine_v3.dto.DrinkDto;
import vMachine_v3.dto.MemberDto;
import vMachine_v3.dto.SalesDto;
import vMachine_v3.service.DrinkService;
import vMachine_v3.service.MemberService;
import vMachine_v3.service.SalesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberView {
    private final MemberService memberService;
    private final DrinkService drinkService;
    private final SalesService salesService;
    private final Scanner sc;


    public MemberView(MemberService memberService, DrinkService drinkService, SalesService salesService, Scanner sc) {
        this.memberService = memberService;
        this.drinkService = drinkService;
        this.salesService = salesService;
        this.sc = sc;
    }

    public void register() {
        List<MemberDto> memberDtoList = memberService.getAll();
        boolean userIdCheck = true;
        String userId = "";
        System.out.println("회원가입 창입니다.");

        do { // 중복 아이디 체크
            userIdCheck = true;
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

            if ((sum % 10) != 0) { // 4번 조건
                System.out.println("유효하지 않은 카드번호입니다.");
            }
        } while ((sum % 10) != 0);

        for (String str : strCreditDigits) {  // (sum % 10) == 0 이면 cardNum에 입력받은 카드번호 저장
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
        while (true) {
            System.out.println();
            System.out.println("1. 메뉴보기");
            System.out.println("2. 음료 구매");
            System.out.println("3. 금액 충전");
            System.out.println("4. 구매 내역");
            System.out.println("5. 로그아웃");
            System.out.print(">  ");
            int choice = sc.nextInt();
            List<DrinkDto> drinkDtoList = drinkService.getAll();
            switch (choice) {
                case 1: // 메뉴보기
                    System.out.println("ID      제품명     가격      재고");
                    System.out.println("-----------------------------");
                    for (DrinkDto dto : drinkDtoList) {
                        System.out.println(dto);
                    }
                    System.out.println("-----------------------------");
                    break;
                case 2: // 음료 구매
                    System.out.println("음료구매 창입니다.");
                    System.out.print("구매할 음료 ID: ");
                    int menuId = sc.nextInt(); // @@음료 리스트의 ID 중 하나 인지 체크 구현 필요@@

                    for (DrinkDto dto : drinkDtoList) { // 잔액 부족, 재고 0 이면 구매 불가
                        if (dto.getId() == menuId) {
                            if (dto.getStock() == 0) { // 재고 부족
                                System.out.println("재고가 없어 구매 불가합니다.");
                                return;
                            }
                            if (memberDto.getBalance() < dto.getPrice()) { // 잔액 부족
                                System.out.println("잔액 부족하여 구매 불가합니다.");
                                return;
                            }
                        }
                    }
                    int success = drinkService.sell(memberDto.getId(), menuId); // 구매, sales 테이블에 기록
                    if (success == 1)
                        System.out.println("구매 완료");
                    else
                        System.out.println("구매 실패");
                    // 구매 후 balance 변경되므로 DB에서 새로 찾아오기
                    MemberDto afterSellMemberDto = memberService.findById(memberDto.getId());
                    System.out.println("잔액: " + afterSellMemberDto.getBalance());
                    break;
                case 3: // 금액 충전
                    System.out.println("금액 충전 창입니다.");
                    int addBalance = 0;

                    do {
                        System.out.print("충전할 금액(1,000원 단위): ");
                        addBalance = sc.nextInt();
                        sc.nextLine(); // 버퍼 비우기
                        if ((addBalance % 1000) != 0)
                            System.out.println("1,000원 단위로 입력해주세요.");
                        if (addBalance < 0)
                            System.out.println("음수 입력 불가! 다시 입력하세요.");
                    } while ((addBalance % 1000) != 0 || addBalance < 0);

                    memberDto.setBalance(memberDto.getBalance() + addBalance);
                    int result = memberService.update(memberDto);
                    if (result == 1)
                        System.out.println("금액 충전 성공");
                    else
                        System.out.println("금액 충전 실패");
                    break;
                case 4: // 구매 내역
                    List<SalesDto> salesDtoList = salesService.getByMember(memberDto.getId());
                    int total = 0;

                    System.out.println("구매일시                            제품명     금액");
                    System.out.println("-------------------------------------------");
                    for (SalesDto salesDto : salesDtoList) {
                        DrinkDto drinkDto = drinkService.getById(salesDto.getMenu_id());
                        System.out.println(salesDto.getSold_at() + "\t\t" + drinkDto.getName() + "\t\t" + drinkDto.getPrice() + "원");
                        total += drinkDto.getPrice();
                    }
                    System.out.println("-------------------------------------------");
                    System.out.println("총 구매금액: " + total);
                    break;
                case 5: // 로그아웃
                    System.out.println("로그아웃 합니다.");
                    return;
                default:
                    System.out.println("1 ~ 5 중 하나를 선택해주세요.");
            }
        }
    }

    public void memberManagement() {
        int choice = 0;
        do {
            System.out.println("2-1. 회원 추가");
            System.out.println("2-2. 회원 수정");
            System.out.println("2-3. 회원 삭제");
            System.out.println("2-4. 전체 조회");
            System.out.print("1 ~ 4 중 하나 입력:  ");
            choice = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기
            if (choice < 1 || choice > 4)
                System.out.println("1 ~ 4중 하나를 입력해 주세요.");
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1: // 회원 추가
                register();
                break;
            case 2: // 회원 수정
                System.out.print("수정할 회원 ID: ");
                int updateId = sc.nextInt();
                sc.nextLine(); // 버퍼 비우기
                MemberDto originMember = memberService.findById(updateId);
                if (originMember.getId() == 0) {
                    System.out.println("존재하지 않는 회원입니다.");
                    return;
                }
                System.out.println("ID: " + originMember.getId() + " 회원 수정");
                System.out.println("수정 전 회원 사용자 ID: " + originMember.getUserId());
                System.out.print("수정할 사용자 ID: ");
                String updateUserId = sc.nextLine();
                System.out.println("수정 전 회원 비밀번호: " + originMember.getPassword());
                System.out.print("수정할 비밀번호: ");
                String updatePassword = sc.nextLine();
                System.out.println("수정 전 회원 이름: " + originMember.getName());
                System.out.print("수정할 이름: ");
                String updateName = sc.nextLine();
                System.out.println("수정 전 회원 전화번호: " + originMember.getTel());
                System.out.print("수정할 전화번호: ");
                String updateTel = sc.nextLine();
                System.out.println("수정 전 회원 잔액: " + originMember.getBalance());
                System.out.print("수정할 잔액: ");
                int updateBalance = sc.nextInt();
                sc.nextLine(); // 버퍼 비우기

                // 신용카드 번호 16자리를 입력받아 Luhn 알고리즘으로 유효한지 검증
                // Luhn 알고리즘
                // 1. 오른쪽 끝에서 짝수 번째 자리(끝에서 2번째, 4번째, ...) 숫자를 2배한다.
                // 2. 2배한 값이 9를 초과하면 9를 뺀다.
                // 3. 모든 자리의 숫자를 더한다.
                // 4. 합계가 10의 배수이면 유효한 카드번호
                int sum = 0; // 모든 자리의 숫자를 더한 값의 저장을 위한 변수 sum
                String updateCardNum = "";
                String[] strCreditDigits;
                do {
                    System.out.println("수정 전 회원 카드번호: " + originMember.getCardNum());
                    System.out.print("수정할 카드번호(16자리): "); // 카드번호 유효성: Luhn 알고리즘(CH 07 실습 10번)

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

                    if ((sum % 10) != 0) { // 4번 조건
                        System.out.println("유효하지 않은 카드번호입니다.");
                    }
                } while ((sum % 10) != 0);

                for (String str : strCreditDigits) {  // (sum % 10) == 0 이면 cardNum에 입력받은 카드번호 저장
                    updateCardNum = updateCardNum.concat(str);
                }

                MemberDto memberDto = new MemberDto(updateId, updateUserId, updatePassword, updateName, updateTel, updateBalance, updateCardNum);
                int updateResult = memberService.update(memberDto);

                if (updateResult == 1) {
                    System.out.println("회원 수정 완료");
                } else {
                    System.out.println("회원 수정 실패");
                }
                break;
            case 3: // 회원 삭제
                System.out.print("삭제할 회원 ID: ");
                int deleteId = sc.nextInt();
                sc.nextLine(); // 버퍼 비우기
                MemberDto deleteMember = memberService.findById(deleteId);
                if (deleteMember.getId() == 0) {
                    System.out.println("존재하지 않는 회원입니다.");
                    return;
                }

                int deleteResult = memberService.delete(deleteId);

                if (deleteResult == 1) {
                    System.out.println("회원 삭제 완료");
                } else {
                    System.out.println("회원 삭제 실패");
                }
                break;
            case 4: // 전체 조회
                List<MemberDto> memberDtoList = memberService.getAll();
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println("ID          USER_ID     PASSWORD        NAME        TEL             BALANCE     CARD_NUM");
                System.out.println("-----------------------------------------------------------------------------------------");
                memberDtoList.forEach(x -> System.out.println(x));
                System.out.println("----------------------------------------------------------------------------------------------------------");
                break;
        }
    }
}