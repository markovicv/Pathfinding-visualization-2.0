package view;

import javax.swing.*;
import java.awt.*;

public class CommandView extends JPanel {

    private String[] algorithms = {"A*","Dijkstra","BFS","DFS"};
    private String[] speedOptions = {"slow","medium","fast"};
    private JComboBox algoList;
    private JComboBox speedList;
    private JButton btnVisualize = new JButton("Visualize");
    private JButton btnClear = new JButton("Clear");
    private JButton btnNewBoard = new JButton("new board");
    private PathfindingView pathfindingView;

    public CommandView(PathfindingView pathfindingView){
        this.pathfindingView = pathfindingView;
        this.setLayout(new FlowLayout());
        this.algoList = new JComboBox(algorithms);
        this.speedList = new JComboBox(speedOptions);
        initView();
        initListeners();

    }
    private void initView(){
        btnVisualize.setPreferredSize(new Dimension(120,25));
        btnClear.setPreferredSize(new Dimension(120,25));
        btnNewBoard.setPreferredSize(new Dimension(120,25));
        this.algoList.setFocusable(false);
        this.btnVisualize.setFocusable(false);
        this.btnClear.setFocusable(false);
        this.speedList.setFocusable(false);
        this.btnNewBoard.setFocusable(false);
        this.add(new JLabel("Pathfinding algorithm: "));
        this.add(algoList);
        this.add(new JLabel("Speed: "));
        this.add(speedList);
        this.add(btnVisualize);
        this.add(btnClear);
        this.add(btnNewBoard);
    }
    private void initListeners(){
        btnVisualize.addActionListener(actionEvent->{
            pathfindingView.startPathfindingAlgorithm(algoList.getSelectedItem().toString(),10);

        });
//        btnClear.addActionListener(actionEvent->{
//                visualization.clearAlgo();
//            else{
//                JOptionPane.showMessageDialog(this,Constants.ALGO_WORKING_ERROR,"Error",JOptionPane.ERROR_MESSAGE);
//            }
//        });
//        btnNewBoard.addActionListener(actionEvent->{
//                visualization.clearBoard();
//            else{
//                JOptionPane.showMessageDialog(this,Constants.ALGO_WORKING_ERROR,"Error",JOptionPane.ERROR_MESSAGE);
//            }
//        });


    }


//    private int generatePathfindingSpeed(String speed){
//        if(speed.equals("slow"))
//            return Constants.SLOW;
//        else if(speed.equals("medium"))
//            return Constants.MEDIUM;
//        else
//            return Constants.FAST;
//    }


}
