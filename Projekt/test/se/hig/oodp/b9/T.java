package se.hig.oodp.b9;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
public class T
{
    public static Object getField(Object obj, String fieldName)
    {
        try
        {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <A> A serialization(A in)
    {
        try
        {
            byte[] data;

            try (ByteArrayOutputStream outStream = new ByteArrayOutputStream())
            {
                try (ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream))
                {
                    objectOutStream.writeObject(in);
                    data = outStream.toByteArray();
                }
            }

            A out;

            try (ByteArrayInputStream inStream = new ByteArrayInputStream(data))
            {
                try (ObjectInputStream objectOutStream = new ObjectInputStream(inStream))
                {
                    out = (A) objectOutStream.readObject();
                }
            }

            return out;
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }

        return null;
    }
}
