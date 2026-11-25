import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DK_1260800_1261485 {

    static final char NAME_SEPARATOR = ',';
    static final int NO_ARGUMENTS = 0;
    static final int FILE_NAME_INDEX = 0;

    static final int NAME_WIDTH = 13;
    static final int COLUMN_WIDTH = 11;
    static final int PERCENT_WIDTH = 12;
    static final int PROFILE_WIDTH = 13;

    static final int MAX_MODERATE = 90;
    static final int MAX_REGULAR = 180;
    static final int MAX_INTENSIVE = 300;

    static final int NO_USAGE = 0;
    static final double PERCENT_TOTAL = 100.0;
    static final double PERCENT_PER_ASTERISK = 5.0;
    static final double ROUNDING = 0.5;
    static final char ASTERISK = '*';
    static final int LEGEND_INTERVAL = 25;
    static final int LEGEND_MAX = 100;
    static final int LEGEND_COLUMN_WIDTH = 5;
    static final String LEGEND_SEGMENT = "----+";


    public static void main(String[] args) throws FileNotFoundException {

        // a)
        Scanner scanner = openDataSource(args);
        String[] names = readNames(scanner);
        String[] socialMedia = readNames(scanner);
        int[][] minutes = readMinutes(scanner, names.length, socialMedia.length);
        scanner.close();

        // b)
        showFormattedTable(names, socialMedia, minutes);
        System.out.println();

        // c)
        showDailyUsage(names, minutes);
        System.out.println();

        // d)
        showMostActivePeople(names, socialMedia, minutes);
        System.out.println();

        // e)
        showPeopleAboveAverage(names, minutes);
        System.out.println();

        // f)
        showMostUsedSocialNetwork(names, socialMedia, minutes);
        System.out.println();

        // g)
        showPercentageOfUse(names, socialMedia, minutes);
        System.out.println();

        // h)
        showProfiles(names, minutes);
        System.out.println();

        // i)
        showSocialNetworkChart(socialMedia, minutes);
        System.out.println();

        // j)
        showMostBalancedUsers(names, minutes);
    }

    // a)
    public static Scanner openDataSource(String[] args) throws FileNotFoundException {
        if (args.length > NO_ARGUMENTS) {
            return new Scanner(new File(args[FILE_NAME_INDEX]));
        }
        return new Scanner(System.in);
    }

    public static String[] readNames(Scanner scanner) {
        String line = scanner.nextLine();
        String[] names = new String[countNames(line)];
        String currentName = "";
        int nameIndex = 0;

        for (int position = 0; position < line.length(); position++) {
            char character = line.charAt(position);

            if (character == NAME_SEPARATOR) {
                names[nameIndex] = currentName;
                nameIndex++;
                currentName = "";
            } else {
                currentName = currentName + character;
            }
        }
        names[nameIndex] = currentName;

        return names;
    }

    public static int countNames(String line) {
        int separators = 0;

        for (int position = 0; position < line.length(); position++) {
            if (line.charAt(position) == NAME_SEPARATOR) {
                separators++;
            }
        }
        return separators + 1;
    }

    public static int[][] readMinutes(Scanner scanner, int peopleCount, int socialMediaCount) {
        int[][] minutes = new int[peopleCount][socialMediaCount];

        for (int n = 0; n < peopleCount; n++) {
            for (int s = 0; s < socialMediaCount; s++) {
                minutes[n][s] = scanner.nextInt();
            }
        }
        return minutes;
    }

    // b)
    public static void showFormattedTable(String[] names, String[] socialMedia, int[][] minutes) {

        System.out.println("b) Social Media Usage (minutes/day)");

        System.out.printf("%-" + NAME_WIDTH + "s", "");
        for (int s = 0; s < socialMedia.length; s++) {
            System.out.printf("%" + COLUMN_WIDTH + "s", socialMedia[s]);
        }
        System.out.println();

        for (int n = 0; n < names.length; n++) {
            System.out.printf("%-" + NAME_WIDTH + "s", names[n]);
            for (int s = 0; s < socialMedia.length; s++) {
                System.out.printf("%" + COLUMN_WIDTH + "d", minutes[n][s]);
            }
            System.out.println();

        }
    }

    // c)
    public static void showDailyUsage(String[] names, int[][] minutes) {
        System.out.println("c) Daily usage");

        for (int n = 0; n < names.length; n++) {
            int total = getTotal(minutes[n]);
            double average = getAverage(total, minutes[n].length);

            System.out.printf("%-" + NAME_WIDTH + "s: %5d min (%5.1f min average/network)%n", names[n], total, average);
        }
    }

    // d)
    public static void showMostActivePeople(String[] names, String[] socialMedia, int[][] minutes) {

        System.out.println("d) Most active Users");

        for (int s = 0; s < socialMedia.length; s++) {

            int max = maxColumn(minutes, s);
            System.out.printf("%-" + NAME_WIDTH + "s: ", socialMedia[s]);

            for (int n = 0; n < names.length; n++) {
                if (minutes[n][s] == max){
                    System.out.printf("\"%s\" ", names[n]);
                }
            }
            System.out.println();
        }
    }

    // e)
    public static void showPeopleAboveAverage(String[] names, int[][] minutes) {
        System.out.println("e) Users consistently above average");

        double[] averages = getAveragesPerNetwork(minutes);
        boolean found = false;

        for (int n = 0; n < names.length; n++) {
            if (isAboveAverageInAll(minutes[n], averages)) {
                System.out.println(names[n]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("none");
        }
    }

    // f)
    public static void showMostUsedSocialNetwork(String[] names, String[] socialMedia, int[][] minutes) {

        System.out.println("f) Most used social network");

        for (int n = 0; n < names.length; n++) {
            int max = maxLine(minutes, n);

            System.out.printf("%-" + NAME_WIDTH + "s: ", names[n]);

            for (int s = 0; s < socialMedia.length; s++) {
                if (minutes[n][s] == max){
                    System.out.printf("[%s] ", socialMedia[s]);
                }
            }
            System.out.println();
        }
    }

    // g)
    public static void showPercentageOfUse(String[] names, String[] socialMedia, int[][] minutes) {
        System.out.println("g) Percentage of use");

        System.out.printf("%-" + NAME_WIDTH + "s", "");
        for (int s = 0; s < socialMedia.length; s++) {
            System.out.printf("%" + PERCENT_WIDTH + "s", socialMedia[s]);
        }
        System.out.println();

        for (int n = 0; n < names.length; n++) {
            int total = getTotal(minutes[n]);
            System.out.printf("%-" + NAME_WIDTH + "s", names[n]);

            for (int s = 0; s < socialMedia.length; s++) {
                if (isUsed(minutes[n][s])) {
                    System.out.printf("%" + (PERCENT_WIDTH - 1) + ".2f%%", getPercentage(minutes[n][s], total));
                } else {
                    System.out.printf("%" + PERCENT_WIDTH + "s", "");
                }
            }
            System.out.println();
        }
    }

    // h)
    public static void showProfiles(String[] names, int[][] minutes) {

        String[] userProfiles = {"Moderate", "Regular", "Intensive", "Very Intensive"};
        System.out.println("h) Users profile");

        for (int p = 0; p < userProfiles.length; p++) {
            System.out.printf("%-" + PROFILE_WIDTH + "s", userProfiles[p]);
        }
        System.out.println();

        for (int p = 0; p < userProfiles.length; p++) {
            getTab();
            if (p < userProfiles.length - 1){
                System.out.print("|");
            }
        }
        System.out.println();

        String[][] matrix = getProfileMatrix(names, minutes, userProfiles.length);

        for (int n = 0; n < matrix.length; n++) {
            for (int p = 0; p < matrix[n].length; p++) {
                System.out.printf("%-" + PROFILE_WIDTH + "s", matrix[n][p]);
            }
            System.out.println();
        }
    }

    // i)
    public static void showSocialNetworkChart(String[] socialMedia, int[][] minutes) {
        System.out.println("i) Use of social networks");

        int[] totals = getTotalsPerNetwork(minutes);
        int[] users = getUsersPerNetwork(minutes);
        int[] order = sortSocialMedia(socialMedia, totals, users);
        int overallTotal = getTotal(totals);

        for (int p = 0; p < order.length; p++) {
            int s = order[p];
            double percentage = getPercentage(totals[s], overallTotal);
            int asterisks = (int) (percentage / PERCENT_PER_ASTERISK + ROUNDING);

            System.out.printf("%-" + NAME_WIDTH + "s|", socialMedia[s]);
            for (int a = 0; a < asterisks; a++) {
                System.out.print(ASTERISK);
            }
            System.out.printf(" %.2f%%%n", percentage);
        }

        showLegend();
    }

    // j)
    public static void showMostBalancedUsers(String[] names, int[][] minutes) {

        System.out.println("j) Most balanced users");

        int smallest = getSmallestDifference(minutes);
        boolean first = true;

        System.out.print("{");
        for (int n = 0; n < names.length; n++) {
            if (getDifference(minutes, n) == smallest){
                if (!first){
                    System.out.print(", ");
                }
                System.out.printf("\"%s\"", names[n]);
                first = false;
            }
        }
        System.out.println("}");
    }

    // Other functions

    // Used in: c), g), h), i)
    public static int getTotal(int[] values) {
        int total = 0;

        for (int i = 0; i < values.length; i++) {
            total += values[i];
        }
        return total;
    }

    // Used in: c), e)
    public static double getAverage(int total, int count) {
        return (double) total / count;
    }

    // Used in: g), i)
    public static double getPercentage(int part, int total) {
        return PERCENT_TOTAL * part / total;
    }

    // Used in: g), i)
    public static boolean isUsed(int minutes) {
        return minutes > NO_USAGE;
    }

    // Used in: e), i)
    public static int[] getTotalsPerNetwork(int[][] minutes) {
        int[] totals = new int[minutes[0].length];

        for (int s = 0; s < totals.length; s++) {
            for (int n = 0; n < minutes.length; n++) {
                totals[s] += minutes[n][s];
            }
        }
        return totals;
    }

    // Used in: f), j)
    public static int maxLine(int[][] minutes, int line) {
        int max = minutes[line][0];

        for (int s = 1; s < minutes[line].length; s++) {
            if (minutes[line][s] > max) {
                max = minutes[line][s];
            }
        }
        return max;
    }

    // Functions for d)
    public static int maxColumn(int[][] minutes, int column) {
        int max = minutes[0][column];

        for (int n = 1; n < minutes.length; n++) {
            if (minutes[n][column] > max) {
                max = minutes[n][column];
            }
        }
        return max;
    }

    // Functions for e)
    public static double[] getAveragesPerNetwork(int[][] minutes) {
        int[] totals = getTotalsPerNetwork(minutes);
        double[] averages = new double[totals.length];

        for (int s = 0; s < totals.length; s++) {
            averages[s] = getAverage(totals[s], minutes.length);
        }
        return averages;
    }

    public static boolean isAboveAverageInAll(int[] personMinutes, double[] averages) {
        boolean above = true;
        int s = 0;

        while (above && s < personMinutes.length) {
            if (personMinutes[s] <= averages[s]) {
                above = false;
            }
            s++;
        }
        return above;
    }

    // Functions for h)
    public static void getTab() {
        for (int i = 0; i < NAME_WIDTH - 1; i++) {
            System.out.print("-");
        }
    }

    public static int getProfileColumn(int total) {
        if (total < MAX_MODERATE) {
            return 0;
        } else if (total <= MAX_REGULAR) {
            return 1;
        } else if (total <= MAX_INTENSIVE) {
            return 2;
        }
        return 3;
    }

    public static String[][] getProfileMatrix(String[] names, int[][] minutes, int profileCount) {
        String[][] matrix = new String[names.length][profileCount];

        for (int n = 0; n < names.length; n++) {
            for (int p = 0; p < profileCount; p++) {
                matrix[n][p] = "";
            }
            int column = getProfileColumn(getTotal(minutes[n]));
            matrix[n][column] = names[n];
        }
        return matrix;
    }

    // Functions for i)
    public static void showLegend() {
        System.out.printf("%" + NAME_WIDTH + "s+", "");
        for (int percentage = LEGEND_INTERVAL; percentage <= LEGEND_MAX; percentage += LEGEND_INTERVAL) {
            System.out.print(LEGEND_SEGMENT);
        }
        System.out.println();

        System.out.printf("%" + (NAME_WIDTH + 1) + "s", "%");
        for (int percentage = LEGEND_INTERVAL; percentage <= LEGEND_MAX; percentage += LEGEND_INTERVAL) {
            System.out.printf("%" + LEGEND_COLUMN_WIDTH + "d", percentage);
        }
        System.out.println();
    }

    public static int[] getUsersPerNetwork(int[][] minutes) {
        int[] users = new int[minutes[0].length];

        for (int s = 0; s < users.length; s++) {
            for (int n = 0; n < minutes.length; n++) {
                if (isUsed(minutes[n][s])) {
                    users[s]++;
                }
            }
        }
        return users;
    }

    public static int[] sortSocialMedia(String[] socialMedia, int[] totals, int[] users) {
        int[] order = new int[socialMedia.length];

        for (int s = 0; s < order.length; s++) {
            order[s] = s;
        }

        for (int p = 0; p < order.length; p++) {
            int first = p;

            for (int c = p; c < order.length; c++) {
                if (comesBefore(order[c], order[first], socialMedia, totals, users)) {
                    first = c;
                }
            }

            int auxiliary = order[p];
            order[p] = order[first];
            order[first] = auxiliary;
        }
        return order;
    }

    public static boolean comesBefore(int a, int b, String[] socialMedia, int[] totals, int[] users) {
        if (totals[a] != totals[b]) {
            return totals[a] > totals[b];
        }
        if (users[a] != users[b]) {
            return users[a] > users[b];
        }
        return socialMedia[a].toLowerCase().compareTo(socialMedia[b].toLowerCase()) < 0;
    }

    // Functions for j)
    public static int minLine(int[][] minutes, int line) {
        int min = minutes[line][0];

        for (int s = 1; s < minutes[line].length; s++) {
            if (minutes[line][s] < min) {
                min = minutes[line][s];
            }
        }
        return min;
    }

    public static int getDifference(int[][] minutes, int line) {
        return maxLine(minutes, line) - minLine(minutes, line);
    }

    public static int getSmallestDifference(int[][] minutes) {
        int smallest = getDifference(minutes, 0);

        for (int n = 1; n < minutes.length; n++) {
            int difference = getDifference(minutes, n);

            if (difference < smallest) {
                smallest = difference;
            }
        }
        return smallest;
    }
}


