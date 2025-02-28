package com.exam.dao.model.mongoModel;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalVisitsMongo extends VisitorMongo {
    @SerializedName("_id")
    private ObjectId id;
    private  Date date;
    private List<AnimalMongo> animals;
    private VisitorMongo visitor;
}
