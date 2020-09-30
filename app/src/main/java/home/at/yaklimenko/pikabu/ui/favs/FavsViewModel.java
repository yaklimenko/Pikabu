package home.at.yaklimenko.pikabu.ui.favs;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import home.at.yaklimenko.pikabu.entity.Story;
import home.at.yaklimenko.pikabu.favs.FavStoriesStorage;
import home.at.yaklimenko.pikabu.ui.common.StorySaverViewModel;

public class FavsViewModel extends ViewModel implements StorySaverViewModel {

    public MutableLiveData<List<Story>> stories = new MutableLiveData<>();

    public FavsViewModel() {
        stories.setValue(FavStoriesStorage.getInstance().getSavedStories());
    }


    @Override
    public void saveStory(Story story) {
        FavStoriesStorage.getInstance().saveStory(story);
    }

    @Override
    public void removeStory(int id) {
        //story.setFav(false);
        FavStoriesStorage.getInstance().removeFromSavedStories(id);
    }
}