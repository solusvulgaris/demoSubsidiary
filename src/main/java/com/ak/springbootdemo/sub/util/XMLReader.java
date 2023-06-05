package com.ak.springbootdemo.sub.util;

import com.ak.springbootdemo.sub.exceptions.SubsidiaryControllerException;
import com.ak.springbootdemo.sub.service.SubsidiaryService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

public class XMLReader implements Reader {
    private final String fileName;

    public XMLReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<SubsidiaryDTO> getSubsidiaries() {
        List<SubsidiaryDTO> subsidiariesFromXML;
        try (final InputStream resourceAsStream = SubsidiaryService.class.getClassLoader().getResourceAsStream(fileName)) {
            SAXReader saxReader = new SAXReader();
            saxReader.setValidation(false);
            saxReader.setEntityResolver((publicId, systemId) -> {
                if (systemId.contains(".dtd")) {
                    return new InputSource(new StringReader(""));
                } else {
                    return null;
                }
            });

            Document document = saxReader.read(resourceAsStream);
            List<Node> nodes = document.selectNodes("/subsidiaries/subsidiary");

            subsidiariesFromXML = nodes.stream()
                    .map(p -> new SubsidiaryDTO(
                            p.selectSingleNode("innerCode").getText(),
                            p.selectSingleNode("address").getText(),
                            p.selectSingleNode("name").getText(),
                            p.selectSingleNode("phoneNumber").getText())).collect(Collectors.toList());
        } catch (IOException | DocumentException e) {
            throw new SubsidiaryControllerException("Unable get subsidiaries list from xml file.");
        }
        return subsidiariesFromXML;
    }
}
