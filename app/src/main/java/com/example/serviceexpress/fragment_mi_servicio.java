package com.example.serviceexpress;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class fragment_mi_servicio extends Fragment implements OnMapReadyCallback {

    private MapView mMapView;
    private GoogleMap googleMapR;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference db_services;
    private Servicios user_service;
    private boolean hasService;

    private TextView tv_service_name;
    private TextView tv_service_no_services;
    private TextView tv_hours;
    private TextView tv_creation_date;
    private TextView tv_phone;
    private TextView tv_email;
    private TextView tv_whatsapp;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.mi_servicio, null);
        mAuth = FirebaseAuth.getInstance();
        // Check if user has a Service
        database = FirebaseDatabase.getInstance();
        db_services = database.getReference("servicio");
        currentUser = mAuth.getCurrentUser();

        hasService = false;


        tv_service_name = (TextView)rootView.findViewById(R.id.field_service_name);
        tv_service_no_services = (TextView)rootView.findViewById(R.id.field_service_no_services);
        tv_hours = (TextView)rootView.findViewById(R.id.field_hours);
        tv_creation_date= (TextView)rootView.findViewById(R.id.field_creation_date);
        tv_phone = (TextView)rootView.findViewById(R.id.field_phone);
        tv_email = (TextView)rootView.findViewById(R.id.field_email);
        tv_whatsapp = (TextView)rootView.findViewById(R.id.field_whatsapp);

        MapFragment staticMap = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_static);

        staticMap.getMapAsync(this);


        return rootView;

    }


    @Override
    public void onResume() {
        super.onResume();

        db_services.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot service : dataSnapshot.getChildren()){
                    Servicios checkService = service.getValue(Servicios.class);
                    if (checkService.getUserID().equals(currentUser.getUid())){
                        hasService = true;
                        user_service = checkService;
                        break;
                    }
                }

                if (!hasService){
                    showNewServiceDialog();

                }else{
                    tv_service_name.setText(user_service.getNombre());
                    tv_service_no_services.setText("No de Solicitudes: " + String.valueOf(user_service.getNo_servicios()));
                    tv_hours.setText(user_service.getHora_apertura() + " - " + user_service.getHora_clausura());
                    tv_creation_date.setText(user_service.getCreationDate());
                    tv_phone.setText(user_service.getTelefono());
                    tv_email.setText(user_service.getEmail());
                    tv_whatsapp.setText(user_service.getWhatsapp());

                    LatLng serviceLatLng = user_service.getUbicacion().getLatLNG();

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(serviceLatLng);
                    markerOptions.title(user_service.getNombre());
                    googleMapR.animateCamera(CameraUpdateFactory.newLatLng(serviceLatLng));
                    googleMapR.animateCamera(CameraUpdateFactory.zoomTo(16));
                    googleMapR.addMarker(markerOptions);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showNewServiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Aun no has Agregado ningun servicio");
        builder.setTitle("Agregar Servicio");
        builder.setPositiveButton("Agregar Servicio", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), new_service.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                changeFragment();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                changeFragment();
            }
        });
        Log.d("Tambien aca","lol");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void changeFragment() {
        Intent intent = new Intent(getActivity(), user_profile.class);
        startActivity(intent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapR = googleMap;
    }
}