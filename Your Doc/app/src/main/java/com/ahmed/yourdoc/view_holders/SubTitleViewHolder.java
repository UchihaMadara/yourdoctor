package com.ahmed.yourdoc.view_holders;

import android.view.View;
import android.widget.TextView;

import com.ahmed.yourdoc.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by kuliza-195 on 12/23/16.
 */

public class SubTitleViewHolder extends ChildViewHolder {

    private TextView subTitleTextView;

    public SubTitleViewHolder(View itemView) {
        super(itemView);
        subTitleTextView = (TextView) itemView.findViewById(R.id.subtitle);
    }

    public void setSubTitletName(String sub) {
        subTitleTextView.setText(sub);
    }
}
