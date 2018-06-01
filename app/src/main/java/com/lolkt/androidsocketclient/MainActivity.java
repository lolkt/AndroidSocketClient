package com.lolkt.androidsocketclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.lolkt.socket.LotteryClient;
import com.lolkt.socket.SocketMessageUtils;
import com.lolkt.socketclient.SocketClient;

public class MainActivity extends Activity {
    final MainActivity self = this;


    private SocketClient client;

    LotteryClient testClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testClient = new LotteryClient();
        client = testClient.initClient();
        client.connect();
        String serviceIp = BuildConfig.SERVER_IP;
        String servicePort = BuildConfig.SERVER_PORT;

//                if (BuildConfig.DEBUG) {
//                    serviceIp = etService.getText().toString();
//                    servicePort = etPort.getText().toString();
//                }

        //发送消息之前 先关闭心跳 防止心跳数据覆盖messsage
        client.stopHearBeat();

        String deviceId = "123456";
        String wifiName = "";
        String wifiPasswd = "'";

        sendWifiData(deviceId, wifiName, wifiPasswd);
    }

    /**
     * 发送wifi账号密码
     *
     * @param deviceId   设备id
     * @param wifiName   wifi名称
     * @param wifiPasswd wifi密码
     */
    private void sendWifiData(String deviceId, String wifiName, String wifiPasswd) {
        String wifi = "\"" + wifiName + "\",\"" + wifiPasswd + "\"";
        Log.i("wifi===  ", wifi);
        client.sendBytes(SocketMessageUtils.getInstance().getMessage(1, deviceId, wifi));
    }

}
