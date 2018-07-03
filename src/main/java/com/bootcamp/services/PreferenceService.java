package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.crud.PreferenceCRUD;
import com.bootcamp.entities.Media;
import com.bootcamp.entities.Preference;
import com.rintio.elastic.client.ElasticClient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hilaire .
 */
@Component
public class PreferenceService implements DatabaseConstants {

    /**
     * Insert the given preference in the database
     *
     * @param preference
     * @return preference
     * @throws SQLException
     */
    public Preference create(Preference preference) throws SQLException {
        preference.setDateCreation(System.currentTimeMillis());
        PreferenceCRUD.create(preference);
        return preference;
    }

    /**
     * Update the given preference in the database
     *
     * @param preference
     * @return preference id
     * @throws SQLException
     */
    public Preference update(Preference preference) throws SQLException {
        preference.setDateMiseAJour(System.currentTimeMillis());
        PreferenceCRUD.update(preference);
        return preference;
    }

    /**
     * Delete a preference in the database by its id
     *
     * @param id
     * @return preference
     * @throws SQLException
     */
    public boolean delete(int id) throws Exception {
        Preference preference = read(id);
        return PreferenceCRUD.delete(preference);
    }

    /**
     * Get a preference by its id
     *
     * @param id
     * @return preference
     * @throws SQLException
     */
    public Preference read(int id) throws Exception {
//        Criterias criterias = new Criterias();
//        criterias.addCriteria(new Criteria("id", "=", id));
//        List<Preference> preferences = PreferenceCRUD.read(criterias);
        return getAllPreferenceIndex().stream().filter(t->t.getId()==id).findFirst().get();
    }

    /**
     * Get all the preferences of a given user
     *
     * @param userId
     * @return preferences list
     * @throws SQLException
     */
    public List<Preference> readAll(int userId) throws Exception {
//        Criterias criterias = new Criterias();
//        criterias.addCriteria(new Criteria("userId", "=", userId));
//        List<Preference> preferences = PreferenceCRUD.read(criterias);
        List<Preference> preferences = getAllPreferenceIndex().stream().filter(t->t.getUserId()==userId).collect(Collectors.toList());
        return preferences;
    }
    public List<Preference> getAllPreferenceIndex() throws Exception{
        ElasticClient elasticClient = new ElasticClient();
        List<Object> objects = elasticClient.getAllObject("preferences");
        ModelMapper modelMapper = new ModelMapper();
        List<Preference> rest = new ArrayList<>();
        for(Object obj:objects){
            rest.add(modelMapper.map(obj,Preference.class));
        }
        return rest;
    }
//    public Preference read(int id) throws SQLException {
//        Criterias criterias = new Criterias();
//        criterias.addCriteria(new Criteria("id", "=", id));
//        List<Preference> preferences = PreferenceCRUD.read(criterias);
//        return preferences.get(0);
//    }

    public boolean exist(Preference preference) throws Exception {
        return read(preference.getId()) != null;
    }

    public boolean exist(int id) throws Exception {
        return read(id) != null;

    }
}
