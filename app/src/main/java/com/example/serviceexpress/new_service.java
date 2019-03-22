package com.example.serviceexpress;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;


import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class new_service extends FragmentActivity implements OnMapReadyCallback, OnMyLocationButtonClickListener, OnMyLocationClickListener {

    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private FirebaseUser currentFirebaseUser;
    private LatLng currentMarker = null;
    private AwesomeValidation awesomeValidation;
    private TextView service_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_services);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        service_address = findViewById(R.id.field_address);

        final TextView service_name = findViewById(R.id.service_field);
        final TextView service_telefone = findViewById(R.id.phone_field);
        final TextView service_email = findViewById(R.id.email_field);
        final TextView service_whatsapp = findViewById(R.id.wa_field);
        final TextView service_hora_apertura = findViewById(R.id.start_hour_field);
        final TextView service_hora_clausura = findViewById(R.id.end_hour_field);
        final Spinner service_categoria = findViewById(R.id.spinner);

        //Add Validations
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        validateFields();


        //Add service to user profile
        Button add_service_btn = findViewById(R.id.button6);
        add_service_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(awesomeValidation.validate()){

                    if (currentMarker != null){

                        AlertDialog.Builder builder = new AlertDialog.Builder(new_service.this);
                        builder.setCancelable(true);
                        builder.setTitle("¿Estas seguro?");
                        builder.setMessage( "Nombre:" + service_name.getText().toString() + "\n" +
                                            "Telefono:" + service_telefone.getText().toString() + "\n" +
                                            "Email:" + service_email.getText().toString() + "\n" +
                                            "WhatsApp:" + service_whatsapp.getText().toString() + "\n" +
                                            "Hora Apertura:" + service_hora_apertura.getText().toString() + "\n" +
                                            "Hora Clausura:" + service_hora_clausura.getText().toString() + "\n" +
                                            "Categoria:" + service_categoria.getSelectedItem().toString() + "\n"
                                            );
                        builder.setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Date currentTime = Calendar.getInstance().getTime();
                                        String shortTime = DateFormat.getDateInstance(DateFormat.SHORT).format(currentTime);

                                        Servicios new_service = new Servicios();
                                        LatLng_FB customlocation = new LatLng_FB(currentMarker.latitude,currentMarker.longitude,service_address.getText().toString());
                                        new_service.setUbicacion(customlocation);
                                        new_service.setNombre(service_name.getText().toString());
                                        new_service.setTelefono(service_telefone.getText().toString());
                                        new_service.setEmail(service_email.getText().toString());
                                        new_service.setWhatsapp(service_whatsapp.getText().toString());
                                        new_service.setHora_apertura(service_hora_apertura.getText().toString());
                                        new_service.setHora_clausura(service_hora_clausura.getText().toString());
                                        new_service.setCategoria(service_categoria.getSelectedItem().toString());
                                        new_service.setUserID(currentFirebaseUser.getUid());
                                        new_service.setCreationDate(shortTime);
                                        new_service.setNo_servicios(0);
                                        mDatabase.child("servicio").push().setValue(new_service);

                                        Intent intent = new Intent(new_service.this, user_profile.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }else{

                        AlertDialog.Builder builder = new AlertDialog.Builder(new_service.this);
                        builder.setCancelable(true);
                        builder.setTitle("Informacion incompleta");
                        builder.setMessage("No has seleccionado tu ubicacion");
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }

                }

            }
        });


    }

    private void getAddress(LatLng position){

        Geocoder geoCoder = new Geocoder(this);
        List<Address> matches = null;
        try {
            matches = geoCoder.getFromLocation(position.latitude, position.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address bestMatch = (matches.isEmpty() ? null : matches.get(0));

        Log.d("Direccion",bestMatch.toString());

        String calle = bestMatch.getAddressLine(0);

        service_address.setText(calle);

    }

    private void validateFields() {
        //Validate all Empty Fields
        awesomeValidation.addValidation(new_service.this,R.id.service_field, RegexTemplate.NOT_EMPTY,R.string.err_empty);
        awesomeValidation.addValidation(new_service.this,R.id.phone_field, RegexTemplate.NOT_EMPTY,R.string.err_empty);
        awesomeValidation.addValidation(new_service.this,R.id.email_field, RegexTemplate.NOT_EMPTY,R.string.err_empty);
        awesomeValidation.addValidation(new_service.this,R.id.wa_field, RegexTemplate.NOT_EMPTY,R.string.err_empty);
        awesomeValidation.addValidation(new_service.this,R.id.start_hour_field, RegexTemplate.NOT_EMPTY,R.string.err_empty);
        awesomeValidation.addValidation(new_service.this,R.id.end_hour_field, RegexTemplate.NOT_EMPTY,R.string.err_empty);

        //Validate Data types
        awesomeValidation.addValidation(new_service.this,R.id.phone_field, Patterns.PHONE,R.string.err_phone);
        awesomeValidation.addValidation(new_service.this,R.id.email_field, Patterns.EMAIL_ADDRESS,R.string.err_email);
        awesomeValidation.addValidation(new_service.this,R.id.wa_field, Patterns.PHONE,R.string.err_phone);
        awesomeValidation.addValidation(new_service.this,R.id.start_hour_field, "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$",R.string.err_hour);
        awesomeValidation.addValidation(new_service.this,R.id.end_hour_field, "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$",R.string.err_hour);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {mMap.setMyLocationEnabled(true);
            setUpMap();
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpMap();
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }

    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();
                // Setting the position for the marker
                markerOptions.position(latLng);
                // Set de current marker to latlng
                currentMarker = latLng;
                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                // Clears the previously touched position
                getAddress(latLng);
                mMap.clear();
                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
                mMap.getUiSettings().setMapToolbarEnabled(true);

            }
        });
    }


    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
}
