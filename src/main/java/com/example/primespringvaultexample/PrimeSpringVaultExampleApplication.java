package com.example.primespringvaultexample;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.SslConfiguration;
import org.springframework.vault.support.VaultResponseSupport;

@Slf4j
public class PrimeSpringVaultExampleApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                VaultConfiguration.class);

        context.start();

        VaultTemplate vaultTemplate = context.getBean(VaultTemplate.class);

        MySecretData mySecretData = new MySecretData();
        mySecretData.setUsername("walter");
        mySecretData.setPassword("white");

        vaultTemplate.write(
                "secret/myapplication/user/3128", mySecretData);
        log.info("Wrote data to Vault");

        VaultResponseSupport<MySecretData> response = vaultTemplate.read(
                "secret/myapplication/user/3128", MySecretData.class);

        log.info("Retrieved data {} from Vault", response.getData().getUsername());

        context.stop();
    }

    @Configuration
    static class VaultConfiguration extends AbstractVaultConfiguration {

        @Override
        public VaultEndpoint vaultEndpoint() {
            return new VaultEndpoint();
        }

        @Override
        public ClientAuthentication clientAuthentication() {
            return new TokenAuthentication("00000000-0000-0000-0000-000000000000");
        }

        @Override
        public SslConfiguration sslConfiguration() {
            return ExamplesSslConfiguration.create();
        }
    }

    @Data
    static class MySecretData {

        String username;
        String password;
    }

}
