import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.BorderLayout;

public class visualize extends JPanel {
	
	private static final long serialVersionUID = 1L; //holy shit why do I need this
	
	private final static int width = 1280;
	private final static int height = 720;
	
	private static int[] nums = new int[0];
	private static int largest = 0;
	
	private static JPanel panel = new visualize();
	private static JFrame frame = new JFrame("The sight of sorting"); //title for the window
	
	public static void init(int _length, int _largest) {
		panel.setBackground(Color.black); //window's panel's background
		frame.setSize(width, height); //frame resolution
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //allows the program to terminate when the window is closed
		frame.getContentPane().add(panel, BorderLayout.CENTER); //puts the panel inside the window...?
		frame.setVisible(true); //allows window to appear (idk why tf this isn't the default setting)
		
		nums = new int[_length];
		largest = _largest;
	}
	
	public static void updateArray(int[] _nums, int firstIndex, int secondIndex) {
		for (int i = firstIndex; i <= secondIndex; i++) {
			nums[i] = _nums[i-firstIndex];
		}
		frame.repaint();
	}
	
	public static void updateIndex(int num, int index) {
		nums[index] = num;
		frame.repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.white);
		
		for (int i = 0; i < nums.length; i++) {	
			g.fillRect((i*getWidth())/nums.length, getHeight()-((nums[i]*getHeight())/largest), (getWidth()/nums.length)+1, (nums[i]*getHeight())/largest);
		}
	}
}
