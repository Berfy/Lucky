// Generated code from Butter Knife. Do not modify!
package cn.berfy.demo.lucky;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import cn.berfy.demo.lucky.view.LuckyPanView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding<T extends MainActivity> implements Unbinder {
  protected T target;

  @UiThread
  public MainActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.mLuckyPanView = Utils.findRequiredViewAsType(source, R.id.id_luckypan, "field 'mLuckyPanView'", LuckyPanView.class);
    target.mStartBtn = Utils.findRequiredViewAsType(source, R.id.id_start_btn, "field 'mStartBtn'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mLuckyPanView = null;
    target.mStartBtn = null;

    this.target = null;
  }
}
