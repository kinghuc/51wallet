package com.carefree.coldwallet.commons.sign.ecdsa;

import java.math.BigInteger;

/**
 * Created by wb on 2018/3/23.
 */
public interface IKeyPair {
    String canonicalPubHex();
    byte[] canonicalPubBytes();

    BigInteger pub();
    BigInteger priv();
    String privHex();

    boolean verifySignature(byte[] message, byte[] sigBytes);
    byte[] signMessage(byte[] message);

    byte[] pub160Hash();
}
