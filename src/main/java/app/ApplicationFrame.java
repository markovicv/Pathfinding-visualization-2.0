package app;

import config.ApplicationConfiguration;

import javax.swing.*;

public class ApplicationFrame extends JFrame {

    public ApplicationFrame(){
        super("Pathfinding visualization");
        initJFrameProperties();

    }
    private void initJFrameProperties(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ApplicationConfiguration.getInstance().getApplicationWidth(),
                ApplicationConfiguration.getInstance().getApplicationHeight());
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}
