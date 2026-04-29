package vMachine_v3.repository;

import vMachine_v3.dto.DrinkDto;
import vMachine_v3.dto.MemberDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final Connection conn;

    public Repository(Connection conn) {
        this.conn = conn;
    }

    public boolean insert(MemberDto memberDto) {
        PreparedStatement psmt = null;

        try {
            String sql = "INSERT INTO member (user_id, password, name, tel, card_num) values (?, ?, ?, ?, ?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, memberDto.getUserId());
            psmt.setString(2, memberDto.getPassword());
            psmt.setString(3, memberDto.getName());
            psmt.setString(4, memberDto.getTel());
            psmt.setString(5, memberDto.getCardNum());

            psmt.executeUpdate();
            psmt.close();
        } catch (Exception e) {
            System.out.println("insert() 오류: " + e.getMessage());
        }
        return true;
    }

    public List<MemberDto> findAll() {
        List<MemberDto> memberDtoList = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM member WHERE is_admin = 0";
            psmt = conn.prepareStatement(sql);

            rs = psmt.executeQuery();

            while (rs.next()) {
                MemberDto memberDto = new MemberDto(
                        rs.getInt("id"),
                        rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("tel"),
                        rs.getInt("balance"),
                        rs.getString("card_num")
                );
                memberDtoList.add(memberDto);
            }
            psmt.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("findAll() 오류: " + e.getMessage());
        }

        return memberDtoList;
    }

    public MemberDto findMemberByUserId(String userId) {
        MemberDto memberDto = new MemberDto();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM member WHERE user_id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, userId);

            rs = psmt.executeQuery();

            while (rs.next()) {
                memberDto.setId(rs.getInt("id"));
                memberDto.setUserId(rs.getString("user_id"));
                memberDto.setPassword(rs.getString("password"));
                memberDto.setName(rs.getString("name"));
                memberDto.setTel(rs.getString("tel"));
                memberDto.setBalance(rs.getInt("balance"));
                memberDto.setCardNum(rs.getString("card_num"));
                memberDto.setAdmin(rs.getBoolean("is_admin"));
            }
            psmt.close(); // 사용 후 닫아주기
            rs.close(); // 사용 후 닫아주기
        } catch (Exception e) {
            System.out.println("findMember() 오류: " + e.getMessage());
        }
        return memberDto;
    }

    public MemberDto findMemberById(int id){
        MemberDto memberDto = new MemberDto();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM member WHERE id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);

            rs = psmt.executeQuery();

            while (rs.next()) {
                memberDto.setId(rs.getInt("id"));
                memberDto.setUserId(rs.getString("user_id"));
                memberDto.setPassword(rs.getString("password"));
                memberDto.setName(rs.getString("name"));
                memberDto.setTel(rs.getString("tel"));
                memberDto.setBalance(rs.getInt("balance"));
                memberDto.setCardNum(rs.getString("card_num"));
                memberDto.setAdmin(rs.getBoolean("is_admin"));
            }
            psmt.close(); // 사용 후 닫아주기
            rs.close(); // 사용 후 닫아주기
        } catch (Exception e) {
            System.out.println("findMember() 오류: " + e.getMessage());
        }
        return memberDto;
    }

    public List<DrinkDto> getAllDrink() {
        List<DrinkDto> drinkDtoList = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM vending_menu";
            psmt = conn.prepareStatement(sql);

            rs = psmt.executeQuery();

            while (rs.next()) {
                DrinkDto drinkDto = new DrinkDto(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock")
                );
                drinkDtoList.add(drinkDto);
            }

            psmt.close(); // 사용 후 닫아주기
            rs.close(); // 사용 후 닫아주기
        } catch (Exception e) {
            System.out.println("getAllDrink() 오류: " + e.getMessage());
        }

        return drinkDtoList;
    }

    public DrinkDto findDrinkById(int menuId){
        DrinkDto drinkDto = new DrinkDto();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM vending_menu WHERE id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, menuId);

            rs = psmt.executeQuery();

            while (rs.next()){
                drinkDto.setId(rs.getInt("id"));
                drinkDto.setName(rs.getString("name"));
                drinkDto.setPrice(rs.getInt("price"));
                drinkDto.setStock(rs.getInt("stock"));
            }
            psmt.close(); // 사용 후 닫아주기
            rs.close(); // 사용 후 닫아주기
        } catch (Exception e) {
            System.out.println("findDrinkById() 오류: " + e.getMessage());
        }

        return drinkDto;
    }



    // 멤버의 잔액 줄이고, 음료의 재고 줄이고 멤버의 구매 내역 sales 테이블에 기록
    // -> 쿼리 3개 필요
    public int sell(int id, int menuId) {
        int result = 0;
        PreparedStatement psmt = null;
        MemberDto memberDto = findMemberById(id);
        DrinkDto drinkDto = findDrinkById(menuId);

        try {
            String sql = "UPDATE member SET balance = ? WHERE id = ?"; // 멤버 잔액 줄이기
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, memberDto.getBalance() - drinkDto.getPrice());
            psmt.setInt(2, id);
// ##########여기부터##################
            result = psmt.executeUpdate();

            sql = "UPDATE vending_menu SET stock = ? WHERE id = ?"; // 음료 재고 줄이기
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, drinkDto.getStock() - 1);
            psmt.setInt(2, drinkDto.getId());

            result = psmt.executeUpdate();

            sql = "INSERT INTO sales (id, member_id, menu_id) values (?, ?, ?)"; // 멤버의 구매 내역 sales 테이블에 기록
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);
            psmt.setInt(2, menuId);
            psmt.setInt(3, drinkDto.getPrice());

            result = psmt.executeUpdate();

            psmt.close(); // 사용 후 닫아주기
        } catch (Exception e) {
            System.out.println("sell() 오류: " + e.getMessage());
        }
        return result;
    }
}
