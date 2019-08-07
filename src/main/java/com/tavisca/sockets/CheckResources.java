package com.tavisca.sockets;

import java.io.FileInputStream;
import java.io.IOException;

public class CheckResources {
    private static String filename;
    public CheckResources(String string) {
        filename = string;
    }

    public static int CheckFile() {
        try (FileInputStream fileInputStream = new FileInputStream(filename)) {
        } catch (IOException e) {
            return 0;
        }
        return 1;
    }
}
