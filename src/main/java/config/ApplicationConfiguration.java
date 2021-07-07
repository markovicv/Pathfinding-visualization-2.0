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
    private String applicationName;
    private String algorithmStillWorkingError;
    private String startNodeNotDefinedError;
    private String endNodeNotDefinedError;
    private String algorithmNotClearedError;


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
        applicationName = properties.getProperty("application.name");
        algorithmStillWorkingError = properties.getProperty("application.error.working");
        startNodeNotDefinedError = properties.getProperty("application.error.start");
        endNodeNotDefinedError = properties.getProperty("application.error.end");
        algorithmNotClearedError = properties.getProperty("application.error.algorithm");
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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getAlgorithmStillWorkingError() {
        return algorithmStillWorkingError;
    }

    public void setAlgorithmStillWorkingError(String algorithmStillWorkingError) {
        this.algorithmStillWorkingError = algorithmStillWorkingError;
    }

    public String getStartNodeNotDefinedError() {
        return startNodeNotDefinedError;
    }

    public void setStartNodeNotDefinedError(String startNodeNotDefinedError) {
        this.startNodeNotDefinedError = startNodeNotDefinedError;
    }

    public String getEndNodeNotDefinedError() {
        return endNodeNotDefinedError;
    }

    public void setEndNodeNotDefinedError(String endNodeNotDefinedError) {
        this.endNodeNotDefinedError = endNodeNotDefinedError;
    }

    public String getAlgorithmNotClearedError() {
        return algorithmNotClearedError;
    }

    public void setAlgorithmNotClearedError(String algorithmNotClearedError) {
        this.algorithmNotClearedError = algorithmNotClearedError;
    }
}
