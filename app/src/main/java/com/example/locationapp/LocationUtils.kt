package com.example.locationapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale



class LocationUtils
@SuppressLint("MissingPermission")
class LocationUtilss (var context : Context){
        //This helps to get the data of locaton like longitude and latidude from google satelite , so we storing everything in "_fusedlocationupdates"
    private val _fusedlocationClient : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)


    //NOW creating funtion to update the data of our data class " LocationData"
    fun requestLocationUpdates(viewmodel_4 : LocationViewModel){   // created LocationViewModel object
        val LocationCallBack =object : LocationCallback(){                                                  //storing Locationg callbacck in LocationCallBack variable

            override fun onLocationResult(LocationResult: LocationResult) {                                 //overridng onLocationResult
                super.onLocationResult(LocationResult)
                LocationResult.lastLocation?.let{                                                           //unpacking the location result so that we can store it to out "Location Data class "

                    val location = LocationData( latitude = it.latitude , longitude = it.longitude)
                    viewmodel_4.updateLocation(location)                                                    //calling updatelocation funcction in LOcation view model class , where we are updating the values

                }
            }

        }

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        _fusedlocationClient.requestLocationUpdates(locationRequest,LocationCallBack , Looper.getMainLooper())

    }

    fun hasLocation(context: Context): Boolean {

        if(ContextCompat.checkSelfPermission(context , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(context , Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            return  true
        }

        else {
            return false
        }

    }

    fun reverseGeoCodeLocation( location : LocationData): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val coordinate = LatLng(location.latitude, location.longitude)
        val addresses: MutableList<Address>? =
            geocoder.getFromLocation(coordinate.latitude, coordinate.longitude, 1)

        return if (addresses?.isNotEmpty() == true)
            addresses[0].getAddressLine(0)
        else
            "address not found bro!"



    }

}