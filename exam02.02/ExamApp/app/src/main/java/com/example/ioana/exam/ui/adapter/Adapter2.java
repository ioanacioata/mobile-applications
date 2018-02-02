package com.example.ioana.exam.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ioana.exam.R;
import com.example.ioana.exam.domain.Seat;
import com.example.ioana.exam.ui.TheaterDetailsActivity;

import java.util.List;

/**
 * Created by Ioana on 01/02/2018.
 */

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {
    private static final String TAG = Adapter2.class.getName();

    private List<Seat> list;
    private Context context;

    public Adapter2(List<Seat> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public Adapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item2, parent,
                false);
        return new Adapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Adapter2.ViewHolder holder, int position) {
        final Seat seat = list.get(position);
        holder.nameTextView.setText(seat.getName());
        holder.typeTextView.setText(seat.getType().toString());
        holder.statusTextView.setText(seat.getStatus().toString());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Pressed on " + seat.toString());

                //todo redirect to detail activity and stop this activity
                Intent i = new Intent(context, TheaterDetailsActivity.class);
                i.putExtra(TheaterDetailsActivity.PROJECT, list.get(position));

                ((Activity) holder.view.getContext()).finish();
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView nameTextView;
        final TextView typeTextView;
        final TextView statusTextView;

        Seat currentItem;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            nameTextView = itemView.findViewById(R.id.nameTextView2);
            typeTextView = itemView.findViewById(R.id.typeTextView2);
            statusTextView = itemView.findViewById(R.id.statusTextView2);
        }


        @Override
        public String toString() {
            return super.toString() + " name: " + nameTextView.getText() + " type: " + typeTextView
                    .getText() + " budget:";
        }
    }
}