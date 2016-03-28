package com.tilepay.core.service;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class AppDataDirectoryService {

    public String getDataDirectory() {
        String userHome = System.getProperty("user.dir");

        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN"))
            userHome = System.getenv("APPDATA");
        else if (OS.contains("MAC"))
            userHome = System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application " + "Support";
        else if (OS.contains("NUX"))
            userHome = System.getProperty("user.home");

        String tilepayDirPath = userHome + File.separator + "tilepay" + File.separator;
        File tilepayDir = new File(tilepayDirPath);

        if (!tilepayDir.exists()) {
            tilepayDir.mkdir();
        }

        return tilepayDirPath;
    }
}
