package com.lolkt.socket;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;


import com.lolkt.socketclient.SocketClient;
import com.lolkt.socketclient.SocketResponsePacket;
import com.lolkt.socketclient.util.CharsetNames;
import com.vilyever.androidsocketclient.BuildConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * TestClient
 * Created by vilyever on 2016/7/26.
 * Feature:
 */
public class LotteryClient {
    private final String HeartBeat = "keepAlive";

    /* Properties */
    private SocketClient localSocketClient;

    public static List wifiList = new ArrayList();

    public SocketClient initClient() {
        if (localSocketClient == null) {
            localSocketClient = new SocketClient(BuildConfig.RemoteIP, BuildConfig.RemotePort);
            localSocketClient.setCharsetName(CharsetNames.UTF_8);

            localSocketClient.setConnectionTimeout(15 * 1000); // 连接超时时长，单位毫秒

            localSocketClient.setHeartBeatMessage(HeartBeat);
            localSocketClient.setHeartBeatInterval(3 * 1000);

            localSocketClient.disableRemoteNoReplyAliveTimeout();//禁用自动断开
            localSocketClient.registerSocketDelegate(new SocketClient.SocketDelegate() {
                @Override
                public void onConnected(SocketClient client) {
                    Log.i("socket", "localSocketClient onConnected");
//                    getLocalSocketClient().send("再见");
                    if (wifiList != null) wifiList.clear();
                }

                @Override
                public void onDisconnected(SocketClient client) {
                    Log.i("socket", "localSocketClient onDisconnected");
                    // 连接断开后 自动重连
                    new Handler().postDelayed(() -> client.connect(), 3000);

                }

                @Override
                public void onResponse(SocketClient client, SocketResponsePacket responsePacket) {
                    Log.i("socket", "localSocketClient onResponse " + responsePacket.getMessage());
                    String message = responsePacket.getMessage();
//                    +CWLAP:("DIRECT-dd-HP M427 LaserJet")
                    if (!TextUtils.isEmpty(message)) {
                        Log.i("socket", "onResponse2  " + message);
                       //在此处接收消息

                    }

                }
            });

            localSocketClient.registerSocketHeartBeatDelegate(new SocketClient.SocketHeartBeatDelegate() {
                @Override
                public void onHeartBeat(SocketClient socketClient) {
                    Log.i("socket", "localSocketClient onHeartBeat");
                }
            });

            localSocketClient.registerSocketPollingDelegate(new SocketClient.SocketPollingDelegate() {
                @Override
                public void onPollingQuery(SocketClient socketClient, SocketResponsePacket pollingQueryPacket) {
                    Log.i("socket", "localSocketClient onPollingQuery " + pollingQueryPacket.getMessage());
                }

                @Override
                public void onPollingResponse(SocketClient socketClient, SocketResponsePacket pollingResponsePacket) {
                    Log.i("socket", "localSocketClient onPollingResponse " + pollingResponsePacket.getMessage());
                }
            });
        }
        return localSocketClient;
    }
}