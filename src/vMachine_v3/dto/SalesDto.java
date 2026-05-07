package vMachine_v3.dto;

import java.time.LocalDateTime;

public class SalesDto {
    private int id;
    private int member_id;
    private  int menu_id;
    private int price;
    private LocalDateTime sold_at;
    private int count;
    private int sum;

    public SalesDto(int menu_id, int count, int sum) {
        this.menu_id = menu_id;
        this.count = count;
        this.sum = sum;
    }

    public SalesDto(int memberId, int sum) {
        this.member_id = memberId;
        this.sum = sum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

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

    public SalesDto(int id, int member_id, int menu_id, int price, LocalDateTime sold_at) {
        this.id = id;
        this.member_id = member_id;
        this.menu_id = menu_id;
        this.price = price;
        this.sold_at = sold_at;
    }

    public SalesDto() {
    }
}