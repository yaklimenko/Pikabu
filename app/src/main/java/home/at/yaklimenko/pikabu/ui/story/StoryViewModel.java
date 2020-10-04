package home.at.yaklimenko.pikabu.ui.story;

import androidx.annotation.StringRes;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import home.at.yaklimenko.pikabu.R;
import home.at.yaklimenko.pikabu.entity.Story;
import home.at.yaklimenko.pikabu.repository.StoryRepository;
import io.reactivex.Single;

public class StoryViewModel extends ViewModel {

    public ObservableField<Integer> errorTextRes = new ObservableField<>();
    public ObservableField<Boolean> isLoading = new ObservableField<>(true);
    public MutableLiveData<Story> story = new MutableLiveData<>();


    public void loadStory(int id) {
        if (story.getValue() != null) {
            return;
        }

        StoryRepository.getInstance().getSingleStory(id)
                .subscribe(story1 -> {
                            story.setValue(story1);
                            isLoading.set(false);
                            errorTextRes.set(null);
                        },
                        t -> {
                            isLoading.set(false);
                            errorTextRes.set(translateError(t));
                        });

    }

    public void loadStoryError() {
        isLoading.set(false);
        errorTextRes.set(R.string.tv_story_error_title);
        story.setValue(null);
    }

    @StringRes
    private int translateError(Throwable t) {
        /// some translation Code
        return R.string.tv_story_error_title;
    }

    public Single<Boolean> switchStoryFavor(int id) {
        return StoryRepository.getInstance()
                .switchStoryFavor(id);
    }

}
