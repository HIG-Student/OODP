package se.hig.oodp;

import java.util.List;

class FigurePrinterImpl implements FigurePrinter
{
	List<FigureType> figures;
	
	public FigurePrinterImpl (List<FigureType> figures)
	{
		this.figures = figures;
	}
	
	@Override
	public void printAll ()
	{
		for (FigureType f : figures)
			System.out.println (f);
	}
}
