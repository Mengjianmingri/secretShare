package rxz.secretshare.core;

/*中国剩余定理，根据公式需要求取大数的逆元*/
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class CRT {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int n;
        BigInteger M_All = BigInteger.ONE,res = BigInteger.ZERO;
        ArrayList<BigInteger> b = new ArrayList<BigInteger>();
        ArrayList<BigInteger> m = new ArrayList<BigInteger>();
        ArrayList<BigInteger> M = new ArrayList<BigInteger>();
        ArrayList<BigInteger> Mi = new ArrayList<BigInteger>();                // M·Mi = 1 (mod mi)
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        for (int i = 0 ; i < n ; i++){
            b.add(scan.nextBigInteger());
            m.add(scan.nextBigInteger());
        }
        for (int i = 0 ; i < n ; i++){
            M_All = M_All.multiply(m.get(i));
        }
        for (int i = 0 ; i < n ; i++){
            BigInteger M_tmp = M_All.divide(m.get(i));
            M.add(M_tmp);
            Mi.add(getMi(M_tmp,m.get(i)));
        }
        for (int i = 0 ; i < n ; i++){
            res = res.add(M.get(i).multiply(Mi.get(i)).multiply(b.get(i)));
        }
        res = res.mod(M_All);
    }

    //获取Mi'使得满足MiMi' = 1 (mod m)，使用扩展欧几里得算法
    public static BigInteger getMi(BigInteger M, BigInteger m){
        ArrayList<BigInteger> List_Q = new ArrayList<BigInteger>();
        ArrayList<BigInteger> List_S = new ArrayList<BigInteger>();
        BigInteger temp, res,m_rec = m;
        //通过辗转相除获得List_Q
        while (!m.equals(BigInteger.ZERO)){
            temp = m;
            List_Q.add(M.divide(m));
            m = M.remainder(m);
            M = temp;
        }

        //根据递推公式获得List_S
        List_S.add(BigInteger.ONE);
        List_S.add(BigInteger.ZERO);
        for (int i = 2 ; i < 1 + List_Q.size() ; i++){
            List_S.add(List_S.get(i - 2).subtract(List_Q.get(i - 2).multiply(List_S.get(i - 1))));
        }
        res = List_S.get(List_S.size() - 1);
        while (res.compareTo(BigInteger.ZERO) < 0){
            res = res.add(m_rec);
        }
        return res.mod(m_rec);
    }
}
