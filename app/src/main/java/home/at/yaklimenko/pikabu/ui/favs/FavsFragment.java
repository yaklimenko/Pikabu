package home.at.yaklimenko.pikabu.ui.favs;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.button.MaterialButton;

import home.at.yaklimenko.pikabu.R;
import home.at.yaklimenko.pikabu.databinding.FragmentFavsBinding;
import home.at.yaklimenko.pikabu.ui.stories.OnStoryClickListener;
import home.at.yaklimenko.pikabu.ui.stories.StoryListAdapter;

public class FavsFragment extends Fragment {

    private FavsViewModel favsViewModel;
    private StoryListAdapter storyListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favsViewModel = new ViewModelProvider(this).get(FavsViewModel.class);
        FragmentFavsBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_favs, container, false);
        storyListAdapter = new StoryListAdapter((storyId, button) -> favsViewModel.switchStoryFavor(storyId)
                .subscribe());
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
}