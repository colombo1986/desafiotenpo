package org.tenpo.challenge.cvergara;

import org.springframework.boot.SpringApplication;

public class TestCvergaraApplication {

    public static void main(String[] args) {
        SpringApplication.from(CvergaraApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
