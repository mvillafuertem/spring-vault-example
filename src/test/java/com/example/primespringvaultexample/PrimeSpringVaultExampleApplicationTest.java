package com.example.primespringvaultexample;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.VaultResponseSupport;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = VaultTestConfiguration.class)
@Slf4j
class PrimeSpringVaultExampleApplicationTest {


    @Autowired
    VaultOperations vaultOperations;

    @Test
    public void shouldWriteAndReadData() throws Exception {

        MySecretData mySecretData = new MySecretData();
        mySecretData.setSecurityQuestion("Say my name");
        mySecretData.setAnswer("Heisenberg");

        vaultOperations.write("secret/myapplication/user/3128", mySecretData);
        log.info("Wrote data to Vault");

        VaultResponseSupport<MySecretData> response = vaultOperations.read(
                "secret/myapplication/user/3128", MySecretData.class);

        MySecretData data = response.getData();
        assertThat(data.getSecurityQuestion()).isEqualTo(
                mySecretData.getSecurityQuestion());
        assertThat(data.getAnswer()).isEqualTo(mySecretData.getAnswer());
    }

    @Data
    static class MySecretData {

        String securityQuestion;
        String answer;
    }

}