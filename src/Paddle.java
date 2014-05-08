import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class Paddle implements MouseMotionListener/*, KeyListener */{

    public int width = 6, length = 70; 
    public double x = 50, y = 101;
    public Color color = Color.white;
    public int direction = 0;
    
    public void mouseMoved(MouseEvent e) {
	y = e.getY();
	
    }
    
    public void paint(Graphics g){
	g.setColor(color);
	g.fillRect((int)x - width/2, (int)y - length / 2, width, length);
    }
    
    public void mouseDragged(MouseEvent arg0) {}
    
    public String toString(){
	return String.format("Paddle\n  x: %d y: %d \n width: %d length: %d", (int)x,(int)y, width, length);
    }
    
}
