package com.zlx.sharelive.base.event.oneclick;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @date: 2019\3\5 0005
 * @author: zlx
 * @email: 1170762202@qq.com
 * @description:
 */
public class LimitQueue<E>  {

    private int limitedSize;

    private LinkedList<E> linkedList = new LinkedList<>();

    public LimitQueue(int size) {
        this.limitedSize = size;
    }

    public void offer(E e) {
        if (linkedList.size() >= limitedSize) {
            linkedList.poll();
        }
        linkedList.offer(e);
    }

    public E get(int position) {
        return linkedList.get(position);
    }

    public E getLast() {
        return linkedList.getLast();
    }

    public E getFirst() {
        return linkedList.getFirst();
    }

    public int getLimit() {
        return limitedSize;
    }

    public void setLimitedSize(int size) {
        this.limitedSize = size;
    }

    public int size() {
        return linkedList.size();
    }

    public ArrayList<E> getArrayList() {
        ArrayList<E> arrayList = new ArrayList<>();
        for (int i = 0; i < linkedList.size(); i ++) {
            arrayList.add(linkedList.get(i));
        }
        return arrayList;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < linkedList.size(); i++) {
            buffer.append(linkedList.get(i));
            buffer.append(" ");
        }
        return buffer.toString();
    }
}
