package com.zlx.module_base.base_api.res_data;

import java.util.List;

/**
 * FileName: RankListRes
 * Created by zlx on 2020/9/21 15:52
 * Email: 1170762202@qq.com
 * Description: 积分排行版
 */
public class RankListRes {

    /**
     * curPage : 1
     * datas : [{"coinCount":23067,"level":231,"rank":"1","userId":20382,"username":"g**eii"},{"coinCount":19859,"level":199,"rank":"2","userId":29303,"username":"深**士"},{"coinCount":19609,"level":197,"rank":"3","userId":3559,"username":"A**ilEyon"},{"coinCount":16503,"level":166,"rank":"4","userId":2,"username":"x**oyang"},{"coinCount":12317,"level":124,"rank":"5","userId":28694,"username":"c**ng0218"},{"coinCount":12258,"level":123,"rank":"6","userId":3753,"username":"S**phenCurry"},{"coinCount":12154,"level":122,"rank":"7","userId":29185,"username":"轻**宇"},{"coinCount":12086,"level":121,"rank":"8","userId":12467,"username":"c**yie"},{"coinCount":11958,"level":120,"rank":"9","userId":27535,"username":"1**08491840"},{"coinCount":11920,"level":120,"rank":"10","userId":1534,"username":"j**gbin"},{"coinCount":11841,"level":119,"rank":"11","userId":9621,"username":"S**24n"},{"coinCount":11745,"level":118,"rank":"12","userId":28607,"username":"S**Brother"},{"coinCount":11650,"level":117,"rank":"13","userId":27,"username":"y**ochoo"},{"coinCount":11614,"level":117,"rank":"14","userId":7891,"username":"h**zkp"},{"coinCount":11592,"level":116,"rank":"15","userId":14829,"username":"l**changwen"},{"coinCount":11580,"level":116,"rank":"16","userId":12351,"username":"w**igeny"},{"coinCount":11472,"level":115,"rank":"17","userId":26707,"username":"p**xc.com"},{"coinCount":11469,"level":115,"rank":"18","userId":833,"username":"w**lwaywang6"},{"coinCount":11385,"level":114,"rank":"19","userId":12331,"username":"R**kieJay"},{"coinCount":11361,"level":114,"rank":"20","userId":7809,"username":"1**23822235"},{"coinCount":11274,"level":113,"rank":"21","userId":7710,"username":"i**Cola7"},{"coinCount":11140,"level":112,"rank":"22","userId":7590,"username":"陈**啦啦啦"},{"coinCount":11130,"level":112,"rank":"23","userId":4886,"username":"z**iyun"},{"coinCount":11065,"level":111,"rank":"24","userId":29076,"username":"f**ham"},{"coinCount":10940,"level":110,"rank":"25","userId":2068,"username":"i**Cola"},{"coinCount":10690,"level":107,"rank":"26","userId":25419,"username":"蔡**打篮球"},{"coinCount":10606,"level":107,"rank":"27","userId":2160,"username":"R**iner"},{"coinCount":10560,"level":106,"rank":"28","userId":25128,"username":"f**wandroid"},{"coinCount":10515,"level":106,"rank":"29","userId":3825,"username":"请**娃哈哈"},{"coinCount":10120,"level":102,"rank":"30","userId":25793,"username":"F**_2014"}]
     * offset : 0
     * over : false
     * pageCount : 1703
     * size : 30
     * total : 51062
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<RankBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RankBean> getDatas() {
        return datas;
    }

    public void setDatas(List<RankBean> datas) {
        this.datas = datas;
    }

}
