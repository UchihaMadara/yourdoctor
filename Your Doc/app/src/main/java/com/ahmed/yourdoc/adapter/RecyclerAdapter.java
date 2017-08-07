package com.ahmed.yourdoc.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.yourdoc.Constant;
import com.ahmed.yourdoc.R;
import com.ahmed.yourdoc.models.SubTitle;
import com.ahmed.yourdoc.models.TitleMenu;
import com.ahmed.yourdoc.view_holders.SubTitleViewHolder;
import com.ahmed.yourdoc.view_holders.TitleViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 12/23/16.
 */

public class RecyclerAdapter extends ExpandableRecyclerViewAdapter<TitleViewHolder, SubTitleViewHolder> {
    public static String [] name = {"تصنيفات", "تصنيفات كتابيه"};
    ArrayList<String[]>sub= Constant.getSub();
    private Context context;
    private ItemClickChild mListener;
    public RecyclerAdapter(Context context, List<? extends ExpandableGroup> groups, Activity activity) {
        super(groups);
        this.context = context;

        mListener = (ItemClickChild) activity;
    }

    @Override
    public TitleViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_title, parent, false);
        return new TitleViewHolder(view);
    }

    @Override
    public SubTitleViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subtitle, parent, false);
        return new SubTitleViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(SubTitleViewHolder holder, int flatPosition,
                                      ExpandableGroup group, final int childIndex) {

        final SubTitle subTitle = ((TitleMenu) group).getItems().get(childIndex);

        holder.setSubTitletName(subTitle.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onChildClick(childIndex);
            }
        });
    }

    @Override
    public void onBindGroupViewHolder(TitleViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setTiltle(context,name);
    }

    public interface ItemClickChild{
        void onChildClick(int position);
    }
}
