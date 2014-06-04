/*
 * The MIT License
 *
 * Copyright 2014 micmeist.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package helper;

import org.apache.commons.math3.random.RandomDataGenerator;

/**
 * Singleton class providing random values of exponential and normal distribution
 *
 * @author micmeist
 */
public class RandomGenerator {

    private static RandomGenerator instance;
    private final RandomDataGenerator randomDataGenerator;

    private RandomGenerator() {
        randomDataGenerator = new RandomDataGenerator();
    }

    /**
     * Returns the always the same instance of <code>RandomGenerator</code> or creats one,
     * if not created before
     * 
     * @return instance of RandomGenerator
     */
    public static RandomGenerator getInstance() {
        if (instance == null) {
            instance = new RandomGenerator();
        }
        return instance;
    }

    /**
     * Generates a random value from the exponential distribution with specified
     * mean using org.apache.commons.math3.random.RandomDataGenerator. 
     *
     * @param mean - the mean of the distribution
     * @return a random value of exponential distribution
     */
    public double getExponential(double mean) {
        return randomDataGenerator.nextExponential(mean);
    }

    /**
     * Generates a uniformly distributed random integer between lower and upper 
     * (endpoints included) using org.apache.commons.math3.random.RandomDataGenerator.
     * 
     * @param lower - lower bound for generated integer
     * @param upper - upper bound for generated integer
     * @return a random integer greater than or equal to lower and less than or equal to upper
     */
    public int getInt(int lower, int upper) {
        return randomDataGenerator.nextInt(lower, upper);
    }

}
