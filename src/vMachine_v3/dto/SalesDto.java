package vMachine_v3.dto;

import java.time.LocalDateTime;

public class SalesDto {
    private int id;
    private int member_id;
    private  int menu_id;
    private int price;
    private LocalDateTime sold_at;

    public SalesDto(int id, int member_id, int menu_id, int price) {
        this.id = id;
        this.member_id = member_id;
        this.menu_id = menu_id;
        this.price = price;
        sold_at = LocalDateTime.now();
    }
}
