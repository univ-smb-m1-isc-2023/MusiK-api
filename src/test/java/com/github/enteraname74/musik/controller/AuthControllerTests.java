package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.controller.jsonbody.JsonUser;
import com.github.enteraname74.musik.domain.model.Token;
import com.github.enteraname74.musik.domain.service.AuthService;
import com.github.enteraname74.musik.domain.utils.ServiceMessages;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    public void givenCorrectUserInfo_whenAuthenticate_thenShouldReturnToken() throws Exception {

        Gson gson = new Gson();
        Token token = new Token();
        Mockito.when(authService.authenticateUser(any(), any())).thenAnswer(i -> new ServiceResult<>(HttpStatus.OK, token));

        JsonUser jsonUser = new JsonUser();
        String json = gson.toJson(jsonUser);

        MvcResult result = mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        Token returnedToken = gson.fromJson(result.getResponse().getContentAsString(), Token.class);

        Assert.isTrue(returnedToken.equals(token), "The returned token should match");
    }

    @Test
    public void givenWrongUserInfo_whenAuthenticate_thenShouldReturnToken() throws Exception {

        Gson gson = new Gson();
        Mockito.when(authService.authenticateUser(any(), any())).thenAnswer(i -> new ServiceResult<>(HttpStatus.NOT_FOUND, ServiceMessages.WRONG_USER_ID));

        JsonUser jsonUser = new JsonUser();
        String json = gson.toJson(jsonUser);

        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(ServiceMessages.WRONG_USER_ID)));
    }
}
