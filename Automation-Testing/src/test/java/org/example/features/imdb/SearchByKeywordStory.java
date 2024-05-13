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
@UseTestDataFrom("src/test/resources/valid_movie_search.csv")
public class SearchByKeywordStory {
    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public EndUserStepsImdb dorian;

    public String keyword;
    public String information;

    @Qualifier
    public String getQualifier() {
        return keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Issue("#WIKI-1")
    @Test
    public void searching_by_keyword_should_display_the_corresponding_information_ddt() {
        dorian.opens_home_page();
        dorian.looks_for(getKeyword());
        dorian.should_see_search_information(getInformation());
    }
} 
