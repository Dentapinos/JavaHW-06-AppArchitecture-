package org.example;

import java.util.Map;

/**
 *<p>Купюроприемник. Зачисляет и выдает деньги
 **/
public interface IBillAcceptor {
    /**
     *<p>Выдача денег
     * @param banknoteNominals - коллекция {@code Map} денег
     * @return возвращает {@code boolean} логическое значение зачислены деньги или нет
     **/
    boolean acceptTheBill(Map<Integer, Integer> banknoteNominals);
    /**
     *<p>Прием денег
     * @param amount сколько денег выдать
     * @return возвращает коллекцию денежных средств {@code Map} где ключ - номинал купюры, а значение - количество таких купюр.
     * Либо возвращает коллекцию с одним ключом {code 0} и значением рекомендуемой суммы, она рассчитывается от как наименьшая купюра в банкомате
     **/
    Map<Integer, Integer> giveTheBill(int amount);

    /**
     *<p>Сумма денег на счету банкомата
     * @param moneys - принимает сумму денег в виде коллекции {@code Map} где ключ - номинал купюры, а значение - количество таких купюр
     * @return возвращает сумму денег
     **/
    int getRemainingMoney(Map<Integer, Integer> moneys);
}
