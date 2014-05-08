import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Pong extends JFrame implements MouseListener{

    //TODO
    // add higher difficulties for Comp
    // fix bug with paddle and ball bouncing
    // add menu (colors, difficulty, size of window, controls, two players, instructions/about)
    // add obstacles for higher levels
    
    
    GameDisplay view = new GameDisplay();
    boolean paused;
    Thread t;

    public Pong(String s) {
	super(s);
	view.addMouseListener(this);
	setSize(500, 700);
	add(view);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
	setResizable(false);
	view.init();
	view.pongFrame = this;
	t = new Thread(view);
	t.setPriority(Thread.NORM_PRIORITY+2);
	t.start();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	Pong game = new Pong("Pong");
	game.setVisible(true);
	System.out.println("New Game loaded and starting...");
    }
    
    public void pause(){
	if(t.isAlive())
	    t.interrupt();
    }
    public void unpause(){
	if(!t.isAlive())//TODO cannot restart from pause
	{
	    t = null;
	    t = new Thread(view);
	    t.setPriority(Thread.NORM_PRIORITY+1);
	    t.start();
	}
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	if(!t.isAlive())//TODO cannot restart from pause
	{
	    this.unpause();
	}
	else
	    this.pause();
    }
    
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
