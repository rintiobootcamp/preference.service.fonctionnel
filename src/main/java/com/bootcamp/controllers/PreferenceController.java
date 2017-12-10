/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.controllers;

import com.bootcamp.commons.ws.usecases.pivotone.PreferenceWS;
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
    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new Preference", notes = "Create a new Preference")
    public ResponseEntity<Integer> create(@RequestBody @Valid Preference preference) {
        HttpStatus httpStatus = null;
        int id = -1;
        try {
            id = preferenceService.create(preference);
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
            Logger.getLogger(PreferenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<>(id, httpStatus);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{usersId}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a preference", notes = "Read a preference")
    public ResponseEntity<List<PreferenceWS>> readAllPreferences(@PathVariable(name = "usersId") int id) {
        List<PreferenceWS> preferencesWS = new ArrayList<>();
        PreferenceWS preferenceWS = new PreferenceWS();
        HttpStatus httpStatus ;
        try {
            List<Preference> preferences = preferenceService.readAll(id);
            for (Preference current_preference : preferences) {
                preferenceWS.setEntityId(current_preference.getEntityId());
                preferenceWS.setEntityType(current_preference.getEntityTypes());
                preferenceWS.setDateCreation(current_preference.getDateCreation());
                preferencesWS.add(preferenceWS);
            }
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
            Logger.getLogger(PreferenceController.class.getName()).log(Level.SEVERE, null, ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(preferencesWS, httpStatus);
    }  
}
