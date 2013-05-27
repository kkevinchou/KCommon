package kcommon.quadtree.test;

import kcommon.geometry.Rectangle;
import kcommon.geometry.Vector2D;
import kcommon.quadtree.IQuadElement;

public final class TestQuadElement implements IQuadElement {
	private Rectangle rect;
	
	public TestQuadElement(float x, float y, float width, float height) {
		rect = new Rectangle(x, y, width, height);
	}

	@Override
	public Rectangle getAABoundingBox() {
		return rect;
	}
	
	public void setPosition(Vector2D position) {
		rect.setX(position.getX());
		rect.setY(position.getY());
	}
}
