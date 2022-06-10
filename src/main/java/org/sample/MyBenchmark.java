/*
 * ============================== HOW TO RUN THIS TEST: ====================================
 *
 * You can run this test:
 *
 * a) Via the command line:
 *    $ mvn clean install
 *    $ java -jar target/benchmarks.jar JMHSample_08 -f 1
 *    (we requested single fork; there are also other options, see -h)
 *
 * b) Via the Java API:
 *    (see the JMH homepage for possible caveats when running from IDE:
 *      http://openjdk.java.net/projects/code-tools/jmh/)
 * 
 * c) Via Eclipse
 *    see https://stackoverflow.com/questions/40311990/run-jmh-benchmark-under-eclipse
 */
package org.sample;

//import java.io.IOException;
import java.util.concurrent.TimeUnit;
//import java.util.logging.FileHandler;
//import java.util.logging.Logger;
//import java.util.logging.SimpleFormatter;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MyBenchmark {	
//	private Logger logger = Logger.getLogger("MyLog");  

	private int[][] array = {{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
				             {11, 12, 13, 14, 15, 16, 17, 18, 19, 20}, 
				             {21, 22, 23, 24, 25, 26, 27, 28, 29, 30}, 
				             {31, 32, 33, 34, 35, 36, 37, 38, 39, 40}, 
				             {41, 42, 43, 44, 45, 46, 47, 48, 49, 50}, 
				             {51, 52, 53, 54, 55, 56, 57, 58, 59, 60}, 
				             {61, 62, 63, 64, 65, 66, 67, 68, 69, 70}, 
				             {71, 72, 73, 74, 75, 76, 77, 78, 79, 80}, 
				             {81, 82, 83, 84, 85, 86, 87, 88, 89, 90}, 
				             {91, 92, 93, 94, 95, 96, 97, 98, 99, 100}};

	private int i = 0;
	private int[] targets = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 
            31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 
            41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 
            51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 
            61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 
            71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 
            81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 
            91, 92, 93, 94, 95, 96, 97, 98, 99, 100};
	
	
	/**
	 * Simple Brute Force Algorithm (Linear Search)
	 * @return [i, j]
	 */
	public static int[] search(int[][] array, int target) {
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				if (array[i][j] == target) {
					return new int[] { i, j };
				}
			}
		}

		return new int[] { -1, -1 };
	}
	
	/**
	 * Optimized Brute Force (Two Row Search)
	 * @return [i, j]
	 */
	public static int[] search2(int[][] array, int target) {
		for(int i = 0; i < array.length; i += 2) {
			for(int j = 0; j < array[i].length; j++) {
				if (array[i][j] == target) {
					return new int[] { i, j };
				}
				
				if (i + 1 < array.length && array[i + 1][j] == target) {
					return new int[] { i + 1, j };
				}
			}
		}
		
		return new int[] { -1, -1 };
	}
	
	/**
	 * Optimized Brute Force (LECS Algorithm)
	 * @return [i, j]
	 */
	public static int[] search3(int[][] array, int target) {
	    int lastIndex = array[0].length - 1;
	    
	    for (int i = 0; i < array.length; i++)
	    {
	        if (target <= array[i][lastIndex])
	        {
	            if (target == array[i][lastIndex])
	                return new int[] { i, lastIndex };
	                
	            for (int j = 0; j < array[i].length; j++)
	            {
	                if (target == array[i][j])
	                {
	                    return new int[] { i, j };
	                }
	            }
	        }
	        
	    }
	    
	    return new int[] { -1, -1 };
	}

	
//	@Setup
//	public void setup() {
//		try {  
//	        // This block configure the logger with handler and formatter  
//			FileHandler fh = new FileHandler("C:/benchmark-test.log");  
//	        logger.addHandler(fh);
//	        SimpleFormatter formatter = new SimpleFormatter();  
//	        fh.setFormatter(formatter);  
//
//	    } catch (SecurityException e) {  
//	        e.printStackTrace();  
//	    } catch (IOException e) {  
//	        e.printStackTrace();  
//	    }
//	}

    
    @Benchmark
    @Fork(value = 1)
    @Warmup(iterations = 0)
    @Measurement(iterations = 1)
    public int[] baseline() {
    	if (i > 100) i = 0;
    	int target = i++;
    	int[] result = search(array, target);

//    	 logger.info("search(array, " + target + ") => " + result[0] + ", " + result[1]);  

    	return result;
    }
    
    @Benchmark
    @Fork(value = 1)
    @Warmup(iterations = 0)
    @Measurement(iterations = 1)
    public int[] benchmark2() {
    	if (i > 100) i = 0;
    	int target = targets[i++];
    	int[] result = search2(array, target);

//    	logger.info("search2(array, " + target + ") => " + result[0] + ", " + result[1]);  

    	return result;
    }
    
    @Benchmark
    @Fork(value = 1)
    @Warmup(iterations = 0)
    @Measurement(iterations = 1)
    public int[] benchmark3() {
    	if (i > 100) i = 0;
    	int target = targets[i++];
    	int[] result = search3(array, target);

//    	logger.info("search3(array, " + target + ") => " + result[0] + ", " + result[1]);  

    	return result;
    }
    
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
