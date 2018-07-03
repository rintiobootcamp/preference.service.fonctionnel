package com.bootcamp;

import com.bootcamp.crud.PreferenceCRUD;
import com.bootcamp.entities.Preference;
import com.rintio.elastic.client.ElasticClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;

public class ElasticTest {
    private final Logger LOG = LoggerFactory.getLogger(ElasticTest.class);


    @Test
    public void createIndexPreference() throws Exception {
        ElasticClient elasticClient = new ElasticClient();
        List<Preference> preferences = PreferenceCRUD.read();
        for (Preference preference: preferences) {
            elasticClient.creerIndexObjectNative("preferences", "preference", preference, preference.getId());
            LOG.info("preference " + preference.getId() + " created");
        }
    }
}
