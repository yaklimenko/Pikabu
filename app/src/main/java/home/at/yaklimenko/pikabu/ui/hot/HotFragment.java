package home.at.yaklimenko.pikabu.ui.hot;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;

import home.at.yaklimenko.pikabu.R;
import home.at.yaklimenko.pikabu.databinding.FragmentHotBinding;
import home.at.yaklimenko.pikabu.ui.common.NavigationFragment;
import home.at.yaklimenko.pikabu.ui.stories.OnButtonClickListener;
import home.at.yaklimenko.pikabu.ui.stories.OnStoryClickListener;
import home.at.yaklimenko.pikabu.ui.stories.StoryListAdapter;

public class HotFragment extends NavigationFragment {
    private static final String TAG = HotFragment.class.getSimpleName();

    private HotViewModel hotViewModel;
    private StoryListAdapter storyListAdapter;

    OnButtonClickListener storyButtonClickListener = (storyId, button) -> hotViewModel.switchStoryFavor(storyId)
            .subscribe(isFavNow -> button.setText(isFavNow ? R.string.btn_remove_from_favs : R.string.btn_add_to_favs));
    OnStoryClickListener storyClickListener = this::openStoryFragment;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hotViewModel = new ViewModelProvider(getActivity()).get(HotViewModel.class);
        FragmentHotBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_hot, container, false);
        binding.setViewModel(hotViewModel);

        initAdapter(binding);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    private void initAdapter(FragmentHotBinding binding) {
        storyListAdapter = new StoryListAdapter(storyButtonClickListener, storyClickListener);
        binding.listStories.setAdapter(storyListAdapter);
        binding.listStories.setLayoutManager(new LinearLayoutManager(getContext()));

        Log.d(TAG, "onCreateView: bind to adapter");
        hotViewModel.getStories().observe(getViewLifecycleOwner(), stories -> storyListAdapter.setData(stories));
        if (hotViewModel.getStories().getValue() != null) {
            storyListAdapter.setData(hotViewModel.getStories().getValue());
        }
    }

    private void openStoryFragment(int storyId) {
        NavDirections action = HotFragmentDirections.actionNavigationHomeToStory()
                .setStoryId(storyId);
        try {
            getNavController().navigate(action);
        } catch (Exception e) {
            Log.e(TAG, "BUG: openStoryFragment: ", e);
        }
    }


}