package com.howbuy.cc.base.util;


import org.apache.log4j.Logger;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 获取本机IP
 * Created by xinwei.cheng on 2015/6/2.
 */
@SuppressWarnings("unused")
public final class IpUtil {

    private static List<String> ipList = null;

    private static Logger logger = org.apache.log4j.Logger.getLogger(IpUtil.class);

    /**
     * 获取本机IP
     * @return ip的list
     */
    public static List<String> getIp(){
        if(ipList != null){
            return ipList;
        }
        List<String> ipList = new ArrayList<>();
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            logger.error(e.getMessage() , e);
            return new ArrayList<>();
        }
        InetAddress ip;
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                ip = (InetAddress) addresses.nextElement();
                if (ip != null && ip instanceof Inet4Address) {
                    String localIp = ip.getHostAddress();
                    ipList.add(localIp);
                }
            }
        }
        IpUtil.ipList = ipList;
        return ipList;
    }
}
