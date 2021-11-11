package com.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

@Client(CertificateConfiguration.CLIENT)
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = MediaType.APPLICATION_JSON)
public interface CertificateClient {

    @Get("/certificate")
    Mono<byte[]> generateCertificate(
            @QueryValue(value="user") String user);
}
