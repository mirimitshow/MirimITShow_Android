package s2017s40.kr.hs.mirim.mirimitshow;

import java.util.ArrayList;

public class Register {
    private String name;
    private String email;
    private String phone;
    private String password;
    private ArrayList<JoinGroup> group = new ArrayList<>();
    private ArrayList<Category> category = new ArrayList<>();
    Register(String name, String email, String password, String phone, String category){
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.category.add(new Category());
    }

    public void setGroup(ArrayList<JoinGroup>   ) {
        this.group = group;
    }



    public ArrayList<JoinGroup> getGroup() {
        return group;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
