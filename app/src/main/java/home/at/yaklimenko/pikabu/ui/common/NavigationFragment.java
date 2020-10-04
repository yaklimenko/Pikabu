package home.at.yaklimenko.pikabu.ui.common;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import home.at.yaklimenko.pikabu.R;

public abstract class NavigationFragment extends Fragment {
    protected NavController getNavController() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            throw new NullPointerException("activity");
        }
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment hostFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment);
        if (hostFragment == null) {
            throw new NullPointerException("nav host fragment");
        }
        NavHostFragment navHostFragment = (NavHostFragment) hostFragment;
        return navHostFragment.getNavController();
    }
}
