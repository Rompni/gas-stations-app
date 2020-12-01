package com.edu.unimagdalena.appmoviles.gasstations.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.edu.unimagdalena.appmoviles.gasstations.models.GasStation;
import com.edu.unimagdalena.appmoviles.gasstations.models.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.edu.unimagdalena.appmoviles.gasstations.database.GasStationContract.GasStationEntry.*;

public class GasStationController {
    private final GasStationDbHelper dbHelper;
    private final Context c;

    public GasStationController(Context c) {
        this.dbHelper = new GasStationDbHelper(c);
        this.c = c;
    }

    public void addGasStation(GasStation gs){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Random random = new Random();

        try
        {
            int randomNumber = random.nextInt(9998) + 1;
            ContentValues values = new ContentValues();
            values.put(_ID, randomNumber);
            values.put(COLUMN_GAS_STATION_NAME, gs.getName());
            values.put(COLUMN_GAS_STATION_COMPANY, gs.getCompany());
            values.put(COLUMN_GAS_STATION_DEPARTMENT, gs.getDepartment());
            values.put(COLUMN_GAS_STATION_MUNICIPALITY, gs.getMunicipality());
            
            values.put(COLUMN_GAS_STATION_LATITUDE, gs.getLocation().getLatitude());
            values.put(COLUMN_GAS_STATION_LONGITUDE, gs.getLocation().getLongitude());

            long newRowId = db.insert(TABLE_GAS_STATION, null, values);

            if (newRowId < 0)
                Toast.makeText(c, "error Estacion de gasolina", Toast.LENGTH_LONG).show();

            db.close();

        }
        catch (Exception e)
        {
            db.close();
            Toast.makeText(c, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public Boolean getGasStation(double latitude, double longitude)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                COLUMN_GAS_STATION_LATITUDE,
                COLUMN_GAS_STATION_LONGITUDE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection =  COLUMN_GAS_STATION_LATITUDE + " = ? AND "+ COLUMN_GAS_STATION_LONGITUDE + " = ? "  ;
        String[] selectionArgs = { latitude+"", longitude+"" };

        try
        {
        Cursor cursor = db.query(TABLE_GAS_STATION,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null);

            while (cursor.moveToNext()) {

             if(  cursor.getDouble(0) == latitude && cursor.getDouble(1) == longitude){
                 db.close();
                 return true;
             }

            }

        }
        catch (Exception e)
        {
            db.close();
            Toast.makeText(c, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        db.close();
        return false;
    }

    public List<GasStation> getAllGasStation() {

        List<GasStation> gasStationsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        try {

            cursor = db.query(
                    TABLE_GAS_STATION,   // The table to query
                    null,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null               // The sort order
            );

            while (cursor.moveToNext()) {

                GasStation gs = new GasStation(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        new Location(
                                cursor.getDouble(5),
                                cursor.getDouble(6)));

                gasStationsList.add(gs);
            }
            cursor.close();
        }catch (Exception e)
        {
            Toast.makeText(c, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return gasStationsList;
    }
}

