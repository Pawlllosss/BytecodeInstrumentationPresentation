package pl.oczadly.example.webapp.matter.entity;

import java.util.Objects;
import java.util.StringJoiner;

public class Matter {

    private long id;
    private String name;
    private String area;

    public Matter(long id, String name, String area) {
        this.id = id;
        this.name = name;
        this.area = area;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matter matter = (Matter) o;
        return id == matter.id &&
                Objects.equals(name, matter.name) &&
                Objects.equals(area, matter.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, area);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Matter.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("area='" + area + "'")
                .toString();
    }
}
