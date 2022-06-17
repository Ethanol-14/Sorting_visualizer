import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class main extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private static int[] drawnNums = new int[0];
	private static int drawnLargest = 0;
	
	private static int greenIndex = 0;
	private static int greenInt = 0;
	private static int redIndex = 0;
	private static int redInt = 0;
	
	private static JPanel panel = new main();
	private static JFrame frame = new JFrame("The sight of sorting"); //title for the window
	
	private static int borderWidth = 200;

	public static void main(String[] args) {
		
		int arrayLength = 64; //the length of the array you want to shuffle and sort
		int delay = 10; //delay in milliseconds, so you can actually watch things happen in front of your slow human eyes
				
		final int width = 1280;
		final int height = 720;
		
		panel.setBackground(Color.black); //window's panel's background
		frame.setSize(width, height); //frame resolution
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //allows the program to terminate when the window is closed
		frame.getContentPane().add(panel, BorderLayout.CENTER); //puts the panel inside the window...?
		frame.setVisible(true); //allows window to appear (idk why tf this isn't the default setting)
		
		run(arrayLength, delay, 0);
	}
	
	private static void run(int arrayLength, int delay, int sortType) {
		int[] nums = new int[arrayLength];
		int largest = 0;
		
		for (largest = 0; largest < nums.length; largest++) {
			nums[largest] = largest;
		}
		largest--;
		
		//initialize array
		setLengthAndLargest(arrayLength, largest);
		//shuffle
		nums = visualSort.shuffle(nums);
		updateArray(nums, 0, nums.length-1);
		
		//sort and print
		if (sortType == 0) {
			nums = visualSort.selection(nums, delay, 0, nums.length-1);
		}
		if (sortType == 1) {
			nums = visualSort.bubble(nums, delay, 0, nums.length-1);
		}
		if (sortType == 2) {
			nums = visualSort.insertion(nums, delay, 0, nums.length-1);
		}
		if (sortType == 3) {
			nums = visualSort.comb(nums, delay, 0, nums.length-1);
		}
		if (sortType == 4) {
			nums = visualSort.merge(nums, delay, 0, nums.length-1);
		}
		if (sortType == 5) {
			nums = visualSort.quick(nums, delay, 0, nums.length-1);
		}
		if (sortType == 6) {
			nums = visualSort.counting(nums, delay, 0, nums.length-1);
		}
		if (sortType == 7) {
			nums = visualSort.bucket(nums, delay, 0, nums.length-1);
		}
		
		//check if sorted
		if (visualSort.check(nums)) {
			System.out.println("Correctly sorted\n");
		}
		else {
			System.out.println("Incorrectly sorted\n");
		}
		System.out.println("Comparisons: "+visualSort.getComparisons());
		System.out.println("Array accesses: "+visualSort.getArrayAccesses());
		visualSort.reset();
	}
	
	private static void setLengthAndLargest(int _length, int _largest) {
		drawnNums = new int[_length];
		drawnLargest = _largest;
	}
	
	public static void updateArray(int[] _nums, int firstIndex, int secondIndex) {
		for (int i = firstIndex; i <= secondIndex; i++) {
			drawnNums[i] = _nums[i-firstIndex];
		}
		frame.repaint();
	}
	
	public static void setGreens(int num, int index) { //represents comparisons
		greenIndex = index;
		greenInt = num;
		frame.repaint();
	}
	
	public static void setReds(int num, int index) { //represents array accesses
		redIndex = index;
		redInt = num;
		frame.repaint();
	}
	
	public static void updateIndex(int num, int index) {
		drawnNums[index] = num;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.white);
		
		int actualWidth = getWidth()-borderWidth;
		
		for (int i = 0; i < drawnNums.length; i++) {	
			g.fillRect((i*actualWidth)/drawnNums.length, getHeight()-((drawnNums[i]*getHeight())/drawnLargest), (actualWidth/drawnNums.length)+1, (drawnNums[i]*getHeight())/drawnLargest);
		}
		
		g.setColor(Color.green);
		g.fillRect((greenIndex*actualWidth)/drawnNums.length, getHeight()-((greenInt*getHeight())/drawnLargest), (actualWidth/drawnNums.length)+1, (greenInt*getHeight())/drawnLargest);
		g.setColor(Color.red);
		g.fillRect((redIndex*actualWidth)/drawnNums.length, getHeight()-((redInt*getHeight())/drawnLargest), (actualWidth/drawnNums.length)+1, (redInt*getHeight())/drawnLargest);
	}
	
	private static String arrayToString(int[] nums) {
		String returnString = "[";
		for (int x = 0; x < nums.length-1; x++) {
			returnString += String.format("%2s", nums[x])+", ";
		}
		returnString += String.format("%2s", nums[nums.length-1])+"]";
		return returnString;
	}
}