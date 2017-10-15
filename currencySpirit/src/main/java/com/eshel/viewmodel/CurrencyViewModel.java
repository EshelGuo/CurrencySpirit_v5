package com.eshel.viewmodel;

import com.eshel.currencyspirit.CurrencySpiritApp;
import com.eshel.model.CurrencyModel;
import com.eshel.model.EssenceModel;
import com.eshel.net.api.NewListApi;
import com.eshel.net.factory.RetrofitFactory;
import com.eshel.viewmodel.BaseViewModel.Mode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import baseproject.util.Log;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * createBy Eshel
 * createTime: 2017/10/15 20:59
 * desc: TODO
 */

public class CurrencyViewModel {
	static BaseViewModel base = new BaseViewModel();
	/**
	 * 请求市值数据
	 */
	public static void getMarketValueData(final Mode mode){
		final long ago = System.currentTimeMillis();
		NewListApi newListApi = RetrofitFactory.getRetrofit().create(NewListApi.class);
		Call<ResponseBody> currency = newListApi.coinInfo(base.start, base.count,"volume",true,"USD");
		currency.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				if(response.isSuccessful()){
					try {
						String json = response.body().string();
						refreshView(json, mode, ago);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					String errMsg = "";
					try {
						errMsg = response.errorBody().string();
					} catch (Throwable e) {
						e.printStackTrace();
					}
					CurrencyModel.MarketValueModel.notifyView(mode,false);
					Log.e(CurrencyViewModel.class, errMsg);
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				CurrencyModel.MarketValueModel.notifyView(mode,false);
				t.printStackTrace();
			}
		});
	}
	private static void refreshView(String json, final Mode mode, long ago) {
		Gson gson = new Gson();
		final ArrayList<CurrencyModel> data = gson.fromJson(json, new TypeToken<ArrayList<CurrencyModel>>() {}.getType());
		CurrencySpiritApp.getApp().getHandler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if(mode == Mode.REFRESH)
					EssenceModel.essenceData.clear();
				else {
					base.start += base.count;
				}
				CurrencyModel.MarketValueModel.loadDataCount = data.size();
				CurrencyModel.MarketValueModel.marketValueData.addAll(data);
				CurrencyModel.MarketValueModel.notifyView(mode,true);
			}
		},base.getRefreshTime(mode,ago));
	}
	public static void refreshData(){
		base.start = 0;
		getMarketValueData(Mode.REFRESH);
	}
}
