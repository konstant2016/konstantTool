package com.konstant.cardprotocol.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 描述:秘钥生成工具
 * 创建人:菜籽
 * 创建时间:2018/3/29 下午3:36
 * 备注:
 */

public class DiffieHellmanUtils {

    private static final SecureRandom rnd = new SecureRandom();


    public static BigInteger findPrime(int bitLength, int certainty) {
        Random rnd = new Random();
        BigInteger p = BigInteger.ZERO;
        p = new BigInteger(bitLength, certainty, rnd);// sufficiently NSA SAFE?!!
        return p;
    }

    public static BigInteger findPrimeRoot(BigInteger p) {
        int start = 2001;// first best probably precalculated by NSA?

        for (int i = start; i < 100000000; i++)
            if (isPrimeRoot(BigInteger.valueOf(i), p))
                return BigInteger.valueOf(i);
        return BigInteger.valueOf(0);
    }


    private static boolean isPrimeRoot(BigInteger g, BigInteger p) {
        BigInteger subtract = p.subtract(BigInteger.ONE); //p-1 for primes;// factor.phi(p);
        List<BigInteger> factors = primeFactors(subtract);
        int i = 0;
        int j = factors.size();
        for (; i < j; i++) {
            BigInteger factor = factors.get(i);//elementAt
            BigInteger t = subtract.divide(factor);
            if (g.modPow(t, p).equals(BigInteger.ONE)) return false;
        }
        return true;
    }

    private static boolean isPrime(BigInteger r) {
        return millerRabin(r);
    }

    private static List<BigInteger> primeFactors(BigInteger number) {
        BigInteger n = number;
        BigInteger i = BigInteger.valueOf(2);
        BigInteger limit = BigInteger.valueOf(10000);// speed hack! -> consequences ???
        List<BigInteger> factors = new ArrayList<BigInteger>();
        while (!n.equals(BigInteger.ONE)) {
            while (n.mod(i).equals(BigInteger.ZERO)) {
                factors.add(i);
                n = n.divide(i);
                if (isPrime(n)) {
                    factors.add(n);// yes?
                    return factors;
                }
            }
            i = i.add(BigInteger.ONE);
            if (i.equals(limit)) return factors;// hack! -> consequences ???
            // System.out.print(i+"    \r");
        }
        System.out.println(factors);
        return factors;
    }

    private static boolean millerRabin(BigInteger n) {
        for (int repeat = 0; repeat < 20; repeat++) {
            BigInteger a;
            do {
                a = new BigInteger(n.bitLength(), rnd);
            } while (a.equals(BigInteger.ZERO));
            if (!millerRabinPass(a, n)) {
                return false;
            }
        }
        return true;
    }

    private static boolean millerRabinPass(BigInteger a, BigInteger n) {
        BigInteger n_minus_one = n.subtract(BigInteger.ONE);
        BigInteger d = n_minus_one;
        int s = d.getLowestSetBit();
        d = d.shiftRight(s);
        BigInteger a_to_power = a.modPow(d, n);
        if (a_to_power.equals(BigInteger.ONE)) return true;
        for (int i = 0; i < s - 1; i++) {
            if (a_to_power.equals(n_minus_one)) return true;
            a_to_power = a_to_power.multiply(a_to_power).mod(n);
        }
        if (a_to_power.equals(n_minus_one)) return true;
        return false;
    }

}
