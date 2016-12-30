/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.appengine.cloudstorage;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;

/**
 *
 * @author matus
 */
public class ixCloudStorage {

    private static final int BUFFER_SIZE = 2 * 1024 * 1024;
    private String prefijoCubeta="ixCubeta_9907709990777099077_GGG_";

    private final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
            .initialRetryDelayMillis(10)
            .retryMaxAttempts(10)
            .totalRetryPeriodMillis(15000)
            .build());

    public OutputStream getOutputStream(String cubeta, String archivo)
            throws Exception {
        GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
        GcsFilename fileName = new GcsFilename(prefijoCubeta+cubeta, archivo);
        GcsOutputChannel outputChannel;
        outputChannel = gcsService.createOrReplace(fileName, instance);
        return Channels.newOutputStream(outputChannel);
    }

    public InputStream getInputString(String cubeta, String archivo) {
        GcsFilename fileName = new GcsFilename(prefijoCubeta+cubeta, archivo);
        GcsInputChannel readChannel
                = gcsService.
                        openPrefetchingReadChannel(fileName, 0, BUFFER_SIZE);
        return Channels.newInputStream(readChannel);
    }

    private void copiar(InputStream input, OutputStream output) throws IOException {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = input.read(buffer);
            while (bytesRead != -1) {
                output.write(buffer, 0, bytesRead);
                bytesRead = input.read(buffer);
            }
        } finally {
            input.close();
            output.close();
        }
    }

    /**
     * @return the prefijoCubeta
     */
    public String getPrefijoCubeta() {
        return prefijoCubeta;
    }
}
