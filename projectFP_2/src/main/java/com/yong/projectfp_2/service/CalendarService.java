package com.yong.projectfp_2.service;

import com.yong.projectfp_2.model.Festival;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CalendarService {

    @Autowired
    private FestivalService festivalService;

    public List<List<String>> generateCalendar(int year, int month) {
        List<List<String>> calendar = new ArrayList<>();

        // 선택한 월의 첫째 날과 마지막 날을 구합니다.
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = YearMonth.of(year, month).atEndOfMonth();

        // 달력을 생성합니다.
        while (!firstDayOfMonth.isAfter(lastDayOfMonth)) {
            List<String> week = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                // 현재 월의 날짜인 경우 날짜를 추가하고, 아닌 경우 빈 문자열을 추가합니다.
                if (firstDayOfMonth.getMonthValue() == month) {
                    week.add(String.valueOf(firstDayOfMonth.getDayOfMonth()));
                } else {
                    week.add("");
                }
                firstDayOfMonth = firstDayOfMonth.plusDays(1);
            }
            calendar.add(week);
        }

        return calendar;
    }
}
