package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class SkiFondo extends Pista{
    private List<String> pueblos;

    public SkiFondo(int id, int km, String provincia, String nombre, String pueblo1, String pueblo2) {
        super(id, km, provincia, nombre);
        pueblos = new ArrayList<>();
        pueblos.add(pueblo1);
        pueblos.add(pueblo2);
    }

    public SkiFondo(int id, int km, String provincia, String nombre, List<String> pueblos) {
        super(id, km, provincia, nombre);
        this.pueblos = pueblos;
    }

    public List<String> getPueblos() {
        return pueblos;
    }

    public void setPueblos(List<String> pueblos) {
        this.pueblos = pueblos;
    }

    @Override
    public String toString() {
        return "SkiFondo;"+id+";"+km+";"+provincia+";"+nombre+";"+pueblos+";";
    }
}
