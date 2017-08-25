package cn.berfy.demo.lucky.ui.lucky.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.berfy.demo.lucky.R;
import cn.berfy.demo.lucky.ui.lucky.model.Lucky;
import cn.berfy.framework.base.adapter.ListAdapter;
import cn.berfy.framework.base.adapter.ViewHolder;

/**
 * Created by Berfy on 2017/8/24.
 * 礼物列表
 */
public class LuckysAdapter extends ListAdapter<Lucky> {

    private boolean mIsDelete;//是否显示删除

    public LuckysAdapter(Context context) {
        super(context, null, R.layout.adapter_lucky_item);
    }

    public void showDelete(boolean isDelete) {
        mIsDelete = isDelete;
        notifyDataSetChanged();
    }

    @Override
    public void setViewData(final ViewHolder holder, final Lucky data) {
        holder.setText(R.id.tv_title, data.getName());
        holder.setText(R.id.tv_time, data.getUpdateTime());
        if (!TextUtils.isEmpty(data.getImgPath())) {
            holder.setVisibility(R.id.tv_img_tip, View.GONE);
            holder.setVisibility(R.id.iv_icon, View.VISIBLE);
            ImageLoader.getInstance().displayImage("file://" + data.getImgPath(), (ImageView) holder.getView(R.id.iv_icon));
        } else {
            holder.setVisibility(R.id.tv_img_tip, View.VISIBLE);
            holder.setVisibility(R.id.iv_icon, View.GONE);
        }
        holder.getView(R.id.v_color).setBackgroundColor(data.getColor());
        holder.getView(R.id.chb).setVisibility(mIsDelete ? View.VISIBLE : View.GONE);
        ((CheckBox) holder.getView(R.id.chb)).setChecked(data.isSelect());
        if (mIsDelete)
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.setSelect(!data.isSelect());
                    notifyDataSetChanged();
                }
            });
    }
}
