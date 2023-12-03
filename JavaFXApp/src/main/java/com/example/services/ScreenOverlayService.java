package com.example.services;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ScreenOverlayService {

    private static final int GWL_EXSTYLE = -20;
    private static final int WS_EX_LAYERED = 0x80000;
    private static final int LWA_COLORKEY = 1;

    private WinDef.HWND hwnd;
    private boolean isOverlayVisible = false;

    public ScreenOverlayService() {
    }

    public void showOverlay() {

    }

    public void hideOverlay() {

    }

    private void setWindowExStyle(WinDef.HWND hwnd, int exStyle) {
        User32.INSTANCE.SetWindowLong(hwnd, GWL_EXSTYLE, exStyle);
    }

    private void setLayeredWindowAttributes(WinDef.HWND hwnd, int crKey, byte alpha, int dwFlags) {
        User32.INSTANCE.SetLayeredWindowAttributes(hwnd, crKey, alpha, dwFlags);
    }

    public void blurScreen() {
        try {
            System.out.println("!!!");
            // Копируем exe файл во временный каталог
            File tempDir = new File(System.getProperty("java.io.tmpdir"));
            File exeFile = new File(tempDir, "overlay.exe");
            copyResourceToFile("/scripts/overlay.exe", exeFile);

            // Создаем процесс для запуска exe файла
            ProcessBuilder processBuilder = new ProcessBuilder(exeFile.getAbsolutePath());
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyResourceToFile(String resourcePath, File destination) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            Files.copy(inputStream, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

//    public interface User32 extends WinUser {
//        User32 INSTANCE = Native.load("user32", User32.class);
//
//        boolean SetLayeredWindowAttributes(WinDef.HWND hwnd, int crKey, byte bAlpha, int dwFlags);
//    }
}