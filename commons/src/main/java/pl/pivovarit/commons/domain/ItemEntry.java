package pl.pivovarit.commons.domain;

import javax.xml.bind.annotation.XmlAttribute;

public class ItemEntry {
    private String name;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
