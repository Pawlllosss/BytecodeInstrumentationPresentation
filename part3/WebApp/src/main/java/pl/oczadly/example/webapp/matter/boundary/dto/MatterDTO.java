package pl.oczadly.example.webapp.matter.boundary.dto;

public class MatterDTO {

    private long id;
    private String name;
    private String area;

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

    public void setAreaOfLaw(String area) {
        this.area = area;
    }
}
