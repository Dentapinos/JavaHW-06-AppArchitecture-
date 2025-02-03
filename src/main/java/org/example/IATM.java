package org.example;

import java.util.List;

/**
 *<p>Банкомат. Основные функции это прием, выдача денег, а так же отображение баланса банкомата
 **/
public interface IATM {
    /**
     *<p>Выдача денег
     * @param amount - необходимая сумма для выдачи
     * @return возвращает сообщение для отображения {@code String}
     **/
    String giveOutMoney(int amount);

    /**
     *<p>Прием денег
     * @param banknotes - принимает сумму денег в виде коллекции {@code List}, а именно номиналы купюр
     * @return возвращает сообщение для отображения {@code String}
     **/
    String acceptMoney(List<Integer> banknotes);

    /**
     *<p>Количество денег на счету банкомата
     *Запрос на отображение оставшегося количества денег в банкомате
     *@return возвращает сообщение для отображения {@code String}
     **/
    String showBalance();

    /**
     *Установка сейфа в банкомат
     * @param safe - объект сейфа
     **/
    void setSafe(ISafe safe);

    /**
     *Установка механизма передачи денег
     * @param mechanismsTransfer - объект механизма передачи денег от клиента в сейф
     **/
    void setMechanismsTransfer(IMechanismsTransfer mechanismsTransfer);
}
