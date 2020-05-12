package rxz.secretshare.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class YiHuo {
    public static List<Integer> getRandom(int n){
        List<Integer> Randoms = new ArrayList<>();
        Random random = new Random(578963);
        for (int i = 0; i < n; i++) {
            int temp = random.nextInt();
            Randoms.add(temp);
        }
        return Randoms;
    }

    public static List<Integer> split(Integer secret ,int n){
        List<Integer> randoms = getRandom(n-1);
        for (Integer x : randoms) {
            secret ^= x;
        }
        randoms.add(secret);
        return randoms;
    }
    public static Integer reconstruct(List<Integer> shares){
        Integer temp = shares.get(0);
        for (int i = 1; i < shares.size(); i++) {
            temp ^= shares.get(i);
        }
        return temp;
    }
}
