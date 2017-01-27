package com.dr.sharingtest.adaptors;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dr.sharingtest.R;
import com.dr.sharingtest.model.Amplification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 1/25/2017.
 */

public class AmplificationListAdapter extends BaseAdapter{

    private List<Amplification> listData;
    private LayoutInflater layoutInflater;

    public AmplificationListAdapter(Context context, List<Amplification> listData) {
        super();
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_amplification, null);
            holder = new ViewHolder();
            holder.tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
            holder.tvValue = (TextView) convertView.findViewById(R.id.tvValue);
            holder.tvAssetURL = (TextView) convertView.findViewById(R.id.tvAssetURL);
            holder.tvShareURL = (TextView) convertView.findViewById(R.id.tvShareURL);
            holder.tvUser = (TextView) convertView.findViewById(R.id.tvUser);
            holder.tvReferrer = (TextView) convertView.findViewById(R.id.tvReferrer);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Amplification amplification = listData.get(position);
        holder.tvTimeStamp.setText(amplification.getTimeStamp());
        String str = String.format(Locale.US, "%d", amplification.getValue());
        holder.tvValue.setText(str);
        holder.tvAssetURL.setText(amplification.getAssetURL());
        holder.tvShareURL.setText(amplification.getShareURL());
        holder.tvUser.setText(amplification.getUser());
        holder.tvReferrer.setText(amplification.getReferrer());
        return convertView;
    }

    static class ViewHolder {

        TextView tvTimeStamp;
        TextView tvValue;
        TextView tvAssetURL;
        TextView tvShareURL;
        TextView tvUser;
        TextView tvReferrer;
    }
}
