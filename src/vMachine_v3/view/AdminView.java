package vMachine_v3.view;

import vMachine_v3.dto.DrinkDto;
import vMachine_v3.dto.MemberDto;
import vMachine_v3.dto.SalesDto;
import vMachine_v3.service.DrinkService;
import vMachine_v3.service.MemberService;
import vMachine_v3.service.SalesService;

import java.util.List;
import java.util.Scanner;

public class AdminView {
    private final DrinkView drinkView;
    private final MemberView memberView;
    private final MemberService memberService;
    private final DrinkService drinkService;
    private final SalesService salesService;
    private final Scanner sc;

    public AdminView(DrinkView drinkView, MemberView memberView, MemberService memberService, DrinkService drinkService, SalesService salesService, Scanner sc) {
        this.drinkView = drinkView;
        this.memberView = memberView;
        this.memberService = memberService;
        this.drinkService = drinkService;
        this.salesService = salesService;
        this.sc = sc;
    }

    public void login(MemberDto memberDto) {
        System.out.println("===================");
        System.out.println("            관리자 메뉴");
        System.out.println("===================");

        while (true) {
            System.out.println("1. 자판기 관리");
            System.out.println("2. 회원 관리");
            System.out.println("3. 판매 관리");
            System.out.println("4. 로그아웃");
            System.out.print(">  ");
            int choice = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1: // 자판기 관리
                    drinkView.vendingManagement();
                    break;
                case 2: // 회원 관리
                    memberView.memberManagement();
                    break;
                case 3: // 판매 관리
                    System.out.println("1. 제품별 판매현황");
                    System.out.println("2. 회원별 구매현황");
                    System.out.print(">  ");
                    int salesChoice = sc.nextInt();
                    switch (salesChoice) {
                        case 1:
                            List<SalesDto> summaryByMenu = salesService.getSummaryByMenu();
                            int count = 0;
                            int sum = 0;
                            System.out.println("제품별 판매현황");
                            System.out.println("제품명         판매수량            판매금액");
                            System.out.println("--------------------------------------");
                            for (SalesDto salesDto : summaryByMenu) {
                                DrinkDto salesDrinkDto = drinkService.findMenuById(salesDto.getMenu_id());
                                System.out.println(
                                        salesDrinkDto.getName() + "\t\t\t\t" +
                                                salesDto.getCount() + "개\t\t\t\t" +
                                                salesDto.getSum() + "원");
                                count += salesDto.getCount();
                                sum += salesDto.getSum();
                            }
                            System.out.println("--------------------------------------");
                            System.out.println("합계          " + count + "개     " + sum + "원");
                            break;
                        case 2:
                            List<SalesDto> summaryByMember = salesService.getSummaryByMember();

                            System.out.println("회원별 구매현황");
                            System.out.println("아이디     회원명     구매금액        충전잔액");
                            System.out.println("------------------------------------------");
                            for (SalesDto salesDto : summaryByMember) {
                                MemberDto salesMemberDto = memberService.findById(salesDto.getMember_id());
                                System.out.println(
                                        salesMemberDto.getUserId() + "\t\t" +
                                                salesMemberDto.getName() + "\t\t" +
                                                salesDto.getSum() + "원\t\t" +
                                                salesMemberDto.getBalance() + "원"
                                );
                            }
                            System.out.println("------------------------------------------");
                            break;
                        default:
                            System.out.println("1 ~ 2 중 하나를 입력하세요.");
                            return;
                    }
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
