package com.example.itassettracking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class LocalDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SolarDataBase";
    private static final String TABLE_CONTACTS = "ExcelData";
    public static final String auditid ="auditid";
    public static final String rfidNo="rfidNo";
    public static final String status="status";
    public static final String assetId="assetId";
    public static final String assetName="assetName";
    public static final String deviceImage="deviceImage";
    public static final String serialNo="serialNo";
    public static final String model="model";
    public static final String category="category";
    public static final String assetStatus="assetStatus";
    public static final String company="company";
    public static final String manufacturer="manufacturer";
    public static final String location="location";
    public static final String sublocation="sublocation";
    public static final String plantLocation="plantLocation";
    public static final String warranty ="warranty";
    public static final String purchaseCost="purchaseCost";
    public static final String supplier="supplier";
    public static final String orderNo="orderNo";
    public static final String purchaseDate="purchaseDate";
    public static final String notes="notes";
    public static final String floor="floor";
    public static final String assetType="assetType";
    public static final String assetClass="assetClass";
    public static final String cumAcqValue="cumAcqValue";
    public static final String acqDep="acqDep";
    public static final String currentBookValue ="currentBookValue";
    public static final String orderDate="orderDate";
    public LocalDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_CONTACTS
                + " ("
                + auditid + " TEXT,"
                + rfidNo + " TEXT,"
                + status + " TEXT,"
                + assetId + " TEXT,"
                + assetName + " TEXT,"
                + serialNo + " TEXT,"
                + model + " TEXT,"
                + category + " TEXT,"
                + assetStatus + " TEXT,"
                + company + " TEXT,"
                + manufacturer + " TEXT,"
                + location + " TEXT,"
                + warranty + " TEXT,"
                + purchaseCost + " TEXT,"
                + orderNo + " TEXT,"
                + purchaseDate + " TEXT,"
                + notes + " TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public void addContact(SearchDataModel dataModelClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(auditid, dataModelClass.getAuditid()); // Contact Name
        values.put(rfidNo, dataModelClass.getRfidNo()); // Contact Name
        values.put(status, dataModelClass.getStatus()); // Contact Name
        values.put(assetId, dataModelClass.getAssetId()); // Contact Name
        values.put(assetName, dataModelClass.getAssetName()); // Contact Name
        values.put(serialNo, dataModelClass.getSerialNo()); // Contact Name
        values.put(model, dataModelClass.getModel()); // Contact Name
        values.put(category, dataModelClass.getCategory()); // Contact Name
        values.put(assetStatus, dataModelClass.getAssetStatus()); // Contact Name
        values.put(company, dataModelClass.getCompany()); // Contact Name
        values.put(manufacturer, dataModelClass.getManufacturer()); // Contact Name
        values.put(location, dataModelClass.getLocation()); // Contact Name
        values.put(warranty, dataModelClass.getWarranty()); // Contact Name
        values.put(purchaseCost, dataModelClass.getPurchaseCost()); // Contact Name
        values.put(orderNo, dataModelClass.getOrderNo()); // Contact Name
        values.put(purchaseDate, dataModelClass.getPurchaseDate()); // Contact Name
        values.put(notes, dataModelClass.getNotes()); // Contact Name


        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public List<SearchDataModel> getAllContacts() {
        List<SearchDataModel> contactList = new ArrayList<SearchDataModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SearchDataModel dataModelClass = new SearchDataModel();
                dataModelClass.setAuditid(cursor.getString(0));
                dataModelClass.setRfidNo(cursor.getString(1));
                dataModelClass.setStatus(cursor.getString(2));
                dataModelClass.setAssetId(cursor.getString(3));
                dataModelClass.setAssetName(cursor.getString(4));
                dataModelClass.setSerialNo(cursor.getString(5));
                dataModelClass.setModel(cursor.getString(6));
                dataModelClass.setCategory(cursor.getString(7));
                dataModelClass.setAssetStatus(cursor.getString(8));
                dataModelClass.setCompany(cursor.getString(9));
                dataModelClass.setManufacturer(cursor.getString(10));
                dataModelClass.setLocation(cursor.getString(11));
                dataModelClass.setWarranty(cursor.getString(12));
                dataModelClass.setPurchaseCost(cursor.getString(13));
                dataModelClass.setOrderNo(cursor.getString(14));
                dataModelClass.setPurchaseDate(cursor.getString(15));
                dataModelClass.setNotes(cursor.getString(16));
                // Adding contact to list
                contactList.add(dataModelClass);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }



}
