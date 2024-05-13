package org.example.features.imdb;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.example.steps.serenity.EndUserStepsImdb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/resources/invalid_login.csv")
public class LoginWithInvalidCredentialsStory {
    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public EndUserStepsImdb dorian;

    public String email;
    public String password;

    @Qualifier
    public String getQualifier() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Issue("#WIKI-1")
    @Test
    public void log_in_with_invalid_credentials_should_prompt_with_error() {
        dorian.opens_home_page();
        dorian.signs_in();
        dorian.signs_in_with_imdb();
        dorian.types_email(getEmail());
        dorian.types_password(getPassword());
        dorian.submits_credentials();
        dorian.should_see_incorrect_password();
    }
}
