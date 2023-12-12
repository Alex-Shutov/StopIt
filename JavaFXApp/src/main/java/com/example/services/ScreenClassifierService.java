package com.example.services;

import com.example.utils.ActiveWindowInfo;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class ScreenClassifierService {
    static double UNSAFE_CLASSIFICATION_THRESHOLD = 0;
    private static ScreenOverlayService screenOverlay = new ScreenOverlayService();
    public static void runClassificationScript() throws IOException {

        String tempDir = System.getProperty("java.io.tmpdir");

        // Копируем скрипт из ресурсов во временную директорию
        Path scriptPath = Paths.get(tempDir, "classifyScreenshot.py");
        Files.copy(ScreenClassifierService.class.getResourceAsStream("/scripts/classifyScreenshot.py"), scriptPath, StandardCopyOption.REPLACE_EXISTING);

        // Запуск скрипта как отдельного процесса
        ProcessBuilder pb = new ProcessBuilder("python", scriptPath.toString());
        pb.directory(new File(tempDir));
        Process p = pb.start();

        double classificationResult = getScriptOutput(p);
        System.out.println(classificationResult);
        KillActiveWindow(classificationResult);

        // Удаление временного скрипта
        Files.deleteIfExists(scriptPath);


        p.destroy();

    }

    public static double getScriptOutput(Process p) throws IOException {

        // Чтение вывода скрипта
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

        // Получение числа из вывода
        String output = stdInput.readLine();
        double number = Double.parseDouble(output);

        return number;

    }

    public static void KillActiveWindow(double classificationResult){
        if (classificationResult > UNSAFE_CLASSIFICATION_THRESHOLD) {
            Platform.runLater(() -> {
                new ActiveWindowInfo().closeActiveWindow();
                screenOverlay.blurScreen();
            });
        }
    }


}
