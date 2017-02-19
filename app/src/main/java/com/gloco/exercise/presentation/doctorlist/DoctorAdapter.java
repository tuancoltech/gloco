package com.gloco.exercise.presentation.doctorlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gloco.exercise.R;
import com.gloco.exercise.domain.model.getdoctorlist.DoctorModel;

import java.util.List;

/**
 * Created by "Tuan Nguyen" on 11/14/2016.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter
        .ViewHolder> {

    private MyClickListener mClickListener;

    private List<DoctorModel> mDoctorData;

    public DoctorAdapter(List<DoctorModel> doctorData) {
        mDoctorData = doctorData;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ViewHolder currentHolder = holder;

        currentHolder.mTextName.setText(mDoctorData.get(position).getName());
        currentHolder.mTextAge.setText(String.valueOf(mDoctorData.get(position).getAge()));

        currentHolder.mTextName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(currentHolder.getAdapterPosition(), v);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDoctorData != null)
            return mDoctorData.size();
        else
            return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_doctor, parent, false);

        return new ViewHolder(view);
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.mClickListener = myClickListener;
    }

    public List<DoctorModel> getData() {
        return this.mDoctorData;
    }

    public interface MyClickListener {

        void onItemClick(int position, View v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextName;

        private final TextView mTextAge;


        public ViewHolder(View itemView) {
            super(itemView);

            mTextName = (TextView) itemView.findViewById(R.id.text_name);
            mTextAge = (TextView) itemView.findViewById(R.id.text_age);
        }

    }
}
