package s2017s40.kr.hs.mirim.mirimitshow;

public class User {
    private String email;
    private String password;
    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}