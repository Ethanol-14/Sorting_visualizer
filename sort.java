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
	
	public static int[] selection(int[] nums) {
		
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
				comparisons++;
			}
			//swaps the index of the smallest integer with the largest index you KNOW is sorted (starting at 0, then 1, then 2, etc.)
			smallestNum = nums[finalSmallestNumIndex];
			nums[finalSmallestNumIndex] = nums[largestSortedIndex];
			nums[largestSortedIndex] = smallestNum;
			arrayAccesses += 2;
		}

		return nums;
	}
	
	public static int[] bubble(int[] nums) {
		
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
					arrayAccesses += 2;
				}
				comparisons++;
			}
			if (swaps == 0) { //counts the number of swaps performed, essentially checking if the list is sorted
				return nums;
			}
		}

		return nums;
	}
	
	public static int[] insertion(int[] nums) {
		
		int largerInt = 0;
		int i = 0;
		
		for (int sortedIndeces = 1; sortedIndeces < nums.length; sortedIndeces++) {
			i = sortedIndeces;
			while (i != 0 && nums[i] < nums[i-1]) {
				largerInt = nums[i];
				nums[i] = nums[i-1];
				nums[i-1] = largerInt;
				i--;
				comparisons++;
				arrayAccesses += 2;
			}
		}

		return nums;
	}
	
	public static int[] comb(int[] nums) {
		int swaps = 1;
		int gap = nums.length;
		int largerInt = 0;
		
		while (swaps != 0) {
			gap *= 0.7;
			swaps = 0;
			
			for (int i = 0; i < nums.length-(gap+1); i++) {
				if (nums[i] > nums[i+gap+1]) {
					largerInt = nums[i];
					nums[i+gap+1] = largerInt;
					arrayAccesses += 2;
					swaps++;
				}
				comparisons++;
			}
		}

		return nums;
	}
	
	public static int[] merge(int[] nums) { //recursive function. beautiful concept, but slower, and code looks ugly
		
		if (nums.length > 2) {
			//if the list length is greater than two, split it in half! this lets us stop recursing when we only have to sort a pair
			int[] firstHalf = new int[nums.length/2];
			int[] secondHalf = new int[nums.length-nums.length/2];
			
			for (int i = 0; i < nums.length/2; i++) {
				firstHalf[i] = nums[i];
				arrayAccesses++;
			}
			for (int i = nums.length/2; i < nums.length; i++) {
				secondHalf[i-nums.length/2] = nums[i];
				arrayAccesses++;
			}
			
			firstHalf = merge(firstHalf);
			secondHalf = merge(secondHalf);

			//now, join the halves
			for (int i = 0; i < nums.length/2; i++) {
				nums[i] = firstHalf[i];
				arrayAccesses++;
			}
			for (int i = nums.length/2; i < nums.length; i++) {
				nums[i] = secondHalf[i-nums.length/2];
				arrayAccesses++;
			}
			comparisons++;
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
			}
			else if (secondRampIndex >= nums.length) {
				memoryNums[i] = nums[firstRampIndex];
				firstRampIndex++;
				arrayAccesses++;
			}
			else {
				if (nums[firstRampIndex] < nums[secondRampIndex]) {
					memoryNums[i] = nums[firstRampIndex];
					firstRampIndex++;
					arrayAccesses++;
				}
				else {
					memoryNums[i] = nums[secondRampIndex];
					secondRampIndex++;
					arrayAccesses++;
				}
				comparisons++;
			}
			comparisons += 2;
		}
		return memoryNums;
	}
	
	public static int[] quick(int[] nums) {

		int[] memoryNums = new int[nums.length];
		int smallersIndex = 0;
		int largersIndex = nums.length-1;
		
		//take the first integer, then shove everything smaller to its left, and everything larger to its right
		for (int i = 1; i < nums.length; i++) {
			//we'll start by putting all the smaller integers from left to right
			if (nums[i] < nums[0]) {
				
				memoryNums[smallersIndex] = nums[i];
				smallersIndex++;
				arrayAccesses++;
			}
			//then put all the larger integers from right to left
			else {
				memoryNums[largersIndex] = nums[i];
				largersIndex--;
				arrayAccesses++;
			}
			comparisons++;
		}
		//then, put the first integer in the remaining gap
		memoryNums[smallersIndex] = nums[0];
		arrayAccesses++;
		
		//then, call ourself to sort the smaller section IF that section's length > 1
		if (smallersIndex > 1) {
			int[] smallers = new int[smallersIndex];
			for (int i = 0; i < smallers.length; i++) {
				smallers[i] = memoryNums[i];
				arrayAccesses++;
			}
			smallers = quick(smallers);
			//then, get our sorted section back, and put them back into memoryNums
			for (int i = 0; i < smallers.length; i++) {
				memoryNums[i] = smallers[i];
				arrayAccesses++;
			}
			comparisons++;
		}
		//then, call ourself to sort the larger section IF that section's length > 1
		if (largersIndex < nums.length-2) {
			int[] largers = new int[memoryNums.length-(smallersIndex+1)];
			for (int i = 0; i < largers.length; i++) {
				largers[i] = memoryNums[i+smallersIndex+1];
				arrayAccesses++;
			}
			//then, get our sorted section back, and put them back into memoryNums
			for (int i = 0; i < largers.length; i++) {
				memoryNums[i+smallersIndex+1] = largers[i];
				arrayAccesses++;
			}
			comparisons++;
		}
		
		//finally, return out sorted section. Eventually, our "section" will be stitched back up as the whole array, and we'll do one final return!
		return memoryNums;
	}
	
	public static int[] counting(int[] nums) {
		
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
			comparisons += 2;
		}
		
		//now, we make a list that fits the range perfectly, to avoid wasting space in the event that the sorted list indeces don't perfectly line up with the values at those indeces
		int[] occurences = new int[(largest-smallest)+1];
		
		//then, we fill the list with the occurences of each number appearing, according to its index. so, index 0 will count the occurences of the integer 0, etc
		for (int i = 0; i < nums.length; i++) {
			//the reason we subtract nums[i] by smallest is to set the smallest integer at index 0, because what if nums[i] = -1? then what?
			occurences[nums[i]-smallest]++;
			arrayAccesses++;
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
				arrayAccesses++;
			}
			cumSumOcc += occurences[i];
		}

		return nums;
	}
	
	public static int[] bucket(int[] nums) { //bucket sort goes hard
		
		int[] buckets = new int[10];
		int[] memoryNums = new int[nums.length];
		int digit = 0;
		int largest = nums[0];
		
		//once again we need to find the largest int... so we can use its length to determine how many times we need to apply a sort loooking at only one digit of each int
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > largest) {
				largest = nums[i];
			}
			comparisons++;
		}

		for (int digitFocus = 0; digitFocus < String.valueOf(largest).length(); digitFocus++) {
			
			//iterate through the whole list
			buckets = new int[10];
			for (int i = 0; i < nums.length; i++) {
				//find out which digit we want to look at
				digit = nums[i];
				for (int x = 0; x < digitFocus; x++) { //continually shaves off the rightmost digit of our integer until we get to our desired digit
					digit /= 10;
				}				
				buckets[digit%10]++; //get the value of that digit we want to look at and at it to the counter list
				arrayAccesses++;
			}
			//now, we accumalate sum of our counter list
			for (int i = 1; i < 10; i++) {
				buckets[i] += buckets[i-1];
				arrayAccesses++;
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
				arrayAccesses += 2;
			}
			
			//java is cringe and doesn't have pointers so I have to copy-paste all the code and manually change the pointers 
			digitFocus++;
			if (digitFocus >= String.valueOf(largest).length()) {
				return memoryNums;
			}
			
			//iterate through the whole list
			buckets = new int[10];
			for (int i = 0; i < memoryNums.length; i++) {
				//find out which digit we want to look at
				digit = memoryNums[i];
				for (int x = 0; x < digitFocus; x++) { //continually shaves off the rightmost digit of our integer until we get to our desired digit
					digit /= 10;
				}				
				buckets[digit%10]++; //get the value of that digit we want to look at and at it to the counter list
				arrayAccesses++;
			}
			//now, we accumalate sum of our counter list
			for (int i = 1; i < 10; i++) {
				buckets[i] += buckets[i-1];
				arrayAccesses++;
			}
			//now we reiterate through our list but backwards, using clever math to determine which index each integer belongs in
			for (int i = memoryNums.length-1; i >= 0; i--) {
				//find out which digit we want to look at
				digit = memoryNums[i];
				for (int x = 0; x < digitFocus; x++) { //continually shaves off the rightmost digit of our integer until we get to our desired digit
					digit /= 10;
				}
				buckets[digit%10]--;
				nums[buckets[digit%10]] = memoryNums[i];
				arrayAccesses += 2;
			}
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
				return false;
			}
		}
		return true;
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