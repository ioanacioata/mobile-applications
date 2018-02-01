package com.example.ioana.gamestore.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ioana.gamestore.R;
import com.example.ioana.gamestore.domain.Game;
import com.example.ioana.gamestore.ui.EditGameActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ioana on 01/02/2018.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder2> {
    private static final String TAG = ClientAdapter.class.getName();
    private List<Game> mValues;
    private Context context;

    public EmployeeAdapter(Context context) {
        this.context = context;
        mValues = new ArrayList<>();
    }

    public void setData(List<Game> mValues) {
        this.mValues = mValues;
        notifyDataSetChanged();
    }

    public void clear() {
        mValues.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_employee, parent, false);
        return new ViewHolder2(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder2 holder, final int position) {
        //set values for each item
        holder.currentItem = mValues.get(position);
        holder.nameTextView.setText(holder.currentItem.getName());
        holder.quantityTextView.setText(String.valueOf(holder.currentItem.getQuantity()));
        holder.typeTextView.setText(holder.currentItem.getType().toString());

        //todo on clicking on a item redirect to an activity that shows details
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Pressed on item " + holder.currentItem.toString());

                Intent intent = new Intent(context, EditGameActivity.class);
                intent.putExtra("action", "edit");
                intent.putExtra("game", mValues.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        final View mView;
        final TextView nameTextView;
        final TextView quantityTextView;
        final TextView typeTextView;
        final TextView statusTextView;

        Game currentItem;

        public ViewHolder2(View itemView) {
            super(itemView);
            mView = itemView;
            nameTextView = itemView.findViewById(R.id.nameTextView2);
            quantityTextView = itemView.findViewById(R.id.quantityTextView2);
            typeTextView = itemView.findViewById(R.id.typeTextView2);
            statusTextView = itemView.findViewById(R.id.statusTextView2);
        }


        @Override
        public String toString() {
            return super.toString() + " name: " + nameTextView.getText() + " type: " + typeTextView
                    .getText() + " quatity:" + quantityTextView.getText();
        }
    }
}
