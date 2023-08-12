package br.com.fiap;


import br.com.fiap.domain.entity.Deposito;
import br.com.fiap.domain.entity.ItemEstocado;
import br.com.fiap.domain.entity.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Deposito deposito = new Deposito();
        Produto produto = new Produto();

        produto.setId(null)
                .setNome("Abacaxi")
                .setDescricao("O melhor da feira")
                .setValor(BigDecimal.valueOf(4.99));

        deposito.setId(null).setNome("Vila caraca");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");

        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(deposito);
        manager.persist(produto);

        var count = 0;
        var qtd = 50;

        while (count < qtd) {
            var item = new ItemEstocado();
            item
                    .setDeposito(deposito)
                    .setProduto(produto)
                    .setEntrada(LocalDateTime.now())
                    .setNumeroDeSerie(count + "NR" + qtd + "-" + produto.getId() + "-" + deposito.getId());

            manager.persist(item);

            count++;
        }

        manager.getTransaction().commit();
        manager.close();

        factory.close();

        System.out.println(deposito.toString());
        System.out.println(produto.toString());
    }
}