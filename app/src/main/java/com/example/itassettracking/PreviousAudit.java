package com.example.itassettracking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PreviousAudit extends Fragment {
RecyclerView recyclerView ;
List<AuditModel> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_previous_audit, container, false);
        CategoriesList();
        recyclerView=view.findViewById(R.id.RecyclerviewPrevious);
        list=new ArrayList<>();

        return view;
    }

            private void CategoriesList() {
//        String url = "http://164.52.223.163:4501/api/storematerial/distinctlocation";
            StringRequest request = new StringRequest(Request.Method.GET, "http://164.52.223.163:4504/api/GetPreviousAuditLogs", response -> {
                try {

                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String auditId = object.getString("auditId");
                        String auditDate = object.getString("auditDate");
                        String auditor = object.getString("auditor");
                        String remarks = object.getString("remarks");
                        String total = object.getString("total");
                        String found = object.getString("found");
                        String notFound = object.getString("notFound");
                        String Statusval = object.optString("auditStatus");
                        list.add(new AuditModel(auditId,auditDate,auditor,remarks,total,found,notFound,Statusval));
                    }
                    if (list.size()==0)
                    {
                        Toast.makeText(getContext(), "No Previous Data Available...", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        AdapterPreviousAudit adapter_list = new AdapterPreviousAudit(list, getContext());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter_list);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }, error -> {
//                dialog.dismiss();
                Toast.makeText(getContext(), "Not Found...", Toast.LENGTH_SHORT).show();
            });

            //adding the request to volley
            Volley.newRequestQueue(getContext()).add(request);

        }
}