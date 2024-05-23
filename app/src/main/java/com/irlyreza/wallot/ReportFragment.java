package com.irlyreza.wallot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




public class ReportFragment extends Fragment {

    private RadioGroup radioGroup,dailyRadioGroup,weeklyRadioGroup,monthlyRadioGroup,yearlyRadioGroup;

    private RadioButton dailyRadioButton, weeklyRadioButton, monthlyRadioButton, yearlyRadioButton, dailyIncomeRadio, dailyOutcomeRadio, weeklyIncomeRadio,weeklyOutcomeRadio,monthlyIncomeRadio, monthlyOutcomeRadio, yearlyIncomeRadio,yearlyOutcomeRadio;
    private View dailyContentLayout, weeklyContentLayout, monthlyContentLayout, yearlyContentLayout;

    private TextView dailyIncomeTotal, dailyOutcomeTotal, dailyDebtTotal, weeklyIncomeTotal, weeklyOutcomeTotal, weeklyDebtTotal, monthlyIncomeTotal, monthlyOutcomeTotal, monthlyDebtTotal, yearlyIncomeTotal, yearlyOutcomeTotal, yearlyDebtTotal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        radioGroup = view.findViewById(R.id.radioGroup);
        dailyContentLayout = view.findViewById(R.id.dailyContentLayout);
        weeklyContentLayout = view.findViewById(R.id.weeklyContentLayout);
        monthlyContentLayout = view.findViewById(R.id.monthlyContentLayout);
        yearlyContentLayout = view.findViewById(R.id.yearlyContentLayout);

        dailyRadioGroup = view.findViewById(R.id.dailyGroupButton);
        monthlyRadioGroup = view.findViewById(R.id.monthlyGroupButton);
        weeklyRadioGroup = view.findViewById(R.id.weeklyGroupButton);
        yearlyRadioGroup = view.findViewById(R.id.yearlyGroupButton);

        dailyRadioButton = view.findViewById(R.id.dailyRadioButton);
        monthlyRadioButton = view.findViewById(R.id.monthlyRadioButton);
        weeklyRadioButton = view.findViewById(R.id.weeklyRadioButton);
        yearlyRadioButton = view.findViewById(R.id.yearlyRadioButton);

        dailyIncomeTotal = view.findViewById(R.id.daily_income_nominal);
        dailyOutcomeTotal = view.findViewById(R.id.daily_outcome_nominal);
        dailyDebtTotal = view.findViewById(R.id.daily_debt_nominal);
        weeklyIncomeTotal = view.findViewById(R.id.weekly_income_nominal);
        weeklyOutcomeTotal = view.findViewById(R.id.weekly_outcome_nominal);
        weeklyDebtTotal = view.findViewById(R.id.weekly_debt_nominal);
        monthlyIncomeTotal = view.findViewById(R.id.monthly_income_nominal);
        monthlyOutcomeTotal = view.findViewById(R.id.monthly_outcome_nominal);
        monthlyDebtTotal = view.findViewById(R.id.monthly_debt_nominal);
        yearlyIncomeTotal= view.findViewById(R.id.yearly_income_nominal);
        yearlyOutcomeTotal = view.findViewById(R.id.yearly_outcome_nominal);
        yearlyDebtTotal = view.findViewById(R.id.yearly_debt_nominal);


        dailyIncomeRadio = view.findViewById(R.id.dailyIncomeRadio);
        dailyOutcomeRadio = view.findViewById(R.id.dailyOutcomeRadio);
        weeklyIncomeRadio = view.findViewById(R.id.weeklyIncomeRadio);
        weeklyOutcomeRadio = view.findViewById(R.id.weeklyOutcomeRadio);
        monthlyIncomeRadio = view.findViewById(R.id.monthlyIncomeRadio);
        monthlyOutcomeRadio = view.findViewById(R.id.monthlyOutcomeRadio);
        yearlyIncomeRadio = view.findViewById(R.id.yearlyIncomeRadio);
        yearlyOutcomeRadio = view.findViewById(R.id.yearlyOutcomeRadio);

        dailyRadioButton.setChecked(true);
        dailyIncomeRadio.setChecked(true);
        weeklyIncomeRadio.setChecked(true);
        monthlyIncomeRadio.setChecked(true);
        yearlyIncomeRadio.setChecked(true);





        //-----daily-----//
        DateRange dateRange = new DateRange();
        String dateToday = dateRange.Today();
        List<Integer> incomeDay = new ArrayList<>();//TODO(get income WHERE date = this.dateToday --> DataType required : Int)
        //remove this-----
        incomeDay.add(50000);
        incomeDay.add(2000);
        incomeDay.add(7000);
        //-----remove this
        int dailyIncome = Total(incomeDay);
        dailyIncomeTotal.setText(String.valueOf(dailyIncome));

        List<Integer> outcomeDay = new ArrayList<>();//TODO(get outcome WHERE date = this.dateToday --> DataType required : Int)
        //remove this -----
        outcomeDay.add(20000);
        outcomeDay.add(5000);
        //-----remove this
        int dailyOutcome = Total(outcomeDay);
        dailyOutcomeTotal.setText(String.valueOf(dailyOutcome));

        List<Integer> debtDaily = new ArrayList<>();//TODO(get debt WHERE date = this.dateToday --> DataType required : Int)
        //remove this-----
        debtDaily.add(500);
        debtDaily.add(2000);
        debtDaily.add(500);
        //-----remove this
        int dailyDebt = Total(debtDaily);
        dailyDebtTotal.setText(String.valueOf(dailyDebt));




        //-----weekly-----//
        Calendar end = Calendar.getInstance();
        int year = end.get(Calendar.YEAR);
        int month = end.get(Calendar.MONTH) + 1;
        int day = end.get(Calendar.DAY_OF_MONTH);
        String startDateStr = DateRange.getMonday(year,month,day);
        String endDateStr = dateToday;
        List<String> dateBeetwenWeekly = new ArrayList<>();
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);
            List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);
            dateBeetwenWeekly.addAll(dateBeetwen);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Integer> incomeWeek = new ArrayList<>();//TODO(get all income WHERE date IN (this.dateBeetwenWeekly) // ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
        //remove this-----
        incomeWeek.add(50000);
        //-----remove this
        int weeklyIncome = Total(incomeWeek);
        weeklyIncomeTotal.setText(String.valueOf(weeklyIncome));

        List<Integer> outcomeWeek = new ArrayList<>();//TODO(get all outcome WHERE date IN (this.dateBeetwenWeekly) // ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
        //remove this -----
        outcomeWeek.add(20000);
        outcomeWeek.add(5000);
        //-----remove this
        int weeklyOutcome = Total(outcomeWeek);
        weeklyOutcomeTotal.setText(String.valueOf(weeklyOutcome));

        List<Integer> debtWeekly = new ArrayList<>();//TODO(get debt WHERE date = this.dateBeetwenWeekly)// ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
        //remove this-----
        debtWeekly.add(500);
        //-----remove this
        int weeklyDebt = Total(debtWeekly);
        weeklyDebtTotal.setText(String.valueOf(weeklyDebt));





        //-----monthly-----//
        String startDateStr2 = DateRange.getFirstDay(year,month,day);
        String endDateStr2 = dateToday;
        ArrayList<String> dateBeetwenMonthly = new ArrayList<>();

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateStr2);
            Date endDate = dateFormat.parse(endDateStr2);
            List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);
            dateBeetwenMonthly.addAll(dateBeetwen);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Integer> incomeMonthly = new ArrayList<>();//TODO(get all income WHERE date IN (this.dateBeetwenMonthly) // ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
        //remove this-----
        incomeMonthly.add(500000);
        incomeMonthly.add(2000);
        incomeMonthly.add(7000);
        //-----remove this
        int monthlyIncome = Total(incomeWeek);
        monthlyIncomeTotal.setText(String.valueOf(monthlyIncome));

        List<Integer> outcomeMonthly = new ArrayList<>();//TODO(get all outcome WHERE date IN (this.dateBeetwenMonthly) // ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
        //remove this -----
        outcomeMonthly.add(200000);
        outcomeMonthly.add(55000);
        //-----remove this
        int monthlyOutcome = Total(outcomeMonthly);
        monthlyOutcomeTotal.setText(String.valueOf(monthlyOutcome));

        List<Integer> debtMonthly = new ArrayList<>();//TODO(get debt WHERE date = this.dateBeetwenMonthly)// ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
        //remove this-----
        debtMonthly.add(5000);
        debtMonthly.add(25000);
        debtMonthly.add(500);
        //-----remove this
        int monthlyDebt = Total(debtMonthly);
        monthlyDebtTotal.setText(String.valueOf(monthlyDebt));




        //-----yearly-----//
        String startDateStr3 = DateRange.getFirstDay(year, month, day);
        String endDateStr3 = dateToday;
        ArrayList dateBeetwenYear = new ArrayList();

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateStr3);
            Date endDate = dateFormat.parse(endDateStr3);
            List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);
            dateBeetwenYear.addAll(dateBeetwen);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Integer> incomeYearly = new ArrayList<>();//TODO(get all income WHERE date IN (this.dateBeetwenYear) // ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
        //remove this-----
        incomeYearly.add(5000000);
        incomeYearly.add(20000);
        incomeYearly.add(70000);
        //-----remove this
        int yearlyIncome = Total(incomeYearly);
        yearlyIncomeTotal.setText(String.valueOf(yearlyIncome));

        List<Integer> outcomeYearly = new ArrayList<>();//TODO(get all outcome WHERE date IN (this.dateBeetwen) // ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
        //remove this -----
        outcomeYearly.add(2000000);
        outcomeYearly.add(550000);
        //-----remove this
        int yearlyOutcome = Total(outcomeYearly);
        yearlyOutcomeTotal.setText(String.valueOf(yearlyOutcome));

        List<Integer> debtYearly = new ArrayList<>();//TODO(get debt WHERE date = this.dateBeetwen// ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
        //remove this-----
        debtYearly.add(55000);
        debtYearly.add(250000);
        debtYearly.add(5000);
        //-----remove this
        int yearlyDebt = Total(debtYearly);
        yearlyDebtTotal.setText(String.valueOf(yearlyDebt));






        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                dailyContentLayout.setVisibility(View.GONE);
                weeklyContentLayout.setVisibility(View.GONE);
                monthlyContentLayout.setVisibility(View.GONE);
                yearlyContentLayout.setVisibility(View.GONE);

                if (checkedId == R.id.dailyRadioButton) {
                    dailyContentLayout.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.weeklyRadioButton) {
                    weeklyContentLayout.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.monthlyRadioButton) {
                    monthlyContentLayout.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.yearlyRadioButton) {
                    yearlyContentLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        dailyContentLayout.setVisibility(View.VISIBLE);

        dailyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });

        return view;
    }

    public static Integer Total(List<Integer> income) {
        int total = 0;
        for (int amount : income) {
            total += amount;
        }
        return total;
    }


}



