package com.basejava.storage.serialize;

import com.basejava.model.*;
import com.basejava.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamStrategy implements SerializeStrategy {
    private final XmlParser xmlParser;

    public XmlStreamStrategy() {
        xmlParser = new XmlParser(
                Resume.class, Organization.class, Link.class, OrganizationSection.class,
                TextSection.class, ListSection.class, Organization.Property.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream outStream) throws IOException {
        try (Writer w = new OutputStreamWriter(outStream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume doRead(InputStream inStream) throws IOException {
        try (Reader r =new InputStreamReader(inStream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
