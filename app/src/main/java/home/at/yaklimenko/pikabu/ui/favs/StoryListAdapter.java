package home.at.yaklimenko.pikabu.ui.favs;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import home.at.yaklimenko.pikabu.databinding.ListItemStoryBinding;
import home.at.yaklimenko.pikabu.entity.Story;

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.ViewHolder> {
    private static final String TAG = StoryListAdapter.class.getSimpleName();


    private List<Story> data = new LinkedList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemStoryBinding binding = ListItemStoryBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: binded " + data.get(position).getTitle());
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Story> newData) {
        Log.d(TAG, "setData: ");
        data = newData;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemStoryBinding binding;

        ViewHolder(ListItemStoryBinding binding) {
            super(binding.getRoot());
            ViewHolder.this.binding = binding;
        }

        void bind(Story story) {
            binding.setStory(story);
        }

    }
}
