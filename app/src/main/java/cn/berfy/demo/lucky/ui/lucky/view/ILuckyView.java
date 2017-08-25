package cn.berfy.demo.lucky.ui.lucky.view;

import android.content.Context;
import android.widget.Button;
import android.widget.ListView;

import cn.berfy.framework.view.TitleBarView;

/**
 * Created by Berfy on 2017/8/24.
 */

public interface ILuckyView {

    Context getContext();

    ListView getListView();

    Button getBtnAdd();

    TitleBarView getTitleBarView();

    boolean getIsDelete();
}
