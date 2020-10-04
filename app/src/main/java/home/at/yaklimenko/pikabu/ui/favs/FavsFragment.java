package home.at.yaklimenko.pikabu.ui.favs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;

import home.at.yaklimenko.pikabu.R;
import home.at.yaklimenko.pikabu.databinding.FragmentFavsBinding;
import home.at.yaklimenko.pikabu.ui.common.NavigationFragment;
import home.at.yaklimenko.pikabu.ui.stories.OnButtonClickListener;
import home.at.yaklimenko.pikabu.ui.stories.OnStoryClickListener;
import home.at.yaklimenko.pikabu.ui.stories.StoryListAdapter;

public class FavsFragment extends NavigationFragment {
    private static final String TAG = FavsFragment.class.getSimpleName();

    private FavsViewModel favsViewModel;
    private StoryListAdapter storyListAdapter;

    private OnButtonClickListener onButtonClickListener = (storyId, button) -> favsViewModel.switchStoryFavor(storyId).subscribe();
    private OnStoryClickListener onStoryClickListener = this::openStoryFragment;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favsViewModel = new ViewModelProvider(this).get(FavsViewModel.class);
        FragmentFavsBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_favs, container, false);
        storyListAdapter = new StoryListAdapter(onButtonClickListener, onStoryClickListener);
        binding.listStories.setAdapter(storyListAdapter);
        binding.listStories.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.setViewModel(favsViewModel);
        binding.executePendingBindings();

        favsViewModel.getStories().observe(getViewLifecycleOwner(), stories -> storyListAdapter.setData(stories));
        favsViewModel.errorTextRes.observe(getViewLifecycleOwner(), errorRes -> {
            if (errorRes == null) {
                binding.tvErrorText.setVisibility(View.INVISIBLE);
            } else {
                binding.tvErrorText.setText(errorRes);
                binding.tvErrorText.setVisibility(View.VISIBLE);
            }

        });
        return binding.getRoot();
    }

    private void openStoryFragment(int storyId) {
        NavController navController = getNavController();
        NavDirections action = FavsFragmentDirections.actionNavigationFavsToStory()
                .setStoryId(storyId);
        try {
            getNavController().navigate(action);
        } catch (Exception e) {
            Log.e(TAG, "BUG: openStoryFragment: ", e);
        }

    }
}