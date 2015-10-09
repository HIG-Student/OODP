/**
 * 
 */
package se.hig.oodp.b9.view;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import se.hig.oodp.b9.data.Shape;
import se.hig.oodp.b9.model.ShapeControl;

public class ShapeIO
{
    public static void showLoadShapesDialog(Component owner, ShapeControl shapeControl)
    {
        try
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load shapes");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Shapes list", "shp");
            fileChooser.setFileFilter(filter);
            fileChooser.addChoosableFileFilter(filter);
            fileChooser.setFileHidingEnabled(false);

            if (fileChooser.showOpenDialog(owner) == JFileChooser.APPROVE_OPTION)
            {
                File fileToLoad = fileChooser.getSelectedFile();
                if (!fileToLoad.exists() && !fileToLoad.getName().endsWith(".shp"))
                {
                    JOptionPane.showMessageDialog(owner, "You have to load an .shp file!", "Wrong file type!", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    loadShapes(fileToLoad, shapeControl);
                }
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(owner, "Error while loading file!\n\n" + e.getMessage(), "Error on loading!", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void loadShapes(File fileToLoad, ShapeControl shapeControl) throws Exception
    {
        FileInputStream fileIn = new FileInputStream(fileToLoad);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Shape[] newShapes = (Shape[]) in.readObject();
        in.close();
        fileIn.close();
        shapeControl.loadShapes(newShapes);
    }

    public static void showSaveShapesDialog(Component owner, ShapeControl shapeControl)
    {
        try
        {
            File file = saveFileDialog(owner, "Save shapes", "shp");
            saveShapes(file, shapeControl);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(owner, "Error on saving!\n\n" + e.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void saveShapes(File file, ShapeControl shapeControl) throws Exception
    {
        // http://www.tutorialspoint.com/java/java_serialization.htm

        if (file == null)
            throw new Exception("No file chosen");
        file.createNewFile();
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(shapeControl.getShapes());
        out.close();
        fileOut.close();
    }

    public static File saveFileDialog(Component owner, String title, String fileEnding) throws Exception
    {
        // http://www.codejava.net/java-se/swing/show-save-file-dialog-using-jfilechooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(fileEnding, fileEnding);
        fileChooser.setFileFilter(filter);
        fileChooser.addChoosableFileFilter(filter);

        if (fileChooser.showSaveDialog(owner) == JFileChooser.APPROVE_OPTION)
        {
            File fileToSave = fileChooser.getSelectedFile();
            if (fileToSave.exists() && !fileToSave.getName().endsWith("." + fileEnding))
            {
                throw new Exception("You have to save as ." + fileEnding + " !");
            }
            else
            {
                String fileName = fileToSave.getAbsolutePath();
                if (!fileName.endsWith("." + fileEnding))
                    fileName = fileName + "." + fileEnding;

                fileToSave = new File(fileName);
                fileToSave.createNewFile();
                return fileToSave;
            }
        }
        else
            return null;
    }
}
