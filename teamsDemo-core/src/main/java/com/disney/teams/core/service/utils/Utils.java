package com.disney.teams.core.service.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utils {

    static public String getHostIp() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
