package com.test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;



public class ByteUtil {

    private static byte[] m_datapadding = { 0x00 }; // 填充空白，以补足字节位数.

    public static void putString(ByteBuffer buffer, String s, int maxlen) {
        try {
            int num = s.getBytes("utf-8").length;
            if (num > maxlen) {
                // todo trim it!
                byte[] bys = new byte[maxlen];
                System.arraycopy(s.getBytes("utf-8"), 0, bys, 0, maxlen);
                buffer.put(bys);
                // addByte(bys);
            } else {
                buffer.put(s.getBytes("utf-8"));
                for (int i = num; i < maxlen; i++) {
                    // 不足位的补空格
                    buffer.put(m_datapadding);		
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] formatLong(Long v) {
        byte[] w = new byte[8];
        w[0] = (v.byteValue());
        w[1] = (byte) (v >> 8);
        w[2] = (byte) (v >> 16);
        w[3] = (byte) (v >> 24);
        w[4] = (byte) (v >> 32);
        w[5] = (byte) (v >> 40);
        w[6] = (byte) (v >> 48);
        w[7] = (byte) (v >> 56);

        return null;
    }

    public static byte[] getBytesBak(Long val) {
        ByteBuffer buf = ByteBuffer.allocate(8);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.asLongBuffer().put(val);
        return buf.array();
    }

    public static byte[] getBytes(Long val) {
        ByteBuffer buf = ByteBuffer.allocate(8);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.putInt(0);
        buf.putInt(val.intValue());
        return buf.array();
    }

    /**
     * @Title: getUnsignIntBytesFromLong
     * @Description: 从long类型中获取unsign int
     * @param value
     * @return
     * @return byte[]
     * @throws
     */
   /* public static byte[] getUnsignIntBytesFromLong(long value) {
        Integer sign = (int) (value / 1000);
        long asLong = sign & 0xffffffffL;
        String bb = Long.toString(asLong, 16);
        System.out.println(bb);
        byte[] result = Hex.decode(bb);
        return result;
    }*/

    public static void main(String[] args) {
        // byte[] bs = new byte[16];
        // bs[0] = -106;
        // bs[1] = -25;
        // bs[2] = -110;
        // bs[3] = 24;
        // bs[4] = -106;
        // bs[5] = 94;
        // bs[6] = -73;
        // bs[7] = 44;
        // bs[8] = -110;
        // bs[9] = -91;
        // bs[10] = 73;
        // bs[11] = -35;
        // bs[12] = 90;
        // bs[13] = 51;
        // bs[14] = 1;
        // bs[15] = 18;
        // System.out.println(printHexString(bs).toUpperCase());

        System.out.println(bytesToHexString(getBytesBak(0L)));
        System.out.println(bytesToHexString(getBytesBak(1L)));
        System.out.println(bytesToHexString(getBytesBak(Integer.MAX_VALUE + 0L)));
        System.out.println(bytesToHexString(getBytesBak(Integer.MAX_VALUE + 1L)));
        System.out.println(bytesToHexString(getBytesBak(Long.MAX_VALUE + 0L)));
        System.out.println(bytesToHexString(getBytesBak(Long.MAX_VALUE + 1L)));
        System.out.println(bytesToHexString(getBytesBak((System.currentTimeMillis() / 1000))));
        System.out.println(Integer.toHexString((int) (System.currentTimeMillis() / 1000)));
        System.out.println();
        System.out.println(bytesToHexString(getBytes(0L)));
        System.out.println(bytesToHexString(getBytes(1L)));
        System.out.println(bytesToHexString(getBytes(Integer.MAX_VALUE + 0L)));
        System.out.println(bytesToHexString(getBytes(Integer.MAX_VALUE + 1L)));
        System.out.println(bytesToHexString(getBytes(Long.MAX_VALUE + 0L)));
        System.out.println(bytesToHexString(getBytes(Long.MAX_VALUE + 1L)));
        System.out.println(bytesToHexString(getBytes((System.currentTimeMillis() / 1000))));
        System.out.println(Integer.toHexString((int) (System.currentTimeMillis() / 1000)));

        ByteBuffer bb = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN);
        bb.put(getBytes(1L));
        System.out.println(bb.getLong());

        ByteBuffer bb2 = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN);
        ByteUtil.putString(bb2, bytesToHexString(getBytes(1L)), 8);
        System.out.println(Arrays.toString(bb2.array()));

    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String printHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        } // end for
        return sb.toString().toUpperCase();
    }
}

