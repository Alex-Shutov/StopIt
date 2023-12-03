package com.example.utils;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;

public class ActiveWindowInfo {
    public static String getActiveWindow() {
        // Загрузка библиотеки User32
        User32 user32 = User32.INSTANCE;

        // Получение идентификатора окна текущего активного процесса
        char[] buffer = new char[1024];
        user32.GetWindowText(user32.GetForegroundWindow(), buffer, buffer.length);

         return Native.toString(buffer);
    }
}
