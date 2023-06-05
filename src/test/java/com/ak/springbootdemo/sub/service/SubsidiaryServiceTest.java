package com.ak.springbootdemo.sub.service;

import com.ak.springbootdemo.sub.data.Subsidiary;
import com.ak.springbootdemo.sub.data.SubsidiaryRepository;
import com.ak.springbootdemo.sub.exceptions.SubsidiaryServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("SubsidiaryService tests")
class SubsidiaryServiceTest {
    static final String INNER_CODE = "VS";
    static final String ADDRESS = "Test";
    static final String PHONE = "111-111-111";

    static final SubsidiaryRepository subsidiaryRepository = Mockito.mock(SubsidiaryRepository.class);
    static final SubsidiaryService subsidiaryService = new SubsidiaryService(subsidiaryRepository);

    static final Subsidiary validSubsidiaryExample = new Subsidiary(
            INNER_CODE, ADDRESS, "Test Valid Subsidiary", PHONE);
    static final Subsidiary updatedSubsidiaryExample = new Subsidiary(
            INNER_CODE, ADDRESS, "Updated Test Valid Subsidiary", PHONE);

    @BeforeAll
    static void setUpAll() {
        when(subsidiaryRepository.save(any(Subsidiary.class))).then(returnsFirstArg());
    }

    public static Stream<Arguments> createSubsidiaryInputParams() {
        return Stream.of(
                Arguments.of("Subsidiary exist - update case", true),
                Arguments.of("No existing Subsidiary - create new case", false)
        );
    }

    @ParameterizedTest
    @MethodSource("createSubsidiaryInputParams")
    @DisplayName("createSubsidiary() test cases")
    void createSubsidiaryTest(String testCaseDescription, boolean validSubsidiary) {
        Optional<Subsidiary> optionalSubsidiary =
                validSubsidiary ? Optional.of(validSubsidiaryExample) : Optional.empty();
        when(subsidiaryRepository.findByInnerCode(anyString())).thenReturn(optionalSubsidiary);
        Assertions.assertEquals(updatedSubsidiaryExample, subsidiaryService.saveSubsidiary(
                updatedSubsidiaryExample.getInnerCode(),
                updatedSubsidiaryExample.getAddress(),
                updatedSubsidiaryExample.getName(),
                updatedSubsidiaryExample.getPhoneNumber()), testCaseDescription);
    }

    @Test
    @DisplayName("Invalid input to the addSubsidiary()")
    void invalidInputAddSubsidiaryTest() {
        final Class<SubsidiaryServiceException> expectedExceptionClass = SubsidiaryServiceException.class;
        final String expectedExceptionMessage = "Subsidiary cannot be null.";

        final Exception actualException = Assertions.assertThrows(
                expectedExceptionClass,
                () -> subsidiaryService.addSubsidiary(null)
        );
        Assertions.assertEquals(expectedExceptionClass, actualException.getClass(), "Exception class");
        Assertions.assertEquals(expectedExceptionMessage, actualException.getMessage(), "Exception message");
    }

    @Test
    @DisplayName("Valid input to the addSubsidiary()")
    void validInputAddSubsidiaryTest() {
        Assertions.assertEquals(validSubsidiaryExample, subsidiaryService.addSubsidiary(validSubsidiaryExample));
    }

}
