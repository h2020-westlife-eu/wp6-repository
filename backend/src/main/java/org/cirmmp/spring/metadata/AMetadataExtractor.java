package org.cirmmp.spring.metadata;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface AMetadataExtractor {
    public JSONObject harvestFile(Path fileordir) throws IOException, FileNotFoundException;
}
