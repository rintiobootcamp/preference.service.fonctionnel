package com.bootcamp;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.crud.PreferenceCRUD;
import com.bootcamp.entities.Preference;
import com.bootcamp.services.PreferenceService;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;

//import org.junit.Test;
//import org.testng.annotations.Test;

/**
 * Created by darextossa on 12/9/17.
 */

@RunWith(PowerMockRunner.class)
@WebMvcTest(value = PreferenceService.class, secure = false)
@ContextConfiguration(classes = {Application.class})
@PrepareForTest(PreferenceCRUD.class)
@PowerMockRunnerDelegate(SpringRunner.class)
public class PreferenceServiceTest {

    @InjectMocks
    private PreferenceService preferenceService;


    @Test
    public void create() throws Exception{
        List<Preference> preferences = loadDataPreferenceFromJsonFile();
        Preference preference = preferences.get(1);

        PowerMockito.mockStatic(PreferenceCRUD.class);
        Mockito.
                when(PreferenceCRUD.create(preference)).thenReturn(true);
    }

    @Test
    public void delete() throws Exception{
        List<Preference> preferences = loadDataPreferenceFromJsonFile();
        Preference preference = preferences.get(1);

        PowerMockito.mockStatic(PreferenceCRUD.class);
        Mockito.
                when(PreferenceCRUD.delete(preference)).thenReturn(true);
    }

    @Test
    public void update() throws Exception{
        List<Preference> preferences = loadDataPreferenceFromJsonFile();
        Preference preference = preferences.get(1);

        PowerMockito.mockStatic(PreferenceCRUD.class);
        Mockito.
                when(PreferenceCRUD.update(preference)).thenReturn(true);
    }


    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if (!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    public List<Preference> loadDataPreferenceFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "preferences.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Preference>>() {
        }.getType();
        List<Preference> preferences = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return preferences;
    }
    
        @Test
    public void getAllPreference() throws Exception {
        List<Preference> preferences = loadDataPreferenceFromJsonFile();
        PowerMockito.mockStatic(PreferenceCRUD.class);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.
                when(PreferenceCRUD.read()).thenReturn(preferences);
        List<Preference> resultPreferences = preferenceService.readAll(1);
        Assert.assertEquals(resultPreferences.size(), 3);
    }

}