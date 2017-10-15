package com.eshel.model;

import com.eshel.currencyspirit.factory.FragmentFactory;
import com.eshel.currencyspirit.fragment.currency.MarketValueFragment;
import com.eshel.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import baseproject.base.BaseFragment;
import baseproject.util.DataUtil;

/**
 * createBy Eshel
 * createTime: 2017/10/15 20:59
 * desc: TODO
 */

public class CurrencyModel {
	public static String moneyFormat(String pre,double turnnumber){
		//9820880109.0
		/*StringBuilder sb = new StringBuilder();
		String turnnumberS = String.valueOf(turnnumber);
		int index = turnnumberS.indexOf(".");
		if(index != -1)
			sb.append(turnnumberS.substring(index,turnnumberS.length()));
		int startIndex;
		while((startIndex = index - 3) > 0){
			sb.insert(0,","+turnnumberS.substring(startIndex,index));
			index = startIndex;
		}
		sb.insert(0,turnnumberS.substring(0,index));
		sb.insert(0,pre);*/
//		return sb.toString();
		return pre + DataUtil.double2Str(turnnumber,true);
	}

	public static class MarketValueModel{
		public static List<CurrencyModel> marketValueData = new ArrayList<>();
		public static int loadDataCount = 20;
		public static CurrencyModel getMarketValueDataByPosition(int position){
			return marketValueData.get(position);
		}
		public static void notifyView(BaseViewModel.Mode mode , boolean isSuccess){
			BaseFragment marketValueFragment = (BaseFragment) FragmentFactory.getFragment(MarketValueFragment.class);
			if(isSuccess) {
				if (marketValueFragment.getCurrState() != BaseFragment.LoadState.StateLoadSuccess)
					marketValueFragment.changeState(BaseFragment.LoadState.StateLoadSuccess);
				else {
					marketValueFragment.notifyView();
				}
			}else {
				if(mode == BaseViewModel.Mode.NORMAL)
					marketValueFragment.changeState(BaseFragment.LoadState.StateLoadFailed);
				else if(mode == BaseViewModel.Mode.REFRESH){
					marketValueFragment.refreshFailed();
				}else {
					marketValueFragment.loadModeFailed();
				}
			}
		}
	}

	/**
	 * chinesename : Ethereum
	 * coin_id : ethereum
	 * englishname : ETH
	 * infoBean : {"chinesename":"Ethereum","englishname":"Ethereum","imageurl":"https://files.coinmarketcap.com/static/img/coins/128x128/ethereum.png","symbol":"ETH"}
	 * marketSymbol : USD
	 * market_type : 33
	 * percent : -4.76
	 * platform : https://coinmarketcap.com
	 * price : 326.049
	 * rank : 2
	 * symbol : ETH
	 * turnnumber : 3.1012869481E10
	 * turnvolume : 6.11255E8
	 * update_time : 1508075352000
	 * url : https://coinmarketcap.com/currencies/ethereum/
	 * yprice : null
	 */
	public String chinesename;
	public String coin_id;
	public String englishname;
	public InfoBean infoBean;
	public String marketSymbol;
	public int market_type;
	public double percent;
	public String platform;
	public double price;
	public int rank;
	public String symbol;
	public double turnnumber;
	public double turnvolume;
	public long update_time;
	public String url;
	public String yprice;

	public static class InfoBean {
		/**
		 * chinesename : Ethereum
		 * englishname : Ethereum
		 * imageurl : https://files.coinmarketcap.com/static/img/coins/128x128/ethereum.png
		 * symbol : ETH
		 */
		public String chinesename;
		public String englishname;
		public String imageurl;
		public String symbol;
	}

	public static void notifyMarketValueView(){}
}
