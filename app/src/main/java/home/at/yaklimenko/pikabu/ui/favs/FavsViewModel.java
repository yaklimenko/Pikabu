package home.at.yaklimenko.pikabu.ui.favs;

import android.util.Log;

import androidx.annotation.StringRes;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import home.at.yaklimenko.pikabu.R;
import home.at.yaklimenko.pikabu.entity.Story;
import home.at.yaklimenko.pikabu.repository.StoryRepository;
import io.reactivex.Completable;

public class FavsViewModel extends ViewModel {

    private static final String TAG = FavsViewModel.class.getSimpleName();

    public MutableLiveData<List<Story>> stories = new MutableLiveData<>();
    public MutableLiveData<Integer> errorTextRes = new MutableLiveData<>();
    public ObservableField<Boolean> isLoading = new ObservableField<>();

    public FavsViewModel() {
        loadFavStories();
    }

    private void loadFavStories() {
        isLoading.set(true);
        StoryRepository.getInstance().getFavStories()
                .subscribe(stories1 -> {
                    refreshStoryOnView(stories1);
                    isLoading.set(false);
                }, t -> {
                    stories.setValue(Collections.emptyList());
                    errorTextRes.setValue(translateError(t));
                    isLoading.set(false);
                });
    }

    @StringRes
    private int translateError(Throwable t) {
        /// some translation Code
        return R.string.tv_error_title;
    }

    public MutableLiveData<List<Story>> getStories() {
        return stories;
    }

    public Completable switchStoryFavor(int id) {
        return StoryRepository.getInstance()
                .switchStoryFavor(id)
                .flatMap(isFav -> StoryRepository.getInstance().getFavStories())
                .flatMapCompletable(stories1 -> {
                    refreshStoryOnView(stories1);
                    return Completable.complete();
                });
    }

    private void refreshStoryOnView(List<Story> stories1) {
        stories.setValue(stories1);
        if (stories1 == null || stories1.isEmpty()) {
            Log.d(TAG, "refreshStoryOnView: error set NO STORIES");
            errorTextRes.setValue(R.string.tv_no_stories_title);
            return;
        }

        //everything is ok
        Log.d(TAG, "refreshStoryOnView: error set to null");
        errorTextRes.setValue(null);
    }


}