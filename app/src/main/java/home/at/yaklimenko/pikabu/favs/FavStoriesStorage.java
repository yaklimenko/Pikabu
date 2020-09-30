package home.at.yaklimenko.pikabu.favs;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import home.at.yaklimenko.pikabu.entity.Story;

public class FavStoriesStorage {
    private static FavStoriesStorage instance;

    private Map<Integer, Story> stories;

    public static synchronized FavStoriesStorage getInstance() {
        if (instance == null) {
            instance = new FavStoriesStorage();
            return instance;
        }
        return instance;
    }

    public FavStoriesStorage() {
        stories = new LinkedHashMap<>();
    }

    public void saveStory(Story story) {
        stories.put(story.getId(), story);
    }

    public void removeFromSavedStories(int id) {
        stories.remove(id);
    }

    public List<Story> getSavedStories() {
        return new LinkedList<>(stories.values());
    }

}
