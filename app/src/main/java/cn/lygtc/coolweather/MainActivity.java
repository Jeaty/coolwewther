package cn.lygtc.coolweather;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BDLocationListener {
    private LocationClient locationClient ;
    private TextView stringView;
    private TextView countryView;
    private TextView cityView;
    private TextView provinceView;
    private TextView districtView;
    private TextView latlngView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationClient = new LocationClient(getApplicationContext() );
        LocationClientOption option = new LocationClientOption() ;
        option .setLocationMode(LocationClientOption .LocationMode .Hight_Accuracy );
        option .setIsNeedAddress(true );
        locationClient .setLocOption(option );
        locationClient .registerLocationListener(this);
        List <String > permissionList = new ArrayList<>() ;
        if (ContextCompat.checkSelfPermission(this ,Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED){
            permissionList .add( Manifest.permission.ACCESS_FINE_LOCATION );
        }
        if (ContextCompat.checkSelfPermission(this ,Manifest.permission.READ_PHONE_STATE)!= PackageManager .PERMISSION_GRANTED){
            permissionList .add( Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this ,Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager .PERMISSION_GRANTED ){
            permissionList .add( Manifest.permission.WRITE_EXTERNAL_STORAGE );
        }
        if (!permissionList .isEmpty()){
            String[] permission = permissionList .toArray(new String[permissionList.size()] );
            ActivityCompat.requestPermissions(this ,permission ,1 ) ;
        }else {
            requestLocation();
        }
        handler = new Handler();
        public void handleMessage(Message message){
        Bundle location = message.getdata();
        countryView .setText("国别:" +location .getString("country") );
        countryView .setText("国别:" +location .getString("country") );
        countryView .setText("国别:" +location .getString("country") );
        countryView .setText("国别:" +location .getString("country") );
        countryView .setText("国别:" +location .getString("country") );
        countryView .setText("国别:" +location .getString("country") );
    }
    }
    private void requestLocation (){
        locationClient .start() ;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==1){
            if (grantResults .length >0){
                for (int result:grantResults ){
                    if (result !=PackageManager .PERMISSION_GRANTED ){
                        Toast .makeText(this ,"必须同意所有权限才能使用本APP",Toast .LENGTH_LONG ).show() ;
                        finish() ;
                        return ;
                    }
                }
                requestLocation() ;
            }
        }
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation .getLocType() ==BDLocation.TypeGpsLocation ||bdLocation .getLocType() ==BDLocation.TypeNetWorkLocation ){
            Bundle data = new Bundle() ;
            data.putString("country",bdLocation .getCountry() );
            data.putString("province",bdLocation .getProvince() );
            data.putString("city",bdLocation .getCity() );
            data.putString("district",bdLocation .getDistrict() );
            data.putString("street",bdLocation .getStreet() );
            data.putString("latlng","("+ bdLocation .getLatitude() + "," + bdLocation .getLongitude() + ")");
            Message message = new Message() ;
            message .setData(data ) ;
            handler.sendMessage(message );
        }

    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }
}
