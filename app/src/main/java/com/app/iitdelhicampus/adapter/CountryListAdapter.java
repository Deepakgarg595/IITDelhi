package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.Country;

import java.util.ArrayList;
import java.util.Locale;

public class CountryListAdapter extends BaseAdapter {

	private final ArrayList<Country> countriesList;
	private final ArrayList<Country> filteredCountriesList;
	private Context mContext;
	private LayoutInflater mInflator;
	private String mSearchText	= "";
	private int	colorHighLight;

	public CountryListAdapter(Context mContext) {
		this.mContext = mContext;
		mInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String[] countriesArray = mContext.getResources().getStringArray(R.array.CountryCodes);
		colorHighLight = ContextCompat.getColor(mContext, R.color.colorBlue);
		countriesList = new ArrayList<>();
		for (String data: countriesArray){
			countriesList.add(new Country(data));
		}

		filteredCountriesList = new ArrayList<>();
		filteredCountriesList.addAll(countriesList);
	}

	@Override
	public int getCount() {
		return filteredCountriesList.size();
	}

	@Override
	public Country getItem(int position) {
		return filteredCountriesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup container) {
		ViewHolder holder;
		if (view == null) {
            view = mInflator.inflate(R.layout.list_item_countries, null);
            holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		Country country = getItem(position);

		int startPos = country.name.toLowerCase(Locale.US).indexOf(mSearchText.toLowerCase(Locale.US));
		int endPos = startPos + mSearchText.length();

		if (startPos != -1) // This should always be true, just a sanity check
		{
			Spannable spannable = new SpannableString(country.name);
			ColorStateList blueColor = new ColorStateList(new int[][] { new int[] {} }, new int[] { colorHighLight });
			TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);

			spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.txvCountryName.setText(spannable);
		} else
			holder.txvCountryName.setText(country.name);

		holder.txvCountryCode.setText(country.countryCode);

		String pngName = country.isoCode.trim().toLowerCase();
		holder.imgFlag.setImageResource(mContext.getResources().getIdentifier("drawable/" + pngName, null, mContext.getPackageName()));

		return view;
	}

	public void filter(String charText) {
		mSearchText = charText.toUpperCase(Locale.ENGLISH);
		filteredCountriesList.clear();
		if (TextUtils.isEmpty(mSearchText)){
			filteredCountriesList.addAll(countriesList);
		} else {
			for (Country country : countriesList) {
				if (country.name.toUpperCase(Locale.ENGLISH).contains(mSearchText)){
					filteredCountriesList.add(country);
				}
			}
		}

		notifyDataSetChanged();
	}

	public static class ViewHolder {
		ImageView imgFlag;
		TextView txvCountryName;
		TextView txvCountryCode;

        public ViewHolder(View view) {
            imgFlag = (ImageView) view.findViewById(R.id.imgFlag);
            txvCountryName = (TextView) view.findViewById(R.id.txvCountryName);
            txvCountryCode = (TextView) view.findViewById(R.id.txvCountryCode);
        }
    }
}
