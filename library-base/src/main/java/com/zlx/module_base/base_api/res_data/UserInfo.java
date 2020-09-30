package com.zlx.module_base.base_api.res_data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * FileName: UserInfo
 * Created by zlx on 2020/9/21 15:04
 * Email: 1170762202@qq.com
 * Description: 用户信息
 */
public class UserInfo implements Parcelable {

    /**
     * chapterTops : []
     * collectIds : [12554]
     * email :
     * icon :
     * id : 30132
     * password :
     * token :
     * type : 0
     * username : zjp
     */

    private String email;
    private String icon;
    private String id;
    private String password;
    private String token;
    private String type;
    private String username;
    private List<?> chapterTops;
    private List<Integer> collectIds; //收藏的文章id

    /**********************************************我的积分******************************************/
    private String coinCount;
    private int level;
    private String rank;
    private String userId, reason, desc, date;

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        email = in.readString();
        icon = in.readString();
        id = in.readString();
        password = in.readString();
        token = in.readString();
        type = in.readString();
        username = in.readString();
        coinCount = in.readString();
        level = in.readInt();
        rank = in.readString();
        userId = in.readString();
        reason = in.readString();
        desc = in.readString();
        date = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<?> getChapterTops() {
        return chapterTops;
    }

    public void setChapterTops(List<?> chapterTops) {
        this.chapterTops = chapterTops;
    }

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }

    public String getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(String coinCount) {
        this.coinCount = coinCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(icon);
        dest.writeString(id);
        dest.writeString(password);
        dest.writeString(token);
        dest.writeString(type);
        dest.writeString(username);
        dest.writeString(coinCount);
        dest.writeInt(level);
        dest.writeString(rank);
        dest.writeString(userId);
        dest.writeString(reason);
        dest.writeString(desc);
        dest.writeString(date);
    }
}
