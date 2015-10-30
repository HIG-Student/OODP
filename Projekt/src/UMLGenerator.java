import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import se.hig.oodp.b9.logic.Event;
import se.hig.oodp.b9.logic.server.*;
import se.hig.oodp.b9.logic.client.*;
import se.hig.oodp.b9.gui.server.*;
import se.hig.oodp.b9.gui.client.*;
import se.hig.oodp.b9.model.*;
import se.hig.oodp.b9.*;

// UML: http://pages.cs.wisc.edu/~hasti/cs302/examples/UMLdiagram.html
// UML: http://www.cs.bsu.edu/homepages/pvg/misc/uml/

public class UMLGenerator
{
    public static void main(String[] args)
    {
        generateUML(Event.class);
    }

    public static void generateUML(Class<?> c)
    {
        StringBuilder UMLString = new StringBuilder();

        if (c.isInterface())
            UMLString.append("<<interface>>\n");
        else
            if (Modifier.isAbstract(c.getModifiers()))
                UMLString.append("<<abstract>>\n");

        if (Modifier.isStatic((c.getModifiers())))
            UMLString.append("<<static>>\n");

        UMLString.append(c.getSimpleName() + "\n");

        UMLString.append("--\n");

        for (Field field : c.getDeclaredFields())
        {
            if (Modifier.isPublic(field.getModifiers()))
                UMLString.append("+");
            else
                if (Modifier.isPrivate(field.getModifiers()))
                    UMLString.append("-");
                else
                    if (Modifier.isProtected(field.getModifiers()))
                        UMLString.append("#");
                    else
                        UMLString.append(" ");

            UMLString.append(" ");

            if (Modifier.isStatic((field.getModifiers())))
                UMLString.append("<<static>> ");

            if (Modifier.isStatic((field.getModifiers())))
                UMLString.append("<<final>> ");

            UMLString.append(field.getName());

            UMLString.append(" : ");

            UMLString.append(field.getType().getSimpleName());

            UMLString.append("\n");
        }

        UMLString.append("--\n");

        for (Constructor<?> constructor : c.getDeclaredConstructors())
        {
            if (Modifier.isPublic(constructor.getModifiers()))
                UMLString.append("+");
            else
                if (Modifier.isPrivate(constructor.getModifiers()))
                    UMLString.append("-");
                else
                    if (Modifier.isProtected(constructor.getModifiers()))
                        UMLString.append("#");
                    else
                        UMLString.append(" ");

            UMLString.append(" ");

            UMLString.append(c.getSimpleName());

            UMLString.append("( ");

            for (Class<?> p : constructor.getParameterTypes())
                UMLString.append(p.getSimpleName() + " , ");

            if (constructor.getParameterTypes().length > 0)
                UMLString.setLength(UMLString.length() - 2);

            UMLString.append(")\n");
        }

        for (Method method : c.getDeclaredMethods())
        {
            if (method.isSynthetic())
                continue;

            if (Modifier.isPublic(method.getModifiers()))
                UMLString.append("+");
            else
                if (Modifier.isPrivate(method.getModifiers()))
                    UMLString.append("-");
                else
                    if (Modifier.isProtected(method.getModifiers()))
                        UMLString.append("#");
                    else
                        UMLString.append(" ");

            UMLString.append(" ");

            if (Modifier.isStatic((method.getModifiers())))
                UMLString.append("<<static>> ");

            if (Modifier.isAbstract(method.getModifiers()) && !c.isInterface())
                UMLString.append("<<abstract>> ");

            UMLString.append(method.getName());

            UMLString.append("( ");

            for (Class<?> p : method.getParameterTypes())
                UMLString.append(p.getSimpleName() + " , ");

            if (method.getParameterTypes().length > 0)
                UMLString.setLength(UMLString.length() - 2);

            UMLString.append(")");

            UMLString.append(" : ");

            UMLString.append(method.getReturnType().getSimpleName());

            UMLString.append("\n");
        }

        System.out.println(UMLString.toString());
    }
}
