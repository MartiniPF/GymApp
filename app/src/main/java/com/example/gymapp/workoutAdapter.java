package com.example.gymapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class workoutAdapter extends RecyclerView.Adapter<workoutAdapter.classesViewHolder> {
    private ArrayList<exerciseData> mWorkoutList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class classesViewHolder extends RecyclerView.ViewHolder{

        public TextView textEx;
        public TextView textWeight;
        public TextView textReps;


        public classesViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            textEx = itemView.findViewById(R.id.text1);
            textWeight = itemView.findViewById(R.id.text2);
            textReps = itemView.findViewById(R.id.text3);


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

    public workoutAdapter(ArrayList<exerciseData> workoutList){
        mWorkoutList = workoutList;
    }

    @NonNull
    @Override
    public classesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_exercise_item, parent, false);
        classesViewHolder cvh = new classesViewHolder(v, mListener);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull classesViewHolder holder, int position) {
        exerciseData currentItem = mWorkoutList.get(position);

            System.out.println("CURRENT ITEM: " + currentItem.getWeight());

            holder.textEx.setText(currentItem.getExname());
            holder.textWeight.setText(String.valueOf(currentItem.getWeight()) + "KG");
            holder.textReps.setText(String.valueOf(currentItem.getReps()) + " reps");

       // System.out.println("Check this out: " + currentItem.getName());
    }

    @Override
    public int getItemCount() {
        return mWorkoutList.size();
    }
}
