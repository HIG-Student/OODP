package se.hig.oodp.b9;

import static org.junit.Assert.*;

import org.junit.*;

import java.lang.reflect.Method;

import se.hig.oodp.Vertex2D;

public class T 
{
	public static final double PRECISION = 0.05;
	
/*	public static void testVertex2D(Object obj,String method_name,Vertex2D vertex)
	{
		for(Method m : obj.getClass().getMethods())
		{
			if(m.getName().equals(method_name))
			{
				try 
				{
					assertTrue(m.getName() + " is off",((Vertex2D)m.invoke(obj, null)).dist(vertex) <= T.PRECISION);
				} 
				catch (Exception e) 
				{
					fail("Exception occured!");
				}
				return;
			}
		}
		
		fail("Method not found");
	}
	
	public static void testDouble2D(Object obj,String method_name,double d)
	{
		for(Method m : obj.getClass().getMethods())
		{
			if(m.getName().equals(method_name))
			{
				try 
				{
					assertTrue(m.getName() + " is off",Math.abs(((double)m.invoke(obj, null)) - d) <= T.PRECISION);
				} 
				catch (Exception e) 
				{
					fail("Exception occured!");
				}
				return;
			}
		}
		
		fail("Method not found");
	}*/
}
