package domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SkiFondo extends Pista {
    @Getter @Setter
    private List<String> pueblos;
    public SkiFondo(String nombre, String provinca, int id, int km, List<String> pueblos) {
        super(nombre, provinca, id, km);
        this.pueblos=pueblos;
    }


}
