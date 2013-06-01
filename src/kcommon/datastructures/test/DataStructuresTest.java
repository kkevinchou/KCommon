package kcommon.datastructures.test;

import kcommon.datastructures.SlidingList;
import kcommon.unittests.Test;

public class DataStructuresTest extends Test {

	@Override
	public void setup() {
		System.out.println("=== DataStructuresTest ===");
	}

	@Override
	public void run() {
		slidingListTest();
	}
	
	private void slidingListTest() {
		SlidingList<Integer> list = new SlidingList<Integer>(2);
		
		list.push(0);
		
		tAssert(list.get().get(0), 0);
		tAssert(list.get().size(), 1);
		
		list.push(1);
		

		tAssert(list.get().get(1), 1);
		tAssert(list.get().size(), 2);
		
		list.push(2);
		
		tAssert(list.get().get(0), 1);
		tAssert(list.get().get(1), 2);
		tAssert(list.get().size(), 2);
	}
	
	// TODO: Test adding bodies outside of QuadTree range
}
