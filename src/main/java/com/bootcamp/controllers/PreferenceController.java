/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.controllers;


import com.bootcamp.commons.enums.EntityType;
import com.bootcamp.commons.ws.usecases.pivotone.PreferenceWS;
import com.bootcamp.crud.PreferenceCRUD;
import com.bootcamp.entities.Preference;
import com.bootcamp.services.PreferenceService;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author root
 */
@RestController("PreferenceController")
@RequestMapping("/preferences")
@Api(value = "Preference API", description = "Preference API")
@CrossOrigin(origins = "*")
public class PreferenceController {

    @Autowired
    PreferenceService preferenceService;
    @Autowired
    HttpServletRequest request;

    /**
     * Insert the given preference in the database
     *
     * @param preference
     * @return preference
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new Preference", notes = "Create a new Preference")
    public ResponseEntity<Preference> create(@RequestBody @Valid Preference preference) throws Exception{
      Preference result = preferenceService.create( preference );
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Get all the preferences of a user
     * @param id
     * @return preferences list
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read all the preferences of a user", notes = "Read all the preferences of a user")
    public ResponseEntity<List<Preference>> readUserPreferences(@PathVariable(name = "userId") int id) throws Exception {
        List<PreferenceWS> preferencesWS = new ArrayList<>();
         List<Preference> preferences = null ;
        PreferenceWS preferenceWS = new PreferenceWS();
        HttpStatus httpStatus;
        try {
              preferences = preferenceService.readAll(id);
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
            Logger.getLogger(PreferenceController.class.getName()).log(Level.SEVERE, null, ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(preferences, httpStatus);
    }

    /**
     * Delete a preference by its id
     * @param id
     * @return preferenceWS
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{idPreference}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Delete a preference", notes = "Delete a preference")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "idPreference") int id) throws Exception{
            boolean done = preferenceService.delete( id );
        return new ResponseEntity<>(done,HttpStatus.OK);
    }
    
    /**
     * Update a preference by its id
     * @param preference
     * @return preferenceWS
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Update a preference", notes = "Update a preference")
    public ResponseEntity<Preference> update(@RequestBody @Valid Preference preference) throws Exception{
       Preference result = preferenceService.update( preference );
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
