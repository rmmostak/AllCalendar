package com.rmproduct.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AboutApp extends AppCompatActivity {

    private ExpandableListView listView;
    private List<String> headerList;
    private HashMap<String, List<String>> childList;
    private CustomAdapter customAdapter;
    private int lastExpandablePosition=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        listView = findViewById(R.id.listView);

        prepareData();

        customAdapter = new CustomAdapter(this, headerList, childList);
        listView.setAdapter(customAdapter);

        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandablePosition !=-1 && lastExpandablePosition!=groupPosition) {
                    listView.collapseGroup(lastExpandablePosition);
                }
                lastExpandablePosition=groupPosition;
            }
        });
    }

    private void prepareData() {
        String[] headerData = getResources().getStringArray(R.array.group);
        String[] childData = getResources().getStringArray(R.array.child);

        headerList = new ArrayList<>();
        childList = new HashMap<>();

        for (int i = 0; i < headerData.length; i++) {
            headerList.add(headerData[i]);

            List<String> child = new ArrayList<>();
            child.add(childData[i]);

            childList.put(headerList.get(i), child);
        }
    }
}
