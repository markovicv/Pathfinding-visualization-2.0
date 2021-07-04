package config;

import java.io.*;
import java.util.Properties;

public class ApplicationConfiguration {

    private static ApplicationConfiguration instance;
    private int applicationWidth;
    private int applicationHeight;
    private int nodeMinWidth;
    private int nodeMaxWidth;
    private int nodeDefaultWidth;
    private int rowNumber;


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
        nodeMaxWidth = Integer.parseInt(properties.getProperty("application.node.max_width"));
        nodeMinWidth = Integer.parseInt(properties.getProperty("application.node.min_width"));
        nodeDefaultWidth = Integer.parseInt(properties.getProperty("application.node.default_width"));
        rowNumber = Integer.parseInt(properties.getProperty("application.node.row_number"));
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

    public int getNodeMinWidth() {
        return nodeMinWidth;
    }

    public void setNodeMinWidth(int nodeMinWidth) {
        this.nodeMinWidth = nodeMinWidth;
    }

    public int getNodeMaxWidth() {
        return nodeMaxWidth;
    }

    public void setNodeMaxWidth(int nodeMaxWidth) {
        this.nodeMaxWidth = nodeMaxWidth;
    }

    public int getNodeDefaultWidth() {
        return nodeDefaultWidth;
    }

    public void setNodeDefaultWidth(int nodeDefaultWidth) {
        this.nodeDefaultWidth = nodeDefaultWidth;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
