package model;

import java.util.Objects;

public class Faculty {
    private int id;
    private String name;
    private String firstDiscipline;
    private String secondDiscipline;
    private String thirdDiscipline;
    private int plan;
    private boolean active;

    public Faculty(int id, String name, String firstDiscipline, String secondDiscipline,
                   String thirdDiscipline, int plan, boolean active) {
        this.id = id;
        this.name = name;
        this.firstDiscipline = firstDiscipline;
        this.secondDiscipline = secondDiscipline;
        this.thirdDiscipline = thirdDiscipline;
        this.plan = plan;
        this.active = active;
    }

    public Faculty(String name, String firstDiscipline, String secondDiscipline,
                   String thirdDiscipline, int plan, boolean active) {
        this(-1, name, firstDiscipline, secondDiscipline, thirdDiscipline, plan, active);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstDiscipline() {
        return firstDiscipline;
    }

    public void setFirstDiscipline(String firstDiscipline) {
        this.firstDiscipline = firstDiscipline;
    }

    public String getSecondDiscipline() {
        return secondDiscipline;
    }

    public void setSecondDiscipline(String secondDiscipline) {
        this.secondDiscipline = secondDiscipline;
    }

    public String getThirdDiscipline() {
        return thirdDiscipline;
    }

    public void setThirdDiscipline(String thirdDiscipline) {
        this.thirdDiscipline = thirdDiscipline;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
