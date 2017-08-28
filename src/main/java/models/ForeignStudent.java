package models;


public class ForeignStudent extends Student {
    private String country;
    private int id;

    public ForeignStudent(String name, String country) {
        super(name);
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ForeignStudent that = (ForeignStudent) o;

        if (id != that.id) return false;
        return country.equals(that.country);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + id;
        return result;
    }
}
