package home.at.yaklimenko.pikabu.repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import home.at.yaklimenko.pikabu.entity.Story;
import home.at.yaklimenko.pikabu.favs.FavStoriesStorage;
import home.at.yaklimenko.pikabu.http.PikabuApiBuilder;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StoryRepository {

    private static StoryRepository instance;

    public static synchronized StoryRepository getInstance() {
        if (instance == null) {
            instance = new StoryRepository();
        }
        return instance;
    }

    private PikabuApiBuilder pikabuApiBuilder;
    private FavStoriesStorage favStoriesStorage;
    private Map<Integer, Story> hotStories;

    public StoryRepository() {
        pikabuApiBuilder = new PikabuApiBuilder();
        favStoriesStorage = new FavStoriesStorage();
    }

    public Single<List<Story>> getHotStories() {
        return pikabuApiBuilder.loadHotStories()
                .map(stories -> {
                    mapStories(stories);
                    return stories;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Story>> getFavStories() {
        return favStoriesStorage.loadFavsStoriesIds()
                .map(ids -> {
                    List<Story> stories = new LinkedList<>();
                    for (Integer id : ids) {
                        stories.add(hotStories.get(id));
                    }
                    return stories;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void mapStories(List<Story> stories) {
        hotStories = new HashMap<>(stories.size());
        for (Story story : stories) {
            hotStories.put(story.getId(), story);
        }
    }

    public Single<Boolean> switchStoryFavor(final int id) {
        return getStory(id)
                .flatMap(story -> {
                    if (story.isFav()) {
                        story.setFav(false);
                        return favStoriesStorage.removeFromSavedStories(story)
                                .andThen(Single.just(story.isFav()));
                    } else {
                        story.setFav(true);
                        return favStoriesStorage.saveStory(story)
                                .andThen(Single.just(story.isFav()));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Single<Story> getStory(int id) {
        return Single.create(emitter -> {
            if (hotStories == null) {
                emitter.onError(new NullPointerException("hot stories are not loaded"));
            }
            Story story = hotStories.get(id);
            if (story == null) {
                emitter.onError(new NullPointerException("cannot find story id: " + id));
            }
            emitter.onSuccess(story);
        });
    }

    public Single<Story> getSingleStory(int id) {
        return getStory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
