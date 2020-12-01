package com.edu.unimagdalena.appmoviles.gasstations.database;

import android.provider.BaseColumns;

public final class GasStationContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    GasStationContract() {}

    /* Inner class that defines the table contents */
    public static class GasStationEntry implements BaseColumns {
        public  static final String TABLE_GAS_STATION = "gas_stations";
        public  static final String COLUMN_GAS_STATION_NAME = "gas_station_Name";
        public  static final String COLUMN_GAS_STATION_COMPANY = "gas_station_Company";
        public  static final String COLUMN_GAS_STATION_DEPARTMENT = "gas_station_Department";
        public  static final String COLUMN_GAS_STATION_MUNICIPALITY = "gas_station_Municipality";
        public  static final String COLUMN_GAS_STATION_LATITUDE = "gas_station_latitude";
        public  static final String COLUMN_GAS_STATION_LONGITUDE = "gas_station_longitude";

    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GasStationEntry.TABLE_GAS_STATION + " (" +
                    GasStationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    GasStationEntry.COLUMN_GAS_STATION_NAME + " TEXT," +
                    GasStationEntry.COLUMN_GAS_STATION_COMPANY + " TEXT," +
                    GasStationEntry.COLUMN_GAS_STATION_DEPARTMENT + " TEXT," +
                    GasStationEntry.COLUMN_GAS_STATION_MUNICIPALITY + " TEXT," +
                    GasStationEntry.COLUMN_GAS_STATION_LATITUDE + " DOUBLE," +
                    GasStationEntry.COLUMN_GAS_STATION_LONGITUDE + " DOUBLE" +
                    ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GasStationEntry.TABLE_GAS_STATION;

}

