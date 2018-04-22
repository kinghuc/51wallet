package com.carefree.coldwallet.commons.sign.ecdsa;

import org.ripple.bouncycastle.asn1.sec.SECNamedCurves;
import org.ripple.bouncycastle.asn1.x9.X9ECParameters;
import org.ripple.bouncycastle.crypto.params.ECDomainParameters;
import org.ripple.bouncycastle.math.ec.ECCurve;
import org.ripple.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;


/**
 * Created by wb on 2018/3/23.
 */
public class SECP256K1 {
    private static final ECDomainParameters ecParams;
    private static final X9ECParameters params;

    static {

        params = SECNamedCurves.getByName("secp256k1");
        ecParams = new ECDomainParameters(params.getCurve(), params.getG(), params.getN(), params.getH());
    }

    public static ECDomainParameters params() {
        return ecParams;
    }

    public static BigInteger order() {
        return ecParams.getN();
    }


    public static ECCurve curve() {
        return ecParams.getCurve();
    }

    public static ECPoint basePoint() {
        return ecParams.getG();
    }

    /**
     * 返回压缩格式的公钥
     * 基点运算函数，给定指定的私钥（随机数），计算公钥（Q=kP）
     * @param secret
     * @return Pub
     */
    static byte[] basePointMultipliedBy(BigInteger secret) {
        return basePoint().multiply(secret).getEncoded(true);
    }
    
    /**
     * 返回非压缩格式公钥
     * @param secret
     * @return Pub
     */
    static byte[] basePointMultipliedWithoutCompressed(BigInteger secret) {
        return basePoint().multiply(secret).getEncoded(false);
    }
    
    /**
     * 改写的几点乘法函数，返回公钥对用的点(ECPoint)
     * @param secret
     * @return
     */
    public static ECPoint basePointMultipleReturnPoint(BigInteger secret) {
    	return basePoint().multiply(secret);
    }
    


}
