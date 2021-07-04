package view;

import config.ApplicationConfiguration;
import model.Node;
import model.NodeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PathfindingView extends JPanel implements MouseMotionListener, KeyListener, MouseListener {

    Node board[][] = new Node[ApplicationConfiguration.getInstance().getRowNumber()][ApplicationConfiguration.getInstance().getRowNumber()];
    private NodePaintComponent nodePaintComponent = new NodePaintComponent();

    private char keyPressed = (char)0;

    Node mouseHoveringNode;
    Node startNode;
    Node endNode;





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
        addMouseListener(this);
        addKeyListener(this);
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
        basicGridCommands(mouseEvent);

    }

    private void basicGridCommands(MouseEvent event){
        int row = event.getX()/ApplicationConfiguration.getInstance().getNodeDefaultWidth();
        int col = event.getY()/ApplicationConfiguration.getInstance().getNodeDefaultWidth();

        Node currentNode = board[row][col];
        int cellWidth = ApplicationConfiguration.getInstance().getNodeDefaultWidth();

        if(SwingUtilities.isLeftMouseButton(event)){

            if(keyPressed == 's' && startNode==null && currentNode.getNodeType()==NodeType.EMPTY){
                startNode = currentNode;
                startNode.setNodeType(NodeType.START);
                repaint(startNode.getRow()*cellWidth,startNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);

            }
            else if(keyPressed == 'e' && endNode == null && currentNode.getNodeType() == NodeType.EMPTY){
                endNode = currentNode;
                endNode.setNodeType(NodeType.END);
                repaint(endNode.getRow()*cellWidth,endNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);
            }
            else if(currentNode.getNodeType() == NodeType.EMPTY){
                currentNode.setNodeType(NodeType.BLOCK);
                repaint(currentNode.getRow()*cellWidth,currentNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);

            }
        }
        else if(SwingUtilities.isRightMouseButton(event)){
            if(currentNode.getNodeType() == NodeType.END){
                endNode=null;
            }
            else if(currentNode.getNodeType() == NodeType.START){

                startNode = null;
            }


            currentNode.setNodeType(NodeType.EMPTY);
            repaint(currentNode.getRow()*cellWidth,currentNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);

        }

    }

    /*
        used for painting a node we are currently hovering over
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

//        if(keyPressed == 's' || keyPressed =='e')
//            return;
//        int row = mouseEvent.getX() / ApplicationConfiguration.getInstance().getNodeDefaultWidth();
//        int col = mouseEvent.getY() / ApplicationConfiguration.getInstance().getNodeDefaultWidth();
//        int cellWidth = ApplicationConfiguration.getInstance().getNodeDefaultWidth();
//
//        Node newSelectedNode = board[row][col];
//
//
//        //TODO srediti !!
//        if(newSelectedNode.getNodeType() == NodeType.BLOCK){
//            if(mouseHoveringNode!=null){
//
//                mouseHoveringNode.setNodeType(NodeType.EMPTY);
//                repaint(mouseHoveringNode.getRow()*cellWidth,mouseHoveringNode.getCol()*cellWidth,cellWidth,cellWidth);
//            }
//
//        }
//
//        if (newSelectedNode.getNodeType() != NodeType.EMPTY) {
//            return;
//        }
//
//        newSelectedNode.setNodeType(NodeType.SELECTED);
//
//        if (mouseHoveringNode != null) {
//            mouseHoveringNode.setNodeType(NodeType.EMPTY);
//            repaint(mouseHoveringNode.getRow()*cellWidth,mouseHoveringNode.getCol()*cellWidth,cellWidth,cellWidth);
//        }
//
//        mouseHoveringNode = newSelectedNode;
//        repaint(mouseHoveringNode.getRow()*cellWidth,mouseHoveringNode.getCol()*cellWidth,cellWidth,cellWidth);

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        System.out.println(keyPressed);
        if(keyEvent.getKeyChar() == 's' || keyEvent.getKeyChar() == 'e')
            keyPressed = keyEvent.getKeyChar();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        basicGridCommands(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}