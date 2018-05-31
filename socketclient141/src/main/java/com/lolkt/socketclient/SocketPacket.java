package com.lolkt.socketclient;

import com.lolkt.socketclient.util.CharsetNames;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SocketPacket
 * AndroidSocketClient
 * Created by vilyever on 2015/9/15.
 * Feature:
 */
public class SocketPacket {
    private final SocketPacket self = this;

    /**
     * sending this message every heartbeat to make sure current client alive
     */
    public static final byte[] DefaultHeartBeatMessage = "$HB$".getBytes(Charset.forName(CharsetNames.UTF_8));

    /**
     * sending DefaultPollingQueryMessage will response DefaultPollingResponseMessage immediately
     * sending DefaultPollingResponseMessage will response nothing
     */
    public static final byte[] DefaultPollingQueryMessage = "$PQ$".getBytes(Charset.forName(CharsetNames.UTF_8));
    public static final byte[] DefaultPollingResponseMessage = "$PR$".getBytes(Charset.forName(CharsetNames.UTF_8));

    private static final AtomicInteger IDAtomic = new AtomicInteger();

    /* Constructors */
    public SocketPacket(byte[] data) {
        this.ID = IDAtomic.getAndIncrement();
        this.data = Arrays.copyOf(data, data.length);
        this.message = null;
    }

    public SocketPacket(String message) {
        this.ID = IDAtomic.getAndIncrement();
        this.message = message;
        this.data = null;
    }

    public static SocketPacket newInstanceWithBytes(byte[] data) {
        return new SocketPacket(data);
    }

    public static SocketPacket newInstanceWithString(String message) {
        return new SocketPacket(message);
    }

    /* Public Methods */

    /* Properties */
    /**
     * ID, unique
     */
    private final int ID;
    public int getID() {
        return this.ID;
    }

    /**
     * string data
     */
    private final String message;
    public String getMessage() {
        return this.message;
    }

    /**
     * bytes data
     */
    private final byte[] data;
    public byte[] getData() {
        return this.data;
    }

}