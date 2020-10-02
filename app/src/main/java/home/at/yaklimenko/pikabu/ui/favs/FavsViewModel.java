package home.at.yaklimenko.pikabu.ui.favs;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import home.at.yaklimenko.pikabu.entity.Story;
import home.at.yaklimenko.pikabu.repository.StoryRepository;
import io.reactivex.Completable;

public class FavsViewModel extends ViewModel {

    public MutableLiveData<List<Story>> stories = new MutableLiveData<>();
    public ObservableField<String> errorText = new ObservableField<>();
    public ObservableField<Boolean> isLoading = new ObservableField<>();

    public FavsViewModel() {
        isLoading.set(true);
        StoryRepository.getInstance().getFavStories()
                .subscribe(stories1 -> {
                    stories.setValue(stories1);
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
                .switchStoryFavor(id)
                .andThen(StoryRepository.getInstance().getFavStories())
                .flatMapCompletable(stories1 -> {
                    stories.setValue(stories1);
                    return Completable.complete();
                });
    }

}