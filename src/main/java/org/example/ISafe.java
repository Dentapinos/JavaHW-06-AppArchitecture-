package org.example;

import java.util.List;

/**
 *<p>Сейф. Хранилище для денег с отсеками под разные номиналы купюр
 **/
public interface ISafe {
    /**
     *<p>Общая сумма
     * @return возвращает общую сумму всех денег в сейфе
     **/
    int getTotalAmountOfMoney();//ок

    /**
     *<p>Прием денег в сейф с последующей раскладкой купюр по номиналу в ячейки
     * @param verifiedBanknotes принимает список {@code List} купюр, перед этим они должны быть проверены на подлинность.
     **/
    void acceptMoney(List<Integer> verifiedBanknotes);

    /**
     *<p>Выдача денег из сейфа
     * @param amount сколько денег выдать
     * @return возвращает строку с сообщением об состоянии завершенной операции
     **/
    String giveOutMoney(int amount);
}
