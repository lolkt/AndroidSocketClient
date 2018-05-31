package com.lolkt.socket;

import android.util.Log;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author lolkt
 * @time 2018-1-17$
 * @descrition: socket数据处理
 */

public class SocketMessageUtils {
    private static SocketMessageUtils instance;

    private SocketMessageUtils() {

    }

    public static SocketMessageUtils getInstance() {
        if (instance == null) {
            instance = new SocketMessageUtils();
        }
        return instance;
    }

    /**
     * @param type 1 wifi 2service
     * @param mid
     * @param info
     * @return
     */
    public byte[] getMessage(int type, String mid, String info) {

        byte[] deviceId = new byte[0];

        //数据
        byte[] message = new byte[0];
        try {
            message = info.getBytes("UTF-8");
            deviceId = mid.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("--->", new String(message));
        byte[] data = new byte[message.length + 14];//总长度
        Arrays.fill(data, (byte) 0x00);
        //  首位是‘[’
        data[0] = 0x5b;
        if (type == 1) {
            data[1] = 0x57;  //  "W"
            data[2] = 0x46;  //  "F"
        } else if (type == 2) {
            data[1] = 0x53;  //  S
            data[2] = 0x45;  //  E
        }
        System.arraycopy(deviceId, 0, data, 3, deviceId.length);
        //数据长度
        data[11] = (byte) message.length;
        System.arraycopy(message, 0, data, 12, message.length);

        //添加校验 倒数第二位是异或操作
        //check(data);
        data[data.length - 2] = (byte) 0x31;
        //最后一位 ']'
        data[data.length - 1] = 0x5d;
        Log.i("", "===  " + bytesToHexString(data));
        return data;
    }

    // 异或校验
    byte BCC_Check(byte[] data) {
        byte temp = 0;
        for (int i = 0; i < data.length - 2; i++) {
            temp ^= data[i];
        }
        return temp;
    }

    public void check(byte[] data) {
        for (int i = 0; i < data.length - 2; i++) {
            data[data.length - 2] ^= data[i];
        }
    }


    public int hexStringToInt(String str) {
        return Integer.parseInt(str, 16);/*16 为要转化的数的基数，这里表示str是16进制表示的字符串*/
    }

    public String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    public static byte[] encodeUINT16(int param) {
        byte[] bytes = ByteBuffer.allocate(4).putInt(param ^ 0xADAD).array();

        byte[] encodedBytes = new byte[2];
        encodedBytes[0] = bytes[3];
        encodedBytes[1] = bytes[2];
        return encodedBytes;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
