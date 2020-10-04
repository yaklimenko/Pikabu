package home.at.yaklimenko.pikabu.ui.hot;

import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG = HotFragment.class.getSimpleName();

    private HotViewModel hotViewModel;
    private StoryListAdapter storyListAdapter;


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

    private void initAdapter(home.at.yaklimenko.pikabu.databinding.FragmentHotBinding binding) {
        storyListAdapter = new StoryListAdapter((storyId, button) -> hotViewModel.switchStoryFavor(storyId)
                .subscribe(isFavNow -> button.setText(isFavNow ? R.string.btn_remove_from_favs : R.string.btn_add_to_favs)));
        binding.listStories.setAdapter(storyListAdapter);
        binding.listStories.setLayoutManager(new LinearLayoutManager(getContext()));

        Log.d(TAG, "onCreateView: bind to adapter");
        hotViewModel.getStories().observe(getViewLifecycleOwner(), stories -> storyListAdapter.setData(stories));
        if (hotViewModel.getStories().getValue() != null) {
            storyListAdapter.setData(hotViewModel.getStories().getValue());
        }
    }


}