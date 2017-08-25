package cn.berfy.demo.lucky.db;

/**
 * Created by Berfy on 2017/8/23.
 */

public class DbConstants {

    public static final String DB_NAME = "lucky";

    public static final String TAB = "tab_luckys";// 内容表

    // 内容表字段
    public static final String[] KEYS_TAB = new String[]{"_id", "name", "imgUrl", "color", "updateTime"};

    /**
     * 创建内容表
     */
    public static final String CREATE_TAB = "CREATE TABLE if not exists "
            + TAB
            + "("
            + KEYS_TAB[0] + " integer primary key autoincrement," + KEYS_TAB[1] + " txt not null ," + KEYS_TAB[2] + " txt,"
            + KEYS_TAB[3] + " integer not null," + KEYS_TAB[4] + " txt not null );";
}
