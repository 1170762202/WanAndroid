package com.zlx.widget.hivelayoutmanager;

public class HiveBucket {
    final static int BITS_PER_WORD = Long.SIZE;

    final static long LAST_BIT = 1L << (Long.SIZE - 1);

    long mData = 0;

    HiveBucket mNext;

    void set(int index) {
        if (index >= BITS_PER_WORD) {
            ensureNext();
            mNext.set(index - BITS_PER_WORD);
        } else {
            mData |= 1L << index;
        }
    }

    private void ensureNext() {
        if (mNext == null) {
            mNext = new HiveBucket();
        }
    }

    void clear(int index) {
        if (index >= BITS_PER_WORD) {
            if (mNext != null) {
                mNext.clear(index - BITS_PER_WORD);
            }
        } else {
            mData &= ~(1L << index);
        }

    }

    boolean get(int index) {
        if (index >= BITS_PER_WORD) {
            ensureNext();
            return mNext.get(index - BITS_PER_WORD);
        } else {
            return (mData & (1L << index)) != 0;
        }
    }

    void reset() {
        mData = 0;
        if (mNext != null) {
            mNext.reset();
        }
    }

    void insert(int index, boolean value) {
        if (index >= BITS_PER_WORD) {
            ensureNext();
            mNext.insert(index - BITS_PER_WORD, value);
        } else {
            final boolean lastBit = (mData & LAST_BIT) != 0;
            long mask = (1L << index) - 1;
            final long before = mData & mask;
            final long after = ((mData & ~mask)) << 1;
            mData = before | after;
            if (value) {
                set(index);
            } else {
                clear(index);
            }
            if (lastBit || mNext != null) {
                ensureNext();
                mNext.insert(0, lastBit);
            }
        }
    }

    boolean remove(int index) {
        if (index >= BITS_PER_WORD) {
            ensureNext();
            return mNext.remove(index - BITS_PER_WORD);
        } else {
            long mask = (1L << index);
            final boolean value = (mData & mask) != 0;
            mData &= ~mask;
            mask = mask - 1;
            final long before = mData & mask;
            // cannot use >> because it adds one.
            final long after = Long.rotateRight(mData & ~mask, 1);
            mData = before | after;
            if (mNext != null) {
                if (mNext.get(0)) {
                    set(BITS_PER_WORD - 1);
                }
                mNext.remove(0);
            }
            return value;
        }
    }

    int countOnesBefore(int index) {
        if (mNext == null) {
            if (index >= BITS_PER_WORD) {
                return Long.bitCount(mData);
            }
            return Long.bitCount(mData & ((1L << index) - 1));
        }
        if (index < BITS_PER_WORD) {
            return Long.bitCount(mData & ((1L << index) - 1));
        } else {
            return mNext.countOnesBefore(index - BITS_PER_WORD) + Long.bitCount(mData);
        }
    }

    @Override
    public String toString() {
        return mNext == null ? Long.toBinaryString(mData)
                : mNext.toString() + "xx" + Long.toBinaryString(mData);
    }
}
