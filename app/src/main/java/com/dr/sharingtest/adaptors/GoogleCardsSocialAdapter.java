package com.dr.sharingtest.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dr.sharingtest.R;
import com.dr.sharingtest.model.Amplification;
import com.dr.sharingtest.model.DummyModel;
import com.dr.sharingtest.utils.ImageUtil;
import com.dr.sharingtest.utils.MyConstants;

import java.util.List;
import java.util.Locale;


public class GoogleCardsSocialAdapter extends ArrayAdapter<Amplification>
		implements OnClickListener {

	private LayoutInflater mInflater;

	public GoogleCardsSocialAdapter(Context context, List<Amplification> items) {
		super(context, 0, items);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_item_google_cards_social, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.list_item_google_cards_social_image);
			holder.profileImage = (ImageView) convertView
					.findViewById(R.id.list_item_google_cards_social_profile_image);
			holder.username = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_social_name);
			holder.place = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_social_place);
			holder.text = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_social_text);
			holder.like = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_social_like);
			holder.follow = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_social_follow);
			holder.like.setOnClickListener(this);
			holder.follow.setOnClickListener(this);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Amplification item = getItem(position);
		ImageUtil.displayRoundImage(holder.profileImage, MyConstants.PHOTO_URL,
				null);
		ImageUtil.displayImage(holder.image, MyConstants.PHOTO_URL, null);
		holder.username.setText("@" + item.getUser());
		holder.place.setText("from Oklahoma");
		String strAmp = String.format(Locale.US, "%d", item.getValue());
		String str = "Amplified on " + item.getTimeStamp() + "\n"
				+ "Url = " + item.getAssetURL() + "\n"
				+ "shareURL" + item.getShareURL() + "\n"
				+ "Amplification Value = " + strAmp + "\n"
				+ "Referrer = " + item.getReferrer();
		holder.text.setText(str);
		holder.like.setTag(position);
		holder.follow.setTag(position);

		return convertView;
	}

	private static class ViewHolder {
		public ImageView profileImage;
		public ImageView image;
		public TextView username;
		public TextView place;
		public TextView text;
		public TextView like;
		public TextView follow;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int possition = (Integer) v.getTag();
		switch (v.getId()) {
		case R.id.list_item_google_cards_social_like:
			// click on like button
			break;
		case R.id.list_item_google_cards_social_follow:
			// click on follow button
			break;
		}
	}
}
