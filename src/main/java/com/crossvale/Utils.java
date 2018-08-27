package com.crossvale;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;

public class Utils {

    public static KieContainer createKieContainer( File directory, int microVersion ) {

        try {
            // KieServices is the entry point of APIs
            KieServices ks = KieServices.Factory.get();

            // KieFileSystem is responsible for gathering resources
            KieFileSystem kfs = ks.newKieFileSystem();

            File[] files = directory.listFiles();
            for ( File file : files ) {
                String filename = file.getName();
                // for simplicity, only pick .drl
                if ( filename.endsWith( ".drl" ) ) {
                    // You can add your DRL files as InputStream here
                    kfs.write( "src/main/resources/rules" + filename, ks.getResources().newInputStreamResource( new FileInputStream( file ) ) );
                }
            }

            // You need to specify a unique ReleaseId per KieContainer (= the unit which you store a set of DRL files)
            // ReleaseId consists of "GroupId" + "ArtifactId" + "Version". The same idea of Maven artifact.
            ReleaseId releaseId = ks.newReleaseId( "com.crossvale", "balancer", "1.0." + microVersion );
            kfs.generateAndWritePomXML( releaseId );

            // Now resources are built and stored into an internal repository
            ks.newKieBuilder( kfs ).buildAll();

            // You can get a KieContainer with the ReleaseId
            KieContainer kcontainer = ks.newKieContainer( releaseId );

            System.out.println( kcontainer.getReleaseId() + " created" );

            return kcontainer;

        } catch ( FileNotFoundException e ) {
            throw new RuntimeException( e );
        }
    }

    public static boolean scanForUpdate( File directory, Map<String, Long> fileTimestampMap ) {

        // This method doesn't look at the difference in details. Just returns true when there is any file timestamp change or file added/deleted/renamed.

        boolean update = false;

        Map<String, Long> currentFileTimestampMap = new HashMap<String, Long>();

        File[] files = directory.listFiles();
        for ( File file : files ) {
            String filename = file.getName();
            // for simplicity, only pick .drl
            if ( filename.endsWith( ".drl" ) ) {
                currentFileTimestampMap.put( filename, file.lastModified() );
            }
        }

        // now compare
        Set<String> currentFiles = currentFileTimestampMap.keySet();
        Set<String> storedFiles = fileTimestampMap.keySet();
        if ( currentFiles.equals( storedFiles ) ) {
            // filenames are not changed. now compare timestamp
            for ( String filename : storedFiles ) {
                if ( !fileTimestampMap.get( filename ).equals( currentFileTimestampMap.get( filename ) ) ) {
                    // file timestamp doesn't match
                    System.out.println( filename + " changed" );
                    fileTimestampMap.clear();
                    fileTimestampMap.putAll( currentFileTimestampMap );
                    update = true;
                    break;
                }
            }
        } else {
            // file added/deleted/renamed
            System.out.println( "file added/deleted/renamed : currentFiles = " + currentFiles + ", storedFiles = " + storedFiles );
            fileTimestampMap.clear();
            fileTimestampMap.putAll( currentFileTimestampMap );
            update = true;
        }

        return update;
    }
}
