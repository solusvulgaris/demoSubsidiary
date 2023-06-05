package com.ak.springbootdemo.sub.util;

import com.ak.springbootdemo.sub.exceptions.SubsidiaryControllerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class XMLReaderTest {
    static final String XML_FILE = "subsidiary.xml";
    static final String XML_INVALID_FILE = "subsidiary_.xml";

    @Test
    @DisplayName("Valid input to the saveSubsidiariesFromXMLFile()")
    void saveSubsidiariesFromXMLFileTest() {
        final XMLReader reader = new XMLReader(XML_FILE);
        Assertions.assertEquals(2, reader.getSubsidiaries().size());
    }

    @Test
    @DisplayName("Invalid input to the saveSubsidiariesFromXMLFile()")
    void saveSubsidiariesFromXMLFileInvalidTest() {
        final XMLReader reader = new XMLReader(XML_INVALID_FILE);
        final Class<SubsidiaryControllerException> expectedExceptionClass = SubsidiaryControllerException.class;
        final String expectedExceptionMessage = "Unable get subsidiaries list from xml file.";

        final Exception actualException = Assertions.assertThrows(
                expectedExceptionClass,
                reader::getSubsidiaries
        );
        Assertions.assertEquals(expectedExceptionClass, actualException.getClass(), "Exception class");
        Assertions.assertEquals(expectedExceptionMessage, actualException.getMessage(), "Exception message");
    }
}
