package vMachine_v3.dto;

public class MemberDto {
    private int id;
    private String userId;
    private String password;
    private String name;
    private String tel;
    private int balance = 0;
    private String cardNum;
    private boolean isAdmin = false;

    public MemberDto(int id, String userId, String password, String name, String tel, int balance, String cardNum, boolean isAdmin) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.balance = balance;
        this.cardNum = cardNum;
        this.isAdmin = isAdmin;
    }

    public MemberDto(int id, String userId, String password, String name, String tel, int balance, String cardNum) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.balance = balance;
        this.cardNum = cardNum;
    }

    public MemberDto(String userId, String password, String name, String tel, String cardNum) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.cardNum = cardNum;
    }


    public MemberDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public MemberDto() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return id + "\t\t\t" + userId + "\t\t" + password + "\t\t" + name + "\t\t" + tel + "\t\t" + balance + "\t\t" + cardNum;
    }
}
