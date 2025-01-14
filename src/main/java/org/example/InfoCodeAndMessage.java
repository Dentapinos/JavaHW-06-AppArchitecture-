package org.example;

public enum InfoCodeAndMessage {
    ACCEPT_BANKNOTES(200, "Банкноты приняты"),
    ERROR_ACCEPT_BANKNOTES(300, "Ошибка принятия банкнот"),
    IMPOSSIBLE_ISSUE(305, "Невозможно выдать сумму"),
    RECOMMENDED_AMOUNT(306, "Невозможно выдать указанную сумму, введите сумму кратную:"),
    ISSUANCE_SUCCESSFUL(205, "Получите ваши деньги:"),
    BALANCE_IN_ATM(100, "Остаток денег в банкомате:");

    private final int code;
    private final String message;
    InfoCodeAndMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getModifyingMessage(String addedMessage) {
        return message.concat(addedMessage);
    }

}
