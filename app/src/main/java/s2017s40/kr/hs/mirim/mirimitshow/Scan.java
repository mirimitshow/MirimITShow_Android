package s2017s40.kr.hs.mirim.mirimitshow;

public class Scan {
    private String email;
    private String cartegory;
    private String name;
    private String url;
    Scan(String email, String cartegory, String name){
        this.email = email;
        this.cartegory = cartegory;
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCartegory(String cartegory) {
        this.cartegory = cartegory;
    }



    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCartegory() {
        return cartegory;
    }
}
