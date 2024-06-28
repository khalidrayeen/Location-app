package com.example.locationapp

import android.content.Context
import android.os.Bundle
import android.Manifest
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewmodel :LocationViewModel = viewModel()
            LocationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                         MyApp(viewmodel)
                }
            }
        }
    }
}

@Composable
fun MyApp(viewmodel: LocationViewModel){
    val context_3 = LocalContext.current
    val LocationUtil_3 = LocationUtilss(context_3)

    LocationDisplay(LocationUtils = LocationUtil_3, viewmodel, context = context_3 )

}


@Composable

fun LocationDisplay(LocationUtils:LocationUtilss ,viewmodel : LocationViewModel, context :Context){

    val location_4 = viewmodel.location.value

    val address = location_4?.let {
        LocationUtils.reverseGeoCodeLocation(location_4)
    }




    val requestPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {

            permissionss ->

            if(permissionss[Manifest.permission.ACCESS_FINE_LOCATION] == true
                && permissionss[Manifest.permission.ACCESS_COARSE_LOCATION ]== true){

                //i have the access

              LocationUtils.requestLocationUpdates(viewmodel_4 = viewmodel)



            }
            else{

               val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(

                   context as MainActivity ,
                   Manifest.permission.ACCESS_FINE_LOCATION
               ) || ActivityCompat.shouldShowRequestPermissionRationale(

                   context as MainActivity ,
                   Manifest.permission.ACCESS_FINE_LOCATION
               )

                if(rationaleRequired == false){
                    Toast.makeText(context , "Location is required for this feature",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(context , "Location is required Please enable it in android settings",Toast.LENGTH_LONG).show()
                }
            }


    })

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
          if(location_4!=null){

              Text("Location ${location_4.latitude} ${location_4.longitude} \n $address")
          }else {
              Text("Location not available")
          }

        Button(onClick = { if(LocationUtils.hasLocation(context)){

            LocationUtils.requestLocationUpdates(viewmodel)

            // permission already granted
        } else{

            requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION ,  Manifest.permission.ACCESS_FINE_LOCATION ))
        }



        }) {

            Text("GET LOCATION")

        }


    }

}
