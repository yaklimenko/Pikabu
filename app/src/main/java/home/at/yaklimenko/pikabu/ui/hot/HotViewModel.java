package home.at.yaklimenko.pikabu.ui.hot;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import home.at.yaklimenko.pikabu.entity.Story;
import home.at.yaklimenko.pikabu.http.PikabuApiBuilder;

public class HotViewModel extends ViewModel {
    private static final String TAG = HotViewModel.class.getSimpleName();

    private MutableLiveData<List<Story>> stories;

    public ObservableField<Boolean> isLoading = new ObservableField<>();

    public HotViewModel() {
        stories = new MutableLiveData<>();
        isLoading.set(true);
        PikabuApiBuilder.getInstance()
                .loadHotStories()
                .subscribe(s -> {
                    Log.d(TAG, "HotViewModel: loaded " + s.size());
                    stories.setValue(s);
                    isLoading.set(false);
                });
    }

    public MutableLiveData<List<Story>> getStories() {
        return stories;
    }
}