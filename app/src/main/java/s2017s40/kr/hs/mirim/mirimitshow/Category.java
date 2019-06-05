package s2017s40.kr.hs.mirim.mirimitshow;

public class Category {
    String name;
    String id;
    Category(){

    }
    public Category(String name){
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
