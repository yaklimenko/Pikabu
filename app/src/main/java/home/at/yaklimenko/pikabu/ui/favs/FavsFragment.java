package home.at.yaklimenko.pikabu.ui.favs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import home.at.yaklimenko.pikabu.R;
import home.at.yaklimenko.pikabu.databinding.FragmentFavsBinding;
import home.at.yaklimenko.pikabu.ui.hot.HotFragmentDirections;
import home.at.yaklimenko.pikabu.ui.stories.OnButtonClickListener;
import home.at.yaklimenko.pikabu.ui.stories.OnStoryClickListener;
import home.at.yaklimenko.pikabu.ui.stories.StoryListAdapter;

public class FavsFragment extends Fragment {

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

            FragmentActivity activity = getActivity();
            if (activity == null) {
                return;
            }
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment hostFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment);
            if (hostFragment == null) {
                throw new NullPointerException("nav host fragment");
            }
            NavHostFragment navHostFragment = (NavHostFragment)hostFragment;
            NavController navController = navHostFragment.getNavController();

            NavDirections action = FavsFragmentDirections.actionNavigationFavsToStory()
                    .setStoryId(storyId);
            navController.navigate(action);

    }
}