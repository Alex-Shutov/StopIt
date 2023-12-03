package com.example.services;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ScreenCreatorService {


    private static final String SCREENSHOT_FOLDER = System.getProperty("user.home") + "\\AppData\\Roaming\\StopIt";
    private static final String SCREENSHOT_FORMAT = "yyyyMMdd_HHmmss";

    private static ScheduledExecutorService scheduler;

    private static void takeScreenshot(int i) {
        try {
            ScreenCreatorService.createScreenshotFolder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String fileName = "screenshot_" + dateFormat.format(new Date()) + ".png";

            // Полный путь к новому файлу
            String newPath = ScreenCreatorService.SCREENSHOT_FOLDER + File.separator + fileName;

            // Удаление предыдущего скриншота, если он существует
            File previousScreenshot = new File(newPath);
            if (previousScreenshot.exists()) {
                previousScreenshot.delete();
            }

            // Создание нового скриншота
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);
            ImageIO.write(screenCapture, "png", previousScreenshot);

            System.out.println("Screenshot saved: " + previousScreenshot.getAbsolutePath());

            if(i+1==5){
                try {
                    System.out.println(123);
                    ScreenClassifierService.runClassificationScript();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void DeleteScreenshots(){
        File screenshotFolder = new File(SCREENSHOT_FOLDER);
        System.out.println("Start deleting");
        if (screenshotFolder.exists() && screenshotFolder.isDirectory()) {
            File[] screenshots = screenshotFolder.listFiles((dir, name) -> name.endsWith(".png"));

            if (screenshots != null) {
                for (File screenshot : screenshots) {
                    screenshot.delete();
                }
                System.out.println("Delete complete");
            }
        }
    }

    private static void createScreenshotFolder() {
        File folder = new File(SCREENSHOT_FOLDER);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("Screenshot folder created: " + folder.getAbsolutePath());
            } else {
                System.err.println("Failed to create screenshot folder.");
            }
        }
    }

    private static void stopScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();

        }
    }


    public static void startScreenshotCreation() {

        stopScheduler(); // Останавливаем предыдущий планировщик

        scheduler = Executors.newScheduledThreadPool(1);

        AtomicInteger screenshotCounter = new AtomicInteger(0);
        scheduler.scheduleAtFixedRate(() -> {
            ScreenCreatorService.DeleteScreenshots();

            for (int i = 0; i < 5; i++) {
                AtomicInteger integer = new AtomicInteger(i);
                ScheduledFuture<?> x = scheduler.schedule(()->{
                    ScreenCreatorService.takeScreenshot(integer.get());
                }, i * 2, TimeUnit.SECONDS);

            }


        }, 0, 30, TimeUnit.SECONDS);
    }

    public static void stopScreenshotCreation(){
        ScreenCreatorService.stopScheduler();
        ScreenCreatorService.DeleteScreenshots();
    }

}
