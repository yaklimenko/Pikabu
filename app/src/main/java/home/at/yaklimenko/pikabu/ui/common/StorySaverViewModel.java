package home.at.yaklimenko.pikabu.ui.common;

import home.at.yaklimenko.pikabu.entity.Story;

public interface StorySaverViewModel {
    void saveStory(Story story);

    void removeStory(int id);
}
