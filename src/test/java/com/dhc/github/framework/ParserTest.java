package com.dhc.github.framework;

import com.dhc.github.framework.parser.TypeParsers;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by hcdeng on 17-8-28.
 */
public class ParserTest {
    @Test
    public void testIntParser(){
        int a = 123;
        byte[] bytes = TypeParsers.toBytes(a);
        int b = TypeParsers.fromBytes(int.class, bytes);
        Assert.assertEquals(a, b);

        Integer c = 123456;
        byte[] cbytes = TypeParsers.toBytes(c);
        Assert.assertEquals(c, TypeParsers.fromBytes(Integer.class, cbytes));
    }

    @Test
    public void testBooleanParser(){
        boolean a = true;
        byte[] bytes = TypeParsers.toBytes(a);
        boolean b = TypeParsers.fromBytes(boolean.class, bytes);
        Assert.assertEquals(a, b);

        Boolean c = false;
        byte[] cbytes = TypeParsers.toBytes(c);
        Assert.assertEquals(c, TypeParsers.fromBytes(Boolean.class, cbytes));
    }


    @Test
    public void testByteParser(){
        byte a = 123;
        byte[] bytes = TypeParsers.toBytes(a);
        byte b = TypeParsers.fromBytes(byte.class, bytes);
        Assert.assertEquals(a, b);

        Byte c = 22;
        byte[] cbytes = TypeParsers.toBytes(c);
        Assert.assertEquals(c, TypeParsers.fromBytes(Byte.class, cbytes));
    }

    @Test
    public void testCharParser(){
        char a = 'A';
        byte[] bytes = TypeParsers.toBytes(a);
        int b = TypeParsers.fromBytes(char.class, bytes);
        Assert.assertEquals(a, b);

        Character c = 'D';
        byte[] cbytes = TypeParsers.toBytes(c);
        Assert.assertEquals(c, TypeParsers.fromBytes(Character.class, cbytes));
    }

    @Test
    public void testLongParser(){
        long a = 123456;
        byte[] bytes = TypeParsers.toBytes(a);
        long b = TypeParsers.fromBytes(long.class, bytes);
        Assert.assertEquals(a, b);

        Long c = 1234567l;
        byte[] cbytes = TypeParsers.toBytes(c);
        Assert.assertEquals(c, TypeParsers.fromBytes(Long.class, cbytes));
    }

    @Test
    public void testDoubleParser(){
        double a = 123;
        byte[] bytes = TypeParsers.toBytes(a);
        double b = TypeParsers.fromBytes(double.class, bytes);
        Assert.assertEquals(Double.compare(a, b), 0);

        Double c = 123456.00;
        byte[] cbytes = TypeParsers.toBytes(c);
        Assert.assertEquals(c, TypeParsers.fromBytes(Double.class, cbytes));
    }

    @Test
    public void testFloatParser(){
        float a = 123;
        byte[] bytes = TypeParsers.toBytes(a);
        float b = TypeParsers.fromBytes(float.class, bytes);
        Assert.assertEquals(Float.compare(a, b),0);

        Float c = 123456f;
        byte[] cbytes = TypeParsers.toBytes(c);
        Assert.assertEquals(c, TypeParsers.fromBytes(Float.class, cbytes));
    }

    @Test
    public void testDateParser(){
        Date a = new Date();
        byte[] bytes = TypeParsers.toBytes(a);
        Date b = TypeParsers.fromBytes(Date.class, bytes);
        Assert.assertEquals(a, b);

    }

    @Test
    public void testStringParser(){
        String a =  "abcdefg";
        byte[] bytes = TypeParsers.toBytes(a);
        String b = TypeParsers.fromBytes(String.class, bytes);
        Assert.assertEquals(a, b);
    }

    @Test
    public void testBigIntegerParser(){
        BigInteger a = BigInteger.valueOf(1233425);
        byte[] bytes = TypeParsers.toBytes(a);
        BigInteger b = TypeParsers.fromBytes(BigInteger.class, bytes);
        Assert.assertEquals(a, b);
    }

    @Test
    public void testBigDecimalParser(){
        BigDecimal a = BigDecimal.valueOf(1233425);
        byte[] bytes = TypeParsers.toBytes(a);
        BigDecimal b = TypeParsers.fromBytes(BigDecimal.class, bytes);
        Assert.assertEquals(a, b);
    }

    @Test
    public void testObjectParser(){
        Book a = new Book();
        byte[] bytes = TypeParsers.toBytes(a);
        Book b = TypeParsers.fromBytes(Book.class, bytes);
        Assert.assertNotNull(b);
    }
}
