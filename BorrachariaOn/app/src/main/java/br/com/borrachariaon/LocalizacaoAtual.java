package br.com.borrachariaon;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static android.widget.Toast.*;


public class LocalizacaoAtual extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapClickListener{

    private GoogleMap mMap;

    private Button bmapa;
    private final int GPS_REQUEST = 100;
    private LocationManager locationManager;


    private Marker currentLocationMaker;
    private LatLng currentLocationLatLong;
    private DatabaseReference mDatabase;
    private Object LatLng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        startGettingLocations();

        mDatabase = FirebaseDatabase.getInstance().getReference();


        //Toast.makeText(getBaseContext(), "Clica no mapa o local para salvar", LENGTH_LONG).show();
        //makeText(getBaseContext(), "CLica no mapa o local para salvar", LENGTH_LONG).show();
        Toast toast = makeText(getBaseContext(), "Clica no mapa o local para salvar", LENGTH_LONG);

        View toastView = toast.getView();

        // Para customizar o texto da mensagem obter a View do componente do texto
        // que no caso é um TextView
        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
        toastMessage.setTextSize(17);
        toastMessage.setTextColor(Color.BLACK);

        // Centraliza a mensagem no centro da tela
        toast.setGravity(Gravity.CENTER,0 ,200 );
        // Altera a cor de fundo da mensagem
        toastView.setBackgroundColor(Color.WHITE);
        // Exibe a mensagem
        toast.show();




        //getMarkers();

       /* bmapa = findViewById(R.id.bLocalizacaoAtual);


        bmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, GPS_REQUEST);
                }else{
                    getLocation();
                }
            }
        });*/


        // findViewById(R.id.bSalvar).setVisibility(View.INVISIBLE);

    }

    public void sair(View view){
        // finishAffinity();
        finish();
        //System.exit(0);
    }

    public void borracharias(View view){
        Intent intent = new Intent(this, Borracharias.class);
        startActivity(intent);
        finish();
    }

    public void localizacaoAtual(View view){
        Intent intent = new Intent(this, LocalizacaoAtual.class);
        startActivity(intent);
        finish();
    }



    public void salvar() {


        Intent intent = new Intent(this, MapsActivity.class);

        startActivity(intent);

    }


    public void salvar(View view){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salvando uma nova borracharia");
        builder.setMessage("Tem certeza que deseja salvar uma nova borracharia?");
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //adapter.notifyDataSetChanged();
                //listaDeCursos.remove(position);

                salvar();


            }
        });
        builder.setNegativeButton("NÃO", null);
        builder.show();


    }



/*
    public void localizacaoAtual(View view){
            getMarkers();
        }


    public void menu(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }



    private void getLocation() {
        try{
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
           // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 0, this);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }*/


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(this);


        mMap.getUiSettings().setZoomControlsEnabled(true);



        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-16.8225959, -49.257091);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        LatLng sydneyy = new LatLng(-16.83, -49.26);
        mMap.addMarker(new MarkerOptions().position(sydneyy).title("Marker in Sydney"));

        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15).target(sydney).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



        getMarkers();*/


    }



    @Override
    public void onLocationChanged(Location location) {




      if (currentLocationMaker != null) {
            currentLocationMaker.remove();
        }
        //Add marker
        currentLocationLatLong = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocationLatLong);
        markerOptions.title("Localização atual");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationMaker = mMap.addMarker(markerOptions);

        //Move to new location
        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15).target(currentLocationLatLong).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

       // LocationData locationData = new LocationData(location.getLatitude(), location.getLongitude());
        //mDatabase.child("location").child(String.valueOf(new Date().getTime())).setValue(locationData);

        Context context = getApplicationContext();
      /** Toast toast = Toast.makeText(context, "NOVA BORRACHARIA SALVA", Toast.LENGTH_LONG);

// Retorna a View padrão da mensagem
        View toastView = toast.getView();

// Para customizar o texto da mensagem obter a View do componente do texto
// que no caso é um TextView
        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
        toastMessage.setTextSize(17);
        toastMessage.setTextColor(Color.BLACK);

        // Centraliza a mensagem no centro da tela
        toast.setGravity(Gravity.CENTER,0 ,550 );


// Altera a cor de fundo da mensagem
        toastView.setBackgroundColor(Color.WHITE);

// Exibe a mensagem
        toast.show();


        // Toast.makeText(this, "NOVA BORRACHARIA SALVA", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Lat: " + location.getLatitude()+"\nLng: " + location.getLongitude(), Toast.LENGTH_SHORT).show();


        //getMarkers();
**/

    }



    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS desativado!");
        alertDialog.setMessage("Ativar GPS?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    private void startGettingLocations() {

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean canGetLocation = true;
        int ALL_PERMISSIONS_RESULT = 101;
        long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;// Distance in meters
        long MIN_TIME_BW_UPDATES = 1000 * 10;// Time in milliseconds

        ArrayList<String> permissions = new ArrayList<>();
        ArrayList<String> permissionsToRequest;

        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);

        //Check if GPS and Network are on, if not asks the user to turn on
        if (!isGPS && !isNetwork) {
            showSettingsAlert();
        } else {
            // check permissions

            // check permissions for later versions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    canGetLocation = false;
                }
            }
        }


        //Checks if FINE LOCATION and COARSE Location were granted
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            makeText(this, "Permissão negada", LENGTH_SHORT).show();
            return;
        }

        //Starts requesting location updates
        if (canGetLocation) {
            if (isGPS) {
                lm.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            } else if (isNetwork) {
                // from Network Provider

                lm.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            }
        } else {
            makeText(this, "Não é possível obter a localização", LENGTH_SHORT).show();
        }


    }

   /* private void getMarkers(){

        mDatabase.child("location").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        if (dataSnapshot.getValue() != null)
                            getAllLocations((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private void getAllLocations(Map<String,Object> locations) {




        for (Map.Entry<String, Object> entry : locations.entrySet()){

            Date newDate = new Date(Long.valueOf(entry.getKey()));
            Map singleLocation = (Map) entry.getValue();
            LatLng latLng = new LatLng((Double) singleLocation.get("latitude"), (Double)singleLocation.get("longitude"));
            addGreenMarker(newDate, latLng);

        }


    }

    private void addGreenMarker(Date newDate, LatLng latLng) {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(dt.format(newDate));
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pneu));
       // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(markerOptions);

         /*LocationData locationData = new LocationData(location.getLatitude(), location.getLongitude());
        mDatabase.child("location").child(String.valueOf(new Date().getTime())).setValue(locationData);*/

        /*LocationData locationData = new LocationData(latLng.latitude, latLng.longitude);
        mDatabase.child("location").child(String.valueOf(new Date().getTime())).setValue(locationData);

        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15).target(markerOptions.getPosition()).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }*/

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onMapClick(final LatLng latLng) {


        //Mostrar na tela a posição latitude e logitude onde eu clicar no mapa
        //Toast.makeText(getBaseContext(), "Localização " + latLng.toString(), Toast.LENGTH_SHORT).show();


        currentLocationLatLong = new LatLng(latLng.latitude, latLng.longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocationLatLong);
        markerOptions.title("Localização atual");
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        //currentLocationMaker = mMap.addMarker(markerOptions);
        currentLocationMaker = mMap.addMarker(markerOptions);





        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salvando uma nova borracharia");
        builder.setMessage("Tem certeza que deseja salvar uma nova borracharia?");
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //adapter.notifyDataSetChanged();
                //listaDeCursos.remove(position);

                //*****************************************************************************************
                Toast toast = makeText(getBaseContext(), "NOVA BORRACHARIA SALVA", LENGTH_LONG);

// Retorna a View padrão da mensagem
                View toastView = toast.getView();

// Para customizar o texto da mensagem obter a View do componente do texto
// que no caso é um TextView
                TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                toastMessage.setTextSize(17);
                toastMessage.setTextColor(Color.BLACK);

                // Centraliza a mensagem no centro da tela
                toast.setGravity(Gravity.CENTER,0 ,550 );


// Altera a cor de fundo da mensagem
                toastView.setBackgroundColor(Color.WHITE);

// Exibe a mensagem
                toast.show();
                //********************************************************************************************




                LocationData locationData = new LocationData(latLng.latitude, latLng.longitude);
                mDatabase.child("location").child(String.valueOf(new Date().getTime())).setValue(locationData);


            }



        });
        builder.setNegativeButton("NÃO", null);
        builder.show();


    }
}
