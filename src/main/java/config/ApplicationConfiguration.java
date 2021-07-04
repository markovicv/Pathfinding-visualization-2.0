package config;

import java.io.*;
import java.util.Properties;

public class ApplicationConfiguration {

    private static ApplicationConfiguration instance;
    private int applicationWidth;
    private int applicationHeight;


    public static ApplicationConfiguration getInstance(){
        if(instance==null){
            instance = new ApplicationConfiguration();
        }
        return instance;
    }

    private ApplicationConfiguration() {
        readProperties();
    }

    private void readProperties() {


        Properties properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream("application.properties")) {
            properties.load(fileInputStream);
            initFields(properties);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void initFields(Properties properties){
        applicationWidth = Integer.parseInt(properties.getProperty("application.width"));
        applicationHeight = Integer.parseInt(properties.getProperty("application.height"));
    }

    public int getApplicationWidth() {
        return applicationWidth;
    }

    public void setApplicationWidth(int applicationWidth) {
        this.applicationWidth = applicationWidth;
    }

    public int getApplicationHeight() {
        return applicationHeight;
    }

    public void setApplicationHeight(int applicationHeight) {
        this.applicationHeight = applicationHeight;
    }
}
