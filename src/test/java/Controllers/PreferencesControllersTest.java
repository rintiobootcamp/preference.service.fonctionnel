package Controllers;

import com.bootcamp.application.Application;
import com.bootcamp.commons.enums.EntityType;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.controllers.PreferenceController;
import com.bootcamp.controllers.PreferenceController;
import com.bootcamp.entities.Preference;
import com.bootcamp.services.PreferenceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;

/**
 * Created by Archange on 17/12/2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = PreferenceController.class, secure = false)
@ContextConfiguration(classes = {Application.class})
public class PreferencesControllersTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PreferenceService preferenceService;

    @Test
    public void createPreference() throws Exception {
        List<Preference> preferences = getPreferenceFromJson();
        Preference preference = preferences.get(0);

        when(preferenceService.read(0)).thenReturn(preference);
        when(preferenceService.create(preference)).thenReturn(preference.getId());
        RequestBuilder requestBuilder
                = post("/preferences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(preference));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        System.out.println(response.getContentAsString());

        System.out.println("*********************************Test for create preference controller done *******************");
    }

/*
    @Test
    public void getAllPreferenceToJson() throws Exception {
        Preference preference = new Preference();
        preference.setId(1);

        List<Preference> allProjets = Arrays.asList(preference);

        given(projetService.findAll()).willReturn(allProjets);

        mvc.perform(get("/projets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].nom",is(projet.getNom())));
    }
  */
    @Test
    public void deletePreference() throws Exception {
        int id = 5;
        when(preferenceService.exist(id)).thenReturn(true);
        when(preferenceService.delete(id)).thenReturn(true);

        RequestBuilder requestBuilder
                = delete("/preferences/delete/{idPreference}", id)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for delete debat in debat controller done *******************");

    }


    
    
    private static String objectToJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Preference> getPreferenceFromJson() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "preferences.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Preference>>() {
        }.getType();
        List<Preference> preferences = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return preferences;
    }

    public File getFile(String relativePath) throws Exception {
        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());
        if (!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }
        return file;
    }
}
