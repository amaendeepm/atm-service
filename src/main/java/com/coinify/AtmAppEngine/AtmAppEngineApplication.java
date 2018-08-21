package com.coinify.AtmAppEngine;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class AtmAppEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmAppEngineApplication.class, args);
	}
	
	@GetMapping("/")
    public String hello() {
            return "Use service with syntax /atm/{withdrawAmt} ";
    }
	
	@RequestMapping(value = "/atm/{withdrawAmt}")
	public List<Integer> atmRequest(@PathVariable Integer withdrawAmt) {
		
		System.out.println("Amount Request came: " + withdrawAmt);
		
		final ArrayList<Integer>  box1 = new ArrayList<Integer>(Arrays.asList(1000, 500, 200, 100, 50 ));
		final ArrayList<Integer>  box2 = new ArrayList<Integer>(Arrays.asList( 20, 5, 2 ));
		final ArrayList<Integer>  box3 = new ArrayList<Integer>(Arrays.asList( 10, 1 ));
		
		return FindDenomination(withdrawAmt, box1,box2,box3);
		
		
	}
	
	
	private ArrayList<Integer> mergeCombinations(ArrayList<Integer> arr1, ArrayList<Integer>  arr2, ArrayList<Integer>  arr3) {
		int i = 0, j = 0, k = 0;
		
		
		int m = arr1.size();
		int n = arr2.size();
		int o = arr3.size();
		
		ArrayList<Integer> retList = new ArrayList<Integer>(m+n+o);
		
		//Greedy first
		while (i < m && j < n && k < o) { 
			int max = Math.max(Math.max(arr1.get(i), arr2.get(j)), arr3.get(k));
			retList.add(max);
			
			if (max==arr1.get(i))
				i++;
			else if (max == arr2.get(j))
				j++;
			else
				k++;
		}
		
		
		//only one of below while look will only enter as it was broken just one of above 3 conditions not met
		
		while (i < m && j < n) {
			//System.out.println("3rd list exhausted to break first loop, going with 1 & 2");
			int max = Math.max(arr1.get(i), arr2.get(j));
			retList.add(max);
			if (max==arr1.get(i))
				i++;
			else
				j++;			
		}
		
		while (i < m && k < o) {
			//System.out.println("2nd list exhausted to break first loop, going with 1 & 3");
			int max = Math.max(arr1.get(i), arr3.get(k));
			retList.add(max);
			if (max==arr1.get(i))
				i++;
			else
				k++;			
		}
		
		while (j < n && k < o) {
			//System.out.println("1st list exhausted to break first loop, going with 2 & 3 : Max of "+ arr2.get(j) +" " + arr3.get(k));
			int max = Math.max(arr2.get(j), arr3.get(k));
			retList.add(max);
			if (max==arr2.get(j))
				j++;
			else
				k++;
		}
		
		//System.out.println("RetList Building up " + retList + "i = "+ i+ " j = " + j + " k = " + k);
		
		// Now 2 lists are exhausted and one remaining to be appended in, one of below will execute now
		
	
		if (i!=m) {
			while(i!=m) {
				retList.add(arr1.get(i++));
			}
		} else if (j !=n) {
			while(j!=n) {
				retList.add(arr2.get(j++));
			}
			
		} else if (k!=o) {
			while(k!=o) {
				retList.add(arr3.get(k++));
			}	
		}
		
		//System.out.println("Final RetList Built " + retList + " Reached -> i = "+ i+ " j = " + j + " k = " + k);
		
		return retList;

	}

	
	protected List<Integer> FindDenomination(int amount, ArrayList<Integer> box1,ArrayList<Integer> box2, ArrayList<Integer> box3) {
		
		
		ArrayList<Integer> combinedList = mergeCombinations(box1,box2,box3);

		
		ArrayList<Integer> denominationToDispense = new ArrayList<Integer>();

		for (int i = 0; i < combinedList.size(); i++) {
			//System.out.println("AmountTODO " + amount + " combinedList.get(i) "+ combinedList.get(i));
			if (amount == 1) {
				denominationToDispense.add(amount);
				break;
			}else if (amount == combinedList.get(i)) {
				denominationToDispense.add(amount);
				break;
			} else if (amount > combinedList.get(i)) {
				int count = amount / combinedList.get(i);
				for (int x = 0; x < count; x++) {
					denominationToDispense.add(combinedList.get(i));
				}
			} 
			amount = amount % combinedList.get(i);
			//System.out.println("Amount remaining to be dispensed :" + amount);
		}
		System.out.println("Denominations are: " + denominationToDispense);
		
		return denominationToDispense;
		
	}
}
