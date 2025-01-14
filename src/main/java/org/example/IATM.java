package org.example;

import java.util.Map;

/**
 *<p>Банкомат. Основные функции это прием, выдача денег, а так же отображение баланса банкомата
 **/
public interface IATM {
    /**
     *<p>Выдача денег
     * @param amount - необходимая сумма для выдачи
     * @return возвращает строку {@code String} с сообщением об состоянии выдачи денег
     **/
    String issuesMoney(int amount);

    /**
     *<p>Прием денег
     * @param money - принимает сумму денег в виде коллекции {@code Map} где ключ - номинал купюры, а значение - количество таких купюр
     * @return возвращает строку {@code String} с сообщением об состоянии зачисления денег
     **/
    String acceptMoney(Map<Integer, Integer> money);

    /**
     *<p>Количество денег на счету банкомата
     * @return возвращает строку {@code String} с сообщением о количестве денег в банкомате
     **/
    String showHowMuchMoneyIsInTheATM();
}

