package kcommon.unittests;

import java.util.ArrayList;
import java.util.List;

import kcommon.quadtree.Test;
import kcommon.quadtree.test.QuadTreeTest;

public class TestDriver {
	public static void main(String[] args) {
		List<Test> tests = new ArrayList<Test>();
		
		Test a = new QuadTreeTest();
		
		tests.add(a);
		
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
