package model;

public class SalaryData {
    private String month;
    private int salary;
    private int dependents;
    private int totalDeductions;
    private int tax;

    public  SalaryData(){}

    public SalaryData(String month, int salary, int dependents, int totalDeductions, int tax) {
        this.month = month;
        this.salary = salary;
        this.dependents = dependents;
        this.totalDeductions = totalDeductions;
        this.tax = tax;
    }

    // Getters và Setters
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getDependents() {
        return dependents;
    }

    public void setDependents(int dependents) {
        this.dependents = dependents;
    }

    public int getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(int totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }
}

