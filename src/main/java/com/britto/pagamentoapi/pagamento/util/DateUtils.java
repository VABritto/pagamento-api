package com.britto.pagamentoapi.pagamento.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	private static final DateTimeFormatter DIA_MES_ANO_HORARIO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	private DateUtils() {}

	public static LocalDateTime diaMesAnoHorario(String data) {
		return LocalDateTime.parse(data, DIA_MES_ANO_HORARIO);
	}	
}
