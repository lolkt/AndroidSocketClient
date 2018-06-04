# AndroidSocketClient
socket client server简易封装

## Import
[JitPack](https://jitpack.io/)

Add it in your project's build.gradle at the end of repositories:

```gradle
repositories {
  // ...
  maven { url "https://jitpack.io" }
}
```

Step 2. Add the dependency in the form

```gradle
dependencies {
  compile 'com.lolkt:socketclient:1.0.0'
}
```


## Usage
### app模块下包含简单的使用demo
### 模拟了向服务端发送wifi账号密码，对消息体做了封装
### 返回结果可以通过RxBus等 返回主页面
 
### 远程端连接信息配置
```java
    localSocketClient = new SocketClient(BuildConfig.RemoteIP, 
    BuildConfig.RemotePort);
   
    localSocketClient.setConnectionTimeout(15 * 1000); // 连接超时时长，单位毫秒
```

### 默认String编码配置
```java
    /**
     * 设置自动转换String类型到byte[]类型的编码
     * 如未设置（默认为null），将不能使用{@link SocketClient#sendString(String)}发送消息
     * 如设置为非null（如UTF-8），在接受消息时会自动尝试在接收线程（非主线程）将接收的byte[]数据依照编码转换为String，在{@link SocketResponsePacket#getMessage()}读取
     */
    localSocketClient.setCharsetName(CharsetNames.UTF_8);// 设置编码为UTF-8
```

### 固定心跳包配置
```java
    /**
     * 设置自动发送的心跳包信息
     */
   
    localSocketClient.setHeartBeatMessage(HeartBeat); // 设置自动发送心跳包的间隔时长，单位毫秒
    localSocketClient.setHeartBeatInterval(3 * 1000);// 设置允许自动发送心跳包，此值默认为false
```


## License
[Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)
