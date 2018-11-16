package org.cirmmp.spring.metadata;


public class TxtMetadataExtractor extends DefaultMetadataExtractor {
    //will not zero normlines during parsing - TXT file thus maximum 16 lines of normal text and all lines with $$ or ##
    //will be included in metadata
    protected void initnormlines() {}
}
