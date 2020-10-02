package home.at.yaklimenko.pikabu.ui.hot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import home.at.yaklimenko.pikabu.R;
import home.at.yaklimenko.pikabu.databinding.FragmentHotBinding;
import home.at.yaklimenko.pikabu.ui.stories.StoryListAdapter;

public class HotFragment extends Fragment {

    private HotViewModel hotViewModel;
    private StoryListAdapter storyListAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hotViewModel = new ViewModelProvider(this).get(HotViewModel.class);
        FragmentHotBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_hot, container, false);
        storyListAdapter = new StoryListAdapter((storyId, position) -> hotViewModel.switchStoryFavor(storyId)
                .subscribe(() -> storyListAdapter.notifyItemChanged(position)));
        binding.listStories.setAdapter(storyListAdapter);
        binding.listStories.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.setViewModel(hotViewModel);
        binding.executePendingBindings();

        hotViewModel.getStories().observe(getViewLifecycleOwner(), stories -> storyListAdapter.setData(stories));
        return binding.getRoot();
    }


}