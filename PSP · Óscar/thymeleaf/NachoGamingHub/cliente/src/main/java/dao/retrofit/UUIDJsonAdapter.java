package dao.retrofit;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import java.util.UUID;

public class UUIDJsonAdapter {

    @ToJson
    String toJson(UUID uuid) {
        return uuid.toString();
    }

    @FromJson
    UUID fromJson(String uuid) {
        return UUID.fromString(uuid);
    }
}
