package rxz.secretshare.secretsharing.scheme.crt;

import rxz.secretshare.secretsharing.exception.ServerException;
import rxz.secretshare.secretsharing.scheme.SecretTransformer;
import rxz.secretshare.secretsharing.scheme.crt.domain.CRTShare;
import rxz.secretshare.secretsharing.scheme.crt.dto.CRTReconstructRequestDTO;

import java.util.ArrayList;
import java.util.List;

public class CRTSecretReconstructor {

    public static String reconstruct(CRTReconstructRequestDTO request) throws ServerException {
        List<CRTShare> shares = request.getShares();
        List<Long> secrets = new ArrayList<>();
        for (int i = 0; i < shares.get(0).getValue().size(); i++) {
            List<Long> constraints = new ArrayList<>();
            List<Long> modulos = new ArrayList<>();
            for (int j = 0; j < shares.size(); j++) {
                constraints.add(shares.get(j).getValue().get(i));
                modulos.add(shares.get(j).getModulo());
            }
            secrets.add(resolveCongruences(constraints, modulos));
        }
        return SecretTransformer.transformToSecret(request.getType(), secrets);
    }

    public static long resolveCongruences(List<Long> constraints, List<Long> modulos)
    {
        //M is the product of the mods
        int M = 1;
        for(int i = 0; i < modulos.size(); i++)
            M *= modulos.get(i);

        long[] multInv = new long[constraints.size()];

    /*
     * this loop applies the Euclidean algorithm to each pair of M/mods[i] and mods[i]
     * since it is assumed that the various mods[i] are pairwise coprime,
     * the end result of applying the Euclidean algorithm will be
     * gcd(M/mods[i], mods[i]) = 1 = a(M/mods[i]) + b(mods[i]), or that a(M/mods[i]) is
     * equivalent to 1 mod (mods[i]). This a is then the multiplicative
     * inverse of (M/mods[i]) mod mods[i], which is what we are looking to multiply
     * by our constraint constraints[i] as per the Chinese Remainder Theorem
     * euclidean(M/mods[i], mods[i])[0] returns the coefficient a
     * in the equation a(M/mods[i]) + b(mods[i]) = 1
     */
        for(int i = 0; i < multInv.length; i++)
            multInv[i] = euclidean(M/modulos.get(i), modulos.get(i))[0];

        long x = 0;

        //x = the sum over all given i of (M/mods[i])(constraints[i])(multInv[i])
        for(int i = 0; i < modulos.size(); i++)
            x += (M/modulos.get(i))*constraints.get(i)*multInv[i];

        x = leastPosEquiv(x, M);

        return x;
    }

    private static long[] euclidean(long a, long b)
    {
        if(b > a)
        {
            //reverse the order of inputs, run through this method, then reverse outputs
            long[] coeffs = euclidean(b, a);
            long[] output = {coeffs[1], coeffs[0]};
            return output;
        }

        long q = a/b;
        //a = q*b + r --> r = a - q*b
        long r = a -q*b;

        //when there is no remainder, we have reached the gcd and are done
        if(r == 0)
        {
            long[] output = {0, 1};
            return output;
        }

        //call the next iteration down (b = qr + r_2)
        long[] next = euclidean(b, r);

        long[] output = {next[1], next[0] - q*next[1]};
        return output;
    }

    public static long leastPosEquiv(long a, long m)
    {
        //a eqivalent to b mod -m <==> a equivalent to b mod m
        if(m < 0)
            return leastPosEquiv(a, -1*m);
        //if 0 <= a < m, then a is the least positive integer equivalent to a mod m
        if(a >= 0 && a < m)
            return a;

        //for negative a, find the least negative integer equivalent to a mod m
        //then add m
        if(a < 0)
            return -1L*leastPosEquiv(-1L*a, m) + m;

        //the only case left is that of a,m > 0 and a >= m

        //take the remainder according to the Division algorithm
        long q = a/m;

    /*
     * a = qm + r, with 0 <= r < m
     * r = a - qm is equivalent to a mod m
     * and is the least such non-negative number (since r < m)
     */
        return a - q*m;
    }

}
