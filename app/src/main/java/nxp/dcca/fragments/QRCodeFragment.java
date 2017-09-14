package nxp.dcca.fragments;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import nxp.dcca.MainActivity;
import nxp.dcca.R;

import static android.content.Context.VIBRATOR_SERVICE;

public class QRCodeFragment extends Fragment implements QRCodeView.Delegate, View.OnClickListener {
    private static final String TAG = QRCodeFragment.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    private QRCodeView mQRCodeView;
    private View mFragView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragView = inflater.inflate(R.layout.frag_qrcode, container, false);

        mQRCodeView = (ZXingView) mFragView.findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);

        mFragView.findViewById(R.id.light_on).setOnClickListener(this);
        mFragView.findViewById(R.id.light_off).setOnClickListener(this);
        mFragView.findViewById(R.id.register).setOnClickListener(this);
        mFragView.findViewById(R.id.cancel).setOnClickListener(this);
        return mFragView;
    }

    @Override
    public void onHiddenChanged(boolean isHide) {
        super.onHiddenChanged(isHide);
        if (isHide) {
            mQRCodeView.stopSpot();
            mQRCodeView.stopCamera();
        } else {
            mQRCodeView.startCamera();
            mQRCodeView.startSpotAndShowRect();
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        mQRCodeView.startCamera();
        mQRCodeView.startSpotAndShowRect();
    }


    @Override
    public void onPause(){
        super.onPause();

        mQRCodeView.stopSpot();
        mQRCodeView.stopCamera();
    }

    @Override
    public void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
        vibrate();
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "Failed to start camera");
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.light_on:
                mQRCodeView.openFlashlight();
                break;
            case R.id.light_off:
               // mQRCodeView.closeFlashlight();

                mQRCodeView.stopCamera();
                break;
            case R.id.register:
                ((MainActivity) getActivity()).switchFragment(MainActivity.BDLIST_FRAG_TAG);
                break;
            case R.id.cancel:
                ((MainActivity) getActivity()).switchFragment(MainActivity.BDLIST_FRAG_TAG);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mQRCodeView.showScanRect();
    }


}