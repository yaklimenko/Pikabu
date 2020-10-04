package home.at.yaklimenko.pikabu.ui.stories;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import home.at.yaklimenko.pikabu.R;

public class StoriesViewPagerAdapter extends PagerAdapter {
    public static final String TAG = StoriesViewPagerAdapter.class.getSimpleName();
    Context mContext;
    List<String> images;

    public StoriesViewPagerAdapter(Context context, List<String> images) {
        mContext = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(TAG,
                "instantiateItem() called with: " + "container = [" + container + "], position = [" + position + "]");
        Context context = container.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_pager, container, false);

        Log.d(TAG, "load in gallery:" + images.get(position) + "#end");
        final ImageView ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);

        RequestOptions options = new RequestOptions().fitCenter();
        if (!images.get(position).equals("")){
            Glide.with(context)
                    .load(images.get(position))
                    .error(Glide.with(context).load(R.drawable.ic_error_loading_image))
                    .apply(options)
                    .into(ivPhoto);
        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(TAG, "destroyItem() called with: " + "container = [" + container + "], position = [" + position
                + "], object = [" + object + "]");
        container.removeView((View) object);
    }
}
