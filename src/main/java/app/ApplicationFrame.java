package app;

import config.ApplicationConfiguration;
import view.PathfindingView;

import javax.swing.*;
import java.awt.*;

public class ApplicationFrame extends JFrame {

    public ApplicationFrame(){
        super("Pathfinding visualization");
        initJFrameProperties();

    }
    private void initJFrameProperties(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ApplicationConfiguration.getInstance().getApplicationWidth(),
                ApplicationConfiguration.getInstance().getApplicationHeight());

        PathfindingView pathfindingView = new PathfindingView();
        JScrollPane scrollPane = new JScrollPane(pathfindingView);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}
