package s2017s40.kr.hs.mirim.mirimitshow;

public class Register {
    private String name;
    private String email;
    private String phone;
    private String password;
    Register(String name, String email, String password, String phone){
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
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
