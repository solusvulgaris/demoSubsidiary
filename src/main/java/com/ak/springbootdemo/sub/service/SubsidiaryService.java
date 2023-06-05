package com.ak.springbootdemo.sub.service;

import com.ak.springbootdemo.sub.constants.SourceType;
import com.ak.springbootdemo.sub.data.Subsidiary;
import com.ak.springbootdemo.sub.data.SubsidiaryRepository;
import com.ak.springbootdemo.sub.exceptions.SubsidiaryControllerException;
import com.ak.springbootdemo.sub.exceptions.SubsidiaryServiceException;
import com.ak.springbootdemo.sub.util.JSONReader;
import com.ak.springbootdemo.sub.util.Reader;
import com.ak.springbootdemo.sub.util.SubsidiaryDTO;
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
     * Get Subsidiaries from various sourceTypes.
     *
     * @return List of SubsidiaryDTOs sorted by name -> innerCode
     */
    public List<SubsidiaryDTO> getSubsidiaries(String sourceType) {
        if (sourceType == null) {
            return getSubsidiaries().stream().map(Subsidiary::toDTO).collect(Collectors.toList());
        } else {
            List<SubsidiaryDTO> subsidiaryDTOs;
            Reader reader = null;
            switch (SourceType.getSourceType(sourceType).orElse(SourceType.UNDEFINED)) {
                case JSON:
                    reader = new JSONReader(JSON_FILE);
                    break;
                case XML:
                    //TODO: implement reading from xml
                    break;
                case UNDEFINED:
                    throw new SubsidiaryControllerException(String.format("Unknown source type value: '%s'.", sourceType));
                default:
                    break;
            }

            assert reader != null;
            subsidiaryDTOs = reader.getSubsidiaries();
            saveToDB(subsidiaryDTOs);
            return subsidiaryDTOs;
        }
    }

    /**
     * Get Subsidiary by InnerCode from DB.
     *
     * @return Subsidiary if exist or empty
     */
    public Optional<Subsidiary> getSubsidiariesByInnerCode(String innerCode) {
        return this.subsidiaryRepository.findByInnerCode(innerCode);
    }

    /**
     * Create or Update Subsidiary entity
     *
     * @param innerCode   unique inner code of the subsidiary
     * @param address     subsidiary address
     * @param name        subsidiary name
     * @param phoneNumber subsidiary phoneNumber
     * @return Created or Updated Subsidiary entity
     */
    public Subsidiary saveSubsidiary(String innerCode, String address, String name, String phoneNumber) {
        Optional<Subsidiary> optionalSub = subsidiaryRepository.findByInnerCode(innerCode);
        optionalSub.ifPresent(sub -> sub.update(innerCode, address, name, phoneNumber));
        return optionalSub.orElse(addSubsidiary(new Subsidiary(innerCode, address, name, phoneNumber)));
    }

    /**
     * Find Subsidiary by inner code.
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
        //TODO: check if null
        subsidiaryDTOList.forEach(importedSubsidiary ->
                saveSubsidiary(
                        importedSubsidiary.getInnerCode(),
                        importedSubsidiary.getAddress(),
                        importedSubsidiary.getName(),
                        importedSubsidiary.getPhoneNumber()));
    }
}