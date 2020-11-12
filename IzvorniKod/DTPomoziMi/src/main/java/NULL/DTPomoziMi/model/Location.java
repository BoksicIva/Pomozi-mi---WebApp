package NULL.DTPomoziMi.model;

import java.math.BigDecimal;

public class Location {
    private BigDecimal longitude; //dužina
    private BigDecimal latitude; //širina

    private String country;
    private String town;
    private String address;

    public Location(BigDecimal longitude, BigDecimal latitude, String country, String town, String address) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
        this.town = town;
        this.address = address;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
