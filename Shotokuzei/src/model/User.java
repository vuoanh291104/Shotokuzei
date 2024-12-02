package model;

public class User {
   private static User instance;
   private String userId;
    private String role;


    private User(){}

    public static User getInstance() {
        if(instance == null){
            instance = new User();
        }
        return instance;
    }

    public static void setInstance(User instance) {
        User.instance = instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



}
