package com.ak.springbootdemo.sub.service;

import com.ak.springbootdemo.sub.constants.SourceType;
import com.ak.springbootdemo.sub.data.Subsidiary;
import com.ak.springbootdemo.sub.data.SubsidiaryRepository;
import com.ak.springbootdemo.sub.exceptions.SubsidiaryControllerException;
import com.ak.springbootdemo.sub.exceptions.SubsidiaryServiceException;
import com.ak.springbootdemo.sub.util.JSONReader;
import com.ak.springbootdemo.sub.util.Reader;
import com.ak.springbootdemo.sub.util.SubsidiaryDTO;
import com.ak.springbootdemo.sub.util.XMLReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Subsidiary Service
 */
@Service
public class SubsidiaryService {
    static final String JSON_FILE = "subsidiary.json";
    static final String XML_FILE = "subsidiary.xml";

    private final SubsidiaryRepository subsidiaryRepository;

    @Autowired
    public SubsidiaryService(SubsidiaryRepository subsidiaryRepository) {
        this.subsidiaryRepository = subsidiaryRepository;
    }

    /**
     * Get all Subsidiaries from DB.
     *
     * @return List of sorted Subsidiaries
     */
    public List<Subsidiary> getSubsidiaries() {
        Iterable<Subsidiary> subsidiaries = subsidiaryRepository.findAll();
        List<Subsidiary> subsidiariesList = new ArrayList<>();
        subsidiaries.forEach(subsidiariesList::add);
        Collections.sort(subsidiariesList);
        return subsidiariesList;
    }

    /**
     * Get Subsidiaries from various sourceTypes and save to DB.
     *
     * @param sourceType source type string
     * @return List of sorted SubsidiaryDTOs
     */
    public List<SubsidiaryDTO> getSubsidiariesFromExternalSource(String sourceType) {
        if (sourceType == null) {
            return getSubsidiaries().stream().map(Subsidiary::toDTO).collect(Collectors.toList());
        } else {
            switch (SourceType.getSourceType(sourceType).orElse(SourceType.UNDEFINED)) {
                case JSON:
                    return getSubsidiariesFromExternalSource(new JSONReader(JSON_FILE));
                case XML:
                    return getSubsidiariesFromExternalSource(new XMLReader(XML_FILE));
                case UNDEFINED:
                    throw new SubsidiaryControllerException(String.format("Unknown source type value: '%s'.", sourceType));
                default:
                    return new ArrayList<>();
            }
        }
    }

    /**
     * Get Subsidiaries from various sourceTypes and save to DB.
     *
     * @param reader for appropriate source type
     * @return List of sorted SubsidiaryDTOs
     */
    protected List<SubsidiaryDTO> getSubsidiariesFromExternalSource(Reader reader) {
        List<SubsidiaryDTO> subsidiaries = reader.getSubsidiaries();
        Collections.sort(subsidiaries);
        saveToDB(subsidiaries);
        return subsidiaries;
    }


    /**
     * Get Subsidiary by InnerCode from DB.
     *
     * @param innerCode by which it is needed to find the Subsidiary
     * @return Subsidiary if exist or empty
     */
    public Optional<Subsidiary> getSubsidiariesByInnerCode(String innerCode) {
        return this.subsidiaryRepository.findByInnerCode(innerCode);
    }

    /**
     * Create or Update Subsidiary entity
     *
     * @param innerCode   unique inner code of the Subsidiary
     * @param address     Subsidiary address
     * @param name        Subsidiary name
     * @param phoneNumber Subsidiary phoneNumber
     * @return Created or Updated Subsidiary entity
     */
    public Subsidiary saveSubsidiary(String innerCode, String address, String name, String phoneNumber) {
        Optional<Subsidiary> optionalSub = subsidiaryRepository.findByInnerCode(innerCode);
        optionalSub.ifPresent(sub -> sub.update(innerCode, address, name, phoneNumber));
        return optionalSub.orElse(addSubsidiary(new Subsidiary(innerCode, address, name, phoneNumber)));
    }

    /**
     * Add new Subsidiary into DB.
     *
     * @param subsidiary Subsidiary entity that have to be added into DB
     * @return Subsidiary entity that was added into DB
     */
    protected Subsidiary addSubsidiary(Subsidiary subsidiary) {
        if (null == subsidiary) {
            throw new SubsidiaryServiceException("Subsidiary cannot be null.");
        }
        this.subsidiaryRepository.save(subsidiary);
        return subsidiary;
    }

    /**
     * Save Subsidiary entities into DB
     *
     * @subsidiaryDTOList subsidiaries list to save
     */
    protected void saveToDB(List<SubsidiaryDTO> subsidiaryDTOList) {
        subsidiaryDTOList.forEach(importedSubsidiary ->
                saveSubsidiary(
                        importedSubsidiary.getInnerCode(),
                        importedSubsidiary.getAddress(),
                        importedSubsidiary.getName(),
                        importedSubsidiary.getPhoneNumber()));
    }
}