package pl.pivovarit.commons.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Customer {

    private String name;
    private Integer age;
    private Integer budget;
    private List<ItemEntry> wantToBuy;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @XmlAttribute
    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    @XmlElement(name = "item")
    public List<ItemEntry> getWantToBuy() {
        return wantToBuy;
    }

    public void setWantToBuy(List<ItemEntry> wantToBuy) {
        this.wantToBuy = wantToBuy;
    }
}
