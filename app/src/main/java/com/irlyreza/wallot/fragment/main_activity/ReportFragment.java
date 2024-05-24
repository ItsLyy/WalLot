package com.irlyreza.wallot.fragment.main_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irlyreza.wallot.DateRange;
import com.irlyreza.wallot.R;
import com.irlyreza.wallot.data.DataTransactionModel;
import com.irlyreza.wallot.data.DataUserWalletModel;
import com.irlyreza.wallot.data.DataWalletModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@RequiresApi(api = Build.VERSION_CODES.O)
public class ReportFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference transactionReference = database.getReference("transactions");
    DatabaseReference debtReference = database.getReference("debts");

    private RadioGroup radioGroup,dailyRadioGroup,weeklyRadioGroup,monthlyRadioGroup,yearlyRadioGroup;

    private RadioButton dailyRadioButton, weeklyRadioButton, monthlyRadioButton, yearlyRadioButton, dailyIncomeRadio, dailyOutcomeRadio, weeklyIncomeRadio,weeklyOutcomeRadio,monthlyIncomeRadio, monthlyOutcomeRadio, yearlyIncomeRadio,yearlyOutcomeRadio;
    private View dailyContentLayout, weeklyContentLayout, monthlyContentLayout, yearlyContentLayout;

    private TextView dailyIncomeTotal, dailyOutcomeTotal, dailyDebtTotal, weeklyIncomeTotal, weeklyOutcomeTotal, weeklyDebtTotal, monthlyIncomeTotal, monthlyOutcomeTotal, monthlyDebtTotal, yearlyIncomeTotal, yearlyOutcomeTotal, yearlyDebtTotal;
    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("dd-MM-yyyy")
            .toFormatter();
    private ArrayList<DataWalletModel> dataWalletArray;
    private ArrayList<DataUserWalletModel> dataUserWalletArray;
    private ArrayList<DataTransactionModel> dataTransactionArray;
    private String idUser;
    List<Integer> incomeDay;//TODO(get income WHERE date = this.dateToday --> DataType required : Int)
    List<Integer> outcomeDay;//TODO(get outcome WHERE date = this.dateToday --> DataType required : Int)
    List<Integer> debtDaily;//TODO(get debt WHERE date = this.dateToday --> DataType required : Int)
    List<Integer> incomeWeek;//TODO(get all income WHERE date IN (this.dateBeetwenWeekly) // ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
    List<Integer> outcomeWeek;//TODO(get all outcome WHERE date IN (this.dateBeetwenWeekly) // ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
    List<Integer> debtWeekly;//TODO(get debt WHERE date = this.dateBeetwenWeekly)// ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
    List<Integer> incomeMonthly;//TODO(get all income WHERE date IN (this.dateBeetwenMonthly) // ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
    List<Integer> outcomeMonthly;//TODO(get all outcome WHERE date IN (this.dateBeetwenMonthly) // ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
    List<Integer> debtMonthly;//TODO(get debt WHERE date = this.dateBeetwenMonthly)// ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
    List<Integer> incomeYearly;//TODO(get all income WHERE date IN (this.dateBeetwenYear) // ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
    List<Integer> outcomeYearly;//TODO(get all outcome WHERE date IN (this.dateBeetwen) // ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)
    List<Integer> debtYearly;//TODO(get debt WHERE date = this.dateBeetwen// ( List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);)--> DataType required : Int)




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        SharedPreferences preferences = getActivity().getSharedPreferences("LOGINAPP", Context.MODE_PRIVATE);
        idUser = preferences.getString("idUser", null);

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



        DateRange dateRange = new DateRange();
        String dateToday = dateRange.Today();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateTime = sdf.format(new Date());

        transactionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                incomeDay = new ArrayList<>();
                outcomeDay = new ArrayList<>();
                dataTransactionArray = new ArrayList<>();
                for (DataSnapshot transactionItem : snapshot.getChildren()) {
                    if(transactionItem.exists()) {
                        if (Objects.equals(transactionItem.child("id_user").getValue(String.class), idUser) && transactionItem.child("date").getValue(String.class).equals(currentDateTime) && transactionItem.child("type").getValue(String.class).equals("income")) {
                            incomeDay.add(Integer.parseInt(unformatRupiah(transactionItem.child("nominal").getValue(String.class))));
                        } else if (Objects.equals(transactionItem.child("id_user").getValue(String.class), idUser) && Objects.equals(transactionItem.child("date").getValue(String.class), currentDateTime) && Objects.equals(transactionItem.child("type").getValue(String.class), "outcome")) {
                            outcomeDay.add(Integer.parseInt(unformatRupiah(transactionItem.child("nominal").getValue(String.class))));
                        }
                    } else {
                        outcomeDay.add(0);
                        incomeDay.add(0);
                    }
                }
                int dailyIncome = Total(incomeDay);
                dailyIncomeTotal.setText(formatRupiah(Double.parseDouble(String.valueOf(dailyIncome))));

                int dailyOutcome = Total(outcomeDay);
                dailyOutcomeTotal.setText(formatRupiah(Double.parseDouble(String.valueOf(dailyOutcome))));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        debtReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                debtDaily = new ArrayList<>();
                for (DataSnapshot debtItem : snapshot.getChildren()) {
                    if (debtItem.exists()) {
                        if (Objects.equals(debtItem.child("id_user").getValue(String.class), idUser) && debtItem.child("date").getValue(String.class).equals(currentDateTime)) {
                            debtDaily.add(Integer.parseInt(unformatRupiah(debtItem.child("nominal").getValue(String.class))));
                        }
                    } else {
                        debtDaily.add(0);
                    }
                }
                int dailyDebt = Total(debtDaily);
                dailyDebtTotal.setText(formatRupiah(Double.parseDouble(String.valueOf(dailyDebt))));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Calendar end = Calendar.getInstance();
        int year = end.get(Calendar.YEAR);
        int month = end.get(Calendar.MONTH) + 1;
        int day = end.get(Calendar.DAY_OF_MONTH);
        String startDateStr = DateRange.getMonday(year,month,day);
        String endDateStr = dateToday;
        List<String> dateBeetwenWeekly = new ArrayList<>();
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);
            List<String> dateBeetwen = DateRange.getDateRange(startDate, endDate);
            dateBeetwenWeekly.addAll(dateBeetwen);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        transactionReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                incomeWeek = new ArrayList<>();
                outcomeWeek = new ArrayList<>();
                for (DataSnapshot transactionItem : snapshot.getChildren()) {
                    if(transactionItem.exists()) {
                        String dateString = transactionItem.child("date").getValue(String.class);
                        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
                        if (Objects.equals(transactionItem.child("id_user").getValue(String.class), idUser) && isWithinLastWeek(date) && transactionItem.child("type").getValue(String.class).equals("income")) {
                            incomeWeek.add(Integer.parseInt(unformatRupiah(transactionItem.child("nominal").getValue(String.class))));
                        } else if (Objects.equals(transactionItem.child("id_user").getValue(String.class), idUser) && isWithinLastWeek(date) && Objects.equals(transactionItem.child("type").getValue(String.class), "outcome")) {
                            outcomeWeek.add(Integer.parseInt(unformatRupiah(transactionItem.child("nominal").getValue(String.class))));
                        }
                    } else {
                        incomeWeek.add(0);
                        outcomeWeek.add(0);
                    }
                }
                int weeklyIncome = Total(incomeWeek);
                weeklyIncomeTotal.setText(formatRupiah(Double.parseDouble(String.valueOf(weeklyIncome))));

                int weeklyOutcome = Total(outcomeWeek);
                weeklyOutcomeTotal.setText(formatRupiah(Double.parseDouble(String.valueOf(weeklyOutcome))));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        debtReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                debtWeekly = new ArrayList<>();
                for (DataSnapshot debtItem : snapshot.getChildren()) {
                    if (debtItem.exists()) {
                        String dateString = debtItem.child("date").getValue(String.class);
                        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
                        if (Objects.equals(debtItem.child("id_user").getValue(String.class), idUser) && isWithinLastWeek(date)) {
                            debtWeekly.add(Integer.parseInt(unformatRupiah(debtItem.child("nominal").getValue(String.class))));
                        }
                    } else {
                        debtWeekly.add(0);
                    }
                }
                int weeklyDebt = Total(debtWeekly);
                weeklyDebtTotal.setText(formatRupiah(Double.parseDouble(String.valueOf(weeklyDebt))));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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


        transactionReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                incomeMonthly = new ArrayList<>();
                outcomeMonthly = new ArrayList<>();
                for (DataSnapshot transactionItem : snapshot.getChildren()) {
                    if(transactionItem.exists()) {
                        String dateString = transactionItem.child("date").getValue(String.class);
                        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
                        if (Objects.equals(transactionItem.child("id_user").getValue(String.class), idUser) && isWithinLastMonth(date) && transactionItem.child("type").getValue(String.class).equals("income")) {
                            incomeMonthly.add(Integer.parseInt(unformatRupiah(transactionItem.child("nominal").getValue(String.class))));
                        } else if (Objects.equals(transactionItem.child("id_user").getValue(String.class), idUser) && isWithinLastMonth(date) && Objects.equals(transactionItem.child("type").getValue(String.class), "outcome")) {
                            outcomeMonthly.add(Integer.parseInt(unformatRupiah(transactionItem.child("nominal").getValue(String.class))));
                        }
                    } else {
                        incomeMonthly.add(0);
                        outcomeMonthly.add(0);
                    }
                }
                int monthlyIncome = Total(incomeMonthly);
                monthlyIncomeTotal.setText(formatRupiah(Double.parseDouble(String.valueOf(monthlyIncome))));

                int monthlyOutcome = Total(outcomeMonthly);
                monthlyOutcomeTotal.setText(formatRupiah(Double.parseDouble(String.valueOf(monthlyOutcome))));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        debtReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                debtMonthly = new ArrayList<>();
                for (DataSnapshot debtItem : snapshot.getChildren()) {
                    if (debtItem.exists()) {
                        String dateString = debtItem.child("date").getValue(String.class);
                        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
                        if (Objects.equals(debtItem.child("id_user").getValue(String.class), idUser) && isWithinLastMonth(date)) {
                            debtMonthly.add(Integer.parseInt(unformatRupiah(debtItem.child("nominal").getValue(String.class))));
                        }
                    } else {
                        debtMonthly.add(0);
                    }
                }
                int monthlyDebt = Total(debtMonthly);
                monthlyDebtTotal.setText(formatRupiah(Double.parseDouble(String.valueOf(monthlyDebt))));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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

        transactionReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                incomeYearly = new ArrayList<>();
                outcomeYearly = new ArrayList<>();
                for (DataSnapshot transactionItem : snapshot.getChildren()) {
                    if(transactionItem.exists()) {
                        String dateString = transactionItem.child("date").getValue(String.class);
                        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
                        if (Objects.equals(transactionItem.child("id_user").getValue(String.class), idUser) && isWithinLastYear(date) && transactionItem.child("type").getValue(String.class).equals("income")) {
                            incomeYearly.add(Integer.parseInt(unformatRupiah(transactionItem.child("nominal").getValue(String.class))));
                        } else if (Objects.equals(transactionItem.child("id_user").getValue(String.class), idUser) && isWithinLastYear(date) && Objects.equals(transactionItem.child("type").getValue(String.class), "outcome")) {
                            outcomeYearly.add(Integer.parseInt(unformatRupiah(transactionItem.child("nominal").getValue(String.class))));
                        }
                    } else {
                        incomeYearly.add(0);
                        outcomeYearly.add(0);
                    }
                }
                int yearlyIncome = Total(incomeYearly);
                yearlyIncomeTotal.setText(formatRupiah(Double.parseDouble(String.valueOf(yearlyIncome))));

                int yearlyOutcome = Total(outcomeYearly);
                yearlyOutcomeTotal.setText(formatRupiah(Double.parseDouble(String.valueOf(yearlyOutcome))));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        debtReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                debtYearly = new ArrayList<>();
                for (DataSnapshot debtItem : snapshot.getChildren()) {
                    if (debtItem.exists()) {
                        String dateString = debtItem.child("date").getValue(String.class);
                        LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
                        if (Objects.equals(debtItem.child("id_user").getValue(String.class), idUser) && isWithinLastYear(date)) {
                            debtYearly.add(Integer.parseInt(unformatRupiah(debtItem.child("nominal").getValue(String.class))));
                        }
                    } else {
                        debtYearly.add(0);
                    }
                }
                int yearlyDebt = Total(debtYearly);
                yearlyDebtTotal.setText(formatRupiah(Double.parseDouble(String.valueOf(yearlyDebt))));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    private String unformatRupiah(String number) {
        String split = number.replace(".", "");
        return  split;
    }
    private String formatRupiah(Double number) {
        Locale localeId = new Locale("IND", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeId);
        String formatrupiah = numberFormat.format(number);
        String[] split = formatrupiah.split(",");
        int length = split[0].length();
        return  split[0].substring(2,length).replace("p", "-");
    }
    @SuppressLint("NewApi")
    private boolean isWithinLastWeek(LocalDate date) {
        LocalDate currentDate = LocalDate.now();

        LocalDate startOfWeek = currentDate.minusWeeks(0).with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = currentDate.plusWeeks(0).with(DayOfWeek.SUNDAY);

        // Check if the given date falls within the week range
        return !date.isBefore(startOfWeek) && !date.isAfter(endOfWeek);
    }

    @SuppressLint("NewApi")
    private boolean isWithinLastMonth(LocalDate date) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        int startMonth = currentMonth - 0;
        int endMonth = currentMonth + 0;

        if (startMonth < 1) {
            startMonth += 12;
            currentYear--;
        }
        if (endMonth > 12) {
            endMonth -= 12;
            currentYear++;
        }

        LocalDate startDate = LocalDate.of(currentYear, startMonth, 1);
        LocalDate endDate = LocalDate.of(currentYear, endMonth, 1).plusMonths(1).minusDays(1);

        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    @SuppressLint("NewApi")
    private boolean isWithinLastYear(LocalDate date) {
        int currentYear = LocalDate.now().getYear();

        int startYear = currentYear - 0;
        int endYear = currentYear + 0;

        LocalDate startDate = LocalDate.of(startYear, 1, 1);
        LocalDate endDate = LocalDate.of(endYear, 12, 31);

        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}



