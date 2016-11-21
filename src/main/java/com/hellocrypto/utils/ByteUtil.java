package com.hellocrypto.utils;

/**
 *
 * @author xulei
 * 
 */
public class ByteUtil {

    /**
     * binary to hex
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * hex to binary
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    
    // 4 bytes little endian integer
    public static byte[] int2byte(int res) {
        byte[] result = new byte[4];
        result[0] = (byte) (res & 0xff);
        result[1] = (byte) ((res >> 8) & 0xff);
        result[2] = (byte) ((res >> 16) & 0xff);
        result[3] = (byte) (res >>> 24);
        return result;
    }

    // 4 bytes little endian integer
    public static int byte2int(byte[] res) {   
        int result = (res[0] & 0xff) | ((res[1] << 8) & 0xff00)   
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return result;
    }
    
}
