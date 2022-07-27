package com.example.itassettracking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.speedata.libuhf.IUHFService;
import com.speedata.libuhf.UHFManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AuditDetrails extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DetailModel> list;
    DetailsAdapter adapter;
    List<String> TempList_Inventory;
    TextView total, found, not_found;
    int counter = 0, len, not_founded;
    int page = 1, limit = 0;
    //    CoordinatorLayout coordinatorLayout;
    List<SearchDataModel> listdb;
    String keyid;
    ProgressDialog dialog;
    IUHFService iuhfService;
    String buttonText;
    File filepath;
    LocalDB localDB;

    Handler handler;
    Button Search, Submit, NewBtn, Back_Btn;

    private LooperDemo looperDemo;
    String result;
//    RecyclerFastScroller fastScroller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_detrails);
        recyclerView = findViewById(R.id.recyclerView);
        total = findViewById(R.id.Total);
        found = findViewById(R.id.Found);
        not_found = findViewById(R.id.not_found);
        NewBtn = findViewById(R.id.New_Button);
        Search = findViewById(R.id.Search_);
//        coordinatorLayout = findViewById(R.id.coordinator);
        Back_Btn = findViewById(R.id.Back_Button);
        Submit = findViewById(R.id.Submit_Button);
        TempList_Inventory = new ArrayList<>();
        list = new ArrayList<>();
        listdb = new ArrayList<>();
        localDB = new LocalDB(this);
//        fastScroller = findViewById(R.id.fasttrcv);
//        fastScroller.attachRecyclerView(recyclerView);
        //Method for Left Swipe to Delete
//        enableSwipeToDeleteAndUndo();
        dialog = new ProgressDialog(this);

        Back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (filepath.exists()) {
//                    UpdateExcel();
//                } else {
//                    createExcelSheet();
//                }
//                startActivity(new Intent(AuditDetrails.this, AuditForm.class));
//                finish();
//                createExcelSheet();
            }
        });
        NewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clear();
            }
        });
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
//                adapter_list.getFilter(msg.obj);
                counter = adapter.getFilter(msg.obj);
//                counter = counter+fnd;
//                iuhfService.inventoryStop();
//                iuhfService.closeDev();
                not_founded = len - counter;
                total.setText(String.valueOf(len));
                found.setText(String.valueOf(counter));
                not_found.setText(String.valueOf(not_founded));


            }
        };
        looperDemo = new LooperDemo();
        iuhfService = UHFManager.getUHFService(this);
        iuhfService.setAntennaPower(30);

        Search.setOnClickListener(v -> {
            Button b = (Button) v;
            if (list.size() > 0) {

                new Thread(new Runnable() {
                    public void run() {
                        buttonText = b.getText().toString();
                        Scan(buttonText, handler);
                    }
                }).start();

                Submit.setEnabled(true);
            } else {
                Toast.makeText(AuditDetrails.this, "No Data Found For Scan", Toast.LENGTH_SHORT).show();
            }

//            String result = iuhfService.read_area(1, "2", "6", "00000000");

        });

        keyid = getIntent().getStringExtra("keyId");
        limit = Integer.parseInt(getIntent().getStringExtra("length"));
        FetchData(keyid, page, limit);
        dialog.setMessage("Fetching Details...");
        dialog.setCancelable(false);
        dialog.show();

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
//                int totalItemCount = layoutManager.getItemCount();
//                int lastVisible = layoutManager.findLastVisibleItemPosition();
//
//                boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
//                if (totalItemCount > 0 && endHasBeenReached) {
//                    //you have reached to the bottom of your recycler view
//                    page++;
//                    FetchData(keyid, page, limit);
//                    dialog.show();
//
//                }
//            }
//        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                new androidx.appcompat.app.AlertDialog.Builder(getApplication())
//                        .setIcon(R.drawable.exitapp)
//                        .setTitle("Generate Excel")
//                        .setMessage("Do ypu want to generate excel file ?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                        {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                              callmethod() ;
//                              createExcelSheet();
//                            }
//
//
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                callmethod();
//                            }
//                        })
//                        .show();

                try {
                    if (list.size() > 0) {

                        submit_Report();
                        dialog.show();
                        dialog.setMessage(getString(R.string.Dialog_Text));
                        dialog.setCancelable(false);

                    } else {
                        Toast.makeText(AuditDetrails.this, "NO Data in list", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void FetchData(String epcvalue, int page, int limit) {

//        String url = "http://164.52.223.163:4504/api/GetauditDetails?AuditId=" + epcvalue;

        if (page > limit) {
            // checking if the page number is greater than limit.
            // displaying toast message in this case when page>limit.
            Toast.makeText(this, "That's all the data..", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            // hiding our progress bar.
            return;
        } else {
            String url = "http://164.52.223.163:4504/api/GetAuditDetailsAndroid?AuditId=" + epcvalue + "&Pagenumber=" + page + "&Count=" + limit;

            // creating a new variable for our request queue
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
//                    JSONArray array = new JSONArray(response);
//

                        JSONObject object1 = new JSONObject(response);
                        JSONArray array = object1.getJSONArray("assetsLists");
                        len = len + array.length();
                        total.setText(String.valueOf(len));
                        not_found.setText("0");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
//                        String auditid = object.optString("auditid");
//                        String rfidNo = object.optString("rfidNo");
//                        String status = object.optString("status");
//                        String assetId = String.valueOf(object.optString("assetId"));
//                        String assetName = object.optString("assetName");
//                        String assetTag = object.optString("assetTag");
//                        String serialNo = object.optString("serialNo");
//                        String model = object.optString("model");
//                        String category = object.optString("category");
//                        String assetStatus = object.optString("assetStatus");
//                        String company = object.optString("company");
//                        String manufacturer = object.optString("manufacturer");
//                        String location = object.optString("location");
//                        String purchaseCost = object.optString("purchaseCost");
//                        String supplier = object.optString("supplier");
//                        String orderNo = object.optString("orderNo");
//                        String purchaseDate = object.optString("purchaseDate");
//                        String notes = object.optString("notes");
                            String rfidNo = object.optString("rfidNo");
                            String assetName = object.optString("assetName");
                            String model = object.optString("model");
                            String category = object.optString("category");
                            String location = object.optString("location");

//                        localDB.addContact(new SearchDataModel(auditid, rfidNo, status, assetId, assetName, assetTag, serialNo, model, category, assetStatus, company, manufacturer, location, purchaseCost, supplier, orderNo, purchaseDate, notes));
                            Back_Btn.setEnabled(true);
//                        Back_Btn.setBackgroundColor(Integer.parseInt("#FF00AA"));
                            TempList_Inventory.add(rfidNo);

//                        setuplist(TempList_Inventory);
                            list.add(new DetailModel(rfidNo, assetName, model, category, location));
                            adapter = new DetailsAdapter(TempList_Inventory, list, getApplicationContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(adapter);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        Toast.makeText(AuditDetrails.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    dialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AuditDetrails.this, "" + error, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            // below line is to make
            // a json object request.
            queue.add(request.setRetryPolicy(new DefaultRetryPolicy(500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        }
    }

//    private void setuplist(List<String> tempList_inventory) {
//        list = localDB.getAllContacts();
//        adapter = new DetailsAdapter(tempList_inventory, list, getApplicationContext());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(adapter);
//        dialog.dismiss();
//    }

    private void Scan(String buttonText, Handler handler) {
        new Thread(new Runnable() {
            public void run() {
                if (buttonText.matches("Start")) {
                    iuhfService.openDev();
                    iuhfService.selectCard(1, "", false);
                    iuhfService.inventoryStart();
                    Search.setText("STOP");
                    iuhfService.setOnInventoryListener(var1 -> {

                        result = var1.getEpc();
                        looperDemo.execute(() -> {
                            Message message = Message.obtain();
                            message.obj = result;
                            handler.sendMessage(message);
                        });

                    });

                } else {
                    iuhfService.inventoryStop();
                    iuhfService.closeDev();
//                adapter_list.RetrySearch();
                    Search.setText("Start");
                }
            }
        }).start();


    }


    private void submit_Report() throws JSONException {

        JSONObject object = new JSONObject();
        object.put("auditId", keyid);
        object.put("total", String.valueOf(len));
        object.put("found", String.valueOf(counter));
        object.put("notFound", String.valueOf(not_founded));

        //JSONArray array = new JSONArray();
        JSONArray array = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStatusF() == "True") {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rfidNo", list.get(i).getRfidNo());
                jsonObject.put("status", Boolean.valueOf(list.get(i).getStatusF()));
                array.put(jsonObject);
            }
        }

        object.put("auditinfos", array);
//
        RequestQueue queue = Volley.newRequestQueue(this);
        System.out.println("JSON DATA " + object);

        final String requestBody = object.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://164.52.223.163:4504/api/UpdateAudits", response -> {
            Toast.makeText(AuditDetrails.this, response, Toast.LENGTH_SHORT).show();
            Clear();
            Log.i("VOLLEY Submit", response);
            dialog.dismiss();
        }, error -> {
            try {
                Clear();
                Log.e("VOLLEY Negative", String.valueOf(error.networkResponse.statusCode));
                Log.e("VOLLEY Negative", String.valueOf(error.getMessage()));
                if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(AuditDetrails.this, "No Result Found", Toast.LENGTH_SHORT).show();
                } else if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(AuditDetrails.this, "Bad Request", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AuditDetrails.this, "Unable to process the request", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                Log.e("VOLLEY Negative", String.valueOf(error.getMessage()));

                Toast.makeText(this, "Update...", Toast.LENGTH_SHORT).show();

            }
            dialog.dismiss();
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                System.out.println("Response Code " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }

    //Method for Clear Data from components
    public void Clear() {
        if (list.size() > 0) {
//            Submit.setEnabled(true);
            NewBtn.setEnabled(true);
            list.clear();
            adapter = new DetailsAdapter(TempList_Inventory, list, getApplicationContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(AuditDetrails.this, "List is Empty", Toast.LENGTH_SHORT).show();
        }


//        adapter.notifyDataSetChanged();
//        Accession.setText("");
        total.setText("0");
        not_found.setText("0");
        found.setText("0");


    }

//    private void createExcelSheet() {
//        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
//        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
//        HSSFRow row = hssfSheet.createRow(0);
//        HSSFRow row1 = hssfSheet.createRow(1);
//        HSSFCell cell = row.createCell(0);
////        Row row = sheet.createRow(0);
//
//
//        cell = (HSSFCell) row.createCell(0);
//        cell.setCellValue("Audit Id");
////        cell.setCellStyle(cellStyle);
//
//        cell = (HSSFCell) row.createCell(1);
//        cell.setCellValue("Rfid No");
////        cell.setCellStyle(cellStyle);
//
//        cell = (HSSFCell) row.createCell(2);
//        cell.setCellValue("Status");
////        cell.setCellStyle(cellStyle);
//
//        cell = (HSSFCell) row.createCell(3);
//        cell.setCellValue("Asset Id");
//        cell = (HSSFCell) row.createCell(4);
//        cell.setCellValue("Asset Name");
//        cell = (HSSFCell) row.createCell(5);
//        cell.setCellValue("Asset Tag");
////        cell.setCellStyle(cellStyle);
//        cell = (HSSFCell) row.createCell(6);
//        cell.setCellValue("Serial No");
//
//        cell = (HSSFCell) row.createCell(7);
//        cell.setCellValue("Model");
//        cell = (HSSFCell) row.createCell(8);
//        cell.setCellValue("Category");
//        cell = (HSSFCell) row.createCell(9);
//        cell.setCellValue("Asset Status");
//
//        cell = (HSSFCell) row.createCell(10);
//        cell.setCellValue("Company");
////        cell.setCellStyle(cellStyle);
//
//        cell = (HSSFCell) row.createCell(11);
//        cell.setCellValue("Manufacture Name");
//        cell = (HSSFCell) row.createCell(12);
//        cell.setCellValue("Location");
//        cell = (HSSFCell) row.createCell(13);
//        cell.setCellValue("Purchase Cost");
////        cell.setCellStyle(cellStyle);
//        cell = (HSSFCell) row.createCell(14);
//        cell.setCellValue("Supplier");
//
//        cell = (HSSFCell) row.createCell(15);
//        cell.setCellValue("Order No");
//        cell = (HSSFCell) row.createCell(16);
//        cell.setCellValue("Purchase Date");
//        cell = (HSSFCell) row.createCell(17);
//        cell.setCellValue("Notes");
//
//        //Value Here
//        String name = "";
//        for (int i = 0; i < list.size(); i++) {
//            Row rowData = hssfSheet.createRow(i + 1);
//            cell = (HSSFCell) rowData.createCell(0);
//            cell.setCellValue(list.get(i).getAuditid());
////        cell.setCellStyle(cellStyle);
//            cell = (HSSFCell) rowData.createCell(1);
//            cell.setCellValue(list.get(i).getRfidNo());
////        cell.setCellStyle(cellStyle);
//            cell = (HSSFCell) rowData.createCell(2);
//            cell.setCellValue(list.get(i).getStatus());
////        cell.setCellStyle(cellStyle);
//            cell = (HSSFCell) rowData.createCell(3);
//            cell.setCellValue(list.get(i).getAssetId());
//            cell = (HSSFCell) rowData.createCell(4);
//            cell.setCellValue(list.get(i).getAssetName());
//            cell = (HSSFCell) rowData.createCell(5);
//            cell.setCellValue(list.get(i).getAssetTag());
////        cell.setCellStyle(cellStyle);ZZZL
//            cell = (HSSFCell) rowData.createCell(6);
//            cell.setCellValue(list.get(i).getSerialNo());
//            cell = (HSSFCell) rowData.createCell(7);
//            cell.setCellValue(list.get(i).getModel());
//            cell = (HSSFCell) rowData.createCell(8);
//            cell.setCellValue(list.get(i).getCategory());
//            cell = (HSSFCell) rowData.createCell(9);
//            cell.setCellValue(list.get(i).getAssetStatus());
//
//            cell = (HSSFCell) rowData.createCell(10);
//            cell.setCellValue(list.get(i).getCompany());
////        cell.setCellStyle(cellStyle);
//
//            cell = (HSSFCell) rowData.createCell(11);
//            cell.setCellValue(list.get(i).getManufacturer());
//            cell = (HSSFCell) rowData.createCell(12);
//            cell.setCellValue(list.get(i).getLocation());
//            cell = (HSSFCell) rowData.createCell(13);
//            cell.setCellValue(list.get(i).getPurchaseCost());
////        cell.setCellStyle(cellStyle);
//            cell = (HSSFCell) rowData.createCell(14);
//            cell.setCellValue(list.get(i).getWarranty());
//
//            cell = (HSSFCell) rowData.createCell(15);
//            cell.setCellValue(list.get(i).getOrderNo());
//            cell = (HSSFCell) rowData.createCell(16);
//            cell.setCellValue(list.get(i).getPurchaseDate());
//            cell = (HSSFCell) rowData.createCell(17);
//            cell.setCellValue(list.get(i).getNotes());
//
////              name=list.get(i).getRfidNo();
//        }
//        filepath = new File(Environment.getExternalStorageDirectory(), keyid + " AssetExcel.xls");
//        try {
//            if (!filepath.exists()) {
//
//                filepath.createNewFile();
//            }
//            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
//            hssfWorkbook.write(fileOutputStream);
//            if (fileOutputStream != null) {
//                Toast.makeText(AuditDetrails.this, "" + filepath, Toast.LENGTH_SHORT).show();
//                fileOutputStream.flush();
//                fileOutputStream.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        }
//    }

//    public void UpdateExcel() {
////        File xlsxFile = new File(filepath);
//
//        //New students records to update in excel file
//        Object[][] newStudents = {
//                {"Audit Id", "Rfid No", "Status", "Asset Id", "Asset Name", "Asset Tag", "Serial No", "Model", "Category", "Asset Status", "Company", "Manufacture Name", "Location", "Purchase Cost", "Supplier", "Order No", "Purchase Date", "Notes"},
//        };
//
//        try {
//            //Creating input stream
//            FileInputStream inputStream = new FileInputStream(filepath);
//
//            //Creating workbook from input stream
//            Workbook workbook = WorkbookFactory.create(inputStream);
//
//            //Reading first sheet of excel file
//            Sheet sheet = workbook.getSheetAt(0);
//
//            //Getting the count of existing records
//            int rowCount = sheet.getLastRowNum();
//            HSSFCell cell;
//            for (int i = 0; i < list.size(); i++) {
//
//                //Creating new row from the next row count
//                Row rowData = sheet.createRow(++rowCount);
//                cell = (HSSFCell) rowData.createCell(0);
//                cell.setCellValue(list.get(i).getAuditid());
////        cell.setCellStyle(cellStyle);
//                cell = (HSSFCell) rowData.createCell(1);
//                cell.setCellValue(list.get(i).getRfidNo());
////        cell.setCellStyle(cellStyle);
//                cell = (HSSFCell) rowData.createCell(2);
//                cell.setCellValue(list.get(i).getStatus());
////        cell.setCellStyle(cellStyle);
//                cell = (HSSFCell) rowData.createCell(3);
//                cell.setCellValue(list.get(i).getAssetId());
//                cell = (HSSFCell) rowData.createCell(4);
//                cell.setCellValue(list.get(i).getAssetName());
//                cell = (HSSFCell) rowData.createCell(5);
//                cell.setCellValue(list.get(i).getAssetTag());
////        cell.setCellStyle(cellStyle);ZZZL
//                cell = (HSSFCell) rowData.createCell(6);
//                cell.setCellValue(list.get(i).getSerialNo());
//                cell = (HSSFCell) rowData.createCell(7);
//                cell.setCellValue(list.get(i).getModel());
//                cell = (HSSFCell) rowData.createCell(8);
//                cell.setCellValue(list.get(i).getCategory());
//                cell = (HSSFCell) rowData.createCell(9);
//                cell.setCellValue(list.get(i).getAssetStatus());
//
//                cell = (HSSFCell) rowData.createCell(10);
//                cell.setCellValue(list.get(i).getCompany());
////        cell.setCellStyle(cellStyle);
//
//                cell = (HSSFCell) rowData.createCell(11);
//                cell.setCellValue(list.get(i).getManufacturer());
//                cell = (HSSFCell) rowData.createCell(12);
//                cell.setCellValue(list.get(i).getLocation());
//                cell = (HSSFCell) rowData.createCell(13);
//                cell.setCellValue(list.get(i).getPurchaseCost());
////        cell.setCellStyle(cellStyle);
//                cell = (HSSFCell) rowData.createCell(14);
//                cell.setCellValue(list.get(i).getWarranty());
//
//                cell = (HSSFCell) rowData.createCell(15);
//                cell.setCellValue(list.get(i).getOrderNo());
//                cell = (HSSFCell) rowData.createCell(16);
//                cell.setCellValue(list.get(i).getPurchaseDate());
//                cell = (HSSFCell) rowData.createCell(17);
//                cell.setCellValue(list.get(i).getNotes());
//
//
//            }
//
//            //Iterating new students to update
////            for (Object[] student : newStudents) {
////
////                //Creating new row from the next row count
////                Row row = sheet.createRow(++rowCount);
////
////                int columnCount = 0;
////
////                //Iterating student informations
////                for (Object info : student) {
////
////                    //Creating new cell and setting the value
////                    Cell cell = row.createCell(columnCount++);
////                    if (info instanceof String) {
////                        cell.setCellValue((String) info);
////                    } else if (info instanceof Integer) {
////                        cell.setCellValue((Integer) info);
////                    }
////                }
////            }
//            //Close input stream
//            inputStream.close();
//
//            //Crating output stream and writing the updated workbook
//            FileOutputStream os = new FileOutputStream(filepath);
//            workbook.write(os);
//
//            //Close the workbook and output stream
////            workbook.close();
//            os.close();
//            Toast.makeText(AuditDetrails.this, "Excel file has been updated successfully.", Toast.LENGTH_SHORT).show();
//            System.out.println("Excel file has been updated successfully.");
//
//        } catch (EncryptedDocumentException | IOException e) {
//            System.err.println("Exception while updating an existing excel file.");
//            Toast.makeText(AuditDetrails.this, "Exception while updating an existing excel file.", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
//        }
//
//    }


//    private void enableSwipeToDeleteAndUndo() {
//        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//
//
//                final int position = viewHolder.getAdapterPosition();
//                final SearchDataModel item = adapter.getData().get(position);
//                adapter.removeItem(position);
//
//
//                Snackbar snackbar = Snackbar
//                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
//                snackbar.setAction("UNDO", view -> {
//
//                    adapter.restoreItem(item, position);
//                    recyclerView.scrollToPosition(position);
//                });
////
//                snackbar.setActionTextColor(Color.YELLOW);
//                snackbar.show();
//
//            }
//        };
//
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
//        itemTouchhelper.attachToRecyclerView(recyclerView);
//    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_F1://KeyEvent { action=ACTION_UP, keyCode=KEYCODE_F1, scanCode=59, metaState=0, flags=0x8, repeatCount=0, eventTime=13517236, downTime=13516959, deviceId=1, source=0x101 }
//                Button b = (Button) v;
                if (list.size() > 0) {

                    new Thread(new Runnable() {
                        public void run() {
                            buttonText = Search.getText().toString();
                            Scan(buttonText, handler);

                        }
                    }).start();
                    Submit.setEnabled(true);
                } else {
                    Toast.makeText(AuditDetrails.this, "No Data Found For Scan", Toast.LENGTH_SHORT).show();
                }


                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AuditDetrails.this, MainActivity.class));
    }
}