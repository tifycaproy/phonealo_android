package org.linphone.fragments.register;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.linphone.LinphoneActivity;
import org.linphone.LinphoneManager;
import org.linphone.LinphonePreferences;
import org.linphone.LinphonePreferences.AccountBuilder;
import org.linphone.R;
import org.linphone.RegisterActivity;
import org.linphone.RegisterFragmentsEnum;
import org.linphone.core.LinphoneCoreException;
import org.linphone.data.Data;
import org.linphone.model.User;


/**
 * Created by macmini02 on 29/9/16.
 */

public class VerifyUserFragment extends Fragment implements View.OnClickListener, TextWatcher {
    private static final String TAG = "VerifyUserFragment";

    EditText pincode_et;
    Button login_btn;
    TextView findPin;

    private User mUser;

    private LinphonePreferences mPrefs;
    int n = 0;

    AccountBuilder accountBuilder;

    public VerifyUserFragment() {
        mPrefs = LinphonePreferences.instance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pin, null);

        pincode_et = (EditText)rootView.findViewById(R.id.pincode_et);
        login_btn = (Button)rootView.findViewById(R.id.login_btn);
        findPin = (TextView) rootView.findViewById(R.id.findPin);
        login_btn.setEnabled(false);
        login_btn.setOnClickListener(this);
        pincode_et.addTextChangedListener(this);
        findPin.setOnClickListener(this);

        mUser = (User) getArguments().getSerializable("user");

        RegisterActivity.instance().currentFragment = RegisterFragmentsEnum.PIN;

        return rootView;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.login_btn) {
            if(pincode_et.getText().toString().equals(mUser.getSecret())) {
                Data.upsert(mUser);
                buildPrefs();

                Intent intent = new Intent(getActivity(), LinphoneActivity.class);
                startActivity(intent);
                getActivity().finish();
            }else {
                Toast.makeText(getActivity(), "PIN incorrecto", Toast.LENGTH_LONG).show();
                getFragmentManager().popBackStack();
            }
        }
        if(v.getId() == R.id.findPin) {
            findPin.setText(mUser.getSecret());
        }

    }

    public void buildPrefs() {
        accountBuilder = new AccountBuilder(LinphoneManager.getLc());
//        accountBuilder.setPrefix(mUser.getUser_country_prefix());
        accountBuilder.setUsername(mUser.getId());
        accountBuilder.setUserId(mUser.getId());
//        accountBuilder.setUserId(mUser.getId());
        accountBuilder.setDomain(mUser.getSipServer());
        accountBuilder.setPassword(mUser.getPinServer());
        accountBuilder.setPrefix(mUser.getUser_country_prefix());
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if(accountBuilder != null) {
                accountBuilder.saveNewAccount();
                LinphoneManager.getLc().refreshRegisters();
            }
        }catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        login_btn.setEnabled(!pincode_et.getText().toString().isEmpty());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
}
