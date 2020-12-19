package com.gouravjat.customviewmapmarker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.appolica.interactiveinfowindow.InfoWindow
import com.appolica.interactiveinfowindow.InfoWindowManager
import com.appolica.interactiveinfowindow.InfoWindowManager.WindowShowListener
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var mMap: GoogleMap
    private var itemList: List<String> = getStubData();
    private lateinit var infoWindowManager: InfoWindowManager

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        itemList = getStubData()


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as MapInfoWindowFragment

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            /*ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission
                .ACCESS_COARSE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);*/
            this.requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 1
            )
        } else {
            mapFragment.getMapAsync(this)
        }


        infoWindowManager = mapFragment.infoWindowManager()
        infoWindowManager.setHideOnFling(true)
        infoWindowManager.setWindowShowListener(object : WindowShowListener {
            override fun onWindowShowStarted(infoWindow: InfoWindow) {}
            override fun onWindowShown(infoWindow: InfoWindow) {}
            override fun onWindowHideStarted(infoWindow: InfoWindow) {
            }

            override fun onWindowHidden(infoWindow: InfoWindow) {
            }
        })

    }

    fun getStubData(): ArrayList<String> {
        var tempList : ArrayList<String> = ArrayList();
        tempList.add("One")
        tempList.add("Two")
        tempList.add("Three")
        tempList.add("Four")
        return tempList
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json))


        mMap.setOnMarkerClickListener(this)

        val location = LatLng(-34.0, 151.0)
        var marker = mMap.addMarker(MarkerOptions().position(location).title("Marker in Sydney"))

        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        marker.tag = itemList
    }

    override fun onMarkerClick(marker: Marker?): Boolean {

        var infoWindow: InfoWindow? = null

        val offsetX = resources.getDimension(R.dimen.marker_offset_x).toInt()
        val offsetY = resources.getDimension(R.dimen.marker_offset_y).toInt();

        val markerSpec: InfoWindow.MarkerSpecification =            InfoWindow.MarkerSpecification(offsetX, offsetY)

        val recyclerWindow =            InfoWindow(marker, markerSpec, MarkerListFragment(marker?.tag as ArrayList<String>))
        infoWindow = recyclerWindow
        if (infoWindow != null) {
            infoWindowManager.toggle(infoWindow, true)
        }
        return true
    }


}