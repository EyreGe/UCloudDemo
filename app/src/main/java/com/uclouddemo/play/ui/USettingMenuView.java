package com.uclouddemo.play.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.uclouddemo.R;
import com.uclouddemo.play.ui.base.UMenuItem;
import com.uclouddemo.play.ui.base.UMenuItemHelper;


/**
 *
 * Created by lw.tan on 2015/10/10.
 *
 */
public class USettingMenuView extends LinearLayout {

//	@Bind(R.id.listview)
	ListView mSettingItemLv;

//	@Bind(R.id.listview_content)
	ListView mSettingContentItemLv;

//	@Bind(R.id.menu_description_txtv)
	TextView mMenuContentTitleTxtv;

//	@Bind(R.id.menu_txtv)
	TextView mMainMenuTitleTxtv;

	private UMenuItem mMainMenuItem;

	private MenuSettingAdapter mMenuSettingAdapter;
	private MenuSettingContentAdapter mMenuSettingContentAdapter;

	private Callback mSettingMenuViewClickListener;


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public USettingMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public USettingMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public USettingMenuView(Context context) {
		super(context, null);
	}

	public interface Callback {
		boolean onSettingMenuSelected(UMenuItem item);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
//		ButterKnife.bind(this);

		mSettingItemLv = (ListView)findViewById(R.id.listview);
		mSettingContentItemLv = (ListView)findViewById(R.id.listview_content);
		mMenuContentTitleTxtv = (TextView)findViewById(R.id.menu_description_txtv);
		mMainMenuTitleTxtv = (TextView)findViewById(R.id.menu_txtv);
	}

	public void init() {
		mMainMenuItem = UMenuItemHelper.getInstance(getContext()).getMainMenu();

		mMenuSettingAdapter = new MenuSettingAdapter();
		mMenuSettingContentAdapter = new MenuSettingContentAdapter();

		if (mMainMenuTitleTxtv != null) {
			mMainMenuTitleTxtv.setText(mMainMenuItem.title);
		}

		if (mMenuContentTitleTxtv != null) {
			mMenuContentTitleTxtv.setText(mMainMenuItem.childs.get(mMainMenuItem.defaultSelected).title);
		}

		mSettingItemLv.setAdapter(mMenuSettingAdapter);

		mSettingItemLv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				UMenuItem item = mMainMenuItem.childs.get(position);
				mMenuSettingAdapter.notifyDataSetChanged();
				mMenuSettingContentAdapter.notifyDataSetChanged();
				if (mMainMenuItem.defaultSelected != position) {
					mMainMenuItem.defaultSelected = position;
					mMenuContentTitleTxtv.setText(item.title);
					if (mSettingMenuViewClickListener != null) {
						mSettingMenuViewClickListener.onSettingMenuSelected(item);
					}
				}
			}
		});

		mSettingContentItemLv.setAdapter(mMenuSettingContentAdapter);

		mSettingContentItemLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				UMenuItem menuItem = mMainMenuItem.childs.get(mMainMenuItem.defaultSelected);
				UMenuItem contentMenuItem = menuItem.childs.get(position);
				if (menuItem.defaultSelected != Integer.parseInt(contentMenuItem.type)) {
					menuItem.defaultSelected = position;
					mMenuSettingContentAdapter.notifyDataSetChanged();
					mMenuSettingAdapter.notifyDataSetChanged();
					if (mSettingMenuViewClickListener != null) {
						mSettingMenuViewClickListener.onSettingMenuSelected(contentMenuItem);
					}
				}
			}
		});
	}

	class MenuSettingAdapter extends BaseAdapter {

		public MenuSettingAdapter() {

		}

		@Override
		public int getCount() {
			return mMainMenuItem.childs.size();
		}

		@Override
		public Object getItem(int position) {
			return mMainMenuItem.childs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getContext(), R.layout.player_layout_setting_menu_item, null);
			TextView titleTxtv= (TextView) view.findViewById(R.id.title_txtv);
			TextView descriptionTxtv = (TextView) view.findViewById(R.id.description_txtv);
			UMenuItem item = mMainMenuItem.childs.get(position);
			if (item != null) {
				titleTxtv.setText(item.title);
				if (item.defaultSelected >= 0 && item.defaultSelected <= item.childs.size() - 1) {
					descriptionTxtv.setText(item.childs.get(item.defaultSelected).title);
				} else {
					if (item.childs != null && item.childs.size() >= 1) {
						descriptionTxtv.setText(item.childs.get(0).title);
					}
				}
			}

			if(position == mMainMenuItem.defaultSelected) {
				titleTxtv.setTextColor(getResources().getColor(R.color.color_progress));
				descriptionTxtv.setTextColor(getResources().getColor(R.color.color_progress));
			}else{
				titleTxtv.setTextColor(getResources().getColor(R.color.color_white));
				descriptionTxtv.setTextColor(getResources().getColor(R.color.color_white_alpha_alpha40));
			}
			return view;
		}
	}

	class MenuSettingContentAdapter extends BaseAdapter {

		public MenuSettingContentAdapter() {

		}

		@Override
		public int getCount() {
			return mMainMenuItem.childs != null && mMainMenuItem.childs.get(mMainMenuItem.defaultSelected).childs != null ? mMainMenuItem.childs.get(mMainMenuItem.defaultSelected).childs.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return mMainMenuItem.childs.get(mMainMenuItem.defaultSelected).childs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			UMenuItem item = null;
			if (mMainMenuItem.childs.get(mMainMenuItem.defaultSelected).childs != null && mMainMenuItem.childs.get(mMainMenuItem.defaultSelected).childs.size() > 0 ) {
				item = mMainMenuItem.childs.get(mMainMenuItem.defaultSelected).childs.get(position);
			}

			View view = null;
			if (item != null && !TextUtils.isEmpty(item.description)) {
				view = View.inflate(getContext(), R.layout.player_layout_setting_menu_content_item, null);
			} else {
				view = View.inflate(getContext(), R.layout.player_layout_setting_menu_content_item2, null);
			}
			TextView titleTxtv= (TextView) view.findViewById(R.id.title_txtv);
			TextView descriptionTxtv = (TextView) view.findViewById(R.id.description_txtv);
			if (item != null) {
				titleTxtv.setText(item.title);
				descriptionTxtv.setText(item.description);
			}

			if((item.parent != null && position == item.parent.defaultSelected) || item.parent.childs.size() == 1){
				titleTxtv.setTextColor(getResources().getColor(R.color.color_progress));
				descriptionTxtv.setTextColor(getResources().getColor(R.color.color_progress));
			} else {
				view.setBackgroundResource(android.R.color.transparent);
				titleTxtv.setTextColor(getResources().getColor(R.color.color_white));
				descriptionTxtv.setTextColor(getResources().getColor(R.color.color_white_alpha_alpha40));
			}
			return view;
		}
	}

	public void setOnMenuItemSelectedListener(Callback l) {
		mSettingMenuViewClickListener = l;
	}
}
