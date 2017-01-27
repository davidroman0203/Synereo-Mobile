package com.dr.sharingtest.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.dr.sharingtest.R;
import com.dr.sharingtest.adaptors.DrawerMediaAdapter;
import com.dr.sharingtest.fragments.AmplificationsFragment;
import com.dr.sharingtest.fragments.ProfileFragment;
import com.dr.sharingtest.ui.font.RobotoTextView;
import com.dr.sharingtest.utils.ImageUtil;
import com.dr.sharingtest.utils.MyConstants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;


public class LeftMenusMediaActivity extends ActionBarActivity implements
		AmplificationsFragment.OnFragmentInteractionListener,
		ProfileFragment.OnFragmentInteractionListener,
		OnDismissCallback {

	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	public static ProfileFragment mProfileFragment;
	public static AmplificationsFragment mAmplificationsFragment;

	public static FragmentManager mFragmentManager;

	SharedPreferences mSharedPref;
	SharedPreferences.Editor mEditor;

	public static String mUserName;

	RobotoTextView tvUserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//hide status bar
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leftmenus_media);

		initGlobal();



		ImageLoader imageLoader = ImageLoader.getInstance();
		if (!imageLoader.isInited()) {
			imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		}

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
//		getSupportActionBar().setTitle("Media");
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mTitle = mDrawerTitle = getTitle();
		mDrawerList = (ListView) findViewById(R.id.list_view);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		View headerView = getLayoutInflater().inflate(
				R.layout.header_navigation_drawer_media, mDrawerList, false);

		ImageView iv = (ImageView) headerView
				.findViewById(R.id.header_navigation_drawer_media_image);
		ImageUtil
				.displayRoundImage(
						iv,
						MyConstants.PHOTO_URL,
						null);

		mDrawerList.addHeaderView(headerView);// Add header before adapter (for
												// pre-KitKat)

		//set user name
		tvUserName = (RobotoTextView) findViewById(R.id.header_navigation_drawer_media_username);
		tvUserName.setText(mUserName);
		///

		mDrawerList.setAdapter(new DrawerMediaAdapter(this));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerList
				.setBackgroundResource(R.drawable.background_listview);
		mDrawerList.getLayoutParams().width = (int) getResources()
				.getDimension(R.dimen.drawer_width_media);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
				R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				tvUserName.setText(mUserName);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

//		if (savedInstanceState == null) {
//			mDrawerLayout.openDrawer(mDrawerList);
//		}

//		final FrameLayout layout = (FrameLayout) findViewById(R.id.content_frame);
		mProfileFragment = ProfileFragment.newInstance("OK", "");
		mAmplificationsFragment = AmplificationsFragment.newInstance("", "");

		mFragmentManager = getFragmentManager();

		mFragmentManager.beginTransaction()
				.replace(R.id.content_frame, mAmplificationsFragment, AmplificationsFragment.TAG)
//                .addToBackStack(null)
				.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onFragmentInteraction(Uri uri) {

	}

	@Override
	public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions) {

	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
//			Toast.makeText(LeftMenusMediaActivity.this,
//					"You selected position: " + position, Toast.LENGTH_SHORT)
//					.show();
			if (position == 0){ // show profile fragment
				mFragmentManager.beginTransaction()
						.replace(R.id.content_frame, mProfileFragment, ProfileFragment.TAG)
						.commit();
			}else if(position == 1){ //show amplifications fragment
				mFragmentManager.beginTransaction()
						.replace(R.id.content_frame, mAmplificationsFragment, AmplificationsFragment.TAG)
						.commit();
			}else{
				return;
			}
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	@Override
	public void setTitle(int titleId) {
		setTitle(getString(titleId));
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


	void initGlobal(){
		mSharedPref = getSharedPreferences(MyConstants.KEY_SHARED_PREF, Context.MODE_PRIVATE);
		mEditor = mSharedPref.edit();
		mUserName = mSharedPref.getString(MyConstants.KEY_USERNAME, "");
	}
}
