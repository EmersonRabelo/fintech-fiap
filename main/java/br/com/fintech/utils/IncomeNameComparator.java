package br.com.fintech.utils;

import java.util.Comparator;

import br.com.fintech.model.MonthlyIncome;

public class IncomeNameComparator implements Comparator<MonthlyIncome> {

	@Override
	public int compare(MonthlyIncome income01, MonthlyIncome income02) {
		return income01.getName().compareTo(income02.getName());
	}
	
}
