package com.philosophy.api.csv;

import org.supercsv.prefs.CsvPreference;

public enum ECSVType {
    COMMA(CsvPreference.EXCEL_PREFERENCE),SEMICOLON(CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE),TAB(CsvPreference.TAB_PREFERENCE);

    private CsvPreference csvPreference;

    private ECSVType(CsvPreference csvPreference)
    {
        this.csvPreference = csvPreference;
    }

    public CsvPreference getCsvPreference()
    {
        return csvPreference;
    }
}
