// Bryce W, template given to us
package java2ddrawingapplication;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.event.*;
import java.util.HashSet;

/**
 *
 * @author acv
 */
public class DrawingApplicationFrame extends JFrame
{
   
    // Create the panels for the top of the application. One panel for each
    JPanel firstLinePanel = new JPanel();
    JPanel secondLinePanel = new JPanel();
    // line and one to contain both of those panels.
    JPanel upperPanel = new JPanel();
    // create the widgets for the firstLine Panel.
    JLabel shapeLabel = new JLabel("Shape:");
    // Combo box for draw shapes 
    String[] shapes = {"Line", "Oval", "Rectangle"};
    
    JComboBox shapeBox = new JComboBox<String>(shapes);
    // Color choosers 
    JButton color1Button = new JButton("1st Color...");
    JButton color2Button = new JButton("2nd Color...");
    
    
    // Undo and clear 
    JButton undo = new JButton("Undo");
    JButton clear = new JButton("Clear");
    
    
    
    //create the widgets for the secondLine Panel.
    
    JLabel optionsLabel = new JLabel("Options: ");
    JCheckBox filled = new JCheckBox("Filled");
    JCheckBox gradient = new JCheckBox("Gradient");
    JCheckBox dashed = new JCheckBox("Dashed");
    JLabel widthLabel = new JLabel("Line Width: ");
    JSpinner strokeWidth = new JSpinner();
    
    JLabel dashLengthLabel = new JLabel("Dash Length: ");
    JSpinner dashLength = new JSpinner();
    
    // Variables for drawPanel.
    JPanel drawPanel = new DrawPanel();
    ArrayList<MyShapes> drawList = new ArrayList<MyShapes>();
    int fX = 0; 
    int fY = 0;
    int sX = 0;
    int sY = 0;
    Color color1 = Color.BLACK;
    Color color2 = Color.BLACK;
    // add status label
    JPanel statusPanel = new JPanel();
    JLabel status = new JLabel("(0, 0)");
   
    
    // Constructor for DrawingApplicationFrame
    public DrawingApplicationFrame()
    {
         super("2D Drawing Application");
         super.setLayout(new BorderLayout());
        // add widgets to panels
      
        // firstLine widgets
        firstLinePanel.add(shapeLabel);
        firstLinePanel.add(shapeBox);
        firstLinePanel.add(color1Button);
        firstLinePanel.add(color2Button);
        firstLinePanel.add(undo);
        firstLinePanel.add(clear);
        firstLinePanel.setBackground(Color.CYAN);
        // secondLine widgets
        secondLinePanel.add(optionsLabel);
        secondLinePanel.add(filled);
        secondLinePanel.add(gradient);
        secondLinePanel.add(dashed);
        secondLinePanel.add(widthLabel);
        secondLinePanel.add(strokeWidth);
        secondLinePanel.add(dashLengthLabel);
        secondLinePanel.add(dashLength);
        secondLinePanel.setBackground(Color.CYAN);
        
        // add top panel of two panels
        upperPanel.add(firstLinePanel);
        upperPanel.add(secondLinePanel);
        upperPanel.setLayout(new GridLayout(2,1));
      
        
        // add topPanel to North, drawPanel to Center, and statusLabel to South
        this.add(upperPanel, BorderLayout.NORTH);
       
        this.add(drawPanel, BorderLayout.CENTER);
        
        status.setHorizontalAlignment(SwingConstants.LEFT);
        
        statusPanel.setBackground(Color.GRAY);
        this.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.add(status);
        //add listeners and event handlers
         color1Button.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent ae){
              color1 = JColorChooser.showDialog(null, "Color 1", Color.RED);
             
        }
        });
         
         color2Button.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent ae){
              color2 = JColorChooser.showDialog(null, "Color 2", Color.RED);
             
        }
        });
        clear.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent ae){
             drawPanel.removeAll();
             drawPanel.repaint();
             
        }
        });
        clear.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent ae){
              drawList.clear();
              drawPanel.repaint();
             
        }
        });
        undo.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent ae){
              drawList.remove(drawList.size()-1);
              drawPanel.repaint();
             
        }
        });
         
    }

    // Create event handlers, if needed

    // Create a private inner class for the DrawPanel.
    private class DrawPanel extends JPanel
    {
        
       
     
        
        public DrawPanel()
        {
            
           this.addMouseListener(new MouseHandler());
           this.addMouseMotionListener(new MouseHandler());
          
          
        }
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            //loop through and draw each shape in the shapes arraylist
            for (MyShapes a : drawList){
                a.draw(g2d);
            }
            
            
            
        }


        private class MouseHandler extends MouseAdapter implements MouseListener, MouseMotionListener 
        {
            
            
            int x,y;
            @Override
            public void mousePressed(MouseEvent event)
            {
               
               Paint a;
               Stroke b;
               Point firstPoint = new Point();
               Point secondPoint = new Point();
                if(gradient.isSelected()){
                    a = new GradientPaint(0,0,color1,50,50,color2,true);
                } 
                else{
                    a = color1;
                }
               
              
                if (dashed.isSelected())
            {
                float[] dashSize = {Float.parseFloat(dashLength.getValue().toString())};
                b = new BasicStroke(Float.parseFloat(strokeWidth.getValue().toString()), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10,dashSize, 0);
            } else
            {
                b = new BasicStroke(Float.parseFloat(strokeWidth.getValue().toString()), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            }
               fX = event.getX();
               fY = event.getY();
                firstPoint.setLocation(fX,fY);
                secondPoint.setLocation(fX+1,fY+1);
                if (shapeBox.getSelectedIndex() == 0) {
                   
                        drawList.add(new MyLine(firstPoint, secondPoint, a, b));
                }
                       
                else if (shapeBox.getSelectedIndex() == 1){
                        
                        drawList.add(new MyOval(firstPoint, secondPoint, a,b, filled.isSelected()));
                       
                }
                    
                else {
                        
                        drawList.add(new MyRectangle(firstPoint,secondPoint,a,b, filled.isSelected()));
                       
                }
              
                
               
                
               
            }
            @Override
            public void mouseReleased(MouseEvent event)
            {
                
               drawPanel.repaint();
                System.out.println(drawList.get(drawList.size()-1).getStartPoint() + " " + drawList.get(drawList.size()-1).getEndPoint() + 
                        " Stroke:" + (int)strokeWidth.getValue() + " color: " + drawList.get(drawList.size()-1).getPaint().toString() +
                        " array size: " + drawList.size());
                
            }

            @Override
            public void mouseDragged(MouseEvent event)
            {
                x = event.getX();
                y = event.getY();
                status.setText("(" + x + ", " + y + ")");
                Point firstPoint = new Point();
                Point secondPoint = new Point();
                sX = event.getX();
                sY = event.getY();
                secondPoint.setLocation(sX, sY);
                drawList.get(drawList.size() -1).setEndPoint(secondPoint);
                 drawPanel.repaint();
                
                
               
            }
             @Override
             public void mouseExited(MouseEvent e){
                 
             }

            @Override
            public void mouseMoved(MouseEvent event)
            {
              x = event.getX();
              y = event.getY();
             status.setText("(" + x + ", " + y + ")");
             
            }
        }

    }
}
