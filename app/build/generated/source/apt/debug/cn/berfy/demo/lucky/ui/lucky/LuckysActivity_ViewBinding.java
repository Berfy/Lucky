// Generated code from Butter Knife. Do not modify!
package cn.berfy.demo.lucky.ui.lucky;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import cn.berfy.demo.lucky.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LuckysActivity_ViewBinding<T extends LuckysActivity> implements Unbinder {
  protected T target;

  private View view2131624072;

  @UiThread
  public LuckysActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.mListView = Utils.findRequiredViewAsType(source, R.id.listView, "field 'mListView'", ListView.class);
    view = Utils.findRequiredView(source, R.id.btn_add, "field 'mBtnAdd' and method 'onClick'");
    target.mBtnAdd = Utils.castView(view, R.id.btn_add, "field 'mBtnAdd'", Button.class);
    view2131624072 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mListView = null;
    target.mBtnAdd = null;

    view2131624072.setOnClickListener(null);
    view2131624072 = null;

    this.target = null;
  }
}
