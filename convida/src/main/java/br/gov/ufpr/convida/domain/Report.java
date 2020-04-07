package br.gov.ufpr.convida.domain;

import java.io.Serializable;
import java.util.Objects;

public class Report implements Serializable{

    private String grr;
    private String report;

    public Report(){}





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