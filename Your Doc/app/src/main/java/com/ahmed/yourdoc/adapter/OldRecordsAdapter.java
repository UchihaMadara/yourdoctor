package com.ahmed.yourdoc.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmed.yourdoc.R;

/**
 * Created by Uchihan on 30/09/17.
 */

public class OldRecordsAdapter extends RecyclerView.Adapter<OldRecordsAdapter.ViewHolder> {
    Context context;
    Typeface face;
    public OldRecordsAdapter(Context context) {
        this.context = context;
        face = Typeface.createFromAsset(context.getAssets(), "font_ar.ttf");

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.old_records_custom_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.description.setTypeface(face);
       // holder.type.setTypeface(face);

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView description, type, identifier;

        public ViewHolder(View view) {
            super(view);
            description = (TextView) view.findViewById(R.id.description);
            type = (TextView) view.findViewById(R.id.category);
            identifier = (TextView) view.findViewById(R.id.identifier);
            description.setTypeface(face);
            //type.setTypeface(face);

        }
    }
}