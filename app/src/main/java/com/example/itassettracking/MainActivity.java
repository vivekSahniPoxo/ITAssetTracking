package com.example.itassettracking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.mainGrid);
        setSingleEvent(gridLayout);


        //Method for Internet Connection Check
        isNetworkConnectionAvailable();

    }

    private void setSingleEvent(GridLayout gridLayout) {

        CardView Stock = (CardView) gridLayout.findViewById(R.id.assetStock);
        CardView SearchForm = gridLayout.findViewById(R.id.Searchform);
        CardView InventoryForm = gridLayout.findViewById(R.id.InventoryForm);
        CardView Registerasset = gridLayout.findViewById(R.id.RegisterAsset);
        CardView Settingfrom = gridLayout.findViewById(R.id.SettingForm);
        CardView Auditform = gridLayout.findViewById(R.id.Auditform);


        Auditform.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AuditForm.class)));
        Settingfrom.setOnClickListener(view -> Toast.makeText(this, "Setting Form", Toast.LENGTH_SHORT).show());
        InventoryForm.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, IdentifyForm.class)));
        SearchForm.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Searchform.class)));
        Stock.setOnClickListener(view -> ShowDailog());
        Registerasset.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AssetRegister.class)));

    }

    private void ShowDailog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);

        View mView = layoutInflaterAndroid.inflate(R.layout.assetdailogbox, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);
        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        CardView iNNN = mView.findViewById(R.id.innnn);
        CardView Out = mView.findViewById(R.id.Out);

        iNNN.setOnClickListener(view -> {
            Toast.makeText(getApplication(), "Innn", Toast.LENGTH_SHORT).show();
            alertDialogAndroid.dismiss();
        });
        Out.setOnClickListener(view -> {
            Toast.makeText(getApplication(), "Out", Toast.LENGTH_SHORT).show();
            alertDialogAndroid.dismiss();

        });
        alertDialogAndroid.show();
    }
    @Override
    public void onBackPressed() {
        //Dialog Box for Exit User
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setIcon(R.drawable.exitapp)
                .setTitle("Quit")
                .setMessage("Are you sure you want to Quit this App ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


    public void checkNetworkConnection(){
        androidx.appcompat.app.AlertDialog.Builder builder =new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(MainActivity.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }
}