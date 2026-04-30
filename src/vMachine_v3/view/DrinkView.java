package vMachine_v3.view;

import vMachine_v3.dto.DrinkDto;
import vMachine_v3.service.DrinkService;

import java.util.List;
import java.util.Scanner;

public class DrinkView {
    private final DrinkService drinkService;
    private final Scanner sc;

    public DrinkView(DrinkService drinkService, Scanner sc) {
        this.drinkService = drinkService;
        this.sc = sc;
    }


    public void vendingManagement() {
        int choice = 0;
        do {
            System.out.println("1-1. 메뉴 추가");
            System.out.println("1-2. 메뉴 수정");
            System.out.println("1-3. 메뉴 삭제");
            System.out.println("1-4. 전체 조회");
            System.out.print("1 ~ 4 중 하나 입력:  ");
            choice = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기
            if (choice < 1 || choice > 4)
                System.out.println("1 ~ 4중 하나를 입력해 주세요.");
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1: // 메뉴 추가
                System.out.print("메뉴 이름: ");
                String name = sc.nextLine();
                System.out.print("메뉴 가격: ");
                int price = sc.nextInt();
                sc.nextLine(); // 버퍼 비우기
                System.out.print("메뉴 재고: ");
                int stock = sc.nextInt();
                sc.nextLine(); // 버퍼 비우기
                System.out.println(name);
                DrinkDto drinkDto = new DrinkDto(name, price, stock);
                System.out.println("DrinkView 추가할 dto: " + drinkDto);
                int result = drinkService.insert(drinkDto);
                if (result == 1) {
                    System.out.println("추가되었습니다.");
                } else {
                    System.out.println("추가 실패");
                }
                break;
            case 2: // 메뉴 수정
                System.out.print("수정할 메뉴 ID: ");
                int updateId = sc.nextInt();
                sc.nextLine(); // 버퍼 비우기
                DrinkDto originMenu = drinkService.findMenuById(updateId);
                if (originMenu.getId() == 0) { // 해당 메뉴가 없으면 리턴
                    System.out.println("해당 ID를 가진 메뉴는 없습니다.");
                    return;
                }
                System.out.println("ID: [" + originMenu.getId() + "]  메뉴 수정");
                System.out.println("수정 전 메뉴 이름: " + originMenu.getName());
                System.out.print("수정 할 메뉴 이름: ");
                String updateName = sc.nextLine();
                System.out.println("수정 전 메뉴 가격: " + originMenu.getPrice());
                System.out.print("수정 할 메뉴 가격: ");
                int updatePrice = sc.nextInt();
                sc.nextLine(); // 버퍼 비우기
                System.out.println("수정 전 메뉴 재고: " + originMenu.getStock());
                System.out.print("수정 할 메뉴 재고: ");
                int updateStock = sc.nextInt();
                sc.nextLine(); // 버퍼 비우기
                DrinkDto updateMenu = new DrinkDto(updateId, updateName, updatePrice, updateStock);
                int updateResult = drinkService.update(updateMenu);

                if (updateResult == 1) {
                    System.out.println("수정되었습니다.");
                } else {
                    System.out.println("수정 실패");
                }
                break;
            case 3: // 메뉴 삭제
                System.out.print("삭제할 메뉴 ID: ");
                int deleteId = sc.nextInt();
                sc.nextLine(); // 버퍼 비우기
                DrinkDto originMenu2 = drinkService.findMenuById(deleteId);
                if (originMenu2.getId() == 0) { // 해당 메뉴가 없으면 리턴
                    System.out.println("해당 ID를 가진 메뉴는 없습니다.");
                    return;
                }

                int deleteResult = drinkService.delete(deleteId);

                if (deleteResult == 1) {
                    System.out.println("삭제되었습니다.");
                } else {
                    System.out.println("삭제 실패");
                }
                break;
            case 4: // 전체 조회
                List<DrinkDto> drinkDtoList = drinkService.getAll();
                System.out.println("------------------------------");
                System.out.println("ID      이름          가격        재고");
                System.out.println("------------------------------");
                drinkDtoList.forEach(x -> System.out.println(x));
                System.out.println("------------------------------");
                break;
        }
    }
}