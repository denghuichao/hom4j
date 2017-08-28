package com.dhc.github.framework.parser;


import org.apache.hadoop.hbase.util.Bytes;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by hcdeng on 17-8-28.
 */
public class TypeParsers {

    private static TypeParser ObjectParser = new TypeParser(){

        @Override
        protected boolean canParseToBytes(Object o) {
            return true;
        }

        @Override
        protected Class<?>[] getTypes() {
            return new Class<?>[] {Object.class};
        }

        @Override
        public Object bytesToObject(byte[] bytes) {
            try {
                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                ObjectInputStream is = new ObjectInputStream(in);
                return is.readObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public byte[] objectToBytes(Object object) {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(out);
                os.writeObject(object);
                return out.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private static TypeParser[] primaryParsers = new TypeParser[]{

            new TypeParser<Boolean>() {
                @Override
                protected Class<?>[] getTypes() {
                    return new Class<?>[]{Boolean.class, boolean.class};
                }

                @Override
                public Boolean bytesToObject(byte[] bytes) {
                    return Bytes.toBoolean(bytes);
                }

                @Override
                public byte[] objectToBytes(Boolean object) {
                    return Bytes.toBytes(object);
                }
            },

            new TypeParser<Byte>() {
                @Override
                protected Class<?>[] getTypes() {
                    return new Class<?>[]{Byte.class, byte.class};
                }

                @Override
                public Byte bytesToObject(byte[] bytes) {
                    return bytes[0];
                }

                @Override
                public byte[] objectToBytes(Byte object) {
                    return new byte[]{object};
                }
            },

            new TypeParser<Character>() {

                @Override
                protected Class<?>[] getTypes() {
                    return new Class<?>[]{Character.class, char.class};
                }

                @Override
                public Character bytesToObject(byte[] bytes) {
                    return (char) (((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF));
                }

                @Override
                public byte[] objectToBytes(Character object) {
                    Character c = (object);
                    return new byte[]{(byte) ((c & 0xFF00) >> 8), (byte) (c & 0xFF)};
                }
            },

            new TypeParser<Short>() {

                @Override
                protected Class<?>[] getTypes() {
                    return new Class<?>[]{Short.class, short.class};
                }

                @Override
                public Short bytesToObject(byte[] bytes) {
                    return Bytes.toShort(bytes);
                }

                @Override
                public byte[] objectToBytes(Short object) {
                    return Bytes.toBytes(object);
                }
            },

            new TypeParser<Integer>() {

                @Override
                protected Class<?>[] getTypes() {
                    return new Class<?>[]{Integer.class, int.class};
                }

                @Override
                public Integer bytesToObject(byte[] bytes) {
                    return Bytes.toInt(bytes);
                }

                @Override
                public byte[] objectToBytes(Integer object) {
                    return Bytes.toBytes(object);
                }
            },

            new TypeParser<Long>() {
                @Override
                protected Class<?>[] getTypes() {
                    return new Class<?>[]{Long.class, long.class};
                }

                @Override
                public Long bytesToObject(byte[] bytes) {
                    return Bytes.toLong(bytes);
                }

                @Override
                public byte[] objectToBytes(Long object) {
                    return Bytes.toBytes(object);
                }
            },

            new TypeParser<Float>() {
                @Override
                protected Class<?>[] getTypes() {
                    return new Class<?>[]{Float.class, float.class};
                }

                @Override
                public Float bytesToObject(byte[] bytes) {
                    return Bytes.toFloat(bytes);
                }

                @Override
                public byte[] objectToBytes(Float object) {
                    return Bytes.toBytes(object);
                }
            },

            new TypeParser<Double>() {
                @Override
                public Double bytesToObject(byte[] bytes) {
                    return Bytes.toDouble(bytes);
                }

                @Override
                public byte[] objectToBytes(Double object) {
                    return Bytes.toBytes(object);
                }

                @Override
                protected Class<?>[] getTypes() {
                    return new Class<?>[]{Double.class, double.class};
                }
            },
            new TypeParser<BigInteger>() {
                @Override
                public BigInteger bytesToObject(byte[] bytes) {
                    return new BigInteger(bytes);
                }

                @Override
                public byte[] objectToBytes(BigInteger object) {
                    return (object).toByteArray();
                }

                @Override
                protected Class<?>[] getTypes() {
                    return new Class<?>[]{BigInteger.class};
                }
            },
            new TypeParser<BigDecimal>() {
                @Override
                protected Class<?>[] getTypes() {
                    return new Class<?>[]{BigDecimal.class};
                }

                @Override
                public BigDecimal bytesToObject(byte[] bytes) {
                    return Bytes.toBigDecimal(bytes);
                }

                @Override
                public byte[] objectToBytes(BigDecimal object) {
                    return Bytes.toBytes(object);
                }
            },

            new TypeParser<String>() {
                @Override
                protected Class<?>[] getTypes() {
                    return new Class<?>[]{String.class};
                }

                @Override
                public String bytesToObject(byte[] bytes) {
                    return Bytes.toString(bytes);
                }

                @Override
                public byte[] objectToBytes(String object) {
                    return Bytes.toBytes(object);
                }
            },

            new TypeParser<Date>() {
                @Override
                protected Class<?>[] getTypes() {
                    return new Class<?>[]{Date.class};
                }

                @Override
                public Date bytesToObject(byte[] bytes) {
                    return new Date(Bytes.toLong(bytes));
                }

                @Override
                public byte[] objectToBytes(Date object) {
                    return Bytes.toBytes(object.getTime());
                }
            }
    };

    private static final Map<Class<?>, TypeParser<?>> DEFAULT_PARSERS;

    static {
        DEFAULT_PARSERS = new LinkedHashMap<>();
        for (TypeParser parser : primaryParsers) {
            for (Class<?> type : parser.getTypes()) {
                DEFAULT_PARSERS.put(type, parser);
            }
        }
    }

    public static TypeParser getTypeParser(Class<?> type) {
        return DEFAULT_PARSERS.getOrDefault(type, ObjectParser);
    }

    public static <T> byte[] toBytes(T t){
        return getTypeParser(t.getClass()).toBytes(t);
    }

    public static <T> T fromBytes(Class<T> type, byte[] bytes){
        return (T)getTypeParser(type).fromBytes(bytes);
    }
}
