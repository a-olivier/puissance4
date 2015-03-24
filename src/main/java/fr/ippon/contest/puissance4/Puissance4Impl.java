package fr.ippon.contest.puissance4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Puissance4Impl implements Puissance4 {

	private String joueurCourant;
	private Map<Integer, Piece> LigneEnCours = new HashMap<Integer, Piece>();

	@Override
	public void nouveauJeu() {
		joueurCourant = (Math.random() % 2 == 1 ? Puissance4.ROUGE
				: Puissance4.JAUNE);
		LigneEnCours = new HashMap<Integer, Piece>();

	}

	@Override
	public void chargerJeu(char[][] grille, char tour) {

		int ligneDansGrille = grille.length;
		int colonneDansGrille = grille[0].length;

		if (isNotTailleCorrecte(ligneDansGrille, colonneDansGrille)) {
			throw new IllegalArgumentException("grille invalide");
		}

		if (isNotCorrectJouer(tour)) {
			throw new IllegalArgumentException("Joueur invalide");
		}

		for (int ligne = 0; ligne < grille.length; ligne++) {
			for (int colonne = 0; colonne < grille.length; colonne++) {
				if (grille[ligne][colonne] == Puissance4.CASE_VIDE)
					continue;

				Piece piece = new Piece(colonne, ligne, grille[ligne][colonne]);
				System.out.println(piece + " :: ");
				LigneEnCours.put(piece.hashCode(), piece);

				List<Integer> casesAdjacentes = majLignesPourPieceJouees(piece);

			}
		}
		System.out.println(LigneEnCours);

	}

	private List<Integer> majLignesPourPieceJouees(Piece piece) {
		List<Integer> casesAdjacentes = piece.getCoordonneesAdjacents();
		casesAdjacentes
				.stream()
				.filter(coordonne -> this.LigneEnCours
						.containsKey(coordonne)
						&& this.LigneEnCours.get(coordonne).getJoueur() == piece
								.getJoueur())
				.forEach(
						caseAdjacente -> {
							Piece pieceAdj = this.LigneEnCours
									.get(caseAdjacente);
							piece.ajouter(pieceAdj.isAdjacent(piece),
									pieceAdj);
							pieceAdj.ajouter(
									piece.isAdjacent(pieceAdj), piece);
						});
		return casesAdjacentes;
	}

	private boolean isNotCorrectJouer(char tour) {
		return tour != Puissance4.JAUNE.charAt(0)
				&& tour != Puissance4.ROUGE.charAt(0);
	}

	private boolean isNotTailleCorrecte(int ligneDansGrille,
			int colonneDansGrille) {
		return ligneDansGrille != Puissance4.MAX_LIGNE
				|| colonneDansGrille != Puissance4.MAX_COLONNE
				|| colonneDansGrille == 0 || ligneDansGrille == 0;
	}

	@Override
	public EtatJeu getEtatJeu() {
	    int nbLignesRouge = 0;
		int nbLignesJaune = 0; 
		
		for (Iterator it_ligneEnCours = LigneEnCours.entrySet().iterator(); it_ligneEnCours.hasNext();) {
			Entry<Integer, Piece> x = (Entry<Integer, Piece>) it_ligneEnCours.next();
			
	
			Map<Integer, Integer> longueursLignesAvecId = x.getValue().getLongueursLignesAvecId();
			Set<Entry<Integer, Integer>> entrySet = longueursLignesAvecId.entrySet();
			
			for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
				Entry<Integer, Integer> entry = (Entry<Integer, Integer>) iterator
						.next();
				if(entry.getValue() >= 4){
					if(x.getValue().getJoueur() == Puissance4.ROUGE.charAt(0)){
						nbLignesRouge ++;
					}else if (x.getValue().getJoueur() == Puissance4.JAUNE.charAt(0)){
						nbLignesJaune ++;
					}
				}
			}
			
		};
		
		if(nbLignesJaune > 0 ){
			if (nbLignesRouge > 0){
					return EtatJeu.MATCH_NUL;
			}else{
				return EtatJeu.JAUNE_GAGNE;
			}
		}else {
			if(nbLignesRouge > 0)
				return EtatJeu.ROUGE_GAGNE;
		}
		
		if(nbLignesJaune == nbLignesRouge && nbLignesRouge == 0)
			return EtatJeu.EN_COURS;
		
		return null;
	}

	@Override
	public char getTour() {
		return this.joueurCourant.charAt(0);
	}

	@Override
	public char getOccupant(int ligne, int colonne) {
		return this.LigneEnCours.get(ligne * 10 + colonne).getJoueur();
	}

	@Override
	public void jouer(int colonne) {
		int ligne = 0;
		boolean pasDeCaseLibre = true;
		while (pasDeCaseLibre) {
			
			if(ligne > Puissance4.MAX_COLONNE)
				throw new IllegalArgumentException("plus de place dans la colonne " + colonne);
			
			if (! LigneEnCours.containsKey(colonne * 10 + ligne)){
				Piece piece = new Piece(colonne, ligne, getTour());
				LigneEnCours.put(colonne * 10 + ligne, piece);
				majLignesPourPieceJouees(piece);
				pasDeCaseLibre = false; 
			}else{
				ligne ++;
			}
			
		
		}

	}

	private boolean isColonneValide(int colonne) {
		return (colonne < Puissance4.MAX_COLONNE && colonne > Puissance4.MIN_COLONNE);
	}

}
