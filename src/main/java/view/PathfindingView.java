package view;

import config.ApplicationConfiguration;
import model.Node;
import model.NodeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class PathfindingView extends JPanel implements MouseMotionListener {

    Node board[][] = new Node[ApplicationConfiguration.getInstance().getRowNumber()][ApplicationConfiguration.getInstance().getRowNumber()];
    private NodePaintComponent nodePaintComponent = new NodePaintComponent();

    Node mouseHoveringNode;


    public PathfindingView() {
        initBoard();
        initViewAcordingToBoard();
        initViewProperties();
    }


    private void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                board[i][j] = new Node(i, j, NodeType.EMPTY);
            }
        }
    }

    private void initViewAcordingToBoard() {
        int cellWidth = ApplicationConfiguration.getInstance().getNodeDefaultWidth();
        int width = board.length * cellWidth;
        int height = board[0].length * cellWidth;

        Dimension viewSize = new Dimension(width, height);
        setMinimumSize(viewSize);
        setPreferredSize(viewSize);
        setMaximumSize(viewSize);
    }

    private void initViewProperties() {
        setFocusable(true);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics graph = g.create();
        nodePaintComponent.paintNodes(graph, board, ApplicationConfiguration.getInstance().getNodeDefaultWidth());
        graph.dispose();

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    /*
        used for painting a node we are currently hovering over
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        int row = mouseEvent.getX() / ApplicationConfiguration.getInstance().getNodeDefaultWidth();
        int col = mouseEvent.getY() / ApplicationConfiguration.getInstance().getNodeDefaultWidth();
        int cellWidth = ApplicationConfiguration.getInstance().getNodeDefaultWidth();

        Node newSelectedNode = board[row][col];


        if (newSelectedNode.getNodeType() != NodeType.EMPTY)
            return;

        newSelectedNode.setNodeType(NodeType.SELECTED);

        if (mouseHoveringNode != null) {
            mouseHoveringNode.setNodeType(NodeType.EMPTY);
            repaint(mouseHoveringNode.getRow()*cellWidth,mouseHoveringNode.getCol()*cellWidth,cellWidth,cellWidth);
        }

        mouseHoveringNode = newSelectedNode;
        repaint(mouseHoveringNode.getRow()*cellWidth,mouseHoveringNode.getCol()*cellWidth,cellWidth,cellWidth);

    }
}
