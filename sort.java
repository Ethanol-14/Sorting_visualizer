import java.util.Random;

public class sort {
	
	private static int comparisons = 0;
	private static int arrayAccesses = 0;
	
	public static void reset() {
		comparisons = 0;
		arrayAccesses = 0;
	}
	public static int getComparisons() {
		return comparisons;
	}
	public static int getArrayAccesses() {
		return arrayAccesses;
	}
	
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
				visualize.setGreens(nums[smallestNumsIndex], smallestNumsIndex);
				comparisons++;
				delay(delay);
			}
			//swaps the index of the smallest integer with the largest index you KNOW is sorted (starting at 0, then 1, then 2, etc.)
			smallestNum = nums[finalSmallestNumIndex];
			nums[finalSmallestNumIndex] = nums[largestSortedIndex];
			visualize.setReds(nums[largestSortedIndex], finalSmallestNumIndex);
			visualize.updateIndex(nums[largestSortedIndex], finalSmallestNumIndex);
			nums[largestSortedIndex] = smallestNum;
			visualize.setReds(smallestNum, largestSortedIndex);
			visualize.updateIndex(smallestNum, largestSortedIndex);
			visualize.setGreens(smallestNum, largestSortedIndex);
			arrayAccesses += 2;
			delay(delay*2);
		}

		visualize.setGreens(0, 0);
		visualize.setReds(0, 0);
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
					visualize.setReds(nums[i+1], i);
					visualize.updateIndex(nums[i+1], i);
					nums[i+1] = largerInt;
					visualize.setReds(largerInt, i+1);
					visualize.updateIndex(largerInt, i+1);
					visualize.setGreens(largerInt, i+1);
					swaps++;
					arrayAccesses += 2;
					delay(delay*2);
				}
				comparisons++;
				delay(delay);
			}
			if (swaps == 0) { //counts the number of swaps performed, essentially checking if the list is sorted
				visualize.setGreens(0, 0);
				visualize.setReds(0, 0);
				return nums;
			}
		}

		visualize.setGreens(0, 0);
		visualize.setReds(0, 0);
		return nums;
	}
	
	public static int[] insertion(int[] nums, int delay, int _smallest, int _largest) {
		
		int largerInt = 0;
		int i = 0;
		
		for (int sortedIndeces = 1; sortedIndeces < nums.length; sortedIndeces++) {
			i = sortedIndeces;
			while (i != 0 && nums[i] < nums[i-1]) {
				largerInt = nums[i];
				nums[i] = nums[i-1];
				visualize.setReds(nums[i], i);
				visualize.updateIndex(nums[i], i);
				nums[i-1] = largerInt;
				visualize.setReds(nums[i-1], i-1);
				visualize.updateIndex(nums[i-1], i-1);
				visualize.setGreens(nums[i-1], i-1);
				i--;
				comparisons++;
				arrayAccesses += 2;
				delay(delay*3);
			}
		}

		visualize.setGreens(0, 0);
		visualize.setReds(0, 0);
		return nums;
	}
	
	public static int[] merge(int[] nums, int delay, int _smallest, int _largest) { //recursive function. beautiful concept, but slower, and code looks ugly
		
		if (nums.length > 2) {
			//if the list length is greater than two, split it in half! this lets us stop recursing when we only have to sort a pair
			int[] firstHalf = new int[nums.length/2];
			int[] secondHalf = new int[nums.length-nums.length/2];
			
			for (int i = 0; i < nums.length/2; i++) {
				firstHalf[i] = nums[i];
				visualize.setGreens(nums[i], i+_smallest);
				arrayAccesses++;
				delay(delay);
			}
			for (int i = nums.length/2; i < nums.length; i++) {
				secondHalf[i-nums.length/2] = nums[i];
				visualize.setGreens(nums[i], i+_smallest);
				arrayAccesses++;
				delay(delay);
			}
			
			firstHalf = merge(firstHalf, delay, _smallest, _smallest+(nums.length/2)-1);
			secondHalf = merge(secondHalf, delay, _smallest+(nums.length/2), _largest);

			//now, join the halves
			for (int i = 0; i < nums.length/2; i++) {
				nums[i] = firstHalf[i];
				visualize.setGreens(nums[i], i+_smallest);
				arrayAccesses++;
				delay(delay);
			}
			for (int i = nums.length/2; i < nums.length; i++) {
				nums[i] = secondHalf[i-nums.length/2];
				visualize.setGreens(nums[i], i+_smallest);
				arrayAccesses++;
				delay(delay);
			}
			visualize.setGreens(0, 0);
			comparisons++;
			delay(delay);
		}
		
		int firstRampIndex = 0;
		int secondRampIndex = nums.length/2;
		int[] memoryNums = new int[nums.length]; //new list for the sake of being able to dump things
		
		//merging two "ramps" (individually sorted lists)
		for (int i = 0; i < nums.length; i++) {
			
			if (firstRampIndex >= nums.length/2) {
				memoryNums[i] = nums[secondRampIndex];
				secondRampIndex++;
				arrayAccesses++;
				delay(delay);
			}
			else if (secondRampIndex >= nums.length) {
				memoryNums[i] = nums[firstRampIndex];
				firstRampIndex++;
				arrayAccesses++;
				delay(delay);
			}
			else {
				if (nums[firstRampIndex] < nums[secondRampIndex]) {
					memoryNums[i] = nums[firstRampIndex];
					firstRampIndex++;
					arrayAccesses++;
					delay(delay);
				}
				else {
					memoryNums[i] = nums[secondRampIndex];
					secondRampIndex++;
					arrayAccesses++;
					delay(delay);
				}
				comparisons++;
				delay(delay);
			}
			visualize.setReds(memoryNums[i], _smallest+i);
			visualize.updateIndex(memoryNums[i], _smallest+i);
			comparisons += 2;
			delay(delay*2);
		}

		visualize.setGreens(0, 0);
		visualize.setReds(0, 0);
		return memoryNums;
	}
	
	public static int[] quick(int[] nums, int delay, int _smallest, int _largest) {

		int[] memoryNums = new int[nums.length];
		int smallersIndex = 0;
		int largersIndex = nums.length-1;
		int largersProgress = 0;
		
		//take the first integer, then shove everything smaller to its left, and everything larger to its right
		for (int i = 1; i < nums.length; i++) {
			//we'll start by putting all the smaller integers from left to right
			if (nums[i] < nums[0]) {
				
				visualize.setReds(nums[i], _smallest+smallersIndex);
				visualize.updateIndex(nums[i], _smallest+smallersIndex);
				
				memoryNums[smallersIndex] = nums[i];
				smallersIndex++;
				arrayAccesses++;
				delay(delay);
			}
			//then put all the larger integers from right to left
			else {
				
				visualize.setReds(nums[i], _largest-largersProgress);
				visualize.updateIndex(nums[i], _largest-largersProgress);
				
				memoryNums[largersIndex] = nums[i];
				largersIndex--;
				largersProgress++;
				arrayAccesses++;
				delay(delay);
			}
			comparisons++;
			delay(delay);
		}
		visualize.setReds(0, 0);
		//then, put the first integer in the remaining gap
		memoryNums[smallersIndex] = nums[0];
		visualize.setReds(nums[0], _smallest+smallersIndex);
		visualize.updateIndex(nums[0], _smallest+smallersIndex);
		visualize.setReds(0, 0);
		arrayAccesses++;
		delay(delay);
		
		//then, call ourself to sort the smaller section IF that section's length > 1
		if (smallersIndex > 1) {
			int[] smallers = new int[smallersIndex];
			for (int i = 0; i < smallers.length; i++) {
				smallers[i] = memoryNums[i];
				visualize.setGreens(smallers[i], i+_smallest);
				arrayAccesses++;
				delay(delay);
			}
			visualize.setGreens(0, 0);
			smallers = quick(smallers, delay, _smallest, _smallest+smallersIndex-1);
			//then, get our sorted section back, and put them back into memoryNums
			for (int i = 0; i < smallers.length; i++) {
				memoryNums[i] = smallers[i];
				visualize.setGreens(smallers[i], i+_smallest);
				arrayAccesses++;
				delay(delay);
			}
			visualize.setGreens(0, 0);
			comparisons++;
			delay(delay);
		}
		//then, call ourself to sort the larger section IF that section's length > 1
		if (largersIndex < nums.length-2) {
			int[] largers = new int[memoryNums.length-(smallersIndex+1)];
			for (int i = 0; i < largers.length; i++) {
				largers[i] = memoryNums[i+smallersIndex+1];
				visualize.setGreens(largers[i], i+_smallest+smallersIndex+1);
				arrayAccesses++;
				delay(delay);
			}
			visualize.setGreens(0, 0);
			largers = quick(largers, delay, _smallest+smallersIndex+1, _largest);
			//then, get our sorted section back, and put them back into memoryNums
			for (int i = 0; i < largers.length; i++) {
				memoryNums[i+smallersIndex+1] = largers[i];
				visualize.setGreens(largers[i], i+_smallest+smallersIndex+1);
				arrayAccesses++;
				delay(delay);
			}
			visualize.setGreens(0, 0);
			comparisons++;
			delay(delay);
		}
		
		//finally, return out sorted section. Eventually, our "section" will be stitched back up as the whole array, and we'll do one final return!
		visualize.setGreens(0, 0);
		visualize.setReds(0, 0);
		return memoryNums;
	}
	
	public static int[] counting(int[] nums, int delay, int _smallest, int _largest) {
		
		//first, we actually need to find the largest number...
		int largest = nums[0];
		int smallest = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > largest) {
				largest = nums[i];
			}
			if (nums[i] < smallest) {
				smallest = nums[i];
			}
			visualize.setGreens(nums[i], i);
			comparisons += 2;
			delay(delay*2);
		}
		
		//now, we make a list that fits the range perfectly, to avoid wasting space in the event that the sorted list indeces don't perfectly line up with the values at those indeces
		int[] occurences = new int[(largest-smallest)+1];
		
		//then, we fill the list with the occurences of each number appearing, according to its index. so, index 0 will count the occurences of the integer 0, etc
		for (int i = 0; i < nums.length; i++) {
			//the reason we subtract nums[i] by smallest is to set the smallest integer at index 0, because what if nums[i] = -1? then what?
			occurences[nums[i]-smallest]++;
			visualize.setGreens(nums[i], i);
			arrayAccesses++;
			delay(delay);
		}
		visualize.setGreens(0, 0);
		
		//the thing is, occurences is sorted! it just doesn't represent the values in the way we want
		//so we can refill our orignal array with the occurences of each index
		//for example, if the integer 0 appears once, occurences[0] will equal 1
		//so we put one 0 in the orginal array at index 0
		//continue for all indeces of occurences
		int cumSumOcc = 0;
		for (int i = 0; i < occurences.length; i++) {
			for (int x = 0; x < occurences[i]; x++) {
				nums[cumSumOcc+x] = i+smallest;
				visualize.setReds(i+smallest, cumSumOcc+x);
				visualize.updateIndex(i+smallest, cumSumOcc+x);
				arrayAccesses++;
				delay(delay);
			}
			cumSumOcc += occurences[i];
		}

		visualize.setReds(0, 0);
		return nums;
	}
	
	public static int[] bucket(int[] nums, int delay, int _smallest, int _largest) { //bucket sort goes hard
		
		int[] buckets = new int[10];
		int[] memoryNums = new int[nums.length];
		int digit = 0;
		int largest = nums[0];
		
		//once again we need to find the largest int...
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > largest) {
				largest = nums[i];
			}
			visualize.setGreens(nums[i], i);
			comparisons++;
			delay(delay);
		}
		//then record its length
		int largestIntLength = String.valueOf(largest).length();

		for (int digitFocus = 0; digitFocus < largestIntLength; digitFocus++) {
			
			//iterate through the whole list
			buckets = new int[10];
			for (int i = 0; i < nums.length; i++) {
				//find out which digit we want to look at
				digit = nums[i];
				for (int x = 0; x < digitFocus; x++) { //continually shaves off the rightmost digit of our integer until we get to our desired digit
					digit /= 10;
				}				
				buckets[digit%10]++; //get the value of that digit we want to look at and at it to the counter list
				visualize.setGreens(nums[i], i);
				arrayAccesses++;
				delay(delay);
			}
			visualize.setGreens(0, 0);
			//now, we accumalate sum of our counter list
			for (int i = 1; i < 10; i++) {
				buckets[i] += buckets[i-1];
				arrayAccesses++;
				delay(delay);
			}
			//now we reiterate through our list but backwards, using clever math to determine which index each integer belongs in
			for (int i = nums.length-1; i >= 0; i--) {
				//find out which digit we want to look at
				digit = nums[i];
				for (int x = 0; x < digitFocus; x++) { //continually shaves off the rightmost digit of our integer until we get to our desired digit
					digit /= 10;
				}
				buckets[digit%10]--;
				memoryNums[buckets[digit%10]] = nums[i];
				visualize.setGreens(nums[i], i);
				arrayAccesses += 2;
				delay(delay*2);
			}
			visualize.setGreens(0, 0);
			
			nums = new int[memoryNums.length];
			for (int i = 0; i < nums.length; i++) {
				nums[i] = memoryNums[i];
				visualize.setReds(nums[i], i);
				visualize.updateIndex(nums[i], i);
				arrayAccesses++;
				delay(delay);
			}
			visualize.setReds(0, 0);
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
		}		
		
		return nums;
	}
	
	public static boolean check(int[] nums) {
		for (int i = 0; i < nums.length-1; i++) {
			if (nums[i] > nums[i+1]) {
				visualize.setGreens(nums[i], i);
				return false;
			}
		}
		visualize.setGreens(0, 0);
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