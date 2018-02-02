package com.example.ioana.exam.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ioana.exam.R;
import com.example.ioana.exam.domain.Seat;
import com.example.ioana.exam.ui.AdminListActivity;

import java.util.List;

/**
 * Created by Ioana on 01/02/2018.
 */

public class Adapter3 extends RecyclerView.Adapter<Adapter3.ViewHolder> {
    private static final String TAG = Adapter3.class.getName();

    private List<Seat> list;
    private Context context;

    public Adapter3(List<Seat> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public Adapter3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item3, parent,
                false);
        return new Adapter3.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Adapter3.ViewHolder holder, int position) {
        final Seat seat = list.get(position);
        holder.nameTextView.setText(seat.getName());
        holder.typeTextView.setText(seat.getType().toString());

//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //todo show a dialog and make an api call
//
//                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case DialogInterface.BUTTON_POSITIVE:
//                                //Yes button clicked
//                                Log.i(TAG, "promoting ... ");
//                                AdminListActivity.promote(list.get(position), holder.view.getContext());
//                                break;
//
//                            case DialogInterface.BUTTON_NEGATIVE:
//                                //No button clicked
//                                Log.i(TAG, "canceling ... ");
//                                break;
//                        }
//                    }
//                };
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(((Activity) holder.view.getContext()));
//                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
//                        .setNegativeButton("No", dialogClickListener).show();
//            }
//        });
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

        Seat currentItem;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            nameTextView = itemView.findViewById(R.id.nameTextView3);
            typeTextView = itemView.findViewById(R.id.typeTextView3);
        }


        @Override
        public String toString() {
            return super.toString() + " name: " + nameTextView.getText() + " type: " + typeTextView
                    .getText();
        }
    }
}
