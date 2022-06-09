/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Mode;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MyBenchmark {
	
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

	private int target = 99;
	
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

    @Benchmark
    public void baseline() {
        
    }
    
    @Benchmark
    public int[] benchmark3() {
		return search3(array, target);
    }
    
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
     */
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
