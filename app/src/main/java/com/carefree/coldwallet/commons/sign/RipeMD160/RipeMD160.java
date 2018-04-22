package com.carefree.coldwallet.commons.sign.RipeMD160;

import org.apaches.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;

/**
 * Created by wb on 2018/3/26.
 */

public class RipeMD160 {
    public static String HashRipeMD160(byte [] data) {
        RIPEMD160Digest ripemd160 = new RIPEMD160Digest();
        ripemd160.update(data, 0, data.length);
        byte[] output = new byte[ripemd160.getDigestSize()];
        ripemd160.doFinal(output, 0);
        return Hex.encodeHexString(output);
    }


}
