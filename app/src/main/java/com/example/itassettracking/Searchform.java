package com.example.itassettracking;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.speedata.libuhf.IUHFService;
import com.speedata.libuhf.UHFManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Searchform extends AppCompatActivity {
    Button Readingbtn, Retrybtn, AddButton, Newbtn;
    IUHFService iuhfService;
    EditText devicdename;
    String buttonText;
    Handler handler;
    Spinner parameter;
    String[] value = new String[]{"Rfid", "AssetName", "AssetId", "CategoryName"};
    String paravalue;
    RecyclerView recyclerView;
    List<SearchDataModel> list_data_Recyclerview;
    Adapter_list adapter_list;
    ProgressDialog dialog;
    CoordinatorLayout coordinate;
    String result;
    private LooperDemo looperDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchform);
        Readingbtn = findViewById(R.id.Reading_btn);
        parameter = findViewById(R.id.spinner2value);
        Retrybtn = findViewById(R.id.Retry);
        AddButton = findViewById(R.id.Add_Asset);
        Newbtn = findViewById(R.id.New_AssetSearch);
        coordinate = findViewById(R.id.coordinator);
        devicdename = findViewById(R.id.InputId);
        iuhfService = UHFManager.getUHFService(this);
        list_data_Recyclerview = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_Search);
        dialog = new ProgressDialog(this);
        //method for swipe delete
        enableSwipeToDeleteAndUndo();

        final List<String> List = new ArrayList<>(Arrays.asList(value));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, List);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        parameter.setAdapter(spinnerArrayAdapter);

        parameter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                paravalue = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                adapter_list.getFilter(msg.obj);

            }
        };
        looperDemo = new LooperDemo();

        Newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Clear();
            }
        });
        Readingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                buttonText = b.getText().toString();
                if (list_data_Recyclerview.size() > 0) {
                    Scan(buttonText, handler);
                } else {
                    Toast.makeText(Searchform.this, "No Data For Search...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Retrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list_data_Recyclerview.size() > 0) {
                    adapter_list.RetrySearch();
                } else {
                    dialog.setMessage("List Already Clear");
                    dialog.setCancelable(true);
                    dialog.show();
                }
            }
        });
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (devicdename.length() > 0) {
                    FetchData(devicdename.getText().toString());
                    dialog.show();
                    dialog.setMessage(getString(R.string.Dialog_Text));
                    dialog.setCancelable(false);

                } else {
                    devicdename.setError("Enter Input...");
                }
            }
        });
    }

    private void FetchData(String epcvalue) {

//        String url = "http://164.52.223.163:4504/api/SearchAssets?assetName=" + epcvalue;
        String url = "http://164.52.223.163:4504/api/SearchAssets?" + paravalue.concat("=") + epcvalue;


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String auditId = object.optString("auditid");
                        String rfidNo = object.optString("rfidNo");
                        String status = object.optString("status");
                        String assetId = object.optString("assetId");
                        String assetName = object.optString("assetName");
                        String assetTag = object.optString("rfidNo");
                        String serialNo = object.optString("serialNo");
                        String model = object.optString("model");
                        String category = object.optString("category");
                        String assetStatus = object.optString("assetStatus");
                        String company = object.optString("company");
                        String manufacturer = object.optString("manufacturer");
                        String location = object.optString("location");
                        String warranty = object.optString("warranty");
                        String purchaseCost = object.optString("purchaseCost");
                        String supplier = object.optString("supplier");
                        String orderNo = object.optString("orderNo");
                        String purchaseDate = object.optString("purchaseDate");
                        String notes = object.optString("notes");
                        devicdename.setText("");
                        list_data_Recyclerview.add(new SearchDataModel(auditId, rfidNo, status, assetId, assetName, assetTag, serialNo, model, category, assetStatus, company, manufacturer, location, warranty, purchaseCost, orderNo, purchaseDate, notes));
                    }
                    if (list_data_Recyclerview.size() == 0) {
                        dialog.dismiss();
                        devicdename.setText("");
                        Toast.makeText(Searchform.this, "No Upcoming Data Available...", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        adapter_list = new Adapter_list(list_data_Recyclerview, Searchform.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Searchform.this));
                        recyclerView.setAdapter(adapter_list);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    devicdename.setText("");
                    dialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Searchform.this, "" + error, Toast.LENGTH_SHORT).show();
                devicdename.setText("");
                dialog.dismiss();
            }
        });

        // below line is to make
        // a json object request.
        queue.add(request);

    }

    private void Scan(String buttonText, Handler handler) {
        if (buttonText.matches("Start")) {
            iuhfService.openDev();
            iuhfService.selectCard(1, "", false);
            iuhfService.inventoryStart();
            Readingbtn.setText("STOP");
            AddButton.setEnabled(false);
            iuhfService.setOnInventoryListener(var1 -> {


                result = var1.getEpc();
                looperDemo.execute(() -> {
                    Message message = Message.obtain();
                    message.obj = result;
                    handler.sendMessage(message);
                });
            });

        } else {
            AddButton.setEnabled(true);
            iuhfService.inventoryStop();
//            iuhfService.closeDev();

            Readingbtn.setText("Start");
        }

    }

    //Method for Clear Data from Components
    public void Clear() {
        list_data_Recyclerview.clear();
        adapter_list = new Adapter_list(list_data_Recyclerview, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter_list);
//        search_data.setText("");
        devicdename.setText("");
    }

    private void enableSwipeToDeleteAndUndo() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final SearchDataModel item = adapter_list.getData().get(position);
                adapter_list.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(coordinate, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", view -> {

                    adapter_list.restoreItem(item, position);
                    recyclerView.scrollToPosition(position);
                });
//
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_F1://KeyEvent { action=ACTION_UP, keyCode=KEYCODE_F1, scanCode=59, metaState=0, flags=0x8, repeatCount=0, eventTime=13517236, downTime=13516959, deviceId=1, source=0x101 }
                buttonText = Readingbtn.getText().toString();
                if (list_data_Recyclerview.size()>0)
                {
                    Scan(buttonText, handler);
                }else
                {
                    Toast.makeText(Searchform.this, "No Data Found For Scan", Toast.LENGTH_SHORT).show();
                }

                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }
}