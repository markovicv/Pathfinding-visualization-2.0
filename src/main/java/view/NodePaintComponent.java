package view;

import model.Node;
import model.NodeType;

import java.awt.*;

public class NodePaintComponent {




    public void paintNodes(Graphics g, Node[][] board,int cellWidth){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                Node currNode = board[i][j];
                g.setColor(getColorForNodeType(currNode.getNodeType()));
                g.fillRect(i*cellWidth,j*cellWidth,cellWidth-1,cellWidth-1);
                g.drawRect(i*cellWidth,j*cellWidth,cellWidth,cellWidth);

            }
        }
    }
    private Color getColorForNodeType(NodeType nodeType){
        if(nodeType == NodeType.EMPTY)
            return new Color(255,255,255);
        if(nodeType == NodeType.BLOCK)
            return new Color(0,0,0);
        if(nodeType == NodeType.SELECTED)
            return new Color(255,150,173);
        if(nodeType == NodeType.END)
            return new Color(213,1,2);
        if(nodeType == NodeType.START)
            return new Color(166,180,1);
        if(nodeType == NodeType.TO_VISIT){
            return new Color(160,160,160);
        }
        if(nodeType == NodeType.VISITED)
            return new Color(135,206,250);
        if(nodeType == NodeType.PATH)
            return new Color(240,215,53);
        return new Color(255,255,255);
    }
}
