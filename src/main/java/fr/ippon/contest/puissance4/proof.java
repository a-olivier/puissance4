package fr.ippon.contest.puissance4;

import java.util.Arrays;
import java.util.List;

public class proof {

	public static void main(String[] args) {
		char [][] grille = {{'-', '-', '-', '-', '-', '-'},
	 			{'-', '-', '-', '-', '-', '-'},
	 			{'-', '-', 'J', '-', '-', '-'},
	 			{'-', '-', 'R', 'J', 'R', '-'},
	 			{'-', 'J', 'R', 'R', 'J', '-'}};
		System.out.println(grille.length);
		List<char[]> asList = Arrays.asList(grille);
		
		System.out.println(asList.get(0));
		
	System.out.println(""+ -1 + -3);
		

	}

}
