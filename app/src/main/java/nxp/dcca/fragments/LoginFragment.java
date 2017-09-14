package nxp.dcca.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nxp.dcca.MainActivity;
import nxp.dcca.R;

/**
 * Created by b41466 on 17-9-6.
 */

public class LoginFragment extends Fragment {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    // UI references.
    private AutoCompleteTextView mUserNameView;
    private Button mSignInButton;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private View mFragView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragView = inflater.inflate(R.layout.frag_login, container, false);
        mUserNameView = (AutoCompleteTextView) mFragView.findViewById(R.id.username);
        mUserNameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                mPasswordView.requestFocus();
                return false;
            }
        });
        mUserNameView.setInputType(InputType.TYPE_NULL);
        mUserNameView.setOnTouchListener(new TextView.OnTouchListener() {
            @Override
            public boolean onTouch(View var1, MotionEvent var2){
                mUserNameView.setInputType(InputType.TYPE_CLASS_TEXT);
                return false;
            }
        });


        mPasswordView = (EditText) mFragView.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                attemptLogin();
                return false;
            }
        });
        mPasswordView.setInputType(InputType.TYPE_NULL);
        mPasswordView.setOnTouchListener(new TextView.OnTouchListener() {
            @Override
            public boolean onTouch(View var1, MotionEvent var2){
                mPasswordView.setInputType(InputType.TYPE_CLASS_TEXT);
                return false;
            }
        });

        mSignInButton = (Button) mFragView.findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = mFragView.findViewById(R.id.login_form);
        mProgressView = mFragView.findViewById(R.id.progressBar);
        return mFragView;
    }

    /**
     * Called when the fragment is resumed.
     */
    @Override
    public void onResume() {
        mPasswordView.setInputType(InputType.TYPE_NULL);
        mUserNameView.setInputType(InputType.TYPE_NULL);
        super.onResume();
    }

    /**
     * Called when the fragment is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid user name, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username) || !isUserNameValid(username)) {
            mUserNameView.setError(getString(R.string.error_invalid_password));
            focusView = mUserNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //mSignInButton.setText(R.string.action_cancel);
            mPasswordView.setInputType(InputType.TYPE_NULL);
            mUserNameView.setInputType(InputType.TYPE_NULL);
            //showProgress(true);

            hideSoftKeyboard();
            ((MainActivity) getActivity()).switchFragment(MainActivity.BDLIST_FRAG_TAG);
        }
    }

    private boolean isUserNameValid(String username) {
        //TODO: Replace this with your own logic
        return username.length() >= 1;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 1;
    }

    private void hideSoftKeyboard(){
        InputMethodManager imm =  (InputMethodManager)getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
