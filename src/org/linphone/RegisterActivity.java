package org.linphone;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import org.linphone.assistant.CountryListFragment;
import org.linphone.fragments.register.RegisterFragment;
import org.linphone.fragments.register.VerifyUserFragment;
import org.linphone.model.Country;
import org.linphone.model.User;


/**
 * Created by macmini02 on 27/9/16.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {

    private ImageView back, cancel, call_quality;
    private static RegisterActivity instance;

    public Country country;

    public RegisterFragmentsEnum currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assistant);
        initUI();
        displayMenu();

        this.instance = this;

//        call_quality = (ImageView) findViewById(R.id.call_quality);
//        call_quality.setVisibility(View.INVISIBLE);
    }

    public static RegisterActivity instance() {
        return instance;
    }

    public void hideKeyboard(){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		View view = this.getCurrentFocus();
		if (imm != null && view != null) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

    private void changeFragment(Fragment newFragment) {
        hideKeyboard();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }

    public void displayCountryChooser() {
        Fragment fragment = new CountryListFragment();
        changeFragment(fragment);
        back.setVisibility(View.VISIBLE);
    }

    public void displayPinFragment(User mUser) {
        if(mUser != null) {
            Fragment fragment = new VerifyUserFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", mUser);
            fragment.setArguments(bundle);
            changeFragment(fragment);
            back.setVisibility(View.VISIBLE);
        }
    }

    public void displayMenu() {
        Fragment rf = new RegisterFragment();
        changeFragment(rf);
        country = null;
    }

    private void initUI() {
        hideKeyboard();
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        cancel = (ImageView) findViewById(R.id.assistant_cancel);
        cancel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.back) {
            if(currentFragment != RegisterFragmentsEnum.HOME) {
                getFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(currentFragment != RegisterFragmentsEnum.HOME) {
                getFragmentManager().popBackStack();
                return true;
            }
        }
        return false;
    }
}
