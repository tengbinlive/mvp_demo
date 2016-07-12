package com.boqii.plant.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boqii.plant.R;
import com.boqii.plant.base.imp.EInitFragmentDate;
import com.gitonway.lee.niftymodaldialogeffects.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.NiftyDialogBuilder;
import com.utils.ToastUtil;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements EInitFragmentDate {

    protected final String TAG = BaseFragment.class.getSimpleName();

    public NiftyDialogBuilder dialogBuilder;

    public LayoutInflater mInflater;

    public Context mContext;

    public abstract boolean onBackPressed();

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        dialogDismiss();
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getContext();
        mInflater = inflater;
        View rootView = inflater.inflate(getLayoutID(), container, false);
        ButterKnife.bind(this, rootView);
        EInit(savedInstanceState);
        return rootView;
    }

    public abstract int getLayoutID();

    @Override
    public void EInit(Bundle savedInstanceState) {

    }

    @Override
    public void EDestroy() {

    }

    @Override
    public void EResetInit() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EDestroy();
    }

    public void showToast(String msg) {
        if (isVisible()) {
            ToastUtil.toast(getContext(), msg);
        }
    }

    public void showToast(int msgID) {
        if (isVisible()) {
            ToastUtil.toast(getContext(), msgID);
        }
    }

    public void dialogShow() {
        dialogDismiss();
        dialogBuilder = NiftyDialogBuilder.getInstance(getActivity());
        dialogBuilder.withDuration(700) // def
                .isCancelableOnTouchOutside(false) // def | isCancelable(true)
                .withEffect(Effectstype.Fadein) // def Effectstype.Slidetop
                .setCustomView(R.layout.loading_view, getActivity()); // .setCustomView(View
        activityHandler.sendEmptyMessage(DIALOGSHOW);

    }

    public void dialogShow(int title) {
        dialogShow(mContext.getString(title));
    }

    public void dialogShow(String title) {
        dialogDismiss();
        LinearLayout convertView = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.loading_view, null);
        TextView dialog_confirm_content = (TextView) convertView.findViewById(R.id.dialog_confirm_content);
        dialog_confirm_content.setText(title);
        dialogBuilder = NiftyDialogBuilder.getInstance(getActivity());
        dialogBuilder.withDuration(700) // def
                .isCancelableOnTouchOutside(false) // def | isCancelable(true)
                .withEffect(Effectstype.Fadein) // def Effectstype.Slidetop
                .setCustomView(convertView, getActivity()); // .setCustomView(View
        activityHandler.sendEmptyMessage(DIALOGSHOW);

    }

    public void dialogDismiss() {
        if (null != dialogBuilder && dialogBuilder.isShowing()) {
            activityHandler.sendEmptyMessage(DIALOGDISMISS);
        }
    }

    private final static int DIALOGSHOW = 1;
    private final static int DIALOGDISMISS = 0;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DIALOGSHOW:
                    dialogBuilder.show();
                    break;
                case DIALOGDISMISS:
                    dialogBuilder.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

}
