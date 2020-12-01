package com.edu.unimagdalena.appmoviles.gasstations.ui.add_gas_station;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.edu.unimagdalena.appmoviles.gasstations.R;
import com.edu.unimagdalena.appmoviles.gasstations.database.GasStationController;
import com.edu.unimagdalena.appmoviles.gasstations.models.GasStation;
import com.edu.unimagdalena.appmoviles.gasstations.models.Location;


public class AddGasStationFragment extends Fragment implements View.OnClickListener{
    EditText name, company, department, municipality, latitude, longitude;
    GasStationController gs_controller;
    Button btnAdd;
    Context context;
    GasStation gs;
    View View;


    public AddGasStationFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View = inflater.inflate(R.layout.fragment_add_gas_station, container, false);
        return View;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = View.getContext();
        gs_controller = new GasStationController(context);

        name = view.findViewById(R.id.NAME);
        company =  view.findViewById(R.id.COMPANY);
        department = view.findViewById(R.id.DEPARTMENT);
        municipality = view.findViewById(R.id.MUNICIPALITY);
        latitude = view.findViewById(R.id.LATITUDE);
        longitude = view.findViewById(R.id.LONGITUDE);

        btnAdd = view.findViewById(R.id.btnAddStation);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddStation) {
            if (name.getText().toString().isEmpty() ||
                    company.getText().toString().isEmpty() ||
                    department.getText().toString().isEmpty() ||
                    municipality.getText().toString().isEmpty() ||
                    latitude.getText().toString().isEmpty() ||
                    longitude.getText().toString().isEmpty()) {
                Toast.makeText(context, "Verifique que los datos no se encuentren vacios", Toast.LENGTH_LONG).show();
            } else {

                gs = new GasStation(name.getText().toString(),
                        company.getText().toString(),
                        department.getText().toString(),
                        municipality.getText().toString(),
                        new Location(Double.parseDouble(latitude.getText().toString()),
                                Double.parseDouble(longitude.getText().toString())));

                if (gs_controller.getGasStation(gs.getLocation().getLatitude(), gs.getLocation().getLatitude()) )
                    Toast.makeText(context, "La estacion de gasolina ya esta registrada", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(context, "Estacion de gasolina agregada correctamente", Toast.LENGTH_LONG).show();
                    gs_controller.addGasStation(gs);
                    clearAll();
                }
            }
        }
    }

    private void clearAll() {
        name.setText("");
        company.setText("");
        department.setText("");
        municipality.setText("");
        latitude.setText("");
        longitude.setText("");
    }
}