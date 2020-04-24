package br.gov.ufpr.convida.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Report implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String grr;
    private String report;
    private Boolean ignored;

    public Report(){}


    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getGrr() {
        return this.grr;
    }

    public void setGrr(String grr) {
        this.grr = grr;
    }

    public String getReport() {
        return this.report;
    }

    public void setReport(String report) {
        this.report = report;
    }


    
    public boolean isIgnored() {
        return this.ignored;
    }

    public boolean getIgnored() {
        return this.ignored;
    }

    public void setIgnored(Boolean ignored) {
        this.ignored = ignored;
    }

    


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Report)) {
            return false;
        }
        Report report = (Report) o;
        return Objects.equals(grr, report.grr) && Objects.equals(report, report.report);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grr, report);
    }

  
}