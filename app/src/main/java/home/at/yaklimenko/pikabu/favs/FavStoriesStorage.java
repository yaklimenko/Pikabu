package home.at.yaklimenko.pikabu.favs;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import home.at.yaklimenko.pikabu.entity.Story;
import io.reactivex.Completable;
import io.reactivex.Single;

public class FavStoriesStorage {
    private final Set<Integer> stories;

    public FavStoriesStorage() {
        stories = new LinkedHashSet<>();
    }

    public Completable saveStory(Story story) {
        return Completable.create(emitter -> {
            stories.add(story.getId());
            emitter.onComplete();
        });
    }

    public Completable removeFromSavedStories(Story story) {
        return Completable.create(emitter -> {
            stories.remove(story.getId());
            emitter.onComplete();
        });
    }

    public Single<List<Integer>> loadFavsStoriesIds() {
        return Single.create(emitter -> emitter.onSuccess(new LinkedList<>(stories)));
    }

}
