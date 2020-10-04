package home.at.yaklimenko.pikabu.ui.story;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import home.at.yaklimenko.pikabu.R;
import home.at.yaklimenko.pikabu.databinding.FragmentStoryBinding;
import home.at.yaklimenko.pikabu.entity.Story;
import home.at.yaklimenko.pikabu.ui.stories.OnButtonClickListener;
import home.at.yaklimenko.pikabu.ui.stories.StoriesViewPagerAdapter;

public class StoryFragment extends Fragment {

    StoryViewModel storyViewModel;
    private int storyId;

    OnButtonClickListener storyButtonClickListener = (storyId, button) -> storyViewModel.switchStoryFavor(storyId)
            .subscribe(isFavNow -> button.setText(isFavNow ? R.string.btn_remove_from_favs : R.string.btn_add_to_favs));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        storyViewModel = new ViewModelProvider(this).get(StoryViewModel.class);
        FragmentStoryBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_story, container, false);
        binding.setViewModel(storyViewModel);
        binding.executePendingBindings();

        storyViewModel.story.observe(getViewLifecycleOwner(), story -> onStoryLoaded(story, binding));

        return binding.getRoot();
    }

    private void onStoryLoaded(Story story, FragmentStoryBinding binding) {
        if (story == null) {
            binding.tvTitle.setVisibility(View.GONE);
            binding.vpCarousel.setVisibility(View.GONE);
            binding.tvBody.setVisibility(View.GONE);
        } else {
            binding.tvTitle.setVisibility(View.VISIBLE);
            binding.vpCarousel.setVisibility(story.getImages() == null || story.getImages().isEmpty() ? View.GONE : View.VISIBLE);
            binding.tvBody.setVisibility(View.VISIBLE);

            binding.tvTitle.setText(story.getTitle());
            binding.tvBody.setText(story.getBody());

            ViewPager viewPager = binding.vpCarousel;
            if (story.getImages() == null || story.getImages().isEmpty()) {
                return;
            }
            viewPager.setAdapter(new StoriesViewPagerAdapter(getContext(), story.getImages()));

            binding.btnSaveToFavs.setText(story.isFav() ? R.string.btn_remove_from_favs : R.string.btn_add_to_favs);
            binding.btnSaveToFavs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storyViewModel.switchStoryFavor(story.getId())
                            .subscribe(isFavNow -> binding.btnSaveToFavs.setText(isFavNow ? R.string.btn_remove_from_favs : R.string.btn_add_to_favs));
                }
            });
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args == null) {
            storyViewModel.loadStoryError();
            return;
        }
        int storyId = StoryFragmentArgs.fromBundle(getArguments()).getStoryId();
        storyViewModel.loadStory(storyId);


    }
}
