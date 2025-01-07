package org.wikipedia.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.wikipedia.driverconfig.BaseTest;
import org.wikipedia.listeners.TestListenerAdaptor;
import org.wikipedia.testdata.SearchDataProvider;

@Listeners(TestListenerAdaptor.class)
public class CountryCapitalSearchTest extends BaseTest {

    @Test(dataProvider = "CountryCapitalData", dataProviderClass = SearchDataProvider.class)
    public void testSearchCapital(String country, String expectedCapital) {
        searchPage.searchForCountry(country);
        String actualCapital = searchPage.getCapitalOfCountry();
        Assert.assertTrue(actualCapital.contains(expectedCapital),
                "Capital city verification failed for " + country + ". Expected: " + expectedCapital + ", but found: " + actualCapital);
    }
}
