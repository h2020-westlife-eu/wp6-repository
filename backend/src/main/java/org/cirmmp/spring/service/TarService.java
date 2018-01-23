package org.cirmmp.spring.service;

import java.io.File;

public interface TarService {
     File createTarFile(String source, Long datasetId);

}
