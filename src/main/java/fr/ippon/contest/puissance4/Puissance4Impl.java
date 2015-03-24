package fr.ippon.contest.puissance4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puissance4Impl implements Puissance4 {

	private String joueurCourant;
	private Map<Integer,Piece> LigneEnCours = new HashMap<Integer,Piece>();

	@Override
	public void nouveauJeu() {
		joueurCourant = (Math.random() % 2 == 1 ? Puissance4.ROUGE
				: Puissance4.JAUNE);
		LigneEnCours = new HashMap<Integer,Piece>();

	}

	@Override
	public void chargerJeu(char[][] grille, char tour) {
		
		int ligneDansGrille = grille.length ;
		int colonneDansGrille = grille[0].length;
		
		if (isNotTailleCorrecte(ligneDansGrille, colonneDansGrille)) {
			throw new IllegalArgumentException("grille invalide" );
		}
		
		if(isNotCorrectJouer(tour)){
			throw new IllegalArgumentException("Joueur invalide");
		}
		
		for (int ligne = 0; ligne < grille.length; ligne ++) {
			for (int colonne = 0; colonne < grille.length; colonne++) {
				if (grille[ligne][colonne] == Puissance4.CASE_VIDE)
					continue; 
				
				Piece piece = new Piece(colonne , ligne  , grille[ligne][colonne]) ;
				System.out.println(piece + " : " + LigneEnCours.hashCode());
				LigneEnCours.put(piece.hashCode(), piece);
			}
		}
		System.out.println(LigneEnCours);
		
	}

	private boolean isNotCorrectJouer(char tour) {
		return tour != Puissance4.JAUNE.charAt(0) && tour != Puissance4.ROUGE.charAt(0);
	}

	private boolean isNotTailleCorrecte(int ligneDansGrille, int colonneDansGrille) {
		return ligneDansGrille != Puissance4.MAX_LIGNE 
				|| colonneDansGrille != Puissance4.MAX_COLONNE 
				|| colonneDansGrille == 0 
				|| ligneDansGrille == 0;
	}

	@Override
	public EtatJeu getEtatJeu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char getTour() {
		return this.joueurCourant.toCharArray()[0];
	}

	

	@Override
	public char getOccupant(int ligne, int colonne) {
		return this.LigneEnCours.get(ligne * 10 + colonne).getJoueur();
	}

	@Override
	public void jouer(int colonne) {
		// TODO Auto-generated method stub

	}

	private boolean isColonneValide(int colonne) {
		return (colonne < Puissance4.MAX_COLONNE && colonne > Puissance4.MIN_COLONNE);
	}

}
