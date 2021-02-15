package com.jonathan.navigation.ui.home;

import java.util.List;

public class Pelicula {
    private Integer id;
    private String posterPath;
    private String backdrop_path;
    private String title;
    private String overview;
    private List<Production_Companies> production_companies;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }

    public void setBackdropPath(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<Production_Companies> getProductionCompanies(){
        return production_companies;
    }

    public void setProduction_Companies(List<Production_Companies> production_companies){
        this.production_companies = production_companies;
    }

    public class Production_Companies{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}