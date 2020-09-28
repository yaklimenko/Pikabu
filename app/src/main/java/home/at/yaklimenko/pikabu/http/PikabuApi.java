package home.at.yaklimenko.pikabu.http;

import java.util.List;

import home.at.yaklimenko.pikabu.entity.Story;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface PikabuApi {

    @GET("feed.php")
    Call<List<Story>> loadHotStories();

    @GET("story.php")
    Call<Story> loadStory(@Query("id") int id);
}
