package nxp.dcca.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nxp.dcca.MainActivity;
import nxp.dcca.R;
import nxp.dcca.draggridview.DragAdapter;

public class BoardsListFragment extends Fragment implements AdapterView.OnItemClickListener,
        PopupWindow.OnDismissListener {
    private List<HashMap<String, Object>> dataSourceList = new ArrayList<HashMap<String, Object>>();

    private static final int VISIBIY_NUMS = 9;
    private DragAdapter mDragAdapter;
    private GridView mDragGridView;
    private View mFragView;
    private PopupWindow mPopupWindow;
    private ImageView mImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragView =inflater.inflate(R.layout.frag_bdlist, container, false);

        mDragGridView = (GridView)mFragView.findViewById(R.id.bdlist_grid);
        mDragGridView.setOnItemClickListener(this);
        mDragGridView.setNumColumns(4);

        View popupView = getActivity().getLayoutInflater().inflate(R.layout.popup_detail, null);
        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.setOnDismissListener(this);

        HashMap<String, Object> itemHashMap;
        for (int i = 0; i < VISIBIY_NUMS; i++) {
            itemHashMap = new HashMap<String, Object>();
            itemHashMap.put("item_image",R.drawable.nxp_layerscap);
            itemHashMap.put("item_text", "LS1043A_" + Integer.toString(i));
            itemHashMap.put("boardid", "LS1043A_board" + Integer.toString(i));
            dataSourceList.add(itemHashMap);
        }
        itemHashMap = new HashMap<String, Object>();
        itemHashMap.put("item_image",R.drawable.add);
        itemHashMap.put("item_text", "Register");
        dataSourceList.add(itemHashMap);

        mDragAdapter = new DragAdapter(getActivity().getApplicationContext(), dataSourceList);

        mDragGridView.setAdapter(mDragAdapter);


        return mFragView;
    }


    @Override
    public void onResume()
    {

        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        HashMap<String, Object>  item = (HashMap<String, Object>)mDragAdapter.getItem(position);
        String boardid = (String)item.get("boardid");

        if (boardid == null) {
            ((MainActivity) getActivity()).switchFragment(MainActivity.QRCODE_FRAG_TAG);
        } else {
            mImageView  = (ImageView) view.findViewById(R.id.item_image);
            mImageView.setColorFilter(R.color.colorPress);
            backgroundAlpha(0.5f);
            setDetailInfor(boardid);
            mPopupWindow.showAtLocation(mFragView, Gravity.CENTER, 0, 0);
        }
    }

    private void setDetailInfor(String boardid){
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        // TODO Auto-generated method stub
        backgroundAlpha(1f);
        if (mImageView != null) {
            mImageView.setColorFilter(null);
            mImageView = null;
        }
    }

}