package com.noam.noamelectricity.functions;

import android.widget.EditText;

import java.util.Locale;

public class helperFunctions {

    /*
    Replaced with <include>
    public static void setCreditsTV(Context cnt, TextView tv) {
    }
    */

    public static boolean inputIsEmpty(EditText input) {
        return input.getText().toString().matches("");
    }

    static boolean checkZero(double num, int size) {
        /*
         * Gets a double type number and size, and divide the number by the size
         * - returns true if the division gives a full number - 1.0
         * - returns false if the division gives a not full number, for example 1.5
         * */
        // if (String.valueOf(num/1000).split("[.]")[1].equalsIgnoreCase("0")) {
        // return String.valueOf(num/size).split("[.]")[1].equalsIgnoreCase("0");
        return returnSplitedNumber(num, size, 1).equalsIgnoreCase("0");
    }

    static boolean checkZeroSimple(double num) {
        return String.valueOf(num).split("[.]")[1].equalsIgnoreCase("0");
    }

    static String returnSplitedNumber(double num, int size, int index) {
        /*
         * Gets a double type number and divide it and returns the requested index after spliting to get the decimal point
         * num: input number
         * size: division number
         * index: index to return
         * For example:
         * 	parameters: 1000, 1000, 0
         * 	Returns: 1000.0/1000=1.0 - 1
         * 		If the last parameter (index) is 1, then it will return the second half - the decimal point.
         * 		Useful to check if the division is .0 or .5 for example (like in the function checkZero)
         * */
        // result = String.valueOf(Integer.parseInt(String.valueOf(num/1000).split("[.]")[0]));
        return String.valueOf(num/size).split("[.]")[index];
    }

    static String removeZero(double num) {
        /*
         * Gets a double type number and removes its zero
         * - or more accurately, removes its decimal point
         * - but in this case, of my code, it will always be a zero, because I won't remove any other decimal point
         * */
        // return String.valueOf(num).split("[.]")[0];
        return String.valueOf(num).split("[.]")[0];
    }

    public static String analyseDot(double num, boolean k, boolean str) {
        /*
         * Getting a float number, and changing it as requested
         * num:
         * 	The float number
         * k:
         * 	True:
         * 		Returns 1 instead of 1000
         * 	False:
         * 		Keeps the number the same
         * str:
         * 	True:
         * 		Adds a matching letter to the output: k for thousands, m for millions, and g for billions (9 zeros)
         * 	False:
         * 		Keeps the number the same
         * For example:
         * 	Input parameters: 1000.0, true, true
         * 	Output: 1k
         * Notes:
         * 	Always gets rid of the decimal point if it's a zero
         */
        String result;
        if (num < 1000) { // From 0 to 999 (Assuming it's just positive values) - just removing the .0, since there is no k or m to add (thousands or millions)
            if (checkZeroSimple(num)) {
                return removeZero(num);
            } else { // For example 1.20000000
                System.out.println("CAN YOU SEE IT?");
                return String.valueOf(String.format("%.2f", num)); // TODO check why is it with a warning
            }
        } else if (num >= 1000 && num < 1000000) { // K RANGE | Between 1,000 and 1,000,000 (not included)
            if (checkZero(num, 1000)) { // If the decimal point is .0
                System.out.println("- k, there is .0, " + num/1000);
                if (k) { // Returns 1 instead of 1000
                    /*
                     * TODO check if the /1000 should be num/1000 or String.valueOf(num).split("[.]")[0])/1000000)
                     * where the division is in the end - it works the same with 1,000, but not the same with 1,000,000, but need to test with more numbers
                     * - if it's in the end, it won't work with big numbers, like 1,000,000 - because it has e, and then it messes up everything
                     * but it works the same with smaller numbers, like 1,000 for example
                     */
                    result = returnSplitedNumber(num, 1000, 0); // Changing it from 1000 to 1
                    if (str) { // Adds k (thousands) to the return value - 1k and not just 1
                        result+="K";
                    }
                    return result;
                } else { // Returns the number as it is (just without the decimal point, which is .0, since it's in the else)
                    return removeZero(num);
                }
            } else { // Printing with the dot because it's not .0
                result = String.valueOf(num/1000);
                if (str) { // Adding k to the return value
                    result+="K";
                }
                return result; // Returning the value with k or without k but with the dot because it's not 0
            }
        } else if (num >= 1000000 && num < 1000000000) { // M RANGE | Between 1,000,000 and 1,000,000,000
            if (checkZero(num, 1000000)) {
                if (k) {
                    result = String.valueOf(Integer.parseInt(String.valueOf(num/1000000).split("[.]")[0]));  // Changing it from 1000 to 1
                    if (str) {
                        result+="M";
                    }
                    return result;
                } else {
                    return removeZero(num); // Keeping it 1000 (not changing it to 1), but as always, without the .0
                }
            } else { // Printing with the dot because it's not .0
                result = String.valueOf(num/1000000);
                if (str) {
                    result+="M";
                }
                return result;
            }
        } else if (num >= 1000000000) { // G RANGE | Above 1,000,000,000 (one billion)
            if (checkZero(num, 1000000000)) {
                // System.out.println("- m, there is .0, " + num/1000000);
                if (k) {
                    result = String.valueOf(Integer.parseInt(String.valueOf(num/1000000000).split("[.]")[0]));  // Changing it from 1000 to 1
                    if (str) {
                        result+="G";
                    }
                    return result;
                } else {
                    return removeZero(num); // Keeping it 1000 and not 1, but without the .0
                }
            } else { // Printing with the dot because it's not .0
                result = String.valueOf(num/1000000000);
                if (str) {
                    result+="G";
                }
                return result;
            }
        } else { // Shouldn't happen, means it's either negative, or bigger than 1 billion - which makes no sense, in this software
            return String.valueOf(num);
        }
    }

}