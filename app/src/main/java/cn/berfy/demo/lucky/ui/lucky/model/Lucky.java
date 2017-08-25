package cn.berfy.demo.lucky.ui.lucky.model;

import android.graphics.Color;

/**
 * Created by Berfy on 2017/8/23.
 * 礼物表
 */

public class Lucky {

    private int id;
    private String name;//礼物名称
    private String imgPath;//礼物图片路径
    private int color;//礼物转盘色块颜色
    private String updateTime;//更新时间
    private boolean isShowImg;//是否显示图片
    private boolean isSelect;//删除标记

    public Lucky() {
        name = "";
        color = Color.BLACK;
        name = "";
        name = "";
        name = "";
    }

    public Lucky(String name, String imgPath, int color, String updateTime, boolean isShowImg) {
        this.name = name;
        this.imgPath = imgPath;
        this.color = color;
        this.updateTime = updateTime;
        this.isShowImg = isShowImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean isShowImg() {
        return isShowImg;
    }

    public void setShowImg(boolean showImg) {
        isShowImg = showImg;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
