package s2017s40.kr.hs.mirim.mirimitshow;

public class Scan {
    private String email;
    private String cartegory;
    private String name;
    private image image;
    Scan(String email, String cartegory, String name){
        this.email = email;
        this.cartegory = cartegory;
        this.name = name;
    }

    public s2017s40.kr.hs.mirim.mirimitshow.image getImage() {
        return image;
    }

    public void setImage(s2017s40.kr.hs.mirim.mirimitshow.image image) {
        this.image = image;
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
