package rxz.secretshare.secretsharing.scheme;

import rxz.secretshare.secretsharing.exception.ServerException;
import rxz.secretshare.secretsharing.util.MathUtils;
import rxz.secretshare.secretsharing.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SecretTransformer {

    public static List<Double> transformSecretToDoubleList(int type, String secret) throws ServerException {
        List<Double> secretList = new ArrayList<>();
        switch (type) {
            case 1:
                secretList.add(Double.parseDouble(secret));
                break;
            case 2:
                String[] tokens = secret.split(",");
                for (String token : tokens) {
                    secretList.add(Double.parseDouble(token));
                }
                break;
            case 3:
                for (int i = 0; i < secret.length(); i++) {
                    secretList.add((double)secret.charAt(i));
                }
                break;
            default:
                throw new ServerException("Unknown type "+type);
        }
        return secretList;
    }

    public static List<Long> transformSecretToLongList(int type, String secret) throws ServerException {
        List<Long> secretList = new ArrayList<>();
        switch (type) {
            case 1:
                secretList.add(Long.parseLong(secret));
                break;
            case 2:
                String[] tokens = secret.split(",");
                for (String token : tokens) {
                    secretList.add(Long.parseLong(token));
                }
                break;
            case 3:
                for (int i = 0; i < secret.length(); i++) {
                    secretList.add((long)secret.charAt(i));
                }
                break;
            default:
                throw new ServerException("Unknown type "+type);
        }
        return secretList;
    }

    public static String transformToSecret(int type, List<? extends Number> secret) throws ServerException {
        switch (type) {
            case 1:
                return MathUtils.numberToString(secret.get(0));
            case 2:
                return StringUtils.vectorToString(secret);
            case 3:
                return secret
                        .parallelStream()
                        .map(val -> Character.toString((char) val.shortValue()))
                        .reduce(String::concat)
                        .get();
            default:
                throw new ServerException("Unknown type "+type);
        }
    }
}
