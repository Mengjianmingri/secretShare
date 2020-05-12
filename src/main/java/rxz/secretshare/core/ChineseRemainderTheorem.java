package rxz.secretshare.core;

import java.math.BigInteger;
import java.util.Scanner;

public class ChineseRemainderTheorem {
	private static Scanner input;
	public static void main(String[] args) {
        input = new Scanner(System.in);
        int k=3;
        BigInteger[] a=new BigInteger[k];
        BigInteger[] m=new BigInteger[k];
        
        System.out.println("x % a_i = b_i x:");
        System.out.print("a1 , a2 , a3");
        m[0]=input.nextBigInteger();
        m[1]=input.nextBigInteger();
        m[2]=input.nextBigInteger();
        
        System.out.print("b1 , b2 , b3");
        a[0]=input.nextBigInteger();
        a[1]=input.nextBigInteger();
        a[2]=input.nextBigInteger();
        
        if(a[0].equals(BigInteger.valueOf(0))
        		&&a[1].equals(BigInteger.valueOf(0))
        		&&a[2].equals(BigInteger.valueOf(0))) {
        	System.out.println(" "+m[0]+" ,"+m[1]+" ,"+m[2]+" "+" ");
        	return;
        }
        	
        	
        BigInteger M=m[0].multiply(m[1]).multiply(m[2]);
        BigInteger e[]=new BigInteger[3];
        
        for(int i=0;i<k;i++) {
        	CRTSet y=new CRTSet();
        	CRTSet x=new CRTSet();
        	inverse(M.divide(m[i]),m[i],x,y);//?????
        	e[i]=x.v;
        }
        BigInteger sum = new BigInteger("0");
        for(int i=0;i<k;i++) {
        	sum=sum.add(M.multiply(a[i]).multiply(e[i]).divide(m[i]));
        }
        System.out.println(sum.mod(M));
        input.close();
	}
	
	//??????????????????x??
	public static void inverse(BigInteger a,BigInteger b,CRTSet x,CRTSet y) {
		if(b.equals(new BigInteger("0"))) {
			x.v=new BigInteger("1");
			y.v=new BigInteger("0");
			return;
		}

		inverse(b, a.mod(b), x, y);
		BigInteger c=y.v;
		y.v=x.v.subtract(a.divide(b).multiply(y.v));
		x.v=c;

	}

}
class CRTSet{
	BigInteger v;
	public CRTSet() {};
	public CRTSet(BigInteger v) {
		this.v=v;
	}
}