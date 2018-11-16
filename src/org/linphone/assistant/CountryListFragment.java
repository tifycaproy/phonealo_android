package org.linphone.assistant;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.linphone.R;
import org.linphone.RegisterActivity;
import org.linphone.RegisterFragmentsEnum;
import org.linphone.data.Data;

import java.util.ArrayList;
import java.util.List;


public class CountryListFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
	private LayoutInflater mInflater;
	private ListView list;
	private EditText search;
	private ImageView clearSearchField;
	private CountryListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mInflater = inflater;

		View view = inflater.inflate(R.layout.assistant_country_list, container, false);
		adapter = new CountryListAdapter(Data.getAllCountries(null), getActivity().getApplicationContext());
		//adapter = new CountryListAdapter(R.raw.countries, getActivity().getApplicationContext());

		search = (EditText)view.findViewById(R.id.search_country);
		clearSearchField = (ImageView) view.findViewById(R.id.clearSearchField);
		clearSearchField.setOnClickListener(this);

		list = (ListView)view.findViewById(R.id.countryList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);

		RegisterActivity.instance().currentFragment = RegisterFragmentsEnum.COUNTRIES;

		search.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				adapter.getFilter().filter(s);
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		org.linphone.model.Country c = (org.linphone.model.Country) view.getTag();
		RegisterActivity.instance().country = c;
		getFragmentManager().popBackStack();
//		AssistantActivity.instance().onBackPressed();
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.clearSearchField) {
			search.setText("");
		}
	}

	/**
	 * This class represents a Country. There's a name, dial_code, code and max number of digits.
	 * It is constructed from a JSON object containing all these parameters.
	 */
	public class Country {
		public String name;
		public String dial_code;
		public String code;
		public int maxNum;

		public Country(JSONObject obj ){
			try {
				name = obj.getString("name");
				dial_code = obj.getString("dial_code");
				code = obj.getString("code");
				maxNum = obj.getInt("maxNum");
			} catch (JSONException e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * This class reads a JSON file containing Country-specific phone number description,
	 * and allows to present them into a ListView
	 */
	private class CountryListAdapter extends BaseAdapter implements Filterable {

		private List<org.linphone.model.Country> allCountries;
		private List<org.linphone.model.Country> filteredCountries;
		private Context context;

		public CountryListAdapter(List<org.linphone.model.Country> countryList, Context ctx) {
			context = ctx;
			allCountries = countryList;


			filteredCountries = allCountries;

		}

		@Override
		public int getCount() {
			return filteredCountries.size();
		}

		@Override
		public org.linphone.model.Country getItem(int position) {
			return filteredCountries.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			View view = null;

			if (convertView != null) {
				view = convertView;
			} else {
				view = mInflater.inflate(R.layout.country_cell, parent, false);
			}

			org.linphone.model.Country c = filteredCountries.get(position);

			TextView name = (TextView) view.findViewById(R.id.country_name);
			name.setText(c.getCountry_name());

			int prefix = Integer.valueOf(c.getCountry_prefix());
			TextView dial_code = (TextView) view.findViewById(R.id.country_prefix);
			dial_code.setText(String.format(getString(R.string.country_code), String.valueOf(prefix)));

			view.setTag(c);
			return view;
		}

		@Override
		public Filter getFilter() {
			return new Filter() {
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					ArrayList<org.linphone.model.Country> filteredCountries = new ArrayList<>();
					for (org.linphone.model.Country c : allCountries) {
						if (c.getCountry_name().toLowerCase().contains(constraint) || c.getCountry_prefix().contains(constraint)){
							filteredCountries.add(c);
						}
					}
					FilterResults filterResults = new FilterResults();
					filterResults.values = filteredCountries;
					return filterResults;
				}

				@Override
				@SuppressWarnings("unchecked")
				protected void publishResults(CharSequence constraint, FilterResults results) {
					filteredCountries = (List<org.linphone.model.Country>) results.values;
					CountryListAdapter.this.notifyDataSetChanged();
				}
			};
		}
	}
}
