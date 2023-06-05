package com.ak.springbootdemo.sub.util;

import java.util.List;

public interface Reader {

    /**
     * Read Subsidiary DTO entities from an external source file
     *
     * @return list of Subsidiary DTOs
     */
    List<SubsidiaryDTO> getSubsidiaries();
}
