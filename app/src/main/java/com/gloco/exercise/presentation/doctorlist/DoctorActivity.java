package com.gloco.exercise.presentation.doctorlist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gloco.exercise.Const;
import com.gloco.exercise.R;
import com.gloco.exercise.domain.model.getdoctorlist.DoctorModel;
import com.gloco.exercise.presentation.common.BaseActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuan Nguyen on 2/15/2017.
 */

public class DoctorActivity extends BaseActivity {

    private List<DoctorModel> mDoctorData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            mDoctorData = extras.getParcelableArrayList(Const.BUNDLE_EXTRAS_DOCTORS);
        }

        initViews();
    }

    private void initViews() {

        RecyclerView listDoctor = (RecyclerView) findViewById(R.id.list_doctor);
        listDoctor.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listDoctor.setLayoutManager(layoutManager);


        listDoctor.addItemDecoration(new HorizontalDividerItemDecoration.Builder
                (this)
                .margin(0, 0)
                .color(Color.WHITE)
                .build());

        if (mDoctorData == null) {
            mDoctorData = new ArrayList<>();
        }

        DoctorAdapter doctorAdapter = new DoctorAdapter(mDoctorData);
        doctorAdapter.setOnItemClickListener(new DoctorAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
            }
        });

        listDoctor.setAdapter(doctorAdapter);


    }
}
