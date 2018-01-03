package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.crud.PreferenceCRUD;
import com.bootcamp.entities.Preference;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

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
        int id = preference.getId();
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        Preference current_preferences = PreferenceCRUD.read(criterias).get(0);
        preference.setDateCreation(current_preferences.getDateCreation());
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
    public boolean delete(int id) throws SQLException {
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
    public Preference read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Preference> preferences = PreferenceCRUD.read(criterias);
        return preferences.get(0);
    }

    /**
     * Get all the preferences of a given user
     *
     * @param userId
     * @return preferences list
     * @throws SQLException
     */
    public List<Preference> readAll(int userId) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("userId", "=", userId));
        List<Preference> preferences = PreferenceCRUD.read(criterias);
        return preferences;
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
