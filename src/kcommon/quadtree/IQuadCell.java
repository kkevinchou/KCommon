package kcommon.quadtree;

public interface IQuadCell {
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
	
	public boolean intersects(IQuadElement quadElement);
}
