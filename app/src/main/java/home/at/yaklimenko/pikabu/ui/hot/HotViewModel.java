package home.at.yaklimenko.pikabu.ui.hot;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import home.at.yaklimenko.pikabu.entity.Story;
import home.at.yaklimenko.pikabu.repository.StoryRepository;
import io.reactivex.Completable;

public class HotViewModel extends ViewModel {
    private static final String TAG = HotViewModel.class.getSimpleName();

    private MutableLiveData<List<Story>> stories = new MutableLiveData<>();
    public ObservableField<String> errorText = new ObservableField<>();
    public ObservableField<Boolean> isLoading = new ObservableField<>();

    private void loadStories() {
        isLoading.set(true);
        StoryRepository.getInstance()
                .getHotStories()
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

    public Completable switchStoryFavor(int id) {
        return StoryRepository.getInstance()
                .switchStoryFavor(id);
    }

}