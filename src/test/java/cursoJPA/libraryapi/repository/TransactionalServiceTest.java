package cursoJPA.libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cursoJPA.libraryapi.service.TransactionalService;

@SpringBootTest
public class TransactionalServiceTest {

    @Autowired
    TransactionalService ts;

    @Test
    void execAcaoComTransacao(){
        ts.execComTransacao();
    }

}
