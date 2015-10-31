package se.hig.oodp.b9.gui;

import javax.swing.JTextArea;

/**
 * Area with multi-line text <br>
 * <br>
 * Code from <a href="#http://stackoverflow.com/a/5816711"> Michael
 * Clark</a>
 */
@SuppressWarnings("serial")
public class TextNode extends JTextArea
{
    /**
     * Create text node
     * 
     * @param text
     *            the text to display
     */
    public TextNode(String text)
    {
        super(text);
        setBackground(null);
        setEditable(false);
        setBorder(null);
        setLineWrap(true);
        setWrapStyleWord(true);
        setFocusable(false);
    }
}