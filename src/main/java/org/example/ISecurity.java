package org.example;

/**
 *<p>Проверка денежных средств на фальшивость
 **/
public interface ISecurity {
    /**
     *<p>Проверка денег на соответствие номинала
     * @param banknoteNominal - номинал купюры
     * @return возвращает логическое значение true - если номинал соответствует и false - если нет
     **/
    boolean isDenominationCompliant(int banknoteNominal);



}
