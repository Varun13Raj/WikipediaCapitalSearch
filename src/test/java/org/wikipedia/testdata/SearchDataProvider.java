package org.wikipedia.testdata;

import org.testng.annotations.DataProvider;

public class SearchDataProvider {

    @DataProvider(name = "CountryCapitalData")
    public static Object[][] dpMethod() {
        return new Object[][]{
                {"India", "New Delhi"},
                {"England", "London"},
                {"France", "Paris"}
        };
    }
}

