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

    public int create(Preference preference ) throws SQLException {
       preference.setDateCreation(System.currentTimeMillis());
               PreferenceCRUD.create(preference);
        return preference.getId();
    }

    public int update(Preference preference ) throws SQLException {
        PreferenceCRUD.update(preference);
        return preference.getId();
    }

    public Preference delete(int id) throws SQLException {
        Preference preference = read(id);
        PreferenceCRUD.delete(preference);
        return preference;
    }

    public Preference read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Preference> preferences = PreferenceCRUD.read(criterias);
        return preferences.get(0);
    }
    public List<Preference> readAll(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("userId", "=", id));
        List<Preference> preferences = PreferenceCRUD.read(criterias);
        return preferences;
    }
//    public Preference read(int id) throws SQLException {
//        Criterias criterias = new Criterias();
//        criterias.addCriteria(new Criteria("id", "=", id));
//        List<Preference> preferences = PreferenceCRUD.read(criterias);
//        return preferences.get(0);
//    }

}
