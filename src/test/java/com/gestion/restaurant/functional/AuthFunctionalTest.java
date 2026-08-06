package com.gestion.restaurant.functional;

import com.gestion.restaurant.support.AbstractPostgresIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthFunctionalTest extends AbstractPostgresIT {

    @Autowired MockMvc mockMvc;

    @Test
    void anonyme_redirigeVersLogin() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void loginPage_accessible() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void login_ok() throws Exception {
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/login")
                        .user("admin")
                        .password("test"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    void login_ko() throws Exception {
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/login")
                        .user("admin")
                        .password("wrong"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    void logout() throws Exception {
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/login")
                .user("admin")
                .password("test"));

        mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"));
    }

    @Test
    void staticCss_pasDeRedirectLogin() throws Exception {
        mockMvc.perform(get("/css/style.css"))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    // 200 si fichier présent, 404 sinon — mais jamais redirect login
                    org.assertj.core.api.Assertions.assertThat(status).isNotEqualTo(302);
                });
    }
}
