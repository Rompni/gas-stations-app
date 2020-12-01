package com.edu.unimagdalena.appmoviles.gasstations.models;

public class GasStation {
    private String name;
    private String company;
    private String department;
    private String municipality;
    private Location location;

    public GasStation(String name, String company, String department, String municipality, Location location) {
        this.name = name;
        this.company = company;
        this.department = department;
        this.municipality = municipality;
        this.location = location;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

