package vMachine_v3.repository;

import vMachine_v3.dto.DrinkDto;
import vMachine_v3.dto.MemberDto;
import vMachine_v3.dto.SalesDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
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

    public MemberDto findMemberByUserId(String userId, String password) {
        MemberDto memberDto = new MemberDto();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM member WHERE user_id = ? AND password = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, userId);
            psmt.setString(2, password);

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

    public MemberDto findMemberById(int id) {
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

    public DrinkDto findDrinkById(int menuId) {
        DrinkDto drinkDto = new DrinkDto();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM vending_menu WHERE id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, menuId);

            rs = psmt.executeQuery();

            while (rs.next()) {
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
    public int sell(int memberId, int menuId) {
        int result = 0;
        PreparedStatement psmt = null;
        MemberDto memberDto = findMemberById(memberId);
        DrinkDto drinkDto = findDrinkById(menuId);

        try {
            String sql = "UPDATE member SET balance = ? WHERE id = ?"; // 멤버 잔액 줄이기
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, memberDto.getBalance() - drinkDto.getPrice());
            psmt.setInt(2, memberId);

            result = psmt.executeUpdate();

            sql = "UPDATE vending_menu SET stock = ? WHERE id = ?"; // 음료 재고 줄이기
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, drinkDto.getStock() - 1);
            psmt.setInt(2, drinkDto.getId());

            result = psmt.executeUpdate();

            sql = "INSERT INTO sales (member_id, menu_id, price) values (?, ?, ?)"; // 멤버의 구매 내역 sales 테이블에 기록
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, memberId);
            psmt.setInt(2, menuId);
            psmt.setInt(3, drinkDto.getPrice());

            result = psmt.executeUpdate();

            psmt.close(); // 사용 후 닫아주기
        } catch (Exception e) {
            System.out.println("sell() 오류: " + e.getMessage());
        }
        return result;
    }

    public int update(MemberDto memberDto) {
        PreparedStatement psmt = null;
        int result = 0;

        try {
            String sql = "UPDATE member SET user_id = ?, password = ?, name = ?, tel = ?, balance = ?, card_num = ? WHERE id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, memberDto.getUserId());
            psmt.setString(2, memberDto.getPassword());
            psmt.setString(3, memberDto.getName());
            psmt.setString(4, memberDto.getTel());
            psmt.setInt(5, memberDto.getBalance());
            psmt.setString(6, memberDto.getCardNum());
            psmt.setInt(7, memberDto.getId());

            result = psmt.executeUpdate();

            psmt.close();
        } catch (Exception e) {
            System.out.println("update() 오류: " + e.getMessage());
        }
        return result;
    }

    public List<SalesDto> findAllSalesByMember(int id) {
        List<SalesDto> salesDtoList = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM sales WHERE member_id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);

            rs = psmt.executeQuery();

            while (rs.next()) {
                SalesDto salesDto = new SalesDto(
                        rs.getInt("id"),
                        id,
                        rs.getInt("menu_id"),
                        rs.getInt("price"),
                        rs.getObject("sold_at", LocalDateTime.class)
                );
                salesDtoList.add(salesDto);
            }
            psmt.close(); // 사용 후 닫아주기
            rs.close(); // 사용 후 닫아주기
        } catch (Exception e) {
            System.out.println("findAllSalesByMember() 오류: " + e.getMessage());
        }
        return salesDtoList;
    }
}
