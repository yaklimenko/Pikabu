package home.at.yaklimenko.pikabu.entity;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class Story {

    int id;
    String title;
    List<String> images;
    String body;

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

    @BindingAdapter("profileImage")
    public static void loadFirstImage(ImageView view, String imageUrl) {
        if (imageUrl == null) {
            return;
        }
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions())
                .into(view);
    }
}
