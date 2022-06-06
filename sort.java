import java.util.Random;

public class sort {
	public static int[] selection(int[] nums, int delay, int _smallest, int _largest) {
		
		int finalSmallestNumIndex = 0;
		int smallestNum = 0;
		
		for (int largestSortedIndex = 0; largestSortedIndex < nums.length-1; largestSortedIndex++) {
			
			//finalSmallestIndex will end up with the smallest integer of its most recent cycle
			//this is a problem, because then it'll start at the smallest integer, however
			//the smallest integer is already sorted
			//meaning it won't ever find the next smallest number because it skips indices it knows are already sorted
			//because it finds the smallest by comparing itself to every integer it doesn't skip
			
			//to be honest, this is just bubble sort but in reverse and retarded
			finalSmallestNumIndex = largestSortedIndex;
			
			for (int smallestNumsIndex = largestSortedIndex; smallestNumsIndex < nums.length; smallestNumsIndex++) {
				if (nums[smallestNumsIndex] < nums[finalSmallestNumIndex]) { //finds the index of the smallest integer
					finalSmallestNumIndex = smallestNumsIndex;
				}
				visualize.updateArray(nums, _smallest, _largest);
				delay(delay);
			}
			//swaps the index of the smallest integer with the largest index you KNOW is sorted (starting at 0, then 1, then 2, etc.)
			smallestNum = nums[finalSmallestNumIndex];
			nums[finalSmallestNumIndex] = nums[largestSortedIndex];
			nums[largestSortedIndex] = smallestNum;
			
			//System.out.println(arrayToString(nums));
		}
		
		return nums;
	}
	
	public static int[] bubble(int[] nums, int delay, int _smallest, int _largest) {
		
		int largerInt = 0;
		int swaps = 0;
		
		for (int sortedInts = 0; sortedInts < nums.length-1; sortedInts++) {
			swaps = 0;
			for (int i = 0; i < nums.length-(sortedInts+1); i++) {
				if (nums[i] > nums[i+1]) { //basically, sorts every single pair of adjacent integers, eventually sorting everything
					largerInt = nums[i];
					nums[i] = nums[i+1];
					nums[i+1] = largerInt;
					swaps++;
				}
				visualize.updateArray(nums, _smallest, _largest);
				delay(delay);
			}
			if (swaps == 0) { //counts the number of swaps performed, essentially checking if the list is sorted
				return nums;
			}
			//System.out.println(arrayToString(nums));
		}
		return nums;
	}
	
	public static int[] merge(int[] nums, int delay, int _smallest, int _largest) { //recursive function. beautiful concept, but slower, and code looks ugly
		
		if (nums.length > 2) {
			//if the list length is greater than two, split it in half! this lets us stop recursing when we only have to sort a pair
			int[] firstHalf = new int[nums.length/2];
			int[] secondHalf = new int[nums.length-nums.length/2];
			
			for (int i = 0; i < nums.length/2; i++) {
				firstHalf[i] = nums[i];
				delay(delay);
			}
			for (int i = nums.length/2; i < nums.length; i++) {
				secondHalf[i-nums.length/2] = nums[i];
				delay(delay);
			}
			
			//now, sort the halves
			System.out.println(_smallest);
			System.out.println((nums.length/2)-1);
			System.out.println((nums.length/2));
			System.out.println(_largest);
			System.out.println();
			
			firstHalf = merge(firstHalf, delay, _smallest, _smallest+(nums.length/2)-1);
			secondHalf = merge(secondHalf, delay, _smallest+(nums.length/2), _largest);

			//now, join the halves
			for (int i = 0; i < nums.length/2; i++) {
				nums[i] = firstHalf[i];
				delay(delay);
			}
			for (int i = nums.length/2; i < nums.length; i++) {
				nums[i] = secondHalf[i-nums.length/2];
				delay(delay);
			}
		}
		
		int firstRampIndex = 0;
		int secondRampIndex = nums.length/2;
		int[] memoryNums = new int[nums.length]; //new list for the sake of being able to dump things
		
		//merging two "ramps" (individually sorted lists)
		for (int i = 0; i < nums.length; i++) {
			
			if (firstRampIndex >= nums.length/2) {
				memoryNums[i] = nums[secondRampIndex];
				secondRampIndex++;
			}
			else if (secondRampIndex >= nums.length) {
				memoryNums[i] = nums[firstRampIndex];
				firstRampIndex++;
			}
			else {
				if (nums[firstRampIndex] < nums[secondRampIndex]) {
					memoryNums[i] = nums[firstRampIndex];
					firstRampIndex++;
				}
				else {
					memoryNums[i] = nums[secondRampIndex];
					secondRampIndex++;
				}
			}
			visualize.updateArray(memoryNums, _smallest, _smallest+i);
			delay(delay);
		}
		
		//System.out.println(arrayToString(memoryNums));
		
		return memoryNums;
	}
	
	public static int[] quick(int[] nums, int delay, int _smallest, int _largest) {

		int[] memoryNums = new int[nums.length];
		int smallersIndex = 0;
		int largersIndex = nums.length-1;
		
		//take the first integer, then shove everything smaller to its left, and everything larger to its right
		for (int i = 1; i < nums.length; i++) {
			//we'll start by putting all the smaller integers from left to right
			if (nums[i] < nums[0]) {
				memoryNums[smallersIndex] = nums[i];
				smallersIndex++;
			}
			//then put all the larger integers from right to left
			else {
				memoryNums[largersIndex] = nums[i];
				largersIndex--;
			}
			delay(delay);
		}
		//then, put the first integer in the remaining gap
		memoryNums[smallersIndex] = nums[0];

		//System.out.println(arrayToString(memoryNums));
		
		//then, call ourself to sort the smaller section IF that section's length > 1
		if (smallersIndex > 1) {
			int[] smallers = new int[smallersIndex];
			for (int i = 0; i < smallers.length; i++) {
				smallers[i] = memoryNums[i];
				
				delay(delay);
			}
			smallers = quick(smallers, delay, _smallest, _largest);
			//then, get our sorted section back, and put them back into memoryNums
			for (int i = 0; i < smallers.length; i++) {
				memoryNums[i] = smallers[i];
				
				delay(delay);
			}
		}
		//then, call ourself to sort the larger section IF that section's length > 1
		if (largersIndex < nums.length-2) {
			int[] largers = new int[memoryNums.length-(smallersIndex+1)];
			for (int i = 0; i < largers.length; i++) {
				largers[i] = memoryNums[i+smallersIndex+1];
				
				delay(delay);
			}
			largers = quick(largers, delay, _smallest, _largest);
			//then, get our sorted section back, and put them back into memoryNums
			for (int i = 0; i < largers.length; i++) {
				memoryNums[i+smallersIndex+1] = largers[i];
				
				delay(delay);
			}
		}

		//finally, return out sorted section. Eventually, our "section" will be stitched back up as the whole array, and we'll do one final return!
		return memoryNums;
	}
	
	public static int[] counting(int[] nums, int delay, int _smallest, int _largest) {
		
		//first, need to know our smallest and largest integers
		int smallest = nums[0];
		int largest = nums[0];
		
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] < smallest) {
				smallest = nums[i];
			}
			else if (nums[i] > largest) {
				largest = nums[i];
			}
			delay(delay);
		}
		
		//now, we make a list that fits the range perfectly, to avoid wasting space in the event that the sorted list indeces don't perfectly line up with the values at those indeces
		int[] occurences = new int[(largest-smallest)+1];
		
		//then, we fill the list with the occurences of each number appearing, according to its index. so, index 0 will count the occurences of the integer 0, etc
		for (int i = 0; i < nums.length; i++) {
			//the reason we subtract nums[i] by smallest is to set the smallest integer at index 0, because what if nums[i] = -1? then what?
			occurences[nums[i]-smallest]++;
			delay(delay);
		}
		
		//the thing is, occurences is sorted! it just doesn't represent the values in the way we want
		//so we can refill our orignal array with the occurences of each index
		//for example, if the integer 0 appears once, occurences[0] will equal 1
		//so we put one 0 in the orginal array at index 0
		//continue for all indeces of occurences
		int cumSumOcc = 0;
		for (int i = 0; i < occurences.length; i++) {
			for (int x = 0; x < occurences[i]; x++) {
				nums[cumSumOcc+x] = i+smallest;
				
				//System.out.println(arrayToString(nums));
				visualize.updateArray(nums, _smallest, _largest);
				delay(delay);
			}
			cumSumOcc += occurences[i];
		}
		
		return nums;
	}
	
	public static int[] shuffle(int[] nums) {
		int temp = 0;
		int tempRand = 0;
		Random randint = new Random();
		
		//shuffle
		for (int x = 0; x < nums.length; x++) {
			tempRand = randint.nextInt(nums.length);
			temp = nums[x];
			nums[x] = nums[tempRand];
			nums[tempRand] = temp;
			
			//System.out.println(arrayToString(nums));
		}		
		return nums;
	}
	
	public static boolean check(int[] nums) {
		for (int i = 0; i < nums.length-1; i++) {
			if (nums[i] > nums[i+1]) {
				return false;
			}
		}
		return true;
	}
	
	private static void delay(int msec) {
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
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