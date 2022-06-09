public class main {
	public static void main(String[] args) {
		
		int tests = 1; //the amount of tests you want to run
		int arrayLength = 1000; //the length of the array you want to shuffle and sort
		int delay = 1; //delay in milliseconds, so you can actually watch things happen in front of your slow human eyes
		
		int largest = 0;
		int[] nums = new int[arrayLength];
		
		for (largest = 0; largest < nums.length; largest++) {
			nums[largest] = largest*largest;
		}
		largest--;
		
		//initialize window
		visualize.init(arrayLength, nums[largest]);
		
		for (int loop = 0; loop < tests; loop++) {
			//shuffle
			nums = sort.shuffle(nums);
			visualize.updateArray(nums, 0, nums.length-1);
			
			//sort and print
			nums = sort.merge(nums, delay, 0, nums.length-1);
			
			//check if sorted
			if (sort.check(nums)) {
				System.out.println("Correctly sorted\n");
			}
			else {
				System.out.println("Incorrectly sorted\n");
			}
			System.out.println("Comparisons: "+sort.getComparisons());
			System.out.println("Array accesses: "+sort.getArrayAccesses());
			sort.reset();
		}
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
