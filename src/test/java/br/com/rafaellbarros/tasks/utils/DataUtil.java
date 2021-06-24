package br.com.rafaellbarros.tasks.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataUtil {

    public static String convertLocalDateToDateStringBR(final LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }
}
