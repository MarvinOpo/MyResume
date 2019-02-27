package com.mvopo.myresume.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvopo.myresume.Helper.MyProjectAdapter;
import com.mvopo.myresume.Model.Project;
import com.mvopo.myresume.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mvopo on 7/18/2018.
 */

public class ProjectsFragment extends Fragment{

    private LinearLayout viewContainer;
    private ArrayList<Project> projects = new ArrayList<>();

    private int[] guia = new int[]{
            R.drawable.guia1,
            R.drawable.guia2,
            R.drawable.guia3,
            R.drawable.guia4,
            R.drawable.guia5
    };

    private int[] pha_check_app = new int[]{
            R.drawable.checkapp1,
            R.drawable.checkapp2,
            R.drawable.checkapp3,
            R.drawable.checkapp4,
            R.drawable.checkapp5
    };

    private int[] ref = new int[]{
            R.drawable.ref1,
            R.drawable.ref2
    };

    private int[] fsms = new int[]{
            R.drawable.fsms1,
            R.drawable.fsms2,
            R.drawable.fsms3,
            R.drawable.fsms4
    };

    private int[] memoir = new int[]{
            R.drawable.memoir1,
            R.drawable.memoir2,
            R.drawable.memoir3,
            R.drawable.memoir4,
            R.drawable.memoir5
    };

    private int[] fcard = new int[]{
            R.drawable.fcard1,
            R.drawable.fcard2,
            R.drawable.fcard3,
            R.drawable.fcard4
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        viewContainer = view.findViewById(R.id.projects_view_container);

        populateProjects();
        initView();

        return view;
    }

    public void populateProjects(){
        projects.clear();
        projects.add(new Project("PHA Check-App", getResources().getString(R.string.checkapp_info_text), pha_check_app));
        projects.add(new Project("Memoir", getResources().getString(R.string.memoir_info_text), memoir));
        projects.add(new Project("FlashCard", getResources().getString(R.string.fcard_info_text), fcard));
        projects.add(new Project("Guia", getResources().getString(R.string.guia_info_text), guia));
        projects.add(new Project("Referral App", getResources().getString(R.string.referral_info_text), ref));
        projects.add(new Project("Floating SMS", getResources().getString(R.string.fsms_info_text), fsms));
    }

    public void initView(){
        for(int i = 0; i < projects.size(); i++){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.project_item_layout, null);

            TextView title = view.findViewById(R.id.project_title);
            TextView body = view.findViewById(R.id.project_body);
            final ViewPager pager = view.findViewById(R.id.project_pager);

            final Project project = projects.get(i);
            title.setText(project.getTitle());
            body.setText(project.getBody());

            pager.setAdapter(new MyProjectAdapter(getContext(), project.getImages()));

            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (project.getCurrentPage() == project.getImageCount()) {
                        project.setCurrentPage(0);
                    }
                    pager.setCurrentItem(project.getCurrentPage(), true);
                    project.incrementPage();
                }
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 3000, 3000);

            viewContainer.addView(view);
        }
    }
}
