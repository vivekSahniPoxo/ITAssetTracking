package com.example.itassettracking;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.speedata.libuhf.IUHFService;
import com.speedata.libuhf.UHFManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IdentifyForm extends AppCompatActivity {
    Button Search, NEW_data, Retry;
    //    EditText search_key;
    TextView LibraryItemType, BookAddedIn, BookCategory, ItemStatus, SubjectTitle, Language, Edition, Publisher, RFIDNo, AccessNo, Author, Title, YearOfPublication, EntryDate;
    ProgressDialog dialog;
    CardView scan_button;
    IUHFService iuhfService;
    String epc,result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identifyy_form);
        scan_button = findViewById(R.id.button_Scan);
//        search_key = findViewById(R.id.Search_key);
        Retry = findViewById(R.id.Retry);
        Search = findViewById(R.id.Search_rfid_button);
        LibraryItemType = findViewById(R.id.Library_item);
        NEW_data = findViewById(R.id.New_accession);
        BookAddedIn = findViewById(R.id.Book_Add);
        BookCategory = findViewById(R.id.BookCategory);
        ItemStatus = findViewById(R.id.Item_status);
        SubjectTitle = findViewById(R.id.Subject_t);
        Language = findViewById(R.id.Language);
        Edition = findViewById(R.id.Edition);
        Publisher = findViewById(R.id.Publisher);
        RFIDNo = findViewById(R.id.RFID_NO);
        AccessNo = findViewById(R.id.Access_No);
        Author = findViewById(R.id.Authorname);
        Title = findViewById(R.id.Booktitle);
        YearOfPublication = findViewById(R.id.YearOfPublication);
        EntryDate = findViewById(R.id.EntryDate);
        iuhfService = UHFManager.getUHFService(this);
        iuhfService.setOnInventoryListener(var1 -> {
//                    tempList.add(var1.getEpc());
//                    System.out.println("List Data" + tempList);
            epc = var1.getEpc();
//            if (epc != null) {
////                try {
//////                    Toast.makeText(Identify_Form.this, epc, Toast.LENGTH_SHORT).show();
////                    //Toast.makeText(Identify_Form.this, "Start Fetching Data...", Toast.LENGTH_SHORT).show();
//////                    FetchData(epc);
////                    //Toast.makeText(Identify_Form.this, "Method Called", Toast.LENGTH_SHORT).show();
//////                    dialog.show();
//////                    dialog.setMessage(getString(R.string.Dialog_Text));
//////                    dialog.setCancelable(false);
////                    iuhfService.inventoryStop();
////                    //Toast.makeText(Identify_Form.this, "Inventory Stopped", Toast.LENGTH_SHORT).show();
////                    iuhfService.closeDev();
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
//            }
            //Toast.makeText(Identify_Form.this, epc, Toast.LENGTH_SHORT).show();
        });
        //Initialize of Dialog Box
        dialog = new ProgressDialog(this);

        //Listeners
        scan_button.setOnClickListener(v -> Scan());

        Retry.setOnClickListener(v -> {
            try {
//                    iuhfService.inventoryStop();
//                    iuhfService.closeDev();
//                    Toast.makeText(Identify_Form.this, "Start Fetching Data...", Toast.LENGTH_SHORT).show();
                if (result != null) {
                    FetchData(result);
                    dialog.show();
                    dialog.setMessage("Fetching...");
                    dialog.setCancelable(false);
                } else {

                    dialog.setMessage("List Already Clear");
                    dialog.setCancelable(true);
                    dialog.show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        NEW_data.setOnClickListener(v -> new AlertDialog.Builder(IdentifyForm.this)
                .setIcon(R.drawable.ic_baseline_cleaning_services_24)
                .setTitle("Quit")
                .setMessage("Are you sure you want to Clear the Data ?")
                .setPositiveButton("Yes", (dialog, which) -> ClearData())
                .setNegativeButton("No", null)
                .show());

    }
    //Method For Clear Components
    private void ClearData() {
        LibraryItemType.setText("--");
        BookAddedIn.setText("--");
        BookCategory.setText("--");
        ItemStatus.setText("--");
        SubjectTitle.setText("--");
        Language.setText("--");
        Edition.setText("--");
        Publisher.setText("--");
        RFIDNo.setText("--");
        AccessNo.setText("--");
        Author.setText("--");
        Title.setText("--");
        YearOfPublication.setText("--");
        EntryDate.setText("--");


    }
    private void FetchData(String epcvalue) throws JSONException {
    RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://164.52.223.163:4504/api/SearchAssets?Rfid=" + epcvalue;
        StringRequest sr = new StringRequest(Request.Method.GET, url, response -> {
            try {
                 JSONArray array = new JSONArray(response);
                 JSONObject object = array.getJSONObject(0);
                String LibraryItemType1 = object.optString("orderNo");
                String BookAddedIn1 = object.optString("supplier");
                String BookCategory1 = object.optString("location");
                String ItemStatus1 = object.optString("manufacturer");
                String SubjectTitle1 = object.optString("company");
                String Language1 = object.optString("assetStatus");
                String Edition1 = object.optString("category");
                String Publisher1 = object.optString("model");
                String RFIDNo1 = object.optString("rfidNo");
                String AccessNo1 = object.optString("assetTag");
                String Author1 = object.optString("assetName");
                String Title1 = object.optString("assetId");
                String YearOfPublication1 = object.optString("purchaseDate");
                String EntryDate1 = object.optString("serialNo");
                LibraryItemType.setText(Language1);
                BookAddedIn.setText(BookCategory1);
                BookCategory.setText(Edition1);
                ItemStatus.setText(SubjectTitle1);
                SubjectTitle.setText(BookAddedIn1);
                Language.setText(RFIDNo1);
                Edition.setText(LibraryItemType1);
                Publisher.setText(LibraryItemType1);
                RFIDNo.setText(RFIDNo1);
                AccessNo.setText(Publisher1);
                Author.setText(ItemStatus1);
                Title.setText(Author1);
                YearOfPublication.setText(YearOfPublication1);
                EntryDate.setText(EntryDate1);
                dialog.dismiss();

                Log.e("response", response.toString());
            } catch (Exception e) {
                Toast.makeText(IdentifyForm.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                dialog.dismiss();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IdentifyForm.this, "No Data Found...", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("Rfid", epcvalue);


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }
    private  void Scan()
    {
        iuhfService.openDev();
        result = iuhfService.read_area(1, "2", "6", "00000000");

        if (result != null) {
            try {
//                    Toast.makeText(Identify_Form.this, epc, Toast.LENGTH_SHORT).show();
                //Toast.makeText(Identify_Form.this, "Start Fetching Data...", Toast.LENGTH_SHORT).show();
                FetchData(result);
                dialog.show();
                dialog.setMessage("Fetching...");
                dialog.setCancelable(false);
                iuhfService.inventoryStop();
                iuhfService.closeDev();
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_F1://KeyEvent { action=ACTION_UP, keyCode=KEYCODE_F1, scanCode=59, metaState=0, flags=0x8, repeatCount=0, eventTime=13517236, downTime=13516959, deviceId=1, source=0x101 }
                Scan();
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

}