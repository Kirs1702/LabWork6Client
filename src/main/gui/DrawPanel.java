package main.gui;

import main.entity.Route;
import main.entity.RouteSet;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;

public class DrawPanel extends JPanel implements ActionListener {
    private int width;
    private int height;
    private RouteSet routeSet = new RouteSet();
    private int factor = 100;
    private Map<String, Color> userColors = new HashMap<>();
    private Map<Long, MyEllipse> ellipses= Collections.synchronizedMap(new HashMap<>());
    private List<Color> colors = new ArrayList<>();
    private Graphics2D g2;
    private Timer timer;


    public DrawPanel() {
        super();
        timer = new Timer(10, this);
        timer.start();
        fillColors();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        width = getWidth();
        height = getHeight();
        g2 = (Graphics2D) g;
        drawGrid(g2);

        for (Long id : ellipses.keySet()) {
            MyEllipse me = ellipses.get(id);
            me.grow(factor, getWidth(), getHeight());
            g2.setColor(me.getColor());
            g2.fill(me.getEllipse());
        }

    }

    private void drawGrid(Graphics2D g2) {
        g2.setColor(Color.LIGHT_GRAY);  //задаем серый цвет

        for(int x=width/2; x<width; x+=factor){  // цикл от центра до правого края
            g2.drawLine(x, 0, x, height);    // вертикальная линия

        }

        for(int x=width/2; x>0; x-=factor){  // цикл от центра до леваого края
            g2.drawLine(x, 0, x, height);   // вертикальная линия
        }

        for(int y=height/2; y<height; y+=factor){  // цикл от центра до верхнего края
            g2.drawLine(0, y, width, y);    // горизонтальная линия
        }

        for(int y=height/2; y>0; y-=factor){  // цикл от центра до леваого края
            g2.drawLine(0, y, width, y);    // горизонтальная линия
        }
        g2.setColor(Color.BLACK);
        g2.drawLine(width/2, 0, width/2, height);
        g2.drawLine(0, height/2, width, height/2);
    }

    private void fillColors(){
        colors.add(Color.BLUE);
        colors.add(Color.ORANGE);
        colors.add(Color.CYAN);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.PINK);
        colors.add(Color.RED);
        colors.add(new Color(0,51,102));
        colors.add(new Color(255, 153, 51));
        colors.add(new Color(153,51,51));
        colors.add(new Color(0,102,255));
        colors.add(new Color(153,204,0));

    }


    public void setRouteSet(RouteSet routeSet){

        RouteSet oldRouteSet = this.routeSet;
        if (routeSet.size() <= oldRouteSet.size()){

            for (Route oldRoute : oldRouteSet.getSet()) {

                for (Route route : routeSet.getSet()) {
                    boolean cont = false;
                    if (route.getId() == oldRoute.getId()) {
                        cont = true;
                    }
                    if(!cont) {
                        ellipses.remove(oldRoute.getId());
                        repaint();
                    }
                }
            }
        }
        if (routeSet.size() == 0) {
            ellipses.clear();
            repaint();
        }

        this.routeSet = routeSet;

        Random rand = new Random();
        for (Route route : routeSet.getSet()) {
            if (!userColors.containsKey(route.getUser())) {
                if (colors.size() > 0) {
                    int i = rand.nextInt(colors.size());
                    userColors.put(route.getUser(), colors.get(i));
                    colors.remove(i);
                } else {
                    userColors.put(route.getUser(), new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
                }
            }

            if (!ellipses.containsKey(route.getId())) {
                ellipses.put(route.getId() , new MyEllipse(route.getId(), userColors.get(route.getUser()), Math.round(route.getCoordinates().getX()), route.getCoordinates().getY(), route.getDistance()));
            }

        }
        repaint();
    }

    public void setFactor(int factor) {
        this.factor = factor;
        repaint();
    }



}


class MyEllipse {
    private long id;
    private Ellipse2D ellipse;
    private double maxSize, currentSize;
    private Color color;
    private int x, y;

    MyEllipse(long id, Color color, int x, int y, double maxSize){
        this.id = id;
        this.maxSize = maxSize;
        this.x = x;
        this.y = y;
        this.color = color;
        ellipse = new Ellipse2D.Double(x, y, 1, 1);
        currentSize = 1;
    }
    public void grow(int factor, int width, int height){
        if (currentSize < maxSize) currentSize+=10;
        if (currentSize > maxSize) currentSize = maxSize;
        ellipse.setFrame(   ((width/2 + x*factor/100) - currentSize*factor/100/2),
                            ((height/2 - y*factor/100)- currentSize*factor/100/2),
                            (currentSize*factor/100),
                            (currentSize*factor/100));
    }

    public long getId() {
        return id;
    }



    public Color getColor() {
        return color;
    }

    public Ellipse2D getEllipse() {
        return ellipse;
    }
}

