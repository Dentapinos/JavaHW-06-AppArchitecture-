package org.example;

import java.util.List;

/**
 *<p>Механизм передачи/приема денег(купюроприемник)
 **/
public interface IMechanismsTransfer {

    /**
     *<p>Передача денег
     * @param listBanknotes список купюр для передачи
     * @param safe сейф, для доставки и возврата денег
     * @return возвращает список {@code List} купюр которые нужно вернуть (не приняты либо небыли забраны из отсека клиентом)
     **/
    List<Integer> transfer(List<Integer> listBanknotes, ISafe safe);
}
