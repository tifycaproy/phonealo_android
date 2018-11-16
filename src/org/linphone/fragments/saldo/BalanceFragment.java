package org.linphone.fragments.saldo;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.linphone.LinphoneActivity;
import org.linphone.LinphoneManager;
import org.linphone.R;
import org.linphone.StatusFragment;
import org.linphone.application.ApplicationConfig;
import org.linphone.data.Data;
import org.linphone.interfaces.CommonInterfaces;
import org.linphone.logs.Buzlog;
import org.linphone.model.Balance;
import org.linphone.model.Rate;
import org.linphone.model.User;
import org.linphone.server.impl.BalanceRestApiUtil;

import java.util.List;


/**
 * Created by macmini02 on 3/10/16.
 */

public class BalanceFragment extends Fragment implements View.OnClickListener, CommonInterfaces.Balance {

    LayoutInflater mInflater;
    List<Rate> mRates;
    Balance mBalance;

    ListView ratesList;
    TextView noDataList;
    TextView balanceAmount;
    TextView recharge;
    TextView currency_head_char;

    View header;
    private StatusFragment statusFragment;

    //usuario que es actualizado
    User mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chatlist, container, false);
        ratesList = (ListView) rootView.findViewById(R.id.ratesList);
        mInflater = inflater;

        noDataList = (TextView) rootView.findViewById(R.id.noDataList);
        balanceAmount = (TextView) rootView.findViewById(R.id.balanceAmount);
        recharge = (TextView) rootView.findViewById(R.id.recharge);
        currency_head_char = (TextView) rootView.findViewById(R.id.currency_head_char);

        currency_head_char.setText(ApplicationConfig.currency_char);

        recharge.setOnClickListener(this);

        header = inflater.inflate(R.layout.balance_header, ratesList, false);
        ratesList.addHeaderView(header);

        //obtenemos la información del usuario de la DB
        mUser = Data.getUser();


        new BalanceRestApiUtil().getBalance(Data.getUser(), this);

        loadData();


        return rootView;
    }

    private void loadData() {
        mRates = Data.getAllRates();
        mBalance = Data.getUserBalance();

        //carga la cantidad de dinero que tiene almacenada en la base de datos
        balanceAmount.setText(mBalance.getAmount());



        if (mRates.size() == 0) {
            noDataList.setVisibility(View.VISIBLE);
            ratesList.setVisibility(View.GONE);
        } else {
            noDataList.setVisibility(View.GONE);
            ratesList.setVisibility(View.VISIBLE);
            ratesList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            ratesList.setAdapter(new RatesListAdapter());
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.recharge) {
            //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://app.phonealo.net/payment/init"));
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(LinphoneManager.getInstance().urlPayments));
            startActivity(browserIntent);
        }
    }

    @Override
    public void onGetBalanceDone(String result) {
        try{
            JSONObject jsonObject = new JSONObject(result);
            Balance mBalance = new Balance(jsonObject);

            LinphoneManager.getInstance().urlPayments = jsonObject.getString("url_payment");

            mUser.setUserBalance(mBalance);

            Data.upsert(mUser);

            //statusFragment.onGetBalanceDone(result);
            //statusFragment
        }catch (JSONException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
        Buzlog.d("BalanceFragment", result);

    }

    class RatesListAdapter extends BaseAdapter {
        private class ViewHolder {
            public TextView country_prefix;
            public TextView price;
            public TextView currency;
            public TextView country_name;



            public ViewHolder(View view) {
                country_prefix = (TextView) view.findViewById(R.id.country_prefix);
                price = (TextView) view.findViewById(R.id.price);
                currency = (TextView) view.findViewById(R.id.currency);
                country_name = (TextView) view.findViewById(R.id.country_name);

            }
        }

        RatesListAdapter() {}

        public int getCount() {
            return mRates.size();
        }

        public Object getItem(int position) {
            return mRates.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = null;
            ViewHolder holder = null;
            Rate mRate = mRates.get(position);

            if (convertView != null) {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            } else {
                view = mInflater.inflate(R.layout.rateslist_cell, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }

            int prefix = Integer.valueOf(mRate.getCountry_prefix());
            holder.country_prefix.setText("+" + String.valueOf(prefix));
            holder.price.setText(mRate.getPrice());
            holder.currency.setText(mRate.getCurrency());
            holder.country_name.setText(mRate.getCountry_name());

            return view;
        }
    }
}
