package com.example;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@MicronautTest
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {8090})
class CertificateClientTest {

    ClientAndServer clientAndServer;

    public CertificateClientTest(ClientAndServer clientAndServer) {

        this.clientAndServer = clientAndServer;
    }

    @Inject
    CertificateClient underTest;

    @Test
    void testGenerateCertificateIsWorking() {

        // given
        String user = "Test User";
        byte[] mockdata = "This is a test for MockServer-based unit test".getBytes(StandardCharsets.UTF_8);

        // when
        clientAndServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/certificate")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withContentType(MediaType.PDF)
                                .withHeader("Content-Disposition", "attachment; filename=FIMM_Cert.pdf")
                                .withBody(mockdata)
                );

        byte[] response = underTest
                .generateCertificate(user)
                .block();

        // then
        assertTrue(Arrays.equals(mockdata, response));
    }
}