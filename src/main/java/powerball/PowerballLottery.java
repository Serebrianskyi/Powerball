package powerball;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

public class PowerballLottery {


    private static final int WINNING_PICK_1 = 4;
    private static final int WINNING_PICK_2 = 8;
    private static final int WINNING_PICK_3 = 23;
    private static final int WINNING_PICK_4 = 46;
    private static final int WINNING_PICK_5 = 65;
    private static final int WINNING_POWERBALL = 1;
    private static final int ALERT_RATE = 100000;
    private static final int LOOP_COUNT = 10000000;

    private static int hasWinningPick1 = 0;
    private static int hasWinningPick2 = 0;
    private static int hasWinningPick3 = 0;
    private static int hasWinningPick4 = 0;
    private static int hasWinningPick5 = 0;
    private static int hasPowerball = 0;
    private static int powerballOnly = 0;
    private static int hasAllButPowerBall = 0;
    private static int hasAll = 0;
    private static int found0 = 0;
    private static int found1AndPowerball = 0;
    private static int found2 = 0;
    private static int found2AndPowerball = 0;
    private static int found3 = 0;
    private static int found3AndPowerball = 0;
    private static int found4 = 0;
    private static int found4AndPowerball = 0;
    private static int found5 = 0;

    private static BigDecimal ticketCost = new BigDecimal("2.00");
    private static BigDecimal powerballOnlyPayout = new BigDecimal("4.00");
    private static BigDecimal powerball2Payout = new BigDecimal("7.00");
    private static BigDecimal powerball3Payout = new BigDecimal("100.00");
    private static BigDecimal powerball4Payout = new BigDecimal("50000.00");
    private static BigDecimal powerball5Payout = new BigDecimal("1000000.00");
    private static BigDecimal powerballAllPayout = new BigDecimal("420900000.00");

    private static Set<String> picks = new HashSet<String>();
    private static int iterations = 0;
    private static int duplicateCount = 0;

    public static void main(String[] args) {
        doPicks();
    }

    private static void doPicks() {
        Random random = new Random();
        iterations = LOOP_COUNT;


        for (int i = 0; i < LOOP_COUNT; i++) {
            if (i > 0) {
                if (i % ALERT_RATE == 0) {
                    System.out.println("Processed " + NumberFormat.getNumberInstance().format(i) + " iterations ...");
                }
            }

            List<Integer> myPicks = new ArrayList<Integer>(5);
            List<Integer> myPowerball = new ArrayList<Integer>(1);

            boolean pickUsed = false;
            while (!pickUsed) {
                fillMyPicksByRandomNumbers(random, myPicks);
                Collections.sort(myPicks);
                myPowerball = Collections.singletonList(random.nextInt(27) + 1);

                String thisPic = (myPicks.get(0) < 10 ? "0" : "") + myPicks.get(0).toString()
                        + (myPicks.get(1) < 10 ? "0" : "") + myPicks.get(1).toString()
                        + (myPicks.get(2) < 10 ? "0" : "") + myPicks.get(2).toString()
                        + (myPicks.get(3) < 10 ? "0" : "") + myPicks.get(3).toString()
                        + (myPicks.get(4) < 10 ? "0" : "") + myPicks.get(4).toString()
                        + (myPowerball.get(0) < 10 ? "0" : "") + myPowerball.get(0).toString();

                if (!picks.contains(thisPic)) {
                    picks.add(thisPic);
                    pickUsed = true;
                } else {
                    duplicateCount++;
                }
            }

            if (processPick(i, myPicks, myPowerball)) {
                break;
            }
        }

        processStatistics(iterations);
    }

    private static void fillMyPicksByRandomNumbers(Random random, List<Integer> myPicks) {
        while (myPicks.size() < 6) {
            int rand = random.nextInt(68)+1; // this will give numbers between 1 and 69.
            if (!myPicks.contains(rand)) {
                myPicks.add(rand);
            }
        }
    }

    private static boolean processPick(int iteration, List<Integer> myPicks, List<Integer> myPowerball) {
        int correctPicksFound = 0;

        boolean foundWinner1 = false;
        boolean foundWinner2 = false;
        boolean foundWinner3 = false;
        boolean foundWinner4 = false;
        boolean foundWinner5 = false;
        boolean foundPowerball = false;

        if (myPicks.contains(WINNING_PICK_1)) {
            foundWinner1 = true;
            hasWinningPick1++;
            correctPicksFound++;
        }

        if (myPicks.contains(WINNING_PICK_2)) {
            foundWinner2 = true;
            hasWinningPick2++;
            correctPicksFound++;
        }

        if (myPicks.contains(WINNING_PICK_3)) {
            foundWinner3 = true;
            hasWinningPick3++;
            correctPicksFound++;
        }

        if (myPicks.contains(WINNING_PICK_4)) {
            foundWinner4 = true;
            hasWinningPick4++;
            correctPicksFound++;
        }

        if (myPicks.contains(WINNING_PICK_5)) {
            foundWinner5 = true;
            hasWinningPick5++;
            correctPicksFound++;
        }

        if (correctPicksFound == 2) {
            found2++;
            if (myPowerball.contains(WINNING_POWERBALL)) {
                found2AndPowerball++;
            }
        }
        if (correctPicksFound == 3) {
            found3++;
            if (myPowerball.contains(WINNING_POWERBALL)) {
                found3AndPowerball++;
            }
        }
        if (correctPicksFound == 4) {
            found4++;
            if (myPowerball.contains(WINNING_POWERBALL)) {
                found4AndPowerball++;
            }
        }
        if (correctPicksFound == 5) {
            if (!myPowerball.contains(WINNING_POWERBALL)) {
                found5++;
            }
        }

        if (myPowerball.contains(WINNING_POWERBALL)) {
            foundPowerball = true;
            hasPowerball++;
            if (correctPicksFound == 0) {
                powerballOnly++;
            } else if (correctPicksFound == 1) {
                found1AndPowerball++;
            }
        }

        if (foundWinner1 && foundWinner2 && foundWinner3 && foundWinner4 && foundWinner5) {
            hasAllButPowerBall++;
        }

        if (!foundWinner1 && !foundWinner2 && !foundWinner3 && !foundWinner4 && !foundWinner5 && !foundPowerball) {
            found0++;
        }

        if (foundWinner1 && foundWinner2 && foundWinner3 && foundWinner4 && foundWinner5 && foundPowerball) {
            hasAll++;
            System.out.println("Found all correct numbers on iteration #" + NumberFormat.getNumberInstance(Locale.US).format(iteration) + " of " + NumberFormat
                    .getNumberInstance(Locale.US).format(LOOP_COUNT));
            iterations = iteration;
            return true;
        }

        return false;
    }

    private static void processStatistics(int processCount) {
        System.out.println("\nProcessed "
                + NumberFormat.getNumberInstance().format(processCount) + " iteration(s)");
        System.out.println("Duplicates avoided during processing = "
                + NumberFormat.getNumberInstance().format(duplicateCount));
        System.out.println("Tickets with zero matches = "
                + NumberFormat.getNumberInstance().format(found0));
        System.out.println("Found winning number " + WINNING_PICK_1 + " = "
                + NumberFormat.getNumberInstance().format(hasWinningPick1) + " time(s)");
        System.out.println("Found winning number " + WINNING_PICK_2 + " = "
                + NumberFormat.getNumberInstance().format(hasWinningPick2) + " time(s)");
        System.out.println("Found winning number " + WINNING_PICK_3 + " = "
                + NumberFormat.getNumberInstance().format(hasWinningPick3) + " time(s)");
        System.out.println("Found winning number " + WINNING_PICK_4 + " = "
                + NumberFormat.getNumberInstance().format(hasWinningPick4) + " time(s)");
        System.out.println("Found winning number " + WINNING_PICK_5 + " = "
                + NumberFormat.getNumberInstance().format(hasWinningPick5) + " time(s)");
        System.out.println("Found Powerball (" + WINNING_POWERBALL + ") = "
                + NumberFormat.getNumberInstance().format(hasPowerball) + " time(s)");
        System.out.println("Instances with 2 correct numbers = "
                + NumberFormat.getNumberInstance().format(found2));
        System.out.println("Instances with 3 correct numbers = "
                + NumberFormat.getNumberInstance().format(found3));
        System.out.println("Instances with 4 correct numbers = "
                + NumberFormat.getNumberInstance().format(found4));
        System.out.println("Instances with 5 correct numbers = "
                + NumberFormat.getNumberInstance().format(found5));
        System.out.println("Instances where all numbers were found, except the Powerball = "
                + NumberFormat.getNumberInstance().format(hasAllButPowerBall));
        System.out.println("Found all winning numbers, including the Powerball = "
                + NumberFormat.getNumberInstance().format(hasAll));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        BigDecimal costTickets = ticketCost.multiply(new BigDecimal(processCount));
        BigDecimal winningPowerballOnly = new BigDecimal("0");
        BigDecimal winning1PlusPowerball = new BigDecimal("0");
        BigDecimal winning2PlusPowerball = new BigDecimal("0");
        BigDecimal winning3NoPowerball = new BigDecimal("0");
        BigDecimal winning3PlusPowerball = new BigDecimal("0");
        BigDecimal winning4NoPowerball = new BigDecimal("0");
        BigDecimal winning4PlusPowerball = new BigDecimal("0");
        BigDecimal winning5NoPowerball = new BigDecimal("0");
        BigDecimal grandPrize = new BigDecimal("0");
        BigDecimal totalWinnings = new BigDecimal("0");

        if (powerballOnly > 0) {
            winningPowerballOnly = powerballOnlyPayout.multiply(new BigDecimal(powerballOnly));
            totalWinnings = totalWinnings.add(winningPowerballOnly);
        }
        if (found1AndPowerball > 0) {
            winning1PlusPowerball = powerballOnlyPayout.multiply(new BigDecimal(found1AndPowerball));
            totalWinnings = totalWinnings.add(winning1PlusPowerball);
        }
        if (found2AndPowerball > 0) {
            winning2PlusPowerball = powerball2Payout.multiply(new BigDecimal(found2AndPowerball));
            totalWinnings = totalWinnings.add(winning2PlusPowerball);
        }
        if (found3 > 0) {
            winning3NoPowerball = powerball2Payout.multiply(new BigDecimal(found3));
            totalWinnings = totalWinnings.add(winning3NoPowerball);
        }
        if (found3AndPowerball > 0) {
            winning3PlusPowerball = powerball3Payout.multiply(new BigDecimal(found3AndPowerball));
            totalWinnings = totalWinnings.add(winning3PlusPowerball);
        }
        if (found4 > 0) {
            winning4NoPowerball = powerball3Payout.multiply(new BigDecimal(found4));
            totalWinnings = totalWinnings.add(winning4NoPowerball);
        }
        if (found4AndPowerball > 0) {
            winning4PlusPowerball = powerball4Payout.multiply(new BigDecimal(found4AndPowerball));
            totalWinnings = totalWinnings.add(winning4PlusPowerball);
        }
        if (found5 > 0) {
            winning5NoPowerball = powerball5Payout.multiply(new BigDecimal(found5));
            totalWinnings = totalWinnings.add(winning5NoPowerball);
        }

        if (hasAll > 0) {
            grandPrize = powerballAllPayout;
            totalWinnings = totalWinnings.add(grandPrize);
        }

        System.out.println("\nCost Analysis:");
        System.out.println("Ticket costs = " + currencyFormat.format(costTickets));
        System.out.println("Powerball (only) Match = " + currencyFormat.format(winningPowerballOnly));
        System.out.println("1 + Powerball Match = " + currencyFormat.format(winning1PlusPowerball));
        System.out.println("2 + Powerball Match = " + currencyFormat.format(winning2PlusPowerball));
        System.out.println("3 Matches (no Powerball) = " + currencyFormat.format(winning3NoPowerball));
        System.out.println("3 + Powerball Match = " + currencyFormat.format(winning3PlusPowerball));
        System.out.println("4 Matches (no Powerball) = " + currencyFormat.format(winning4NoPowerball));
        System.out.println("4 + Powerball Match = " + currencyFormat.format(winning4PlusPowerball));
        System.out.println("5 Matches (no Powerball) = " + currencyFormat.format(winning5NoPowerball));
        System.out.println("Grand Prize = " + currencyFormat.format(grandPrize));
        System.out.println("Total Prize Money = " + currencyFormat.format(totalWinnings));
        System.out.println("Ticket Costs - Total Prizes = " + currencyFormat.format(totalWinnings.subtract(costTickets)));
    }

    private static ArrayList<Integer> getNumbers(Integer lastNumber) {
        ArrayList<Integer> returnList = new ArrayList<Integer>();

        for (int i = 1; i <= lastNumber; i++) {
            returnList.add(new Integer(i));
        }

        return returnList;
    }
}
