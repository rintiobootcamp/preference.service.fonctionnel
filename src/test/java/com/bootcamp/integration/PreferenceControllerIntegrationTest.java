package com.bootcamp.integration;

import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.entities.Debat;
import com.bootcamp.entities.Preference;
import com.bootcamp.entities.Secteur;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * @author Ibrahim
 * <h2> The integration test for Preference controller</h2>
 * <p>
 * In this test class,
 * the methods :
 * <ul>
 * <li>create a preference </li>
 * <li>get one preference by it's id</li>
 * <li>get all preference</li>
 * <li>And update a preference have been implemented</li>
 * </ul>
 * before  getting started , make sure , the preference fonctionnel service is deploy and running as well.
 * you can also test it will the online ruuning service
 * As this test interact directly with the local database, make sure that the specific database has been created
 * and all it's tables.
 * If you have data in the table,make sure that before creating a data with it's id, do not use
 * an existing id.
 * </p>
 */

public class PreferenceControllerIntegrationTest {
    private static Logger logger = LogManager.getLogger(PreferenceControllerIntegrationTest.class);

    /**
     *The Base URI of preference fonctionnal service,
     * it can be change with the online URIof this service.
     */
    private String BASE_URI = "http://localhost:8090/preference";

    /**
     * The path of the Preference controller, according to this controller implementation
     */
    private String PREFERENCE_PATH ="/preferences";

    /**
     * This ID is initialize for create , getById, and update method,
     * you have to change it if you have a save data on this ID otherwise
     * a error or conflit will be note by your test.
     */
    private int preferenceId = 0;

    /**
     * The startDate initialize for statistic method, you have
     * make sure that this is correct in one of the value in database
     */
    private long startDate = 1511907379;

    /**
     * The endDate initialize for statistic method, you have
     * make sure that this is correct in one of the value in database
     */
    private long endDate = 1511907390;

    /**
     * A entity of type:
     * <ul>
     *     <li>
     *         PROJET
     *     </li>
     *     <li>
     *         SECTEUR
     *     </li>
     *     <li>
     *         PILIER
     *     </li>
     *     <li>
     *         AXE
     *     </li>
     *     <li>
     *         PROJET
     *     </li>
     * </ul>
     *
     */
    private String entityType = "PROJET";

    /**
     *The given entity type ID
     * you have to specify this ID according to record in your data
     */
    private int entityId = 2;

    private int userId = 5;


    /**
     * This method create a new preference with the given id
     * @see Preference#getId()
     * <b>you have to chenge the name of
     * the preference if this name already exists in the database
     * @see Preference#getEntityType() ()
     * else, the preference  will be created but not wiht the given ID.
     * and this will accure an error in the getById and update method</b>
     * Note that this method will be the first to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 0, groups = {"PreferenceTest"})
    public void createPreferenceTest() throws Exception{
        String createURI = BASE_URI+PREFERENCE_PATH;
        Preference preference = getPreferenceById( 5 );
        preference.setId( preferenceId );
        Gson gson = new Gson();
        String preferenceData = gson.toJson( preference );
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(preferenceData)
                .expect()
                .when()
                .post(createURI);
        preferenceId = gson.fromJson( response.getBody().print(),Preference.class ).getId();
        logger.debug(response.getBody().prettyPrint());
        Assert.assertEquals(response.statusCode(), 200) ;
    }

    /**
     * This method get a preference with the given id
     * @see Preference#id
     * <b>
     *     If the given ID doesn't exist it will log an error
     * </b>
     * Note that this method will be the second to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 1, groups = {"PreferenceTest"})
    public void getPreferenceByIdTest() throws Exception{
        String getPreferenceById = BASE_URI+PREFERENCE_PATH+"/"+preferenceId;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getPreferenceById);
        logger.debug(response.getBody().prettyPrint());
        Assert.assertEquals(response.statusCode(), 200) ;
    }

    @Test(priority = 2, groups = {"PreferenceTest"})
    public void readUserPreferencesTest(){
        String getPreferenceByUserURI = BASE_URI+PREFERENCE_PATH+"/"+userId;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getPreferenceByUserURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;

    }

   /* *//**
     * Update a preference with the given ID
     * <b>
     *     the preference must exist in the database
     *     </b>
     * Note that this method will be the third to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 3, groups = {"PreferenceTest"})
    public void updatePreferenceTest() throws Exception{
        String updateURI = BASE_URI+PREFERENCE_PATH;
        Preference preference = getPreferenceById( 5 );
        preference.setId( preferenceId );
        preference.setEntityType( entityType );
        preference.setDateMiseAJour( System.currentTimeMillis() );
        Gson gson = new Gson();
        String preferenceData = gson.toJson( preference );
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(preferenceData)
                .expect()
                .when()
                .put(updateURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;

    }
//
//    /**
//     * Get the statistics of the given entity type
//     * <b>
//     * the comments must exist in the database
//     * </b>
//     * Note that this method will be the third to execute If every done , it
//     * will return a 200 httpStatus code
//     *
//     * @throws Exception
//     */
//    @Test(priority = 3, groups = {"CommentaireTest"})
//    public void statsCommentaire() throws Exception {
//        String statsURI = BASE_URI + PREFERENCE_PATH +"/stats/"+entityType;
//        Response response = given()
//                .queryParam( "startDate",startDate)
//                .queryParam( "endDate",endDate )
//                .log().all()
//                .contentType("application/json")
//                .expect()
//                .when()
//                .get(statsURI);
//
//        logger.debug(response.getBody().prettyPrint());
//
//        Assert.assertEquals(response.statusCode(), 200);
//
//    }

/*
    *//**
     * Get All the preferences in the database
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     *//*
    @Test(priority = 4, groups = {"PreferenceTest"})
    public void getAllPreferencesTest()throws Exception{
        String getAllPreferenceURI = BASE_URI+PREFERENCE_PATH;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getAllPreferenceURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;

    }*/

/*
    */
/**
     * Get All the preferences in the database
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     *//*

    @Test(priority = 5, groups = {"PreferenceTest"})
    public void getPreferencesByEntityTest()throws Exception{
        String getPreferenceByEntityURI = BASE_URI+PREFERENCE_PATH+"/"+entityType+"/"+entityId;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getPreferenceByEntityURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;

    }
*/


/**
     * Delete a secteur for the given ID
     * will return a 200 httpStatus code if OK
     * @throws Exception
     */

    @Test(priority = 5, groups = {"SecteurTest"})
    public void deletePreferenceTest() throws Exception{

        String deletePreferenceUI = BASE_URI+PREFERENCE_PATH+"/"+preferenceId;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .delete(deletePreferenceUI);

        Assert.assertEquals(response.statusCode(), 200) ;


    }

    /**
     * Convert a relative path file into a File Object type
     * @param relativePath
     * @return  File
     * @throws Exception
     */
    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if (!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    /**
     * Convert a preferecence json data to a secteur objet list
     * this json file is in resources
     * @return a list of secteur in this json file
     * @throws Exception
     */
    public List<Preference> loadDataPreferenceFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "preferences.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Preference>>() {
        }.getType();
        List<Preference> preferences = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return preferences;
    }

    /**
     * Get on debat by a given ID from the List of axes
     * @param id
     * @return
     * @throws Exception
     */
    public Preference getPreferenceById(int id) throws Exception {
        List<Preference> preferences = loadDataPreferenceFromJsonFile();
        Preference preference = preferences.stream().filter(item -> item.getId() == id).findFirst().get();

        return preference;
    }



    public List<Debat> loadDataDebatFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "debats.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Debat>>() {
        }.getType();
        List<Debat> debats = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return debats;
    }

}
