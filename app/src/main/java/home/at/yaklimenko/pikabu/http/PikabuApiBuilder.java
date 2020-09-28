package home.at.yaklimenko.pikabu.http;

import java.util.List;

import home.at.yaklimenko.pikabu.entity.Story;
import home.at.yaklimenko.pikabu.exceptions.HttpException;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PikabuApiBuilder {

    private final PikabuApi service;
    public static PikabuApiBuilder instance;

    public static PikabuApiBuilder getInstance() {
        if (instance == null) {
            instance = new PikabuApiBuilder();
        }
        return instance;
    }

    public PikabuApiBuilder() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://pikabu.ru/page/interview/mobile-app/test-api/")
                .build();

        service = retrofit.create(PikabuApi.class);
    }

    public Single<List<Story>> loadHotStories() {
        return Single.create(emitter ->
                service.loadHotStories()
                        .enqueue(new DefaultCallback<List<Story>>() {
                            @Override
                            protected void onResponse(ResultEntity<List<Story>> result) {
                                if (result.isSuccess()) {
                                    emitter.onSuccess(result.getEntity());
                                } else {
                                    emitter.onError(new HttpException(result.getCode(), result.getMessage()));
                                }
                            }
                        }));
    }

    public Single<Story> loadStory(int id) {
        return Single.create(emitter ->
                service
                        .loadStory(id)
                        .enqueue(new DefaultCallback<Story>() {
                            @Override
                            public void onResponse(ResultEntity<Story> result) {
                                if (result.isSuccess()) {
                                    emitter.onSuccess(result.getEntity());
                                } else {
                                    emitter.onError(new HttpException(result.getCode(), result.getMessage()));
                                }
                            }
                        }));
    }


}
