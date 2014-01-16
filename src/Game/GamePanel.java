package Game;import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
/**
 * Simple game library for creating games in java. Meant to be overridden with game logic. 
 * @author spotco
 * @see DrawingPanel
 */

public class GamePanel extends DrawingPanel {
	
	protected static int KEY_UP = KeyEvent.VK_UP;
	protected static int KEY_DOWN = KeyEvent.VK_DOWN;
	protected static int KEY_LEFT = KeyEvent.VK_LEFT;
	protected static int KEY_RIGHT = KeyEvent.VK_RIGHT;
	protected static int KEY_SPACE = KeyEvent.VK_SPACE;
	
	private Set<Integer> _keys_down;
	private boolean _mouse_down;
	private int _mouseX,_mouseY;
	protected Graphics _g;
	
	public GamePanel(int width, int height) {
		super(width, height);
		_keys_down = new HashSet<Integer>();
		_g = this.getGraphics();
	}
	
	//ignore all of these methods
	@Override public void mouseMoved(MouseEvent arg0){ _mouseX = arg0.getX(); _mouseY = arg0.getY();}
	@Override public void mousePressed(MouseEvent arg0) { _mouse_down = true;}
	@Override public void mouseReleased(MouseEvent arg0) {_mouse_down = false;}
	@Override public void keyPressed(KeyEvent arg0) {_keys_down.add(arg0.getKeyCode());}
	@Override public void keyReleased(KeyEvent arg0) {_keys_down.remove(arg0.getKeyCode());}
	
	//use these methods
	/**
	 * Clears screen
	 */
	public void clear() {
		clear(Color.WHITE);
	}
	
	public void clear(Color color) {
		_g.setColor(color);
		_g.fillRect(0, 0, this.getSize().width, this.getSize().height);
	}
	
	/**
	 * @return width of screen
	 */
	public int get_width() {
		return this.getSize().width;
	}
	
	/**
	 * @return height of the screen
	 */
	public int get_height() {
		return this.getSize().height;
	}
	
	/**
	 * @param key - keycode of target key
	 * @return if target key is currently pressed
	 */
	public boolean is_key_down(int key) {
		return _keys_down.contains(key);
	}
	
	/**
	 * @return if mouse is down
	 */
	public boolean is_mouse_down() {
		return _mouse_down;
	}
	
	/**
	 * @return current x position of mouse
	 */
	public int get_mouse_x() {
		return _mouseX;
	}
	
	/**
	 * @return current y position of mouse
	 */
	public int get_mouse_y() {
		return _mouseY;
	}
	
	//override this method
	/**
	 * Continuously called, override with game logic
	 */
	@Override
	public void update(){
	}
}
