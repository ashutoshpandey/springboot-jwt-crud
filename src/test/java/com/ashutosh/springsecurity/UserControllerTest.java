package com.ashutosh.springsecurity;

import com.ashutosh.springsecurity.controllers.UserController;
import com.ashutosh.springsecurity.models.User;
import com.ashutosh.springsecurity.models.request.FilterUserRequest;
import com.ashutosh.springsecurity.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User(1L, "Ashutosh", "12345", "ashutosh@ashutosh.com", null);

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        /**
         * JSON response would be like this:
         *  {
         *     success: true,
         *     data: {
         *         id: 1,
         *         name: "Ashutosh",
         *         email: "ashutosh@ashutosh.com"
         *         ...
         *     }
         *  }
         */
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Ashutosh"));
    }

    @Test
    public void testFilterUsers() throws Exception {
        User user = new User(1L, "Ashutosh", "12345", "ashutosh@ashutosh.com", null);

        FilterUserRequest request = new FilterUserRequest();
        request.setName("Ashu");

        when(userService.filterUsers(request)).thenReturn(Stream.of(user).collect(Collectors.toList()));

        /**
         * JSON response would be like this:
         *  {
         *     success: true,
         *     data: [{
         *         id: 1,
         *         name: "Ashutosh",
         *         email: "ashutosh@ashutosh.com"
         *         ...
         *     }]
         *  }
         */
        mockMvc.perform(
                    post("/api/users/filter")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(".success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Ashutosh"));
    }
}
