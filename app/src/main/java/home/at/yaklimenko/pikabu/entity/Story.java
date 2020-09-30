package home.at.yaklimenko.pikabu.entity;

import java.util.List;

public class Story {

    int id;
    String title;
    List<String> images;
    String body;
    boolean isFav;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getImages() {
        return images;
    }

    public String getBody() {
        return body;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }
}
