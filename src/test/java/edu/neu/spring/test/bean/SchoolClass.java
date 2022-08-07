package edu.neu.spring.test.bean;

import java.util.Date;

public class SchoolClass {

    private String className;

    private Date inClassDate;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getInClassDate() {
        return inClassDate;
    }

    public void setInClassDate(Date inClassDate) {
        this.inClassDate = inClassDate;
    }

    @Override
    public String toString() {
        return "SchoolClass{" +
                "className='" + className + '\'' +
                ", inClassDate=" + inClassDate +
                '}';
    }
}
