package se.hig.oodp;

public interface FigureHandler
{
	public void createCircle (double x, double y, double r);
	public void createEllipse (double x, double y, double a, double b);
	public void createLine (double x0, double y0, double x1, double y1);
	public void createPoint (double x, double y);
	public void createRectangle (double x, double y, double a, double b);
	public void createSquare (double x, double y, double a, double b);
	public void createTriangle (double vx0, double vy0, double vx1, double vy1, double vx2, double vy2);
	public void removeAll ();
}
