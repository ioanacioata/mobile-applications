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
import com.example.ioana.exam.domain.Project;
import com.example.ioana.exam.ui.IdeaDetailsActivity;

import java.util.List;


public class Adapter1 extends RecyclerView.Adapter<Adapter1.ViewHolder> {
    private static final String TAG = Adapter1.class.getName();

    private List<Project> list;
    private Context context;

    public Adapter1(List<Project> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item1, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Project project = list.get(position);
        holder.nameTextView.setText(project.getName());
        holder.budgetTextView.setText(String.valueOf(project.getBudget()));
        holder.typeTextView.setText(project.getType().toString());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Pressed on " + project.toString());

                Intent i = new Intent(context, IdeaDetailsActivity.class);
                i.putExtra(IdeaDetailsActivity.PROJECT, project);
                i.putExtra(IdeaDetailsActivity.ACTION, IdeaDetailsActivity.DELETE_IDEA);

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
        final TextView budgetTextView;
        final TextView typeTextView;

        Project currentItem;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            nameTextView = itemView.findViewById(R.id.nameTextView);
            budgetTextView = itemView.findViewById(R.id.budgetTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
        }


        @Override
        public String toString() {
            return super.toString() + " name: " + nameTextView.getText() + " type: " + typeTextView
                    .getText() + " quatity:" + budgetTextView.getText();
        }
    }
}
