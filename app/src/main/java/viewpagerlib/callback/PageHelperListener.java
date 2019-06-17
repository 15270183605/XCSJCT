package viewpagerlib.callback;

import android.view.View;
public interface PageHelperListener<T> {
    void getItemView(View view, T data);
}
