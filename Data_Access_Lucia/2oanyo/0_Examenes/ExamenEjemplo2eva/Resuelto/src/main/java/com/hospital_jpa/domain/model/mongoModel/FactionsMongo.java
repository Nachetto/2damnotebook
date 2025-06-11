package com.hospital_jpa.domain.model.mongoModel;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FactionsMongo {
    @SerializedName("_id")
    private ObjectId id;
    private List<FactionMongo> factions;
}
