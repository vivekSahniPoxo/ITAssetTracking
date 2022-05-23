package com.example.itassettracking;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.speedata.libuhf.IUHFService;
import com.speedata.libuhf.UHFManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssetRegister extends AppCompatActivity {

    private Spinner spCountry, AssetLocation, AssetStatus, AssetSupplier, AssetModel, Manufacture;
    final Calendar myCalendar = Calendar.getInstance();
    TextView Datepurchase;
    EditText AssetSerialNumber, Assetnm, Ordernumber, wrannty, Notes, PurCost;
    IUHFService iuhfService;
    CardView butnscan;
    private static final int Require_Image = 101;
    Bitmap bitmap;
    String PurchaseDAte, DeviceImage, AssetTAg, SupplierID, LocationID, CompanyID, StatusID, ModelID, ManufactureId;
    byte[] decodedString;
    Button uploadbtn, register;
    List<ModelList> CompanyAssetList, LocationAsset, StatusAsset, SupplierAsset, ModelAsset, ManufaactureAsset;
    ProgressDialog dialog;
    List<String> CompanyAssetList1, LocationAsset1, StatusAsset1, SupplierAsset1, ModelAsset1, ManufaactureAsset1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_register);
        Datepurchase = findViewById(R.id.PurchaseDate);
        Notes = findViewById(R.id.Notes);
        Ordernumber = findViewById(R.id.OrderNo);
        uploadbtn = findViewById(R.id.uploadbtn);
        AssetLocation = findViewById(R.id.spinner_AssetLocation);
        AssetStatus = findViewById(R.id.spinner_AssetStatus);
        AssetSupplier = findViewById(R.id.spinner_AssetSupplier);
        AssetModel = findViewById(R.id.spinner_AssetModel);
        AssetSerialNumber = findViewById(R.id.SerialNoAsset);
        butnscan = findViewById(R.id.button_Scan);
        register = findViewById(R.id.button_Register);
        Assetnm = findViewById(R.id.AssetName);
        PurCost = findViewById(R.id.PurchaseCst);
        Manufacture = findViewById(R.id.Warranty);
        spCountry = findViewById(R.id.spinner_PVname);
        dialog = new ProgressDialog(this);

        CategoriesList();

        ManufactureList();
        ModelList();
        SupplierList();
        StatusList();
        LocationList();

        CompanyAssetList = new ArrayList<>();
        LocationAsset = new ArrayList<>();
        StatusAsset = new ArrayList<>();
        SupplierAsset = new ArrayList<>();
        ManufaactureAsset = new ArrayList<>();
        ModelAsset = new ArrayList<>();
        CompanyAssetList1 = new ArrayList<>();
        LocationAsset1 = new ArrayList<>();
        StatusAsset1 = new ArrayList<>();
        SupplierAsset1 = new ArrayList<>();
        ManufaactureAsset1 = new ArrayList<>();
        ModelAsset1 = new ArrayList<>();






        //Set up Local Data with Adapter
//        final List<String> List = new ArrayList<>(Arrays.asList(value));


        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (CompanyAssetList1.size()>0){
//                CompanyID = String.valueOf(CompanyAssetList.get(adapterView.getItemAtPosition(i).toString());
                CompanyID = CompanyAssetList.get(i).getId();
//                }
//                {
//                Toast.makeText(AssetRegister.this, "No DATA "+ CompanyID, Toast.LENGTH_SHORT).show();
//            }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        Manufacture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ManufactureId =ManufaactureAsset.get(i).getId();
//                Toast.makeText(AssetRegister.this, "" + ManufactureId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        AssetStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StatusID = StatusAsset.get(i).getId();
//                Toast.makeText(AssetRegister.this, "" + StatusID, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        AssetSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SupplierID =SupplierAsset.get(i).getId();
//                Toast.makeText(AssetRegister.this, "" + SupplierID, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        AssetModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ModelID = ModelAsset.get(i).getId();
//                Toast.makeText(AssetRegister.this, "" + ModelID, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        AssetLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LocationID = LocationAsset.get(i).getId();
//                Toast.makeText(AssetRegister.this, "" + LocationID, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        iuhfService = UHFManager.getUHFService(this);
        iuhfService.openDev();
        iuhfService.inventoryStart();
        iuhfService.setOnInventoryListener(var1 -> {

        });
        register.setOnClickListener(view -> {
            try {
                Validate();
                FetchData();
                dialog.show();
                dialog.setMessage(getString(R.string.Dialog_Text));
                dialog.setCancelable(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

        butnscan.setOnClickListener(view ->
                Scan()
        );
        uploadbtn.setOnClickListener(view -> TakePictue());
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();

        };
        Datepurchase.setOnClickListener(view -> new DatePickerDialog(AssetRegister.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

    }

    //    Read RFID TAg
    private void Scan() {
        iuhfService.openDev();
        AssetTAg = iuhfService.read_area(1, "2", "6", "00000000");
        iuhfService.inventory_stop();
        Toast.makeText(AssetRegister.this, "Reading...", Toast.LENGTH_SHORT).show();
    }

    private void ModelList() {
        StringRequest request = new StringRequest(Request.Method.GET, "http://164.52.223.163:4504/api/GetAssetModels", response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String NameModel = object.getString("name");
                    String Id = object.getString("id");

                    ModelAsset.add(new ModelList(NameModel, Id));
                }
                SetupModelList(ModelAsset);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> Toast.makeText(getApplication(), "No Data ", Toast.LENGTH_SHORT).show());

        //adding the request to volley
        Volley.newRequestQueue(this).add(request);

    }

    private void SetupModelList(List<ModelList> modelAsset) {
        for (int i = 0; i < ModelAsset.size(); i++) {
            ModelAsset1.add(ModelAsset.get(i).getName());
        }

        ArrayAdapter<String> model = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ModelAsset1);

        // Drop down layout style - list view with radio button
        model.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        AssetModel.setAdapter(model);
    }

    private void ManufactureList() {
        StringRequest request = new StringRequest(Request.Method.GET, "http://164.52.223.163:4504/api/GetManufacturer", response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String NameModel = object.getString("name");
                    String Id = object.getString("id");
                    ManufaactureAsset.add(new ModelList(NameModel, Id));
                }
                SetupManfacturer(ManufaactureAsset);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> Toast.makeText(getApplication(), "No Data ", Toast.LENGTH_SHORT).show());

        //adding the request to volley
        Volley.newRequestQueue(this).add(request);

    }

    private void SetupManfacturer(List<ModelList> manufaactureAsset) {
        for (int i = 0; i < ManufaactureAsset.size(); i++) {
            ManufaactureAsset1.add(ManufaactureAsset.get(i).getName());
        }
        ArrayAdapter<String> Manufacturer = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ManufaactureAsset1);
        // Drop down layout style - list view with radio button
        Manufacturer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        Manufacture.setAdapter(Manufacturer);
    }

    private void SupplierList() {
        StringRequest request = new StringRequest(Request.Method.GET, "http://164.52.223.163:4504/api/GetSupplier", response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String NameModel = object.getString("supplierName");
                    String Id = object.getString("id");
                    SupplierAsset.add(new ModelList(NameModel, Id));
                }
                setUpSupplier(SupplierAsset);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {

        });

        //adding the request to volley
        Volley.newRequestQueue(this).add(request);


    }

    private void setUpSupplier(List<ModelList> supplierAsset) {
        for (int i = 0; i < SupplierAsset.size(); i++) {
            SupplierAsset1.add(SupplierAsset.get(i).getName());
        }
        ArrayAdapter<String> supplier = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SupplierAsset1);
        // Drop down layout style - list view with radio button
        supplier.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        AssetSupplier.setAdapter(supplier);

    }

    private void StatusList() {
        StringRequest request = new StringRequest(Request.Method.GET, "http://164.52.223.163:4504/api/GetStatus", response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String NameModel = object.getString("status");
                    String Id = object.getString("id");
                    StatusAsset.add(new ModelList(NameModel, Id));
                }
                SetupStatus(StatusAsset);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {

        });

        //adding the request to volley
        Volley.newRequestQueue(this).add(request);


    }

    private void SetupStatus(List<ModelList> statusAsset) {
        for (int i = 0; i < StatusAsset.size(); i++) {
            StatusAsset1.add(StatusAsset.get(i).getName());
        }
        ArrayAdapter<String> Status = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, StatusAsset1);
        // Drop down layout style - list view with radio button
        Status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        AssetStatus.setAdapter(Status);
    }

    private void LocationList() {
        StringRequest request = new StringRequest(Request.Method.GET, "http://164.52.223.163:4504/api/GetLocations", response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String NameModel = object.getString("locationName");
                    String Id = object.getString("id");

                    LocationAsset.add(new ModelList(NameModel, Id));
                }
                SetupLocation(LocationAsset);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {

        });

        //adding the request to volley
        Volley.newRequestQueue(this).add(request);


    }

    private void SetupLocation(List<ModelList> locationAsset) {
        for (int i = 0; i < LocationAsset.size(); i++) {
            LocationAsset1.add(LocationAsset.get(i).getName());
        }
        ArrayAdapter<String> location = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LocationAsset1);
        // Drop down layout style - list view with radio button
        location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        AssetLocation.setAdapter(location);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        Datepurchase.setText(dateFormat.format(myCalendar.getTime()));
        PurchaseDAte = dateFormat.format(myCalendar.getTime());

    }

    public void TakePictue() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i, Require_Image);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Require_Image && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            String path = data.getDataString();
            Toast.makeText(AssetRegister.this, "UpLoaded...", Toast.LENGTH_SHORT).show();
//            imageView.setImageBitmap(bitmap);
            DeviceImage = getEncoded64ImageStringFromBitmap(bitmap);

            System.out.println("Image String " + Arrays.toString(decodedString));
        }
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();

        // Get the Base64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    private void CategoriesList() {
//        String url = "http://164.52.223.163:4501/api/storematerial/distinctlocation";
        StringRequest request = new StringRequest(Request.Method.GET, "http://164.52.223.163:4504/api/GetCompanies", response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String NameModel = object.getString("companyName");
                    String Id = object.getString("id");
                    CompanyAssetList.add(new ModelList(NameModel, Id));
                }
                SetupSpinner(CompanyAssetList);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {

        });

        //adding the request to volley
        Volley.newRequestQueue(this).add(request);

    }

    private void SetupSpinner(List<ModelList> companyAssetList) {

        for (int i = 0; i < CompanyAssetList.size(); i++) {
            CompanyAssetList1.add(CompanyAssetList.get(i).getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CompanyAssetList1);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spCountry.setAdapter(dataAdapter);


    }


    private void FetchData() throws JSONException {
        JSONObject obj = new JSONObject();
        int comId = Integer.parseInt(CompanyID);
        int modelId = Integer.parseInt(ModelID);
        int statusId = Integer.parseInt(StatusID);
        int manufacturerId = Integer.parseInt(ManufactureId);
        int locationId = Integer.parseInt(LocationID);

//        obj.put("id", 0);
        obj.put("assetName", Assetnm.getText().toString().trim());
        obj.put("deviceImage", DeviceImage);
        obj.put("assetTag", AssetTAg);
        obj.put("serialNo", AssetSerialNumber.getText().toString().trim());
        obj.put("modelId", modelId);
        obj.put("categoryId", modelId);
        obj.put("statusId", statusId);
        obj.put("companyId", comId);
        obj.put("manufacturerId", manufacturerId);
        obj.put("locationId", locationId);
        obj.put("purchaseCost", (PurCost.getText().toString().trim()));
        obj.put("supplierId", (SupplierID));
        obj.put("orderNo", (Ordernumber.getText().toString().trim()));
        obj.put("purchaseDate", PurchaseDAte);
        obj.put("notes", Notes.getText().toString().trim());
        RequestQueue queue = Volley.newRequestQueue(this);


        final String requestBody = obj.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://164.52.223.163:4504/api/CreateAssets", response -> {

            Toast.makeText(AssetRegister.this,response+ " Successfully... "  , Toast.LENGTH_SHORT).show();

            Clear();

//                    Toast.makeText(Inventory_form.this, name, Toast.LENGTH_LONG).show();

            Log.i("VOLLEY", response);
            dialog.dismiss();
        }, error -> {
            try {
                Clear();
                Log.e("VOLLEY Negative", String.valueOf(error.networkResponse.statusCode));
//
                if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(AssetRegister.this, "No Result Found", Toast.LENGTH_SHORT).show();
                } else if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(AssetRegister.this, "Bad Request", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AssetRegister.this, "Unable to process the request", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                Toast.makeText(this, "Server not responding...", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }//

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

                return super.parseNetworkResponse(response);
            }
        };

        queue.add(stringRequest);
    }

    public void Clear() {
        AssetSerialNumber.setText("");
        Assetnm.setText("");
        Ordernumber.setText("");

        Notes.setText("");
        PurCost.setText("");
    }

    public void Validate() {
        if (!(AssetSerialNumber.length() > 0)) {
            AssetSerialNumber.setError("Enter....");
        }
        if (!(Assetnm.length() > 0)) {
            Assetnm.setError("Enter....");
        }
        if (!(Ordernumber.length() > 0)) {
            Ordernumber.setError("Enter....");
        }

        if (!(Notes.length() > 0)) {
            Notes.setError("Enter....");
        }
        if (!(Notes.length() > 0)) {
            Notes.setError("Enter....");
        }

    }

}