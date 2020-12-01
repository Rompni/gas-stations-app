package com.edu.unimagdalena.appmoviles.gasstations.ui.show_maps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.edu.unimagdalena.appmoviles.gasstations.MainActivity;
import com.edu.unimagdalena.appmoviles.gasstations.R;
import com.edu.unimagdalena.appmoviles.gasstations.database.GasStationController;
import com.edu.unimagdalena.appmoviles.gasstations.models.GasStation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class ShowMapsFragment extends Fragment implements OnMapReadyCallback {

    View view;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFragment;
    GasStationController gs_controller;
    boolean isInfoWindowShow = false;

    public ShowMapsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_show_maps, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }



        /*

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(),  "problem" , Toast.LENGTH_LONG).show();
            return;
        }
  */  }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            gs_controller = new GasStationController(getContext());
            MapsInitializer.initialize(getContext());
            mGoogleMap = googleMap;
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                    mGoogleMap.setMyLocationEnabled(true);
                    mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }


            List<GasStation> gasstationlist = gs_controller.getAllGasStation();
            if (!gasstationlist.isEmpty()) {

                for (GasStation gs : gasstationlist) {
                    LatLng ll = new LatLng(gs.getLocation().getLatitude(), gs.getLocation().getLongitude());
                    mGoogleMap.addMarker(new MarkerOptions().position(ll).title(gs.getName()));
                }

                LatLng mine = new LatLng(MainActivity.latitudeM, MainActivity.longitudeM);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mine));
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mine, 14.0f));

                mGoogleMap.setOnMarkerClickListener(marker -> {
                    Toast.makeText(getContext(), marker.getTitle() , Toast.LENGTH_LONG).show();
                    if (!isInfoWindowShow) {
                        marker.showInfoWindow();
                        isInfoWindowShow = true;
                    } else {
                        marker.hideInfoWindow();
                        isInfoWindowShow = false;
                    }
                    return true;
                });

            }
        }catch (Exception e){
            Toast.makeText(getContext(), "ERROR: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}

