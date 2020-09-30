package home.at.yaklimenko.pikabu.ui.common;

import android.util.Log;
import android.view.LayoutInflater;
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

import home.at.yaklimenko.pikabu.databinding.ListItemStoryBinding;
import home.at.yaklimenko.pikabu.entity.Story;

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.ViewHolder> {
    private static final String TAG = StoryListAdapter.class.getSimpleName();


    private List<Story> data = new LinkedList<>();
    private StorySaverViewModel storySaverViewModel;

    public StoryListAdapter(StorySaverViewModel storySaverViewModel) {
        this.storySaverViewModel = storySaverViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemStoryBinding binding = ListItemStoryBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding, storySaverViewModel);

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
        private StorySaverViewModel storySaverViewModel;

        ViewHolder(ListItemStoryBinding binding, StorySaverViewModel storySaverViewModel) {
            super(binding.getRoot());
            ViewHolder.this.binding = binding;
            initCardHandler(binding);
        }

        private void initCardHandler(home.at.yaklimenko.pikabu.databinding.ListItemStoryBinding binding) {
            MaterialCardView card = binding.storyItemCard;
            card.setOnLongClickListener(v -> {
                card.setChecked(!card.isChecked());
                return true;
            });
        }

        void bind(Story story) {
            binding.setStory(story);
            binding.setViewModel(storySaverViewModel);
        }
    }

    @BindingAdapter("storyImage")
    public static void loadFirstImage(ImageView view, String imageUrl) {
        if (imageUrl == null) {
            return;
        }
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions())
                .into(view);
    }
}
