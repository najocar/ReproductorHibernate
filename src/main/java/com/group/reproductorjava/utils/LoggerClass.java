package com.group.reproductorjava.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerClass {
    private final Logger logger;
    private static final String filePath = "logs.txt";


    public LoggerClass(String className) {
        logger = Logger.getLogger(className);
    }

    public void info(String msg){
        logger.log(Level.INFO, msg);
        writeLog(Level.INFO, msg);
    }

    public void servere(String msg){
        logger.log(Level.SEVERE, msg);
        writeLog(Level.SEVERE, msg);
    }

    public void warning(String msg){
        logger.log(Level.WARNING, msg);
        writeLog(Level.WARNING, msg);
    }

    private void errorToLoggin(String msg) {
        logger.log(Level.WARNING, msg);
    }

    private void writeLog(Level typeLog, String msg) {
        File file = new File(filePath);
        try(FileWriter writer = new FileWriter(file, true)){
            writer.write(typeLog + " / " + LocalDate.now() + " / " + LocalTime.now().withNano(0) + " : " + msg + "\n");

        } catch (IOException err) {
            errorToLoggin("Error to write or overwrite in logs.txt file");
            errorToLoggin(err.getMessage());

        } catch (Exception err) {
            errorToLoggin(err.getMessage());

        }
    }
}
