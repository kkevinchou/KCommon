package kcommon.quadtree.test;

import java.util.List;


import kcommon.geometry.Vector2D;
import kcommon.quadtree.IQuadElement;
import kcommon.quadtree.QuadTree;
import kcommon.unittests.Test;

public class QuadTreeTest extends Test {

	@Override
	public void setup() {
		System.out.println("=== QuadTreeTest ===");
	}

	@Override
	public void run() {
		fractionalPositionTest();
		boundaryTest();
		bodyIntersectionTest();
	}
	
	private void boundaryTest() {
		QuadTree q = new QuadTree(0, 0, 5, 5, 4, 4);
		
		TestQuadElement body = new TestQuadElement(0, 0, 2, 2);
		
		body.setPosition(new Vector2D(0, 0));
		tAssert(q.intersects(body), true);
		
		body.setPosition(new Vector2D(-1, 0));
		tAssert(q.intersects(body), true);
		
		body.setPosition(new Vector2D(4, 0));
		tAssert(q.intersects(body), true);
		
		body.setPosition(new Vector2D(5, 0));
		tAssert(q.intersects(body), false);
		
		body.setPosition(new Vector2D(0, -1));
		tAssert(q.intersects(body), true);
		
		body.setPosition(new Vector2D(0, -2));
		tAssert(q.intersects(body), false);
		
		body.setPosition(new Vector2D(0, -1));
		tAssert(q.intersects(body), true);
		
		body.setPosition(new Vector2D(0, 3));
		tAssert(q.intersects(body), true);
		
		body.setPosition(new Vector2D(0, 5));
		tAssert(q.intersects(body), false);
	}
	
	private void bodyIntersectionTest() {
		QuadTree q = new QuadTree(0, 0, 5, 5, 4, 4);
		
		TestQuadElement body1 = new TestQuadElement(2, 0, 2, 2); // node 0
		TestQuadElement body2 = new TestQuadElement(0, 0, 2, 2); // node 1
		TestQuadElement body3 = new TestQuadElement(0, 2, 2, 2); // node 2
		TestQuadElement body4 = new TestQuadElement(3, 3, 2, 2); // node 3
		TestQuadElement body5 = new TestQuadElement(3, 3, 2, 2); // node 3
		
		q.add(body1);
		q.add(body2);
		q.add(body3);
		q.add(body4);
		q.add(body5);
		
		TestQuadElement testBody = new TestQuadElement(0, 0, 2, 2); // node 0
		List<IQuadElement> candidates;
		
		testBody.setPosition(new Vector2D(3, 0));
		candidates = q.getIntersectionCandidates(testBody);
		tAssert(candidates.size(), 1);
		
		testBody.setPosition(new Vector2D(0, 0));
		candidates = q.getIntersectionCandidates(testBody);
		tAssert(candidates.size(), 3);
		
		testBody.setPosition(new Vector2D(0, 2));
		candidates = q.getIntersectionCandidates(testBody);
		tAssert(candidates.size(), 3);
		
		testBody.setPosition(new Vector2D(1, 0));
		candidates = q.getIntersectionCandidates(testBody);
		tAssert(candidates.size(), 3);
		
		testBody.setPosition(new Vector2D(1, 2));
		candidates = q.getIntersectionCandidates(testBody);
		tAssert(candidates.size(), 5);
		
		testBody.setPosition(new Vector2D(1, 1));
		candidates = q.getIntersectionCandidates(testBody);
		tAssert(candidates.size(), 5);
	}
	
	private void fractionalPositionTest() {
		QuadTree q = new QuadTree(0, 0, 2, 2, 4, 4);
		
		q.add(new TestQuadElement(1, 0, 1, 1));
		q.add(new TestQuadElement(1, 1, 1, 1));
		q.add(new TestQuadElement(1, 1, 1, 1));
		q.add(new TestQuadElement(1, 1, 1, 1));
		q.add(new TestQuadElement(1, 1, 1, 1));
		
		TestQuadElement testBody = new TestQuadElement(0.5f, 0, 1, 1);
		
		List<IQuadElement> candidates = q.getIntersectionCandidates(testBody);
		tAssert(candidates.size(), 1);
	}
	
	// TODO: Test adding bodies outside of QuadTree range
}
