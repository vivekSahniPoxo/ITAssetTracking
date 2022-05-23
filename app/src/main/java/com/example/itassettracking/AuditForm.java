package com.example.itassettracking;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AuditForm extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

//    RecyclerView recyclerView;
//    List<AuditModel> list;
//    AuditAdapter adapter;
//    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_form);
//        recyclerView = findViewById(R.id.recyclerviewAudit);
//        dialog = new ProgressDialog(this);
//        list = new ArrayList<>();
//        CategoriesList();
//        dialog.show();
//        dialog.setMessage("Please Wait...");
//        dialog.setCancelable(false);


        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Previous Audit"));
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming Audit"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
//        private void CategoriesList() {
////        String url = "http://164.52.223.163:4501/api/storematerial/distinctlocation";
//            StringRequest request = new StringRequest(Request.Method.GET, "http://164.52.223.163:4504/api/GetAuditLogs", response -> {
//                try {
//                    JSONArray array = new JSONArray(response);
//                    for (int i = 0; i < array.length(); i++) {
//                        JSONObject object = array.getJSONObject(i);
//                        String auditId = object.getString("auditId");
//                        String auditDate = object.getString("auditDate");
//                        String auditor = object.getString("auditor");
//                        String remarks = object.getString("remarks");
//                        String total = object.getString("total");
//                        String found = object.getString("found");
//
//                        String notFound = object.getString("notFound");
//                        list.add(new AuditModel(auditId,auditDate,auditor,remarks,total,found,notFound));
//                    }
//                    dialog.dismiss();
//                    adapter = new AuditAdapter(list, this);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
//                    recyclerView.setAdapter(adapter);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }, error -> {
//                dialog.dismiss();
//                Toast.makeText(this, "Not Found...", Toast.LENGTH_SHORT).show();
//            });
//
//            //adding the request to volley
//            Volley.newRequestQueue(this).add(request);
//
//        }




    }


