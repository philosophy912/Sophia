package com.philosophy.csv.api;

import org.apache.commons.csv.CSVFormat;

/**
 * @author totti_912@sina.com
 */
public enum CsvEnum {
    /**
     * Standard Comma Separated Value format, as for RFC4180 but allowing empty lines.
     * <p>
     * DEFAULT = new CSVFormat(',', Constants.DOUBLE_QUOTE_CHAR, (QuoteMode)null, (Character)null,
     * (Character)null, false, true, "\r\n", (String)null, (Object[])null,
     * (String[])null, false, false, false, false, false, false, true);
     */
    DEFAULT(CSVFormat.DEFAULT),
    /**
     * The Microsoft Excel CSV format.
     * <p>
     * EXCEL = DEFAULT.withIgnoreEmptyLines(false).withAllowMissingColumnNames();
     */
    EXCEL(CSVFormat.EXCEL),
    /**
     * Informix UNLOAD format used by the UNLOAD TO file_name operation.
     * <p>
     * INFORMIX_UNLOAD = DEFAULT.withDelimiter('|').withEscape('\\').withQuote(Constants.DOUBLE_QUOTE_CHAR)
     * .withRecordSeparator('\n');
     */
    INFORMIX_UNLOAD(CSVFormat.INFORMIX_UNLOAD),
    /**
     * Informix CSV UNLOAD format used by the UNLOAD TO file_name operation (escaping is disabled.)
     * <p>
     * INFORMIX_UNLOAD_CSV = DEFAULT.withDelimiter(',').withQuote(Constants.DOUBLE_QUOTE_CHAR)
     * .withRecordSeparator('\n');
     */
    INFORMIX_UNLOAD_CSV(CSVFormat.INFORMIX_UNLOAD_CSV),
    /**
     * MongoDB CSV format used by the mongoexport operation.
     * <p>
     * MONGODB_CSV = DEFAULT.withDelimiter(',').withEscape(Constants.DOUBLE_QUOTE_CHAR)
     * .withQuote(Constants.DOUBLE_QUOTE_CHAR).withQuoteMode(QuoteMode.MINIMAL).withSkipHeaderRecord(false);
     */
    MONGODB_CSV(CSVFormat.MONGODB_CSV),
    /**
     * MongoDB TSV format used by the mongoexport operation.
     * <p>
     * MONGODB_TSV = DEFAULT.withDelimiter('\t').withEscape(Constants.DOUBLE_QUOTE_CHAR)
     * .withQuote(Constants.DOUBLE_QUOTE_CHAR).withQuoteMode(QuoteMode.MINIMAL).withSkipHeaderRecord(false);
     */
    MONGODB_TSV(CSVFormat.MONGODB_TSV),
    /**
     * The MySQL CSV format.
     * <p>
     * MYSQL = DEFAULT.withDelimiter('\t').withEscape('\\').withIgnoreEmptyLines(false)
     * .withQuote((Character)null).withRecordSeparator('\n').withNullString("\\N")
     * .withQuoteMode(QuoteMode.ALL_NON_NULL);
     */
    MYSQL(CSVFormat.MYSQL),
    /**
     * Default Oracle format used by the SQL*Loader utility.
     * <p>
     * ORACLE = DEFAULT.withDelimiter(',').withEscape('\\').withIgnoreEmptyLines(false)
     * .withQuote(Constants.DOUBLE_QUOTE_CHAR).withNullString("\\N").withTrim().withSystemRecordSeparator()
     * .withQuoteMode(QuoteMode.MINIMAL);
     */
    ORACLE(CSVFormat.ORACLE),
    /**
     * Default PostgreSQL CSV format used by the COPY operation.
     * <p>
     * POSTGRESQL_CSV = DEFAULT.withDelimiter(',').withEscape(Constants.DOUBLE_QUOTE_CHAR)
     * .withIgnoreEmptyLines(false).withQuote(Constants.DOUBLE_QUOTE_CHAR).withRecordSeparator('\n')
     * .withNullString("").withQuoteMode(QuoteMode.ALL_NON_NULL);
     */
    POSTGRESQL_CSV(CSVFormat.POSTGRESQL_CSV),
    /**
     * Default PostgreSQL text format used by the COPY operation.
     * <p>
     * POSTGRESQL_TEXT = DEFAULT.withDelimiter('\t').withEscape('\\').withIgnoreEmptyLines(false)
     * .withQuote(Constants.DOUBLE_QUOTE_CHAR).withRecordSeparator('\n').withNullString("\\N")
     * .withQuoteMode(QuoteMode.ALL_NON_NULL);
     */
    POSTGRESQL_TEXT(CSVFormat.POSTGRESQL_TEXT),
    /**
     * The RFC-4180 format defined by RFC-4180.
     * <p>
     * RFC4180 = DEFAULT.withIgnoreEmptyLines(false);
     */
    RFC4180(CSVFormat.RFC4180),
    /**
     * A tab delimited format.
     * <p>
     * TDF = DEFAULT.withDelimiter('\t').withIgnoreSurroundingSpaces();
     */
    TDF(CSVFormat.TDF);

    private CSVFormat csvFormat;

    CsvEnum(CSVFormat csvFormat) {
        this.csvFormat = csvFormat;
    }

    public CSVFormat getCsvFormat() {
        return csvFormat;
    }
}
