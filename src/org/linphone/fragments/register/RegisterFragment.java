package org.linphone.fragments.register;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.linphone.R;
import org.linphone.RegisterActivity;
import org.linphone.RegisterFragmentsEnum;
import org.linphone.application.ApplicationConfig;
import org.linphone.interfaces.CommonInterfaces;
import org.linphone.logs.Buzlog;
import org.linphone.model.User;
import org.linphone.server.impl.RegisterRestApiUtil;


/**
 * Created by macmini02 on 28/9/16.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener, TextWatcher, CommonInterfaces.registerUser {

    private static final String TAG = "RegisterFragment";

    EditText username;
    EditText phone_number;
    TextView country;
    EditText email;

    String user_name;
    String phoneNumber;
    String countryPrefix;
    String emailAddrs;

    Button register_apply;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.register, null);

        username = (EditText) rootView.findViewById(R.id.username);
        phone_number = (EditText) rootView.findViewById(R.id.phoneNumber);
        country = (TextView) rootView.findViewById(R.id.country);
        email = (EditText) rootView.findViewById(R.id.email);

        username.getText().clear();
        phone_number.getText().clear();
        country.setText("");
        email.getText().clear();

        register_apply = (Button) rootView.findViewById(R.id.register_apply);

        register_apply.setEnabled(false);
        register_apply.setOnClickListener(this);
        country.setOnClickListener(this);
        username.addTextChangedListener(this);
        phone_number.addTextChangedListener(this);
        email.addTextChangedListener(this);

        getActivity().findViewById(R.id.back).setVisibility(View.INVISIBLE);
        RegisterActivity.instance().currentFragment = RegisterFragmentsEnum.HOME;

        if(RegisterActivity.instance().country != null) {
            country.setText(RegisterActivity.instance().country.getCountry_name());
        }

        return rootView;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        register_apply.setEnabled(!username.getText().toString().isEmpty() && !phone_number.getText().toString().isEmpty() && !country.getText().toString().isEmpty() && !email.getText().toString().isEmpty());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.country) {
            RegisterActivity.instance().displayCountryChooser();
        }else if(view.getId() == R.id.register_apply) {
            checkFields();
        }
    }

    public void checkFields() {
        boolean valido = true;

        valido = valido && ApplicationConfig.validateEmail(email, "Campo incorrecto");
        valido = valido && ApplicationConfig.validateEdit(username, "Campo incorrecto");

        if(valido) {
            user_name = username.getText().toString();
            phoneNumber = phone_number.getText().toString();
            countryPrefix = RegisterActivity.instance().country.getCountry_prefix();
            emailAddrs = email.getText().toString();

            new RegisterRestApiUtil().retrieveData(user_name, phoneNumber, emailAddrs, countryPrefix, this);

        }

    }

    @Override
    public void onGetDataDone(String result) {
//        result = StringUtil.loadJSONFromAsset(getActivity(), R.raw.registro);
        User mUser;
        try {
            JSONObject jsonObject = new JSONObject(result);
            mUser = new User(jsonObject);
            mUser.setPhoneNumber(phoneNumber);
            mUser.setEmail(emailAddrs);
            mUser.setUser_country_prefix(countryPrefix);

            RegisterActivity.instance().displayPinFragment(mUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Buzlog.d(TAG, result);

        username.getText().clear();
        phone_number.getText().clear();

        country.setText("");
        email.getText().clear();

    }

}
