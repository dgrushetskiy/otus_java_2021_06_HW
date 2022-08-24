package ru.otus.core.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.base.AbstractHibernateTest;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DataTemplateHibernateTest extends AbstractHibernateTest {

    @Test
    @DisplayName(" корректно сохраняет, изменяет и загружает клиента по заданному id")
    void shouldSaveAndFindCorrectClientById() {
        //given
        var client = new Client("Вася");
        var address = new Address("Лизюкова");
        var phone = new Phone("9999", client);
        client.setPhones(Collections.singletonList(phone));
        client.setAddress(address);

        //when
        var savedClient = transactionManager.doInTransaction(session -> {
            clientTemplate.insert(session, client);
            return client;
        });

        //then
        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getName()).isEqualTo(client.getName());
        assertThat(savedClient.getAddress()).isNotNull();
        assertThat(savedClient.getPhones().size()).isEqualTo(1);

        //when
        var loadedSavedClient = transactionManager.doInTransaction(session ->
                clientTemplate.findById(session, savedClient.getId())
        );

        //then
        assertThat(loadedSavedClient).isPresent().get().usingRecursiveComparison().isEqualTo(savedClient);

        //when
        var updatedClient = savedClient.clone();
        updatedClient.setName("updatedName");
        transactionManager.doInTransaction(session -> {
            clientTemplate.update(session, updatedClient);
            return null;
        });

        //then
        var loadedClient = transactionManager.doInTransaction(session ->
                clientTemplate.findById(session, updatedClient.getId())
        );
        assertThat(loadedClient).isPresent().get().usingRecursiveComparison().ignoringFields("phones").isEqualTo(updatedClient);
        List<Phone> loadedPhones = loadedClient.get().getPhones();
        List<Phone> updatedPhones = updatedClient.getPhones();
        assertThat(loadedPhones).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(updatedPhones);

        //when
        var clientList = transactionManager.doInTransaction(session ->
                clientTemplate.findAll(session)
        );

        //then
        assertThat(clientList.size()).isEqualTo(1);
        assertThat(clientList.get(0)).usingRecursiveComparison().isEqualTo(updatedClient);
    }
}
