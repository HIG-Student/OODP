package se.hig.oodp.b9.communication;

import org.junit.Test;

import se.hig.oodp.b9.T;
import se.hig.oodp.b9.model.CardInfo;
import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
public class TestPackage
{
    @Test
    public void testSerialization()
    {
        Package<CardInfo> pkg = new Package<CardInfo>(CardInfo.getRandom(), Package.Type.CardInfo);

        Package<CardInfo> spkg = T.serialization(pkg);

        assertEquals("Not the same type!", pkg.getType(), spkg.getType());
        assertEquals("Not the same value!", pkg.getValue(), spkg.getValue());
    }
}
