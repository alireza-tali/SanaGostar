package vavkhan.com.sanagostar.views.Adapters;

import android.view.View;

public interface ListClick<T> {
    void onListClicked(T drawerUserModel, View sharedView);
}
