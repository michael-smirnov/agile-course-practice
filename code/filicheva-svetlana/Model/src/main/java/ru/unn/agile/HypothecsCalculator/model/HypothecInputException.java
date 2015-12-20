package ru.unn.agile.HypothecsCalculator.model;

public class HypothecInputException extends Exception {
    private final String message;

    public static final String NEGATIVE_HOUSE_COST =
            "Ошибка: стоимость недвижимости отрицательна";
    public static final String NOT_POSITIVE_CREDIT_PERIOD =
            "Ошибка: срок кредита меньше или равен 0";
    public static final String NEGATIVE_DOWN_PAYMENT =
            "Ошибка: отрицательный первый взнос";
    public static final String DOWN_PAYMENT_IS_MORE_THAN_HOUSE_COST =
            "Ошибка: первый взнос больше "
                    + "стоимости недвижимости";
    public static final String NEGATIVE_INTEREST_RATE =
            "Ошибка: процентная ставка отрицательна";
    public static final String NEGATIVE_MONTHLY_FEE =
            "Ошибка: ежемесячная комиссия отрицательна";
    public static final String NEGATIVE_FLAT_FEE =
            "Ошибка: единовременная комиссия отрицательна";
    public static final String BAD_DATA =
            "Ошибка: введите год не меньше 1991 и не больше 2100";
    public static final String BAD_MONTH =
            "Ошибка: неверный месяц";


    public HypothecInputException() {
        message = "";
    }

    public HypothecInputException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
