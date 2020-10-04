package home.at.yaklimenko.pikabu.ui.stories;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.card.MaterialCardView;

import java.util.LinkedList;
import java.util.List;

import home.at.yaklimenko.pikabu.R;
import home.at.yaklimenko.pikabu.databinding.ListItemStoryBinding;
import home.at.yaklimenko.pikabu.entity.Story;

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.ViewHolder> {
    private static final String TAG = StoryListAdapter.class.getSimpleName();


    private List<Story> data = new LinkedList<>();
    private OnStoryClickListener onStoryClickListener;

    public StoryListAdapter(OnStoryClickListener onStoryClickListener) {
        Log.d(TAG, "StoryListAdapter: constructor");
        this.onStoryClickListener = onStoryClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemStoryBinding binding = ListItemStoryBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding, onStoryClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: binded " + data.get(position).getTitle());
        holder.bind(data.get(position));
        holder.savePosition(position);
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
        private ListItemStoryBinding storyBinding;
        private OnStoryClickListener onStoryClickListener;

        ViewHolder(ListItemStoryBinding binding, OnStoryClickListener onStoryClickListener) {
            super(binding.getRoot());
            storyBinding = binding;
            this.onStoryClickListener = onStoryClickListener;
        }

        void savePosition(int position) {
            storyBinding.getRoot().setTag(position);
        }

        void bind(Story story) {
            storyBinding.setStory(story);
            storyBinding.btnSaveToFavs.setText(story.isFav() ? R.string.btn_remove_from_favs : R.string.btn_add_to_favs);
            storyBinding.btnSaveToFavs.setOnClickListener(v -> onStoryClickListener.onStoryClicked(story.getId(), storyBinding.btnSaveToFavs));
        }
    }

    @BindingAdapter("storyImage")
    public static void loadFirstImage(ImageView view, String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            view.setVisibility(View.GONE);
            Glide.with(view.getContext())
                    .clear(view);
            return;
        }
        view.setVisibility(View.VISIBLE);
        RequestOptions cropOptions = new RequestOptions().fitCenter();
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions())
                .apply(cropOptions)
                .into(view);
    }
}
