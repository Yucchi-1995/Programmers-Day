package jp.yucchi.programmers.day;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.DAYS;
import java.time.temporal.TemporalAdjuster;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Yucchi
 */
public class ProgrammersDay {

    public static void main(String[] args) {

        LocalDate today = LocalDate.now();
        System.out.println("今日は、" + today.format(DateTimeFormatter.ofPattern("yyyy年 M月d日 E曜日")));
        System.out.println("(○･ω･)ﾉ-------------end-------------");

        TemporalAdjuster programmersDayAduster = temporal -> {

            if (DAYS.between(LocalDate.of(today.getYear(), 1, 1), temporal) >= 256) {
                temporal = temporal.plus(1, ChronoUnit.YEARS);
            }

            temporal = temporal.with(ChronoField.DAY_OF_YEAR, 256);
            while (temporal.get(ChronoField.DAY_OF_WEEK) != DayOfWeek.SUNDAY.getValue()) {
                if (Year.isLeap(temporal.get(ChronoField.YEAR))) {
                    temporal = temporal.plus(366, ChronoUnit.DAYS);
                } else {
                    temporal = temporal.plus(365, ChronoUnit.DAYS);
                }
            }
            return temporal;

        };

//        // こちらでもＯＫ！
//        LocalDate programmersDay = (LocalDate) programmersDayAduster.adjustInto(today);
        LocalDate programmersDay = today.with(programmersDayAduster);
        System.out.println("プログラマーの日が日曜日である一番近い日は、"
                + programmersDay.format(DateTimeFormatter.ofPattern("yyyy年 M月d日 E曜日")));
        System.out.println("(○･ω･)ﾉ-------------end-------------");

        System.out.println("プログラマーの日を１０年間表示させる。");
        IntStream.range(today.getYear(), today.getYear() + 10)
                .mapToObj(year -> Year.of(year))
                .map(day -> day.atDay(256))
                .forEach(pDay -> System.out.println(pDay.format(DateTimeFormatter.ofPattern("yyyy年 M月d日 E曜日"))));
        System.out.println("(○･ω･)ﾉ-------------end-------------");

        Stream.iterate(today.getYear(), i -> i + 1)
                .map(year -> Year.of(year))
                .map(day -> day.atDay(256))
                .filter(day -> day.getDayOfWeek() == DayOfWeek.SATURDAY)
                .findFirst().ifPresent(pDay -> System.out.println("プログラマーの日が土曜日である一番近い日は、"
                                + pDay.format(DateTimeFormatter.ofPattern("yyyy年 M月d日 E曜日"))));
        System.out.println("(○･ω･)ﾉ-------------end-------------");

    }

}
