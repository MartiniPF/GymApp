package com.example.gymapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class classesAdapter extends RecyclerView.Adapter<classesAdapter.classesViewHolder> {
    private ArrayList<classData> mClassList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class classesViewHolder extends RecyclerView.ViewHolder{

        public TextView text1;
        public TextView text2;
        public TextView text3;


        public classesViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            text3 = itemView.findViewById(R.id.text3);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int positiion = getAdapterPosition();
                        if(positiion != RecyclerView.NO_POSITION){
                            listener.onItemClick(positiion);
                        }
                    }
                }
            });
        }
    }

    public classesAdapter(ArrayList<classData> classesList){
        mClassList = classesList;
    }

    @NonNull
    @Override
    public classesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_class_item, parent, false);
        classesViewHolder cvh = new classesViewHolder(v, mListener);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull classesViewHolder holder, int position) {
        classData currentItem = mClassList.get(position);

        holder.text1.setText(currentItem.getName());
        holder.text2.setText(currentItem.getDate());
        holder.text3.setText(currentItem.getTime());

        System.out.println("Check this out: " + currentItem.getName());
    }

    @Override
    public int getItemCount() {
        return mClassList.size();
    }
}
