package com.example.utils;

import com.example.models.browsers.ChromiumBrowser;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import org.jetbrains.annotations.NotNull;
//import com.sun.jna.platform.win32.WinUser
import com.sun.jna.platform.win32.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.stream.Stream;

import static com.sun.jna.platform.win32.WinUser.KEYBDINPUT.KEYEVENTF_KEYUP;
import static com.sun.jna.platform.win32.WinUser.VK_CONTROL;

public class ActiveWindowInfo {

    private long browserPid;
    @NotNull
    public void findBrowserProcess() {
        Stream<ProcessHandle> processHandles = ProcessHandle.allProcesses();

        processHandles.filter(p -> {
                    String name = p.info().command().orElse(null);
                    return name != null &&
                            (name.contains("chrome.exe") ||
                                    name.contains("brave.exe") ||
                                    name.contains("firefox.exe") ||
                                    name.contains("opera.exe"));
                })
                .findFirst() //берем первый найденный браузер
                .ifPresent(p -> this.browserPid = p.pid());
    }

    public void closeActiveWindow(){
        try {
            Robot robot = new Robot();

            // Имитация нажатия Ctrl+W
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_W);
            robot.keyRelease(KeyEvent.VK_W);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
