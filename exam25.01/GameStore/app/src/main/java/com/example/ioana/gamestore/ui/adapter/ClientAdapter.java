package com.example.ioana.gamestore.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ioana.gamestore.R;
import com.example.ioana.gamestore.domain.Game;

import java.util.ArrayList;
import java.util.List;


public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {
    private static final String TAG = ClientAdapter.class.getName();
    private List<Game> mValues;

    public ClientAdapter() {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_client, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
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
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView nameTextView;
        final TextView quantityTextView;
        final TextView typeTextView;

        Game currentItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            nameTextView = itemView.findViewById(R.id.nameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
        }


        @Override
        public String toString() {
            return super.toString() + " name: " + nameTextView.getText() + " type: " + typeTextView
                    .getText() + " quatity:" + quantityTextView.getText();
        }
    }
}
