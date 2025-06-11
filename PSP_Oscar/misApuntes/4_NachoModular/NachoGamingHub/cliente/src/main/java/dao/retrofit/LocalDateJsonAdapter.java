package dao.retrofit;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateJsonAdapter {

    @ToJson
    String toJson(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @FromJson
    LocalDate fromJson(String localDate) {
        return LocalDate.parse(localDate, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
