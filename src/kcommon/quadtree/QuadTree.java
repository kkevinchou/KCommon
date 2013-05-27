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
	private int maxDepth = 4;
	private int depth;
	private Rectangle dimension;
	private QuadTree[] nodes;
	private List<IQuadElement> elements;

	public QuadTree(float x, float y, float width, float height, int maxChildren) {
		this(x, y, width, height, 0, maxChildren);
	}
	
	private QuadTree(float x, float y, float width, float height, int depth, int maxChildren) {
		elements = new ArrayList<IQuadElement>();
		dimension = new Rectangle(x, y, width, height);
		nodes = null;
		this.depth = depth;
		this.maxChildren = maxChildren;
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
		
		Vector2D boxTopLeft = new Vector2D(boundingBox.x, boundingBox.y);
		Vector2D boxBtmRight = new Vector2D(boundingBox.x + boundingBox.width - 1, boundingBox.y + boundingBox.height - 1);
		
		Vector2D treeTopLeft = new Vector2D(dimension.x, dimension.y);
		Vector2D treeBtmRight = new Vector2D(dimension.x + dimension.width - 1, dimension.y + dimension.height - 1);
		
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
		
		float childWidth = dimension.width / 2;
		float childHeight = dimension.height / 2;
		int childDepth = depth + 1;
		
		nodes[1] = new QuadTree(dimension.x, dimension.y, childWidth, childHeight, childDepth, maxChildren);
		nodes[0] = new QuadTree(dimension.x + childWidth, dimension.y, dimension.width - childWidth, childHeight, childDepth, maxChildren);
		
		nodes[2] = new QuadTree(dimension.x, dimension.y + childHeight, childWidth, dimension.height - childHeight, childDepth, maxChildren);
		nodes[3] = new QuadTree(dimension.x + childWidth, dimension.y + childHeight, dimension.width - childWidth, dimension.height - childHeight, childDepth, maxChildren);
		
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
