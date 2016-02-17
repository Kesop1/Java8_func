package pl.pivovarit.commons.dataset;

import java.io.File;

import javax.xml.bind.JAXB;

import pl.pivovarit.commons.domain.Mall;


public class Store {
    private final Mall mall;

    public Store() {
        mall = JAXB.unmarshal(new File(getClass().getClassLoader().getResource("data.xml").getFile()), Mall.class);
    }

    public Mall getMall() {
        return mall;
    }
}
