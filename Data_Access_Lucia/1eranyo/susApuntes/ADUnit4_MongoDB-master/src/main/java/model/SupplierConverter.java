package model;

import org.bson.Document;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SupplierConverter {


  public Document convertSupplierDocument(Supplier sup) {
    Document d = new Document().append("street", sup.getStreet())
            .append("town", sup.getTown()).append("country", sup.getCountry()).append("pcode", sup.getPcode());

    d.append("coffees", sup.getCoffees().stream()
            .map(coffees -> new Document().append("_id", coffees.getIdProd())
                    .append("cofName", coffees.getCofName()).append("price", coffees.getPrice()))
            .collect(Collectors.toList()));
    return d;
  }

  public Supplier convertDocumentSupplier(Document d) {

    List<Document> listCof = d.getList("coffees",Document.class);
    if (listCof == null)
      listCof = new ArrayList();

    return Supplier.builder().supp_id(d.getObjectId("_id")).street(d.getString("street")).town(d.getString("town"))
            .country(d.getString("country")).pcode(d.getString("pcode"))
            .coffees(listCof.stream().map(document ->
                    Coffee.builder()
                            .idProd(document.getInteger("_id"))
                            .cofName(document.getString("cofName"))
                            .price(document.getDouble("price")).build()).collect(Collectors.toList()))
            .build();
  }
}
