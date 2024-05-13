package org.example.features.search;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Steps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import org.example.steps.serenity.EndUserSteps;

@RunWith(SerenityRunner.class)
public class SearchByKeywordStory {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public EndUserSteps anna;

    @Issue("#WIKI-1")
    @Test
    public void searching_by_keyword_apple_should_display_the_corresponding_article() {
        anna.is_the_home_page();
        anna.looks_for("apple");
        anna.should_see_definition("A common, round fruit produced by the tree Malus domestica, cultivated in temperate climates.");

    }

    @Test
    public void searching_by_keyword_banana_should_display_the_corresponding_article() {
        anna.is_the_home_page();
        anna.looks_for("pear");
        anna.should_see_definition("An edible fruit produced by the pear tree, similar to an apple but elongated towards the stem.");
    }

    @Issue("#WIKI-2")
    @Test
    public void searching_by_keyword_hook_should_give_definition_for_fishing_hook()
    {
        anna.is_the_home_page();
        anna.looks_for("hook");
        anna.should_see_definition("A rod bent into a curved shape, typically with one end free and the other end secured to a rope or other attachment.");
    }

    @Issue("#WIKI-3")
    @Test
    public void searching_by_keyword_Galati_should_give_definition_for_worst_city_in_romania_that_has_burger_king_fish_and_pimps()
    {
        anna.is_the_home_page();
        anna.looks_for("Galați");
        anna.should_see_definition("A city in Galați, Romania");
    }

    @Pending @Test
    public void searching_by_ambiguious_keyword_should_display_the_disambiguation_page() {
    }
} 