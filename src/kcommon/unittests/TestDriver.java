package kcommon.unittests;

import java.util.ArrayList;
import java.util.List;

import kcommon.datastructures.test.DataStructuresTest;
import kcommon.quadtree.test.QuadTreeTest;

public final class TestDriver {
	public static void main(String[] args) {
		List<Test> tests = new ArrayList<Test>();
		
		Test a = new QuadTreeTest();
		Test b = new DataStructuresTest();
		
		tests.add(a);
		tests.add(b);
		
		System.out.println("Starting Tests");
		System.out.println("*****************");
		System.out.println("");
		
		for (Test test : tests) {
			test.setup();
			test.run();
		}

		System.out.println("");
		System.out.println("*****************");
		System.out.println("Tests Complete!");
	}
}
