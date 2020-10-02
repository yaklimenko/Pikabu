package home.at.yaklimenko.pikabu.favs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import home.at.yaklimenko.pikabu.entity.Story;
import io.reactivex.Completable;
import io.reactivex.Single;

public class FavStoriesStorage {
    private final Set<Story> stories;

    public FavStoriesStorage() {
        stories = new HashSet<>();
    }

    public Completable saveStory(Story story) {
        return Completable.create(emitter -> {
            stories.add(story);
            emitter.onComplete();
        });
    }

    public Completable removeFromSavedStories(Story story) {
        return Completable.create(emitter -> {
            stories.remove(story);
            emitter.onComplete();
        });
    }

    public Single<List<Story>> loadFavsStories() {
        return Single.create(emitter -> {
            emitter.onSuccess(new LinkedList<>(stories));
        });
    }

}
