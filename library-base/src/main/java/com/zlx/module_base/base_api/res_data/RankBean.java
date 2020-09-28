package com.zlx.module_base.base_api.res_data;

/**
 * FileName: RankBean
 * Created by zlx on 2020/9/22 9:02
 * Email: 1170762202@qq.com
 * Description:
 */
public class RankBean {
    private String coinCount;
    private long date;
    private String desc;
    private String id;
    private String reason;
    private String userId;
    private String userName;
    private String username;
    private int type;
    private String rank;
    private int level;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(String coinCount) {
        this.coinCount = coinCount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
