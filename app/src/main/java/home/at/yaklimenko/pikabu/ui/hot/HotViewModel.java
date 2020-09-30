package home.at.yaklimenko.pikabu.ui.hot;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import home.at.yaklimenko.pikabu.entity.Story;
import home.at.yaklimenko.pikabu.favs.FavStoriesStorage;
import home.at.yaklimenko.pikabu.http.PikabuApiBuilder;
import home.at.yaklimenko.pikabu.ui.common.StorySaverViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HotViewModel extends ViewModel implements StorySaverViewModel {
    private static final String TAG = HotViewModel.class.getSimpleName();

    private MutableLiveData<List<Story>> stories = new MutableLiveData<>();
    public ObservableField<String> errorText = new ObservableField<>();
    public ObservableField<Boolean> isLoading = new ObservableField<>();

    public HotViewModel() {
        isLoading.set(true);
        PikabuApiBuilder.getInstance()
                .loadHotStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.d(TAG, "HotViewModel: loaded " + s.size());
                    stories.setValue(s);
                    isLoading.set(false);
                }, t -> {
                    stories.setValue(Collections.emptyList());
                    errorText.set(t.getMessage());
                    isLoading.set(false);
                });
    }

    public MutableLiveData<List<Story>> getStories() {
        return stories;
    }

    @Override
    public void saveStory(Story story) {
        story.setFav(true);
        FavStoriesStorage.getInstance().saveStory(story);
    }

    @Override
    public void removeStory(int id) {
        //story.setFav(false);
        FavStoriesStorage.getInstance().removeFromSavedStories(id);
    }
}