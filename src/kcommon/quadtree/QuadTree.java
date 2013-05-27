package kcommon.quadtree;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import kcommon.geometry.Rectangle;
import kcommon.geometry.Vector2D;

/*
 * Labels for quad tree children:
 * [1][0]
 * [2][3]
 */

public class QuadTree {
	private int maxChildren;
	private int maxDepth;
	private int depth;
	private Rectangle dimensions;
	private QuadTree[] nodes;
	private List<IQuadElement> elements;

	public QuadTree(float x, float y, float width, float height, int maxChildren, int maxDepth) {
		this(x, y, width, height, 0, maxChildren, maxDepth);
	}
	
	private QuadTree(float x, float y, float width, float height, int depth, int maxChildren, int maxDepth) {
		elements = new ArrayList<IQuadElement>();
		dimensions = new Rectangle(x, y, width, height);
		nodes = null;
		this.depth = depth;
		this.maxChildren = maxChildren;
		this.maxDepth = maxDepth;
	}
	
	public void add(IQuadElement body) {
		if (nodes == null) {
			elements.add(body);
		} else {
			for (int i = 0; i < 4; i++) {
				if (nodes[i].intersects(body)) {
					nodes[i].add(body);
				}
			}
		}
		
		if (elements.size() > maxChildren && depth < maxDepth) {
			split();
		}
	}
	
	public boolean intersects(IQuadElement body) {
		Rectangle boundingBox = body.getAABoundingBox();
		
		Vector2D boxTopLeft = new Vector2D(boundingBox.getX(), boundingBox.getY());
		Vector2D boxBtmRight = new Vector2D(boundingBox.getX() + boundingBox.getWidth() - 1, boundingBox.getY() + boundingBox.getHeight() - 1);
		
		Vector2D treeTopLeft = new Vector2D(dimensions.getX(), dimensions.getY());
		Vector2D treeBtmRight = new Vector2D(dimensions.getX() + dimensions.getWidth() - 1, dimensions.getY() + dimensions.getHeight() - 1);
		
		if (boxTopLeft.getX() >= treeBtmRight.getX() + 1) {
			return false;
		}
		
		if (boxTopLeft.getY() >= treeBtmRight.getY() + 1) {
			return false;
		}
		
		if (treeTopLeft.getX() >= boxBtmRight.getX() + 1) {
			return false;
		}
		
		if (treeTopLeft.getY() >= boxBtmRight.getY() + 1) {
			return false;
		}
		
		return true;
	}
	
	private Set<IQuadElement> getIntersectionCandidatesHelper(IQuadElement body) {
		Set<IQuadElement> result = new LinkedHashSet<IQuadElement>();
		
		if (nodes == null) {
			result.addAll(elements);
			return result;
		} else {
			for (int i = 0; i < 4; i++) {
				if (nodes[i].intersects(body)) {
					result.addAll(nodes[i].getIntersectionCandidatesHelper(body));
				}
			}
		}
		
		return result;
	}
	
	public List<IQuadElement> getIntersectionCandidates(IQuadElement body) {
		List<IQuadElement> result = new ArrayList<IQuadElement>();
		result.addAll(getIntersectionCandidatesHelper(body));
		
		return result;
	}
	
	public void split() {
		nodes = new QuadTree[4];
		
		float childWidth = dimensions.getWidth() / 2;
		float childHeight = dimensions.getHeight() / 2;
		int childDepth = depth + 1;
		
		nodes[1] = new QuadTree(dimensions.getX(), dimensions.getY(), childWidth, childHeight, childDepth, maxChildren);
		nodes[0] = new QuadTree(dimensions.getX() + childWidth, dimensions.getY(), dimensions.getWidth() - childWidth, childHeight, childDepth, maxChildren);
		
		nodes[2] = new QuadTree(dimensions.getX(), dimensions.getY() + childHeight, childWidth, dimensions.getHeight() - childHeight, childDepth, maxChildren);
		nodes[3] = new QuadTree(dimensions.getX() + childWidth, dimensions.getY() + childHeight, dimensions.getWidth() - childWidth, dimensions.getHeight() - childHeight, childDepth, maxChildren);
		
		for (IQuadElement element : elements) {
			for (int i = 0; i < 4; i++) {
				if (nodes[i].intersects(element)) {
					nodes[i].add(element);
				}
			}
		}
		
		elements.clear();
	}
	
	public void clear() {
		elements.clear();
		
		if (nodes != null) {
			for (int i = 0; i < 4; i++) {
				nodes[i].clear();
			}
		}
		nodes = null;
	}
}
