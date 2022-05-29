package com.example.springbootstudybasictest.vo;

public class DepartmentVO {
    int dno;        //부서 번호
    String dname;   //부서 이름
    String loc;     //장소

    public DepartmentVO(int dno, String dname, String loc) {
        this.dno = dno;
        this.dname = dname;
        this.loc = loc;
    }

    public int getDno() {
        return dno;
    }

    public void setDno(int dno) {
        this.dno = dno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "DepartmentVO{" +
                "dno=" + dno +
                ", dname='" + dname + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }
}
