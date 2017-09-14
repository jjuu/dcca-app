package nxp.dcca;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import nxp.dcca.fragments.BoardsListFragment;
import nxp.dcca.fragments.LoginFragment;
import nxp.dcca.fragments.QRCodeFragment;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends AppCompatActivity{

    public static final String LOGIN_FRAG_TAG = "Login";
    public static final String BDLIST_FRAG_TAG = "BdList";
    public static final String QRCODE_FRAG_TAG = "QRCode";

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
    private FragmentManager mFragMgr;
    private Fragment mCurrentFrag;


    Fragment mLoginFrag = new LoginFragment();
    Fragment mBdListFrag = new BoardsListFragment();
    Fragment mQRCodeFrag = new QRCodeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        mFragMgr = getSupportFragmentManager();

        FragmentTransaction fragTrans = mFragMgr.beginTransaction();
        mCurrentFrag = mLoginFrag = new LoginFragment();
        mBdListFrag = new BoardsListFragment();
        mQRCodeFrag = new QRCodeFragment();

        fragTrans.add(R.id.fragment_container, mLoginFrag,
                LOGIN_FRAG_TAG).show(mLoginFrag);
        fragTrans.add(R.id.fragment_container, mBdListFrag,
                BDLIST_FRAG_TAG).hide(mBdListFrag);
        fragTrans.add(R.id.fragment_container, mQRCodeFrag,
                QRCODE_FRAG_TAG).hide(mQRCodeFrag);
        fragTrans.commit();
    }

    public void switchFragment(String tag) {
        FragmentTransaction fragTrans = mFragMgr.beginTransaction();
        Fragment to = mFragMgr.findFragmentByTag(tag);
        if (mCurrentFrag != to && to != null) {
            if (!to.isAdded()) {
                fragTrans.hide(mCurrentFrag).add(R.id.fragment_container, to).commit();
            } else {
                fragTrans.hide(mCurrentFrag).show(to).commit();
            }
            mCurrentFrag = to;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction()==KeyEvent.ACTION_DOWN) {
            if (mCurrentFrag == mQRCodeFrag) {
                switchFragment(BDLIST_FRAG_TAG);
                return true;
            } else if (mCurrentFrag == mBdListFrag) {
                switchFragment(LOGIN_FRAG_TAG);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

