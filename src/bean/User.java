package bean;

public class User {
    private String name;
    private String password;
    private int type;
    public static int CUSTOMER = 0;
    public static int MARKETING = 1;
    public static int ACCOUNTANT = 2;
    public static int PURCHASE = 3;
    public static int SUPPLIER = 4;
    public static int OFFLINE = 5;
    public static int MANAGER = 6;
    public User() {}
    public User(String name, String password, int type) {
        this.name = name;
        this.password = password;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
