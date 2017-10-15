package com.eshel.currencyspirit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eshel.currencyspirit.R;
import com.eshel.currencyspirit.factory.FragmentFactory;
import com.eshel.currencyspirit.fragment.currency.AOIFragment;
import com.eshel.currencyspirit.fragment.currency.MarketValueFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import baseproject.base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * createBy Eshel
 * createTime: 2017/10/4 20:43
 * desc: TODO
 */

public class CurrencyFragment extends BaseFragment {

	@BindView(R.id.tab)
	SmartTabLayout tab;
	@BindView(R.id.viewpager)
	ViewPager viewpager;
	Unbinder unbinder;
	private CurrencyAdapter mCurrencyAdapter;
	private View mView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		container.postDelayed(new Runnable() {
			@Override
			public void run() {
				changeState(LoadState.StateLoadSuccess);
			}
		}, 100);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	protected void reloadData() {
	}

	@Override
	public View getLoadSuccessView() {
		if(mView == null) {
			mView = View.inflate(getActivity(), R.layout.view_currency, null);
		}
		unbinder = ButterKnife.bind(this, mView);
/*		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			if(tab != null)
				tab.setElevation(DensityUtil.dp2px(HomeActivity.titleElevation/2));
			HomeActivity activity = (HomeActivity) getActivity();
			if(activity.getTitle2() != null)
				activity.getTitle2().setElevation(0);
		}*/
		if (mCurrencyAdapter == null) {
			mCurrencyAdapter = new CurrencyAdapter();
			viewpager.setAdapter(mCurrencyAdapter);
			tab.setViewPager(viewpager);
		}
		return mView;
	}

	@Override
	public void notifyView() {
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
	class CurrencyAdapter extends FragmentPagerAdapter{
		String[] pagerTitle;
		public CurrencyAdapter() {
			super(getChildFragmentManager());
			pagerTitle = new String[]{
					getString(R.string.market_value),
					getString(R.string.amount_of_increase)
			};
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position){
				case 0:
					fragment = FragmentFactory.getFragment(MarketValueFragment.class);
					break;
				case 1:
					fragment = FragmentFactory.getFragment(AOIFragment.class);
					break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			if(pagerTitle != null)
				return pagerTitle.length;
			return 0;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return pagerTitle[position];
		}
	}

/*	@Override
	public void onResume() {
		super.onResume();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			if(tab != null)
				tab.setElevation(DensityUtil.dp2px(HomeActivity.titleElevation/2));
			HomeActivity activity = (HomeActivity) getActivity();
			if(activity.getTitle2() != null)
				activity.getTitle2().setElevation(0);
		}
	}*/
}
