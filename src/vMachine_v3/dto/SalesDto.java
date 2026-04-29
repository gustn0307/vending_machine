package vMachine_v3.dto;

import java.time.LocalDateTime;

public class SalesDto {
    private int id;
    private int member_id;
    private  int menu_id;
    private int price;
    private LocalDateTime sold_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getSold_at() {
        return sold_at;
    }

    public void setSold_at(LocalDateTime sold_at) {
        this.sold_at = sold_at;
    }

    public SalesDto(int id, int member_id, int menu_id, int price) {
        this.id = id;
        this.member_id = member_id;
        this.menu_id = menu_id;
        this.price = price;
        sold_at = LocalDateTime.now();
    }

}