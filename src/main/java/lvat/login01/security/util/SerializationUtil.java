package lvat.login01.security.util;

import org.springframework.util.SerializationUtils;

import java.util.Base64;

public class SerializationUtil {
    public static String serialize(Object obj) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(obj));
    }

    public static <T> T deserialize(String stringValue, Class<T> type) {
        return type.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(stringValue)));
    }
}
