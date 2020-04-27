package com.zlx.sharelive.widget.euclid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.zlx.sharelive.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Oleksii Shliama on 1/27/15.
 */
public class EuclidListAdapter extends ArrayAdapter<Map<String, Object>> {

    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION_SHORT = "description_short";
    public static final String KEY_DESCRIPTION_FULL = "description_full";

    private final LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public EuclidListAdapter(Context context, int layoutResourceId, List<Map<String, Object>> data) {
        super(context, layoutResourceId, data);
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mViewOverlay = convertView.findViewById(R.id.view_avatar_overlay);
            viewHolder.mListItemAvatar = (ImageView) convertView.findViewById(R.id.image_view_avatar);
            viewHolder.mListItemName = (TextView) convertView.findViewById(R.id.text_view_name);
            viewHolder.mListItemDescription = (TextView) convertView.findViewById(R.id.text_view_description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .override(EuclidActivity.sScreenWidth, EuclidActivity.sProfileImageHeight)
                .placeholder(R.color.main)
                .centerCrop();
        Glide.with(getContext())
                .load((Integer) mData.get(position).get(KEY_AVATAR))
                .apply(options)
                .into(viewHolder.mListItemAvatar);

        viewHolder.mListItemName.setText(mData.get(position).get(KEY_NAME).toString().toUpperCase());
        viewHolder.mListItemDescription.setText((String) mData.get(position).get(KEY_DESCRIPTION_SHORT));
        viewHolder.mViewOverlay.setBackground(EuclidActivity.sOverlayShape);

        return convertView;
    }

    static class ViewHolder {
        View mViewOverlay;
        ImageView mListItemAvatar;
        TextView mListItemName;
        TextView mListItemDescription;
    }
}
